package io.github.unisim.achievements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private final static HashSet<String> getNames() {
        return Stream.of(values())
            .map(a -> a.name)
            .collect(Collectors.toCollection(HashSet::new));
    }

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

    private static Optional<DefinedAchievements> getByName(String name) {
        for (DefinedAchievements a : values()) {
            if (a.name.equals(name)) {
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }

}
