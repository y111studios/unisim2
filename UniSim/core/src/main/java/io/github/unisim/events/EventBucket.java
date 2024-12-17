package io.github.unisim.events;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class EventBucket {
    
    private List<EventCard> events;
    private Random random;

    public EventBucket() {
        events = new ArrayList<>();
        random = new Random();
        initializeEvents();
    }

    private void initializeEvents() {

        events.add(new EventCard(
            "Advertisements on Campus",
            "Would you like to put advertisements across campus?",
            List.of(
                new ChoiceCard("Yes", "+ $1500, - 5% Satisfaction", 1500, -5),
                new ChoiceCard("No", "+ 2.5% Satisfaction", 0, 2.5f),
                new ChoiceCard("Ignore", "No changes.", 0, 0)
            )
        ));

        events.add(new EventCard(
            "Lay Off Staff",
            "Do you want to lay off some staff?",
            List.of(
                new ChoiceCard("Yes", "+ $5000, - 10% Satisfaction", 5000, -10),
                new ChoiceCard("No", "+ 2.5% Satisfaction", 0, 2.5f),
                new ChoiceCard("Ignore", "No changes.", 0, 0)
            )
        ));

        events.add(new EventCard(
            "Hackathon!",
            "Would you like to organize a hackathon?",
            List.of(
                new ChoiceCard("Yes", "+ 5% Satisfaction, - $1000", -1000, 5),
                new ChoiceCard("No", "- 5% Satisfaction", 0, -5),
                new ChoiceCard("Ignore", "No changes.", 0, 0)
            )
        ));

        events.add(new EventCard(
            "Guest Lecturer",
            "Do you want to invite a guest lecturer?",
            List.of(
                new ChoiceCard("Yes", "+ 2.5% Satisfaction, - $500", -500, 2.5f),
                new ChoiceCard("No", "- 5% Satisfaction", 0, -5),
                new ChoiceCard("Ignore", "No changes.", 0, 0)
            )
        ));

        events.add(new EventCard(
            "Talent Show!",
            "Would you like to organize a talent show?",
            List.of(
                new ChoiceCard("Yes", "+ 10% Satisfaction, - $2000", -2000, 10),
                new ChoiceCard("No", "- 5% Satisfaction", 0, -5),
                new ChoiceCard("Ignore", "No changes.", 0, 0)
            )
        ));
        
        events.add(new EventCard(
            "Open Day!",
            "Do you want to organize an open day?",
            List.of(
                new ChoiceCard("Yes", "+ 2.5% Satisfaction, - $1000", -1000, 2.5f),
                new ChoiceCard("No", "- 2.5% Satisfaction", 0, -2.5f),
                new ChoiceCard("Ignore", "No changes.", 0, 0)
            )
        ));
    }

    public EventCard get(int index) {
        return events.get(index);
    }

    public EventCard get(String eventTitle) {
        for (EventCard event : events) {
            if (event.getTitle().equals(eventTitle)) {
                return event;
            }
        }
        return null;
    }

    public void add(EventCard event) {
        events.add(event);
    }

    public void remove(int index) {
        events.remove(index);
    }

    public void remove(String eventTitle) {
        events.removeIf(event -> event.getTitle().equals(eventTitle));
    }

    public void remove(EventCard event) {
        events.remove(event);
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }

    public EventCard getRandomEvent() {
        if (events.isEmpty()) {
            return null;
        }
        EventCard event = events.get(random.nextInt(events.size()));
        events.remove(event);
        return event;
    }

}
