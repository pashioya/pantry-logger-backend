package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.ShoppingList;
import int3.team2.website.pantry_loogr.repository.ShoppingListRepository;
import org.springframework.stereotype.Component;

@Component
public class ShoppingListServiceImpl implements ShoppingListService {

    private ShoppingListRepository shoppingListRepository;
    private IngredientService ingredientService;

    public ShoppingListServiceImpl(ShoppingListRepository shoppingListRepository, IngredientService ingredientService) {
        this.shoppingListRepository = shoppingListRepository;
        this.ingredientService = ingredientService;
    }

    @Override
    public ShoppingList get(int shoppingListId) {
        return shoppingListRepository.get(shoppingListId);
    }

    @Override
    public ShoppingList getByUser(int userId) {
        ShoppingList shoppingList = shoppingListRepository.getByUser(userId);
        shoppingList.setIngredients(ingredientService.getForShoppingList(shoppingList.getId()));
        return shoppingList;
    }

    @Override
    public ShoppingList removeIngredientByAmount(int shoppingListId, int ingredientId, int amount) {
        return shoppingListRepository.removeIngredientByAmount(shoppingListId, ingredientId, amount);
    }
}
