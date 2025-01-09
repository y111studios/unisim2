package io.github.unisim.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class EventCardTest {

    // Testing contructor
    @Test
    public void testConstructor() {
        EventCard eventCard = new EventCard("title", "description", null);
        assertEquals("title", eventCard.getTitle());
        assertEquals("description", eventCard.getDescription());
        assertNull(eventCard.getChoices());
    }

    // Testing getTitle
    @Test
    public void testGetTitle() {
        EventCard eventCard = new EventCard("title", "description", null);
        assertEquals("title", eventCard.getTitle());
    }

    @Test
    public void testGetTitleEmpty() {
        EventCard eventCard = new EventCard("", "description", null);
        assertEquals("", eventCard.getTitle());
    }

    @Test
    public void testGetTitleNull() {
        EventCard eventCard = new EventCard(null, "description", null);
        assertNull(eventCard.getTitle());
    }
    
    // Testing getDescription
    @Test
    public void testGetDescription() {
        EventCard eventCard = new EventCard("title", "description", null);
        assertEquals("description", eventCard.getDescription());
    }

    @Test
    public void testGetDescriptionEmpty() {
        EventCard eventCard = new EventCard("title", "", null);
        assertEquals("", eventCard.getDescription());
    }

    @Test
    public void testGetDescriptionNull() {
        EventCard eventCard = new EventCard("title", null, null);
        assertNull(eventCard.getDescription());
    }

    // Testing getChoices
    @Test
    public void testGetChoices() {
        EventCard eventCard = new EventCard("title", "description", null);
        assertNull(eventCard.getChoices());
    }

    @Test
    public void testGetChoicesEmpty() {
        EventCard eventCard = new EventCard("title", "description", null);
        assertNull(eventCard.getChoices());
    }

    @Test
    public void testGetChoicesNull() {
        EventCard eventCard = new EventCard("title", "description", null);
        assertNull(eventCard.getChoices());
    }
}
