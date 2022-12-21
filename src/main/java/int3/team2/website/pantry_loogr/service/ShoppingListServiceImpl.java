package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.ShoppingList;
import int3.team2.website.pantry_loogr.domain.Tag;
import int3.team2.website.pantry_loogr.repository.ShoppingListRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if(shoppingList == null) {
            shoppingList = shoppingListRepository.add(userId, new ShoppingList());
        }
        shoppingList.setIngredients(ingredientService.getForShoppingList(shoppingList.getId()));
        return shoppingList;
    }


    @Override
    public ShoppingList removeIngredientByAmount(int shoppingListId, int ingredientId, int amount) {
        return shoppingListRepository.removeIngredientByAmount(shoppingListId, ingredientId, amount);
    }

    @Override
    public ShoppingList removeIngredient(int shoppingListId, int ingredientId) {
        return shoppingListRepository.removeIngredient(shoppingListId, ingredientId);
    }


    @Override
    public ShoppingList add(int userId, ShoppingList shoppingList) {
        return shoppingListRepository.add(userId, shoppingList);
    }


}
