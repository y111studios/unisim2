package io.github.unisim.achievements;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ScoreModifierTemplateTest {

    @Test
    public void testAddFunction() {
        ScoreModifierTemplate template = ScoreModifierTemplate.ADD;
        Function<Integer, Integer> function = template.getFunction(5);

        assertEquals(15, function.apply(10));
        assertEquals(0, function.apply(-5));
        assertEquals(5, function.apply(0));
    }

    @Test
    public void testMulFunction() {
        ScoreModifierTemplate template = ScoreModifierTemplate.MUL;
        Function<Integer, Integer> function = template.getFunction(2);

        assertEquals(20, function.apply(10));
        assertEquals(-10, function.apply(-5));
        assertEquals(0, function.apply(0));
    }

    @Test
    public void testInvalidScoreModifier() {
        assertThrows(IllegalArgumentException.class, () -> {
            ScoreModifierTemplate template = ScoreModifierTemplate.valueOf("INVALID");
            template.getFunction(5);
        });
    }
}
