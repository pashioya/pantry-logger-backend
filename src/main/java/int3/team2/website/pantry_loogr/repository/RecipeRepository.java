package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Difficulty;
import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.Time;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByName(String recipe_name);
    List<Recipe> findByDifficulty(Difficulty recipe_difficulty);
    List<Recipe> findByTime(Time recipe_time);
}
