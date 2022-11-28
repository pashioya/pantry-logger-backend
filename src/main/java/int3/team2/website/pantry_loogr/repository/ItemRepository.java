package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> getAll();
    Item get(int id);
    Item getbyCode(String code);
    List<Item> getByPantryZoneId(int pantryZoneId);

    void addToPantry(int itemId, int pantryZoneId);
}
