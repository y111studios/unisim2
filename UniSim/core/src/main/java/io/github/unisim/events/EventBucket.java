package io.github.unisim.events;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import io.github.unisim.achievements.ScoreModifierTemplate;

/**
 * Bucket class to hold all choice events that can be triggered in the game.
 * This class is used to randomly select an event to trigger.
 */
public class EventBucket {

    /**
     * List of all the event cards remaining in the bucket.
     */
    private List<EventCard> events;
    /**
     * Reference to the random number generator.
     */
    private Random random;

    /**
     * Constructor to initialize the event bucket with all the events and
     * a randomly seeded random number generator.
     */
    public EventBucket() {
        events = new ArrayList<>();
        random = new Random();
        initializeEvents();
    }

    /**
     * Adds all the events into the events list.
     */
    private void initializeEvents() {

        events.add(new EventCard(
            "Advertisements on Campus",
            "Would you like to put advertisements across campus?",
            List.of(
                new ChoiceCard("Yes", "+ $1500, - 5% Satisfaction", 1500, ScoreModifierTemplate.ADD, -5),
                new ChoiceCard("No", "+ 2.5% Satisfaction", 0, ScoreModifierTemplate.ADD, 2.5f),
                new ChoiceCard("Ignore", "No changes.", 0, ScoreModifierTemplate.ADD, 0)
            )
        ));

        events.add(new EventCard(
            "Lay Off Staff",
            "Do you want to lay off some staff?",
            List.of(
                new ChoiceCard("Yes", "+ $5000, - 10% Satisfaction", 5000, ScoreModifierTemplate.ADD, -10),
                new ChoiceCard("No", "+ 2.5% Satisfaction", 0, ScoreModifierTemplate.ADD, 2.5f),
                new ChoiceCard("Ignore", "No changes.", 0, ScoreModifierTemplate.ADD, 0)
            )
        ));

        events.add(new EventCard(
            "Hackathon!",
            "Would you like to organize a hackathon?",
            List.of(
                new ChoiceCard("Yes", "+ 5% Satisfaction, - $1000", -1000, ScoreModifierTemplate.ADD, 5),
                new ChoiceCard("No", "- 5% Satisfaction", 0, ScoreModifierTemplate.ADD, -5),
                new ChoiceCard("Ignore", "No changes.", 0, ScoreModifierTemplate.ADD, 0)
            )
        ));

        events.add(new EventCard(
            "Guest Lecturer",
            "Do you want to invite a guest lecturer?",
            List.of(
                new ChoiceCard("Yes", "+ 2.5% Satisfaction, - $500", -500, ScoreModifierTemplate.ADD, 2.5f),
                new ChoiceCard("No", "- 5% Satisfaction", 0, ScoreModifierTemplate.ADD, -5),
                new ChoiceCard("Ignore", "No changes.", 0, ScoreModifierTemplate.ADD, 0)
            )
        ));

        events.add(new EventCard(
            "Talent Show!",
            "Would you like to organize a talent show?",
            List.of(
                new ChoiceCard("Yes", "+ 10% Satisfaction, - $2000", -2000, ScoreModifierTemplate.ADD, 10),
                new ChoiceCard("No", "- 5% Satisfaction", 0, ScoreModifierTemplate.ADD, -5),
                new ChoiceCard("Ignore", "No changes.", 0, ScoreModifierTemplate.ADD, 0)
            )
        ));

        events.add(new EventCard(
            "Open Day!",
            "Do you want to organize an open day?",
            List.of(
                new ChoiceCard("Yes", "+ 2.5% Satisfaction, - $1000", -1000, ScoreModifierTemplate.ADD, 2.5f),
                new ChoiceCard("No", "- 2.5% Satisfaction", 0, ScoreModifierTemplate.ADD, -2.5f),
                new ChoiceCard("Ignore", "No changes.", 0, ScoreModifierTemplate.ADD, 0)
            )
        ));
    }

    /**
     * Get a random event from the bucket.
     *
     * @return a random event from the bucket
     */
    public EventCard getRandomEvent() {
        if (events.isEmpty()) {
            return null;
        }
        EventCard event = events.get(random.nextInt(events.size()));
        events.remove(event);
        return event;
    }

}
