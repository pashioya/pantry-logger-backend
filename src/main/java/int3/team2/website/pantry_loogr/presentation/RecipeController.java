package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.Difficulty;
import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.Time;
import int3.team2.website.pantry_loogr.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5432")
@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    RecipeRepository repository;

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipe(@RequestParam(required = false) String recipe_name) {
        try {
            List<Recipe> recipes = new ArrayList<>();

            if (recipe_name == null)
                recipes.addAll(repository.findAll());
            else
                repository.findByName(recipe_name).forEach(recipes::add);

            if (recipes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") long id) {
        Optional<Recipe> recipeData = repository.findById(id);

        if (recipeData.isPresent()) {
            return new ResponseEntity<>(recipeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        try {
            Recipe _recipe = repository
                    .save(new Recipe(recipe.getRecipe_name(), recipe.getRecipe_difficulty(), recipe.getRecipe_description(), recipe.getRecipe_instructions(), recipe.getRecipe_time()));
            return new ResponseEntity<>(_recipe, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/recipes/{id}")
    public ResponseEntity<Recipe> updateTutorial(@PathVariable("id") long id, @RequestBody Recipe recipe) {
        Optional<Recipe> recipeData = repository.findById(id);

        if (recipeData.isPresent()) {
            Recipe _recipe = recipeData.get();
            _recipe.setRecipe_name(recipe.getRecipe_name());
            _recipe.setRecipe_difficulty(recipe.getRecipe_difficulty());
            _recipe.setRecipe_description(recipe.getRecipe_description());
            _recipe.setRecipe_instructions(recipe.getRecipe_instructions());
            _recipe.setRecipe_time(recipe.getRecipe_time());
            return new ResponseEntity<>(repository.save(_recipe), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/recipes")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            repository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/recipes/name")
    public ResponseEntity<List<Recipe>> findByName(String name) {
        try {
            List<Recipe> recipes = repository.findByName(name);

            if (recipes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recipes/difficulty")
    public ResponseEntity<List<Recipe>> findByDifficulty(Difficulty difficulty) {
        try {
            List<Recipe> recipes = repository.findByDifficulty(difficulty);

            if (recipes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recipes/name")
    public ResponseEntity<List<Recipe>> findByTime(Time time) {
        try {
            List<Recipe> recipes = repository.findByTime(time);

            if (recipes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}