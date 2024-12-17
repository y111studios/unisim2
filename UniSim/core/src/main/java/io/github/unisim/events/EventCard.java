package io.github.unisim.events;

import java.util.List;

public class EventCard {
    
    private String title;
    private String description;
    private List<ChoiceCard> choices;

    public EventCard(String title, String description, List<ChoiceCard> choices) {
        this.title = title;
        this.description = description;
        this.choices = choices;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<ChoiceCard> getChoices() {
        return choices;
    }

}
