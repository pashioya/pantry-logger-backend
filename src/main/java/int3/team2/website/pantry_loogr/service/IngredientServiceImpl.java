package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.PantryZoneProduct;
import int3.team2.website.pantry_loogr.domain.Product;
import int3.team2.website.pantry_loogr.repository.IngredientRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IngredientServiceImpl implements IngredientService {
    private IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public Ingredient get(int ingredientID) {
        return ingredientRepository.get(ingredientID);
    }

    @Override
    public List<Ingredient> add(Ingredient ingredient) {
        return null;
    }

    @Override
    public List<Ingredient> getByName(String name) {
        return ingredientRepository.findByName(name);
    }

    @Override
    public Map<Ingredient, Integer> getIngredientsByRecipeId(int id) {
        return ingredientRepository.findIngredientsByRecipeId(id);
    }

    @Override
    public Map<Ingredient, Integer> addToRelationTable(int recipeID, Map<Ingredient, Integer> ingredients) {
        return ingredientRepository.addToRelationTable(recipeID, ingredients);
    }

    @Override
    public List<PantryZoneProduct> getByPantryZoneId(int pantryZoneId) {
        return ingredientRepository.getByPantryZoneId(pantryZoneId);
    }

    @Override
    public Product getByCode(String code) {
        return ingredientRepository.getByCode(code);
    }

    @Override
    public void addToPantry(int productId, int zone) {
        ingredientRepository.addToPantry(productId, zone);
    }

    @Override
    public  Map<Ingredient, Integer> getForShoppingList(int shoppingListId) {
        return ingredientRepository.getForShoppingList(shoppingListId);
    }

    @Override
    public List<PantryZoneProduct> getProductsAndPantryZonesByUser(int userId) {
        List<PantryZoneProduct> list = ingredientRepository.getProductsAndPantryZonesByUser(userId);
        list.forEach(x -> LoggerFactory.getLogger("lol").debug(x.getProductName()));
        return list;
    }

    @Override
    public List<Ingredient> getIngredientsByUser(int userID) {
        return ingredientRepository.findIngredientsByUser(userID);
    }

    @Override
    public void addShoppingListIngredients(int shoppingListId,  Map<Ingredient, Integer> shoppingListIngredients) {
        ingredientRepository.addToShoppingListIngredients(shoppingListId, shoppingListIngredients);
    }

    @Override
    public void clearShoppingListIngredients(int shopping_list_id) {
        ingredientRepository.clearShoppingListIngredients(shopping_list_id);
    }
}
