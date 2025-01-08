package io.github.unisim.events;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class EventBucketTest {

    EventBucket bucket;
    
    // Testing constructor
    @Test
    public void testConstructor() {
        bucket = new EventBucket();
        assertNotNull(bucket);
    }

    // Testing getRandomEvent
    @Test
    public void testGetRandomEvent() {
        bucket = new EventBucket();
        assertNotNull(bucket.getRandomEvent());
    }
}
