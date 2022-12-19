package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;

@Repository
public class IngredientRepositoryImpl implements IngredientRepository {
    private Logger logger;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert ingredientInserter;
    private SimpleJdbcInsert ingredientRecipeInserter;
    private SimpleJdbcInsert shoppingListInserter;
    private SimpleJdbcInsert pantryZoneProductInserter;

    public IngredientRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.jdbcTemplate = jdbcTemplate;
        this.ingredientInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("INGREDIENTS")
                .usingGeneratedKeyColumns("ID");
        this.ingredientRecipeInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RECIPE_INGREDIENTS")
                .usingColumns("RECIPE_ID", "INGREDIENT_ID", "QUANTITY", "OPTIONAL");
        this.shoppingListInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("SHOPPING_LIST_INGREDIENTS")
                .usingColumns("SHOPPING_LIST_ID", "INGREDIENT_ID", "AMOUNT");
        this.pantryZoneProductInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("PANTRY_ZONE_PRODUCTS")
                .usingColumns("PRODUCT_ID", "PANTRY_ZONE_ID");
    }
    private Ingredient mapIngredientRow(ResultSet rs, int rowid) throws SQLException {
        return new Ingredient(
                rs.getInt("ID"),
                rs.getString("NAME"),
                rs.getString("IMAGE_PATH")
        );
    }

    private Product mapProductRow(ResultSet rs, int rowid) throws SQLException {
        return new Product(
                rs.getInt("INGREDIENT_ID"),
                rs.getInt("ID"),
                rs.getString("NAME"),
                rs.getString("PRODUCT_NAME"),
                rs.getString("CODE"),
                rs.getInt("SIZE"),
                rs.getString("IMAGE_PATH")
        );
    }

    private PantryZoneProduct mapPantryZoneProductRow(ResultSet rs, int rowid) throws SQLException {
        return new PantryZoneProduct(
                rs.getInt("ID"),
                rs.getString("NAME"),
                rs.getString("PRODUCT_NAME"),
                rs.getString("CODE"),
                rs.getInt("SIZE"),
                rs.getInt("QUANTITY"),
                rs.getInt("AMOUNT_USED"),
                new PantryZone(
                        rs.getInt("PANTRY_ID"),
                        rs.getString("PANTRY_NAME")
                ),
                rs.getDate("DATE_ENTERED").toLocalDate(),
                rs.getString("IMAGE_PATH")
        );
    }


    @Override
    public Ingredient get(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM INGREDIENTS where id = ?",
                preparedStatement -> preparedStatement.setInt(1, id),
                this::mapIngredientRow).get(0);
    }
    @Override
    public List<Ingredient> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM INGREDIENTS", this::mapIngredientRow
        );
    }
    @Override
    public List<Ingredient> findByName(String name) {
        return jdbcTemplate.query(
                "SELECT * FROM INGREDIENTS WHERE position(LOWER(?) in LOWER(NAME)) > 0", new Object[] {name}, this::mapIngredientRow
        );
    }
    @Override
    public List<Ingredient> findIngredientsByUser(int userID) {
        String sql = "SELECT " +
                "          INGREDIENTS.*" +
                "       FROM " +
                "           PANTRY_ZONES "+
                "       JOIN " +
                "           PANTRY_ZONE_PRODUCTS " +
                "               ON " +
                "           PANTRY_ZONES.ID = PANTRY_ZONE_PRODUCTS.PANTRY_ZONE_ID " +
                "       JOIN " +
                "           PRODUCTS " +
                "               ON " +
                "           PANTRY_ZONE_PRODUCTS.PRODUCT_ID = PRODUCTS.ID " +
                "       JOIN " +
                "           INGREDIENTS " +
                "               ON " +
                "           INGREDIENTS.ID = PRODUCTS.INGREDIENT_ID " +
                "       WHERE " +
                "           PANTRY_ZONES.USER_ID = ?";
        return jdbcTemplate.query(sql, preparedStatement -> preparedStatement.setInt(1, userID), this::mapIngredientRow);
    }
    @Override
    public Map<Ingredient, Integer> findIngredientsByRecipeId(int id) {
        List<Ingredient> ingredients = jdbcTemplate.query(
                "SELECT * " +
                        "FROM RECIPE_INGREDIENTS " +
                        "JOIN INGREDIENTS ON INGREDIENTS.ID = RECIPE_INGREDIENTS.INGREDIENT_ID " +
                        "WHERE RECIPE_INGREDIENTS.RECIPE_ID = ?",
                this::mapIngredientRow, id);
        List<Integer> amounts = jdbcTemplate.query(
                "SELECT QUANTITY " +
                        "FROM RECIPE_INGREDIENTS " +
                        "JOIN INGREDIENTS ON INGREDIENTS.ID = RECIPE_INGREDIENTS.INGREDIENT_ID " +
                        "WHERE RECIPE_INGREDIENTS.RECIPE_ID = ?",
                (rs, rowNum) -> rs.getInt("QUANTITY"), id);
        return IntStream.range(0, ingredients.size()).boxed().collect(Collectors.toMap(ingredients::get, amounts::get));
    }
    @Override
    public Map<Ingredient, Integer> addToRelationTable(int recipeID, Map<Ingredient, Integer> ingredients) {
        for (Ingredient i: ingredients.keySet()) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("RECIPE_ID", recipeID);
            parameters.put("INGREDIENT_ID", i.getId());
            parameters.put("QUANTITY", ingredients.get(i));
            parameters.put("OPTIONAL", "FALSE");
            ingredientRecipeInserter.execute(parameters);
        }
        return ingredients;
    }

    @Override
    public Product getProduct(int id) {
        return jdbcTemplate.query(
                "SELECT " +
                            "PRODUCTS.ID, PRODUCTS.PRODUCT_NAME, PRODUCTS.CODE, PRODUCTS.SIZE, " +
                            "INGREDIENTS.ID AS INGREDIENT_ID, INGREDIENTS.NAME, INGREDIENTS.IMAGE_PATH " +
                        "FROM PRODUCTS " +
                        "JOIN INGREDIENTS ON PRODUCTS.INGREDIENT_ID = INGREDIENTS.ID " +
                        "WHERE PRODUCTS.ID = ?",
                preparedStatement -> preparedStatement.setInt(1, id),
                this::mapProductRow).get(0);
    }

    @Override
    public Product addProduct(Product product) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "INSERT INTO PRODUCTS(INGREDIENT_ID, PRODUCT_NAME, CODE, SIZE) " +
                        "VALUES (?, ?, ?, ?)",
                INTEGER, VARCHAR, VARCHAR, INTEGER
        );
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                        product.getId(),
                        product.getProductName(),
                        product.getCode(),
                        product.getSize()
                )
        );
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return this.getProduct(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public Product getByCode(String code) {
        List<Product> list = jdbcTemplate.query("SELECT PRODUCTS.*,INGREDIENTS.NAME as INGREDIENT_NAME FROM PRODUCTS JOIN INGREDIENTS ON INGREDIENTS.ID = PRODUCTS.INGREDIENT_ID WHERE PRODUCTS.CODE = ?",
                preparedStatement -> preparedStatement.setString(1, code),
                this::mapProductRow);
        return list.isEmpty() ? null : list.get(0);
    }
    @Override
    public void addToPantry(int productId, int zone, int quantity, int amountUsed) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("PRODUCT_ID", productId);
        parameters.put("PANTRY_ZONE_ID", zone);
        parameters.put("QUANTITY", quantity);
        parameters.put("AMOUNT_USED", amountUsed);
        pantryZoneProductInserter.execute(parameters);
    }
    @Override
    public PantryZoneProduct getPantryZoneProduct(int productId, int pantryId) {
        return jdbcTemplate.query(
                "SELECT " +
                            "INGREDIENTS.ID, INGREDIENTS.NAME, INGREDIENTS.IMAGE_PATH, " +
                            "PRODUCTS.PRODUCT_NAME, PRODUCTS.CODE, PRODUCTS.SIZE," +
                            "PANTRY_ZONE_PRODUCTS.QUANTITY, PANTRY_ZONE_PRODUCTS.AMOUNT_USED, PANTRY_ZONE_PRODUCTS.DATE_ENTERED," +
                            "PANTRY_ZONES.ID AS PANTRY_ID, PANTRY_ZONES.NAME AS PANTRY_NAME " +
                        "FROM PANTRY_ZONE_PRODUCTS " +
                        "JOIN PRODUCTS " +
                            "ON PRODUCTS.ID = PANTRY_ZONE_PRODUCTS.PRODUCT_ID " +
                        "JOIN INGREDIENTS " +
                            "ON PRODUCTS.INGREDIENT_ID = INGREDIENTS.ID " +
                        "JOIN PANTRY_ZONES " +
                            "ON PANTRY_ZONE_PRODUCTS.PANTRY_ZONE_ID = PANTRY_ZONES.ID " +
                        "WHERE PRODUCT_ID = ? AND PANTRY_ZONE_PRODUCTS.PANTRY_ZONE_ID = ?",
                preparedStatement -> {
                    preparedStatement.setInt(1, productId);
                    preparedStatement.setInt(2, pantryId);
                },
                this::mapPantryZoneProductRow).get(0);
    }
    @Override
    public List<PantryZoneProduct> getByPantryZoneId(int pantryZoneId) {
        return jdbcTemplate.query(
                "SELECT " +
                            "INGREDIENTS.ID, INGREDIENTS.NAME, INGREDIENTS.IMAGE_PATH," +
                            "PRODUCTS.PRODUCT_NAME, PRODUCTS.CODE, PRODUCTS.SIZE," +
                            "PANTRY_ZONE_PRODUCTS.QUANTITY, PANTRY_ZONE_PRODUCTS.AMOUNT_USED, PANTRY_ZONE_PRODUCTS.DATE_ENTERED," +
                            "PANTRY_ZONES.ID AS PANTRY_ID, PANTRY_ZONES.NAME AS PANTRY_NAME " +
                        "FROM PANTRY_ZONES " +
                            "JOIN PANTRY_ZONE_PRODUCTS ON PANTRY_ZONES.ID = PANTRY_ZONE_PRODUCTS.PANTRY_ZONE_ID " +
                            "JOIN PRODUCTS ON PANTRY_ZONE_PRODUCTS.PRODUCT_ID = PRODUCTS.ID " +
                            "JOIN INGREDIENTS ON INGREDIENTS.ID = PRODUCTS.INGREDIENT_ID " +
                        "WHERE PANTRY_ZONES.ID = ? ",
                preparedStatement -> preparedStatement.setInt(1, pantryZoneId),
                this::mapPantryZoneProductRow
        );
    }
    @Override
    public List<PantryZoneProduct> getProductsAndPantryZonesByUser(int userId) {
        return jdbcTemplate.query(
                "SELECT " +
                            "INGREDIENTS.ID, INGREDIENTS.NAME, INGREDIENTS.IMAGE_PATH," +
                            "PRODUCTS.PRODUCT_NAME, PRODUCTS.CODE, PRODUCTS.SIZE," +
                            "PANTRY_ZONE_PRODUCTS.QUANTITY, PANTRY_ZONE_PRODUCTS.AMOUNT_USED, PANTRY_ZONE_PRODUCTS.DATE_ENTERED," +
                            "PANTRY_ZONES.ID AS PANTRY_ID, PANTRY_ZONES.NAME AS PANTRY_NAME " +
                        "FROM PANTRY_ZONES " +
                            "JOIN PANTRY_ZONE_PRODUCTS ON PANTRY_ZONES.ID = PANTRY_ZONE_PRODUCTS.PANTRY_ZONE_ID " +
                            "JOIN PRODUCTS ON PANTRY_ZONE_PRODUCTS.PRODUCT_ID = PRODUCTS.ID " +
                            "JOIN INGREDIENTS ON INGREDIENTS.ID = PRODUCTS.INGREDIENT_ID " +
                        "WHERE USER_ID = ? ",
                preparedStatement -> preparedStatement.setInt(1, userId),
                this::mapPantryZoneProductRow
        );
    }
    @Override
    public void updatePantryZoneProduct(PantryZoneProduct product) {
        jdbcTemplate.update(
                "UPDATE PANTRY_ZONE_PRODUCTS SET " +
                            "QUANTITY = ?, " +
                            "AMOUNT_USED = ? ," +
                            "DATE_ENTERED = ? " +
                        "WHERE PRODUCT_ID = ? AND PANTRY_ZONE_ID = ?;",
                product.getQuantity(), product.getAmountUsed(), product.getDateEntered(), product.getId(), product.getPantryZone().getId()
        );
    }

    @Override
    public void removePantryZoneProduct(PantryZoneProduct product) {
        jdbcTemplate.update(
                "DELETE FROM PANTRY_ZONE_PRODUCTS WHERE PRODUCT_ID = ? AND PANTRY_ZONE_ID = ?;",
                product.getId(), product.getPantryZone().getId()
        );
    }

    @Override
    public  Map<Ingredient, Integer> getForShoppingList(int shoppingListId) {
        String sql = "SELECT " +
                "          * " +
                "       FROM " +
                "           SHOPPING_LIST_INGREDIENTS" +
                "       JOIN " +
                "           INGREDIENTS " +
                "               ON " +
                "           INGREDIENTS.ID = SHOPPING_LIST_INGREDIENTS.INGREDIENT_ID " +
                "       WHERE " +
                "           SHOPPING_LIST_INGREDIENTS.SHOPPING_LIST_ID = ?";
        List<Ingredient> ingredients = jdbcTemplate.query(sql, this::mapIngredientRow, shoppingListId);
        List<Integer> amounts = jdbcTemplate.query("SELECT AMOUNT FROM SHOPPING_LIST_INGREDIENTS JOIN INGREDIENTS ON INGREDIENTS.ID = SHOPPING_LIST_INGREDIENTS.INGREDIENT_ID WHERE SHOPPING_LIST_INGREDIENTS.SHOPPING_LIST_ID = ?",
                (rs, rowNum) -> rs.getInt("AMOUNT"), shoppingListId);
        return IntStream.range(0, ingredients.size()).boxed().collect(Collectors.toMap(ingredients::get, amounts::get));
    }

    @Override
    public void clearShoppingListIngredients(int shopping_list_id) {
        jdbcTemplate.update("DELETE FROM SHOPPING_LIST_INGREDIENTS WHERE SHOPPING_LIST_ID = ?", shopping_list_id);
    }

    @Override
    public void addToShoppingListIngredients(int shoppingListId, Map<Ingredient, Integer> shoppingListIngredients) {
        for (Ingredient i: shoppingListIngredients.keySet()) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SHOPPING_LIST_ID", shoppingListId);
            parameters.put("INGREDIENT_ID", i.getId());
            parameters.put("AMOUNT", shoppingListIngredients.get(i));
            shoppingListInserter.execute(parameters);
        }
    }
}




