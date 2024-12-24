package io.github.unisim.scoring;

import java.util.HashMap;
import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingType;

public class SatisfactionNeeds {

    /**
     * Calculates the satisfaction of the students.
     *
     * <p>
     * This function currently takes the minimum % of students that can be satisfied by the buildings.
     * i.e. if there are 100 students and 50 beds, 50 students can be satisfied.
     * </p>
     * <p>
     * The satisfaction is calculated as the minimum of the satisfaction of the students in housing,
     * catering, and teaching buildings. There is a special case where no student can be satisfied if
     * there are no buildings of a certain type or if there are no students, which returns 0.
     * </p>
     *
     * @param buildings The buildings in the game.
     * @param totalStudents The total number of students.
     * @return The satisfaction of the students as a value in the range [0, 1].
     */
    public float getSatisfaction(Iterable<Building> buildings, int totalStudents) {
        HashMap<BuildingType, Integer> capacityByType = new HashMap<>(3);
        for (Building building : buildings) {
            BuildingType type = building.type;
            int capacity = building.capacity;
            capacityByType.put(type, capacityByType.getOrDefault(type, 0) + capacity);
        }

        float housingCapacity = capacityByType.getOrDefault(BuildingType.SLEEPING, 0);
        float cateringCapacity = capacityByType.getOrDefault(BuildingType.EATING, 0);
        float teachingCapacity = capacityByType.getOrDefault(BuildingType.LEARNING, 0);

        if (housingCapacity == 0 || cateringCapacity == 0 || teachingCapacity == 0 || totalStudents == 0) {
            // Special case where no student can be satisfied
            return 0;
        }

        float housingSatisfaction = Math.min(1, housingCapacity / totalStudents);
        float cateringSatisfaction = Math.min(1, cateringCapacity / totalStudents);
        float teachingSatisfaction = Math.min(1, teachingCapacity / totalStudents);

        return Math.min(housingSatisfaction, Math.min(cateringSatisfaction, teachingSatisfaction));
    }

}
