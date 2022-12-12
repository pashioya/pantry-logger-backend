package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.PantryZoneProduct;
import int3.team2.website.pantry_loogr.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.sql.Types.INTEGER;

@Repository
public class IngredientRepositoryImpl implements IngredientRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert ingredientInserter;
    private SimpleJdbcInsert ingredientRecipeInserter;
    private SimpleJdbcInsert shoppingListInserter;

    public IngredientRepositoryImpl(JdbcTemplate jdbcTemplate) {
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


    }

    private Ingredient mapRow(ResultSet rs, int rowid) throws SQLException {
        return new Ingredient(rs.getInt("ID"), rs.getString("NAME"));
    }

    private PantryZoneProduct mapPantryZoneProductRow(ResultSet rs, int rowid) throws SQLException {
        return new PantryZoneProduct(rs.getInt("ID"),
                                    rs.getString("NAME"),
                                    rs.getString("PRODUCT_NAME"),
                                    rs.getString("CODE"),
                                    rs.getInt("SIZE"),
                                    rs.getInt("QUANTITY"),
                                    rs.getInt("AMOUNT_USED"),
                                    rs.getString("PANTRY_ZONE_NAME")
        );
    }

    private Product mapProductRow(ResultSet rs, int rowid) throws SQLException {
        return new Product(rs.getInt("ID"),
                rs.getString("NAME"),
                rs.getString("PRODUCT_NAME"),
                rs.getString("CODE"),
                rs.getInt("SIZE"));
    }

    @Override
    public List<Ingredient> findAll() {
        return jdbcTemplate.query("SELECT * FROM INGREDIENTS", this::mapRow);
    }

    @Override
    public Ingredient get(int id) {
        return jdbcTemplate.query("SELECT * FROM INGREDIENTS where id = " + id, this::mapRow).get(0);
    }

    @Override
    public List<Ingredient> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM INGREDIENTS WHERE position(LOWER(?) in LOWER(NAME)) > 0", this::mapRow, name);
    }

    @Override
    public Map<Ingredient, Integer> findIngredientsByRecipeId(int id) {
        List<Ingredient> ingredients = jdbcTemplate.query("SELECT * FROM RECIPE_INGREDIENTS JOIN INGREDIENTS ON INGREDIENTS.ID = RECIPE_INGREDIENTS.INGREDIENT_ID WHERE RECIPE_INGREDIENTS.RECIPE_ID = " + id, this::mapRow);
        List<Integer> amounts = jdbcTemplate.query("SELECT QUANTITY FROM RECIPE_INGREDIENTS JOIN INGREDIENTS ON INGREDIENTS.ID = RECIPE_INGREDIENTS.INGREDIENT_ID WHERE RECIPE_INGREDIENTS.RECIPE_ID = " + id,
                (rs, rowNum) -> rs.getInt("quantity"));
        return IntStream.range(0, ingredients.size()).boxed().collect(Collectors.toMap(ingredients::get, amounts::get));
    }
    @Override
    public List<PantryZoneProduct> getByPantryZoneId(int pantryZoneId) {
        String sql = "SELECT " +
                "          INGREDIENTS.*, PANTRY_ZONE_PRODUCTS.*, PRODUCTS.*, PANTRY_ZONES.NAME AS PANTRY_ZONE_NAME " +
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
                "           PANTRY_ZONES.ID = ? ";
        return jdbcTemplate.query(sql, preparedStatement -> preparedStatement.setInt(1, pantryZoneId), this::mapPantryZoneProductRow);
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
        return jdbcTemplate.query(sql, preparedStatement -> preparedStatement.setInt(1, userID), this::mapRow);
    }

    @Override
    public List<PantryZoneProduct> getProductsAndPantryZonesByUser(int userId) {
        String sql = "SELECT " +
                "          INGREDIENTS.*, PRODUCTS.PRODUCT_NAME, PRODUCTS.SIZE, PRODUCTS.CODE, PANTRY_ZONES.NAME AS PANTRY_ZONE_NAME, PANTRY_ZONE_PRODUCTS.* " +
                "       FROM " +
                "            PANTRY_ZONES " +
                "        JOIN " +
                "            PANTRY_ZONE_PRODUCTS " +
                "                ON " +
                "            PANTRY_ZONES.ID = PANTRY_ZONE_PRODUCTS.PANTRY_ZONE_ID " +
                "        JOIN " +
                "            PRODUCTS " +
                "                ON " +
                "            PANTRY_ZONE_PRODUCTS.PRODUCT_ID = PRODUCTS.ID " +
                "       JOIN " +
                "           INGREDIENTS " +
                "               ON " +
                "           INGREDIENTS.ID = PRODUCTS.INGREDIENT_ID " +
                "       WHERE " +
                "            USER_ID = ? ";
        return jdbcTemplate.query(sql, preparedStatement -> preparedStatement.setInt(1, userId), this::mapPantryZoneProductRow);
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
    public Product getByCode(String code) {
        return jdbcTemplate.query("SELECT PRODUCTS.*,INGREDIENTS.NAME as INGREDIENT_NAME FROM PRODUCTS JOIN INGREDIENTS ON INGREDIENTS.ID = PRODUCTS.INGREDIENT_ID WHERE PRODUCTS.CODE = ?",
                preparedStatement -> preparedStatement.setString(1, code),
                this::mapProductRow).get(0);
    }

    @Override
    public void addToPantry(int productId, int zone) {

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
        List<Ingredient> ingredients = jdbcTemplate.query(sql, this::mapRow, shoppingListId);
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




