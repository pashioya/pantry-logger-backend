package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.ShoppingList;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Types.INTEGER;

@Repository
public class ShoppingListRepositoryImpl implements ShoppingListRepository{
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;
    private SimpleJdbcInsert ShoppingListIngredientsInserter;

    public ShoppingListRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("SHOPPING_LISTS")
                .usingGeneratedKeyColumns("id");
        this.ShoppingListIngredientsInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("SHOPPING_LISTS_INGREDIENTS");

    }

    private ShoppingList mapShoppingListRow(ResultSet rs, int rowid) throws SQLException {
        return new ShoppingList(rs.getInt("ID"));
    }

    @Override
    public ShoppingList get(int id) {
        String sql = "SELECT " +
                "          * " +
                "       FROM " +
                "           SHOPPING_LISTS" +
                "       WHERE " +
                "           SHOPPING_LISTS.ID = ?";
        List<ShoppingList> list = jdbcTemplate.query(sql, this::mapShoppingListRow, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public ShoppingList getByUser(int userId) {
        String sql = "SELECT " +
                "          * " +
                "       FROM " +
                "           SHOPPING_LISTS" +
                "       WHERE " +
                "           SHOPPING_LISTS.USER_ID = ?";
        List<ShoppingList> list = jdbcTemplate.query(sql, this::mapShoppingListRow, userId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public ShoppingList removeIngredientByAmount(int shoppingListId, int ingredientId, int amount) {
        String sql =
                "SELECT * FROM SHOPPING_LISTS " +
                "WHERE SHOPPING_LISTS.SHOPPING_LIST_ID = ?";
        List<ShoppingList> list = jdbcTemplate.query(sql, this::mapShoppingListRow, shoppingListId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public ShoppingList removeIngredient(int shoppingListId, int ingredientId) {
        jdbcTemplate.update(
                "DELETE FROM SHOPPING_LIST_INGREDIENTS WHERE INGREDIENT_ID = ? AND SHOPPING_LIST_ID = ?;",
                ingredientId, shoppingListId
        );
        return null;
    }

    @Override
    public void addIngredientByAmount(int shoppingListId, int ingredientId, int amount) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SHOPPING_LIST_ID", shoppingListId);
        parameters.put("INGREDIENT_ID", ingredientId);
        ShoppingListIngredientsInserter.executeAndReturnKey(parameters).intValue();
    }

    @Override
    public ShoppingList add(int userId, ShoppingList shoppingList) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USER_ID", userId);
        shoppingList.setId(inserter.executeAndReturnKey(parameters).intValue());
        return shoppingList;
    }
}
