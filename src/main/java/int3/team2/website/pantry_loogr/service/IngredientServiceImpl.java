package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.repository.IngredientRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredientServiceImpl implements IngredientService {
    private IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> getAll() {
        return null;
    }

    @Override
    public Ingredient get(int ingredientID) {
        return null;
    }

    @Override
    public List<Ingredient> add(Ingredient ingredient) {
        return null;
    }
}
