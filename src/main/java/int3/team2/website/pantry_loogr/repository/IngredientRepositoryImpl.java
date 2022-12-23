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

import java.sql.Date;
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
    private SimpleJdbcInsert productInserter;
    private SimpleJdbcInsert ingredientRecipeInserter;
    private SimpleJdbcInsert shoppingListInserter;
    private SimpleJdbcInsert pantryZoneProductInserter;

    public IngredientRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.jdbcTemplate = jdbcTemplate;
        this.ingredientInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("INGREDIENTS")
                .usingGeneratedKeyColumns("id");
        this.productInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");
        this.ingredientRecipeInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("recipe_ingredients")
                .usingColumns("recipe_id", "ingredient_id", "quantity", "optional");
        this.shoppingListInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("shopping_list_ingredients")
                .usingColumns("shopping_list_id", "ingredient_id", "amount");
        this.pantryZoneProductInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("pantry_zone_products")
                .usingColumns("product_id", "pantry_zone_id", "quantity", "amount_used", "date_entered");
    }
    private Ingredient mapIngredientRow(ResultSet rs, int rowid) throws SQLException {
        return new Ingredient(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("image_path")
        );
    }

    private Product mapProductRow(ResultSet rs, int rowid) throws SQLException {
        return new Product(
                rs.getInt("INGREDIENT_ID"),
                rs.getInt("PRODUCT_ID"),
                rs.getString("INGREDIENT_NAME"),
                rs.getString("PRODUCT_NAME"),
                rs.getString("CODE"),
                rs.getInt("SIZE"),
                rs.getString("IMAGE_PATH")
        );
    }


    private PantryZoneProduct mapPantryZoneProductRow(ResultSet rs, int rowid) throws SQLException {
        return new PantryZoneProduct(
                rs.getInt("ingredient_id"),
                rs.getInt("product_id"),
                rs.getString("ingredient_name"),
                rs.getString("product_name"),
                rs.getString("code"),
                rs.getInt("size"),
                rs.getInt("quantity"),
                rs.getInt("amount_used"),
                new PantryZone(
                        rs.getInt("pantry_id"),
                        rs.getString("pantry_name")
                ),
                rs.getDate("date_entered").toLocalDate(),
                rs.getString("image_path")
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
                            "PRODUCTS.ID AS PRODUCT_ID, PRODUCTS.PRODUCT_NAME, PRODUCTS.CODE, PRODUCTS.SIZE, " +
                            "INGREDIENTS.ID AS INGREDIENT_ID, INGREDIENTS.NAME AS INGREDIENT_NAME, INGREDIENTS.IMAGE_PATH " +
                        "FROM PRODUCTS " +
                        "JOIN INGREDIENTS ON PRODUCTS.INGREDIENT_ID = INGREDIENTS.ID " +
                        "WHERE PRODUCTS.ID = ?",
                this::mapProductRow,
                id
        ).get(0);
    }

    @Override
    public Product addProduct(Product product) {
        logger.debug(product.toString());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ingredient_id", product.getId());
        parameters.put("product_name", product.getProductName());
        parameters.put("code", product.getCode());
        parameters.put("size", product.getSize());
        product.setProductId(productInserter.executeAndReturnKey(parameters).intValue());
        return product;
    }

    @Override
    public Product getByCode(String code) {
        List<Product> list = jdbcTemplate.query(
                "SELECT " +
                            "INGREDIENTS.ID AS INGREDIENT_ID, INGREDIENTS.NAME AS INGREDIENT_NAME, INGREDIENTS.IMAGE_PATH, " +
                            "PRODUCTS.ID AS PRODUCT_ID, PRODUCTS.PRODUCT_NAME, PRODUCTS.CODE, PRODUCTS.SIZE " +
                        "FROM PRODUCTS " +
                            "JOIN INGREDIENTS ON INGREDIENTS.ID = PRODUCTS.INGREDIENT_ID " +
                        "WHERE PRODUCTS.CODE = ?",
                this::mapProductRow,
                code
        );
        return list.isEmpty() ? null : list.get(0);
    }

    //TODO make so that it combines products
    @Override
    public void addToPantry(PantryZoneProduct product) {
        logger.debug(product.getDateEntered().toString());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("PRODUCT_ID", product.getProductId());
        parameters.put("PANTRY_ZONE_ID", product.getPantryZone().getId());
        parameters.put("quantity", product.getQuantity());
        parameters.put("amount_used", product.getAmountUsed());
        parameters.put("date_entered", Date.valueOf(product.getDateEntered()));
        pantryZoneProductInserter.execute(parameters);
    }
    @Override
    public PantryZoneProduct getPantryZoneProduct(int productId, int pantryId) {
        List<PantryZoneProduct> list = jdbcTemplate.query(
                "SELECT " +
                            "ingredients.id AS ingredient_id, ingredients.name AS ingredient_name, ingredients.image_path, " +
                            "PRODUCTS.PRODUCT_NAME, PANTRY_ZONE_PRODUCTS.PRODUCT_ID, PRODUCTS.CODE, PRODUCTS.SIZE," +
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
                this::mapPantryZoneProductRow,
                productId,
                pantryId
        );
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PantryZoneProduct> getByPantryZoneId(int pantryZoneId) {
        String sql = "SELECT " +
                "INGREDIENTS.ID AS INGREDIENT_ID, PANTRY_ZONE_PRODUCTS.PRODUCT_ID AS PRODUCT_ID, INGREDIENTS.NAME AS INGREDIENT_NAME, " +
                "PRODUCTS.PRODUCT_NAME, PRODUCTS.CODE, PRODUCTS.SIZE, PANTRY_ZONE_PRODUCTS.QUANTITY, PANTRY_ZONE_PRODUCTS.AMOUNT_USED, " +
                "INGREDIENTS.IMAGE_PATH, PANTRY_ZONE_PRODUCTS.DATE_ENTERED, " +
                "PANTRY_ZONES.ID AS PANTRY_ID, PANTRY_ZONES.NAME AS PANTRY_NAME " +
                "FROM PANTRY_ZONES " +
                "JOIN PANTRY_ZONE_PRODUCTS ON PANTRY_ZONES.ID = PANTRY_ZONE_PRODUCTS.PANTRY_ZONE_ID " +
                "JOIN PRODUCTS ON PANTRY_ZONE_PRODUCTS.PRODUCT_ID = PRODUCTS.ID " +
                "JOIN INGREDIENTS ON INGREDIENTS.ID = PRODUCTS.INGREDIENT_ID " +
                "WHERE PANTRY_ZONES.ID = ? ";
        return jdbcTemplate.query(
                sql,
                this::mapPantryZoneProductRow,
                pantryZoneId
        );
    }
    @Override
    public List<PantryZoneProduct> getProductsAndPantryZonesByUser(int userId) {
        return jdbcTemplate.query(
                "SELECT " +
                            "INGREDIENTS.ID AS INGREDIENT_ID, INGREDIENTS.NAME AS INGREDIENT_NAME, INGREDIENTS.IMAGE_PATH," +
                            "PRODUCTS.PRODUCT_NAME, PANTRY_ZONE_PRODUCTS.PRODUCT_ID, PRODUCTS.CODE, PRODUCTS.SIZE," +
                            "PANTRY_ZONE_PRODUCTS.QUANTITY, PANTRY_ZONE_PRODUCTS.AMOUNT_USED, PANTRY_ZONE_PRODUCTS.DATE_ENTERED," +
                            "PANTRY_ZONES.ID AS PANTRY_ID, PANTRY_ZONES.NAME AS PANTRY_NAME " +
                        "FROM PANTRY_ZONES " +
                            "JOIN PANTRY_ZONE_PRODUCTS ON PANTRY_ZONES.ID = PANTRY_ZONE_PRODUCTS.PANTRY_ZONE_ID " +
                            "JOIN PRODUCTS ON PANTRY_ZONE_PRODUCTS.PRODUCT_ID = PRODUCTS.ID " +
                            "JOIN INGREDIENTS ON INGREDIENTS.ID = PRODUCTS.INGREDIENT_ID " +
                        "WHERE USER_ID = ? ",
                this::mapPantryZoneProductRow,
                userId
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
                product.getQuantity(), product.getAmountUsed(), product.getDateEntered(), product.getProductId(), product.getPantryZone().getId()
        );
    }

    @Override
    public void removePantryZoneProduct(PantryZoneProduct product) {
        jdbcTemplate.update(
                "DELETE FROM PANTRY_ZONE_PRODUCTS WHERE PRODUCT_ID = ? AND PANTRY_ZONE_ID = ?;",
                product.getProductId(), product.getPantryZone().getId()
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




