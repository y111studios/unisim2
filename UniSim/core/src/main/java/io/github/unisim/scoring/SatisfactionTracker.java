package io.github.unisim.scoring;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;
import io.github.unisim.achievements.ScoreModifierTemplate;
import io.github.unisim.building.Building;

public class SatisfactionTracker implements ScoringObject {
    private float satisfaction;
    private SatisfactionNeeds satisfactionNeeds;

    List<SatisfactionModifier> modifiers = new ArrayList<>();

    public SatisfactionTracker() {
        satisfactionNeeds = new SatisfactionNeeds();
        satisfaction = 0;
    }

    public float getSatisfaction() {
        return satisfaction;
    }

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

    public void changeSatisfaction(float change) {
        satisfaction += change;
    }

    public void addModifier(ScoreModifierTemplate template, float value, Duration duration) {
        addModifier(new SatisfactionModifier(template, value, duration));
    }

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
