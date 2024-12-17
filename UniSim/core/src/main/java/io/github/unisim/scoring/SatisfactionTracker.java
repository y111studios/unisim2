package io.github.unisim.scoring;

import java.time.Duration;
import java.util.Iterator;
import java.util.stream.StreamSupport;
import io.github.unisim.building.Building;

public class SatisfactionTracker implements ScoringObject {
    private float satisfaction;
    private SatisfactionNeeds satisfactionNeeds;

    public SatisfactionTracker() {
        satisfactionNeeds = new SatisfactionNeeds();
        satisfaction = 0;
    }

    public float getSatisfaction() {
        return satisfaction;
    }

    public void updateSatisfaction(Iterable<Building> buildings, Building previewBuilding, int totalStudents) {
        final Iterator<Building> filteredBuildings;
        if (previewBuilding == null) {
            filteredBuildings = buildings.iterator();
        } else {
            filteredBuildings = StreamSupport.stream(buildings.spliterator(), false)
                .filter(b -> b != previewBuilding)
                .iterator();
        }
        satisfaction = 100 * satisfactionNeeds.getSatisfaction(() -> filteredBuildings, totalStudents);
    }

    @Override
    public float getScore() {
        if (Float.isFinite(satisfaction)) {
            return satisfaction;
        }
        return 0;
    }

    @Override
    public Duration getUpdateInterval() {
        return Duration.ofSeconds(1);
    }
}
