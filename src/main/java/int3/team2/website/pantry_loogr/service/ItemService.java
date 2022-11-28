package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAll();
    Item get(int ingredientID);
    Item getByCode(String code);
    List<Item> getByPantryZoneId(int pantryZoneId);

    void addToPantry(int itemId, int pantryZoneId);
}
