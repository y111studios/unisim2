package io.github.unisim.scoring;

import java.util.HashMap;
import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingType;
import io.github.unisim.world.World;

public class SatisfactionNeeds {

    public float getSatisfaction(World world) {
        HashMap<BuildingType, Integer> capacityByType = new HashMap<>(3);
        for (Building building : world.getBuildings()) {
            BuildingType type = building.type;
            int capacity = building.capacity;
            capacityByType.put(type, capacityByType.getOrDefault(type, 0) + capacity);
        }

        int totalStudents = world.numberOfStudents;
        float housingCapacity = capacityByType.getOrDefault(BuildingType.SLEEPING, 0);
        float cateringCapacity = capacityByType.getOrDefault(BuildingType.EATING, 0);
        float teachingCapacity = capacityByType.getOrDefault(BuildingType.LEARNING, 0);

        if (housingCapacity == 0 || cateringCapacity == 0 || teachingCapacity == 0) {
            // Special case where no student can be satisfied
            return 0;
        }

        float housingSatisfaction = Math.min(1, totalStudents / housingCapacity);
        float cateringSatisfaction = Math.min(1, totalStudents / cateringCapacity);
        float teachingSatisfaction = Math.min(1, totalStudents / teachingCapacity);

        return Math.min(housingSatisfaction, Math.min(cateringSatisfaction, teachingSatisfaction));
    }

}
