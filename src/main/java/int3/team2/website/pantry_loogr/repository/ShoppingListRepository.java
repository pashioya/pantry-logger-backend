package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.ShoppingList;

public interface ShoppingListRepository {
    ShoppingList get(int id);
    ShoppingList getByUser(int userId);
    ShoppingList removeIngredientByAmount(int shoppingListId, int ingredientId, int amount);
    ShoppingList removeIngredient(int shoppingListId, int ingredientId);
    ShoppingList add(int userId, ShoppingList shoppingList);
    void addIngredientByAmount(int shoppingListId, int ingredientId, int amount);
}
