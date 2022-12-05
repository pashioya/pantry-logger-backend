package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.ShoppingList;

public interface ShoppingListService {
    ShoppingList get(int shoppingListId);
    ShoppingList getByUser(int userId);

    ShoppingList removeIngredientByAmount(int shoppingListId, int ingredientId, int amount);
}
