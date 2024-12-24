package io.github.unisim.achievements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Enum containing the constant definitions for achievements in the game.
 */
public enum DefinedAchievements {
    Bankruptcy("Bankruptcy", "Lose all your money", ScoreModifierTemplate.ADD, 25f,
        true),
    Capitalist("Capitalist", "Earn 1000 money", ScoreModifierTemplate.ADD, 100f,
        false);


    DefinedAchievements(String name, String description, ScoreModifierTemplate functionTemplate,
            float scoreModifierValue, boolean hidden) {
        this.name = name;
        this.description = description;
        this.functionTemplate = functionTemplate;
        this.scoreModifierValue = scoreModifierValue;
        this.hidden = hidden;
    }

    final String name;
    final String description;
    final ScoreModifierTemplate functionTemplate;
    final float scoreModifierValue;
    final boolean hidden;

    /**
     * Internal method to get the names of all the defined achievements.
     *
     * @return a set of the names of all the defined achievements
     */
    private final static HashSet<String> getNames() {
        return Stream.of(values())
            .map(a -> a.name)
            .collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Returns a list of all defined achievements that are not in the provided list of achievements,
     * if any are missing.
     *
     * @param achievements the list of achievements to find missing achievements in
     * @return a list of defined achievements that are not in the provided list of achievements or
     * an empty optional if all are present.
     */
    public static Optional<List<DefinedAchievements>> getMissingAchievements(Iterable<Achievement> achievements) {
        ArrayList<String> definedNames = new ArrayList<>(getNames());
        for (Achievement a : achievements) {
            definedNames.remove(a.name);
        }
        if (definedNames.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(definedNames.stream()
                .map(DefinedAchievements::getByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList()));
        }
    }

    /**
     * Gets the defined achievement by its name if it exists.
     *
     * @param name the name of the achievement
     * @return the defined achievement if it exists, otherwise an empty optional
     */
    private static Optional<DefinedAchievements> getByName(String name) {
        for (DefinedAchievements a : values()) {
            if (a.name.equals(name)) {
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }

}
