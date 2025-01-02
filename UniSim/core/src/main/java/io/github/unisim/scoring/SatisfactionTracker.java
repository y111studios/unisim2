package io.github.unisim.scoring;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;
import io.github.unisim.achievements.ScoreModifierTemplate;
import io.github.unisim.building.Building;

/**
 * A class that tracks the satisfaction of the students.
 */
public class SatisfactionTracker implements ScoringObject {
    private float satisfaction;
    private SatisfactionNeeds satisfactionNeeds;

    List<SatisfactionModifier> modifiers;

    /**
     * Initializes the satisfaction tracker to 0 with no modifiers.
     */
    public SatisfactionTracker() {
        satisfactionNeeds = new SatisfactionNeeds();
        satisfaction = 0;
        modifiers = new ArrayList<>();
    }

    public float getSatisfaction() {
        return satisfaction;
    }

    /**
     * Returns the satisfaction as a string to one decimal place.
     * 
     * @return The satisfaction as a string
     */
    public String getStringSatisfaction() {
        return String.format("%.1f", satisfaction);
    }

    /**
     * Updates the satisfaction of the students based on the buildings and the total number of students.
     * The previewBuilding is used to calculate the satisfaction without the building that has not been built yet.
     * If previewBuilding is null, all buildings are considered.
     *
     * @param buildings The buildings that are currently built
     * @param previewBuilding The building that is being previewed
     * @param totalStudents The total number of students
     */
    public void updateSatisfaction(Iterable<Building> buildings, Building previewBuilding, int totalStudents) {
        if (buildings == null) {
            // Special case where there are no buildings
            satisfaction = 0;
            return;
        }
        final Iterator<Building> filteredBuildings;
        if (previewBuilding == null) {
            filteredBuildings = buildings.iterator();
        } else {
            filteredBuildings = StreamSupport.stream(buildings.spliterator(), false)
                .filter(b -> b != previewBuilding)
                .iterator();
        }
        satisfaction = 100 * satisfactionNeeds.getSatisfaction(() -> filteredBuildings, totalStudents);
        for (SatisfactionModifier modifier : modifiers) {
            if (modifier.isActive()) {
                satisfaction = modifier.getModifierFunction().apply(satisfaction);
            }
        }
        modifiers.removeIf(SatisfactionModifier::isExpired);
    }

    /**
     * Adds a new modifier to the satisfaction tracker.
     *
     * @param template The template of the modifier
     * @param value The value the modifier will change the satisfaction by
     * @param duration The duration of the modifier
     */
    public void addModifier(ScoreModifierTemplate template, float value, Duration duration) {
        addModifier(new SatisfactionModifier(template, value, duration));
    }

    /**
     * Adds a new modifier to the satisfaction tracker.
     *
     * @param modifier The modifier to add
     */
    public void addModifier(SatisfactionModifier modifier) {
        // Insert the modifier in the correct order
        for (int i = 0; i < modifiers.size(); i++) {
            if (modifier.compareTo(modifiers.get(i)) < 0) {
                modifiers.add(i, modifier);
                return;
            }
        }
        modifiers.add(modifier);
    }

    @Override
    public float getScore() {
        if (Float.isFinite(getSatisfaction())) {
            return getSatisfaction();
        }
        return 0;
    }

    @Override
    public Duration getUpdateInterval() {
        return Duration.ofSeconds(1);
    }
}
