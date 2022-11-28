package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.Item;
import int3.team2.website.pantry_loogr.repository.IngredientRepository;
import int3.team2.website.pantry_loogr.repository.ItemRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemServiceImpl implements ItemService{
    private ItemRepository itemRepository;
    private IngredientService ingredientService;

    public ItemServiceImpl(ItemRepository itemRepository, IngredientService ingredientService) {
        this.itemRepository = itemRepository;
        this.ingredientService = ingredientService;
    }
    @Override
    public List<Item> getAll() {
        return itemRepository.getAll();
    }

    @Override
    public Item get(int ingredientID) {
        return itemRepository.get(ingredientID);
    }

    @Override
    public Item getByCode(String code) {
        Item item = itemRepository.getbyCode(code);
        item.setIngredient(ingredientService.get(item.getIngredientId()));
        return item;
    }

    @Override
    public List<Item> getByPantryZoneId(int pantryZoneId) {
        return itemRepository.getByPantryZoneId(pantryZoneId);
    }

    public void addToPantry(int itemId, int pantryZoneId) {
        itemRepository.addToPantry(itemId, pantryZoneId);
    }
}
