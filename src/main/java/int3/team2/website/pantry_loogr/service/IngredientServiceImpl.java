package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.PantryZoneProduct;
import int3.team2.website.pantry_loogr.domain.Product;
import int3.team2.website.pantry_loogr.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IngredientServiceImpl implements IngredientService {
    private Logger logger;
    private IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.logger = LoggerFactory.getLogger(this.getClass());
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

    /**
     * when the user uses a certain amount of a product, it gets deducted from PantryZoneProduct.amountUsed
     * @param pantryId used to retrieve the pantryZoneProduct from the DB
     * @param productId used to retrieve the pantryZoneProduct from the DB
     * @param percentage the percentage of the pack that the user has used
     */
    public void editPantryZoneProductAmountUsed(int pantryId, int productId, double percentage) {
        PantryZoneProduct product = ingredientRepository.getPantryZoneProduct(productId, pantryId);
        product.setAmountUsed((int) (product.getSize() * (1 - percentage)));

        //TODO add check for 0% to remove quantity
        ingredientRepository.updatePantryZoneProduct(product);
    }

    @Override
    public void editPantryZoneProductQuantity(int pantryId, int productId, int quantity) {
        PantryZoneProduct product = ingredientRepository.getPantryZoneProduct(productId, pantryId);
        if(product.setQuantity(quantity)) {
            ingredientRepository.updatePantryZoneProduct(product);
        } else {
            ingredientRepository.removePantryZoneProduct(product);
        }
    }

    @Override
    public void removePantryZoneProductQuantity(int pantryId, int productId, int quantityToRemove) {
        PantryZoneProduct product = ingredientRepository.getPantryZoneProduct(productId, pantryId);
        if(product.removeFromQuantity(quantityToRemove)) {
            ingredientRepository.updatePantryZoneProduct(product);
        } else {
            ingredientRepository.removePantryZoneProduct(product);
        }
    }

    @Override
    public  Map<Ingredient, Integer> getForShoppingList(int shoppingListId) {
        return ingredientRepository.getForShoppingList(shoppingListId);
    }

    @Override
    public List<PantryZoneProduct> getProductsAndPantryZonesByUser(int userId) {
        return ingredientRepository.getProductsAndPantryZonesByUser(userId);
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
