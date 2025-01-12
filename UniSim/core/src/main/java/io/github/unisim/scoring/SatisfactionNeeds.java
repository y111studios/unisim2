package io.github.unisim.scoring;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import io.github.unisim.building.Building;
import io.github.unisim.building.BuildingType;
import io.github.unisim.Point;

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
     * there are no buildings of a certain type or if there are no students, which returns 0. A modifier
     * is then applied based on the proximity to the nearest housing building (for non housing buildings).
     * An additional modifier is applied to the final satisfaction based on the quality of the buildings.
     * </p>
     *
     * @param buildings The buildings in the game.
     * @param totalStudents The total number of students.
     * @return The satisfaction of the students as a value in the range [0, 1].
     */
    public float getSatisfaction(Iterable<Building> buildings, int totalStudents) {
        HashMap<BuildingType, Integer> capacityByType = new HashMap<>(4);
        HashMap<BuildingType, Float> qualityByType = new HashMap<>(4);
        HashMap<BuildingType, List<Point>> pointsByType = new HashMap<>(4);

        for (BuildingType type : BuildingType.values()) {
            pointsByType.put(type, new ArrayList<Point>());
        }

        for (Building building : buildings) {
            BuildingType type = building.type;
            capacityByType.put(type, capacityByType.getOrDefault(type, 0) + building.capacity);
            qualityByType.put(type, qualityByType.getOrDefault(type, 0f) + building.quality);
            if(building.location == null) {
                continue;
            }
            pointsByType.get(type).add(new Point(building.location.x + building.size.x / 2, building.location.y + building.size.y / 2));
        }

        HashMap<BuildingType, Float> proximityModifiers = new HashMap<>(3);

        for (BuildingType type : BuildingType.values()) {
            int listSize = pointsByType.get(type).size();
            if (listSize > 0) {
                qualityByType.put(type, qualityByType.get(type) / listSize / 3);
            } else {
                qualityByType.put(type, 1f);
            }

            if (type == BuildingType.SLEEPING) {
                continue;
            }
            if (listSize == 0) {
                proximityModifiers.put(type, 1f);
                continue;
            }
            float sum = 0;
            for (Point point : pointsByType.get(type)) {
                float distance = 999;
                for (Point housingPoint : pointsByType.get(BuildingType.SLEEPING)) {
                    float dx = point.x - housingPoint.x;
                    float dy = point.y - housingPoint.y;
                    distance = Math.min(distance, (float) Math.sqrt(dx * dx + dy * dy));
                }
                sum += distance;
            }
            proximityModifiers.put(type, 20 / sum * listSize);
        }

        float housingCapacity = capacityByType.getOrDefault(BuildingType.SLEEPING, 0);
        float cateringCapacity = capacityByType.getOrDefault(BuildingType.EATING, 0) * proximityModifiers.get(BuildingType.EATING);
        float teachingCapacity = capacityByType.getOrDefault(BuildingType.LEARNING, 0) * proximityModifiers.get(BuildingType.LEARNING);
        float recreationCapacity = capacityByType.getOrDefault(BuildingType.RECREATION, 0) * proximityModifiers.get(BuildingType.RECREATION);

        if (housingCapacity == 0 || cateringCapacity == 0 || teachingCapacity == 0  || recreationCapacity == 0 || totalStudents == 0) {
            // Special case where no student can be satisfied
            return 0;
        }

        float housingSatisfaction = Math.min(1, housingCapacity / totalStudents) * qualityByType.get(BuildingType.SLEEPING);
        float cateringSatisfaction = Math.min(1, cateringCapacity / totalStudents) * qualityByType.get(BuildingType.EATING);
        float teachingSatisfaction = Math.min(1, teachingCapacity / totalStudents) * qualityByType.get(BuildingType.LEARNING);
        float recreationSatisfaction = Math.min(1, recreationCapacity / totalStudents) * qualityByType.get(BuildingType.RECREATION);

        return Math.min(housingSatisfaction, Math.min(cateringSatisfaction, Math.min(teachingSatisfaction, recreationSatisfaction)));
    }

}
