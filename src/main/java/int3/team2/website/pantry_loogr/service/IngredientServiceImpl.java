package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.PantryZoneProduct;
import int3.team2.website.pantry_loogr.domain.Product;
import int3.team2.website.pantry_loogr.repository.IngredientRepository;
import int3.team2.website.pantry_loogr.repository.PantryZoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;

@Component
public class IngredientServiceImpl implements IngredientService {
    private Logger logger;
    private IngredientRepository ingredientRepository;
    private PantryZoneRepository pantryZoneRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, PantryZoneRepository pantryZoneRepository) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.ingredientRepository = ingredientRepository;
        this.pantryZoneRepository = pantryZoneRepository;
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
    public Product addProduct(Product product) {
        return ingredientRepository.addProduct(product);
    }

    @Override
    public Product getProduct(int productId) {
        return ingredientRepository.getProduct(productId);
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
    public void addToPantry(PantryZoneProduct product, int pantryId) {
        //gets the already present product in the pantry, is null if there is no product
        PantryZoneProduct alreadyPresentProduct = ingredientRepository.getPantryZoneProduct(product.getProductId(), pantryId);
        //sets the PantryZone object in the PantryZoneProducts object
        product.setPantryZone(pantryZoneRepository.get(pantryId));
        //if already present in the pantry then the objects are combined and updated.
        if (alreadyPresentProduct != null) {
            if (!alreadyPresentProduct.combine(product)) {
                logger.error("The two PantryZoneProducts that are being combined are not the same.");
            }
            ingredientRepository.updatePantryZoneProduct(alreadyPresentProduct);
        } else {
            //else the new product is added to the pantry.
            ingredientRepository.addToPantry(product);
        }
    }

    /**
     * when the user uses a certain amount of a product, it gets deducted from PantryZoneProduct.amountUsed
     * @param pantryId used to retrieve the pantryZoneProduct from the DB
     * @param productId used to retrieve the pantryZoneProduct from the DB
     * @param percentage the percentage of the pack that the user has used
     */
    public void editPantryZoneProductAmountUsed(int pantryId, int productId, double percentage) {
        PantryZoneProduct product = ingredientRepository.getPantryZoneProduct(productId, pantryId);
        if (percentage == 0) {
            if (product.getQuantity() == 1) {
                ingredientRepository.removePantryZoneProduct(product);
            } else {
                product.setQuantity(product.getQuantity() - 1);
                ingredientRepository.updatePantryZoneProduct(product);
            }
        } else {
            product.setAmountUsed((int) (product.getSize() * (1 - percentage)));
            ingredientRepository.updatePantryZoneProduct(product);
        }
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
        logger.debug(productId + " " + pantryId);
        PantryZoneProduct product = ingredientRepository.getPantryZoneProduct(productId, pantryId);
        if(product.removeFromQuantity(quantityToRemove)) {
            logger.debug("yes");
            ingredientRepository.updatePantryZoneProduct(product);
        } else {
            logger.debug("no");
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
    public List<Ingredient> getProductsEnteredAWeekAgo(int userId) {
        return ingredientRepository.getProductsEnteredAWeekAgo(userId);
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
