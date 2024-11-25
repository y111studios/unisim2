package io.github.unisim.scoring;

import java.util.HashMap;
import java.util.Map;
import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingType;

public class SatisfactionTracker {
    private float satisfaction;

    public SatisfactionTracker() {
        satisfaction = 0;
    }

    public float getSatisfaction() {
        return satisfaction;
    }

    public void updateSatisfaction(Iterable<Building> buildings) {
        Map<BuildingType, Float> buildingCounts = new HashMap<>(4);
        long buildingCount = 0;
        long sumStudents = 0;
        for (Building building : buildings) {
            if (building.type == BuildingType.SLEEPING) {
                sumStudents += building.capacity;
            }
            buildingCounts.put(building.type, buildingCounts.getOrDefault(building.type, 0f) + 1);
            buildingCount += 1;
        }
        for (Map.Entry<BuildingType, Float> entry : buildingCounts.entrySet()) {
            if (entry.getKey() == BuildingType.SLEEPING) {
                continue;
            }
            entry.setValue(entry.getValue() / buildingCount);
        }
        float teachFactor = buildingCounts.getOrDefault(BuildingType.LEARNING, 0f)
                / buildingCounts.getOrDefault(BuildingType.SLEEPING, 1.0f);
        float eatingFactor = buildingCounts.getOrDefault(BuildingType.EATING, 0f)
                / buildingCounts.getOrDefault(BuildingType.SLEEPING, 1.0f);
        float funFactor = buildingCounts.getOrDefault(BuildingType.RECREATION, 0f)
                / buildingCounts.getOrDefault(BuildingType.SLEEPING, 1.0f);
        float intermediateSatisfaction = (teachFactor + eatingFactor + funFactor) / 3;
        satisfaction = 10000f * intermediateSatisfaction / sumStudents;
    }
}
