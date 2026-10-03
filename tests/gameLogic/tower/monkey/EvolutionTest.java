package gameLogic.tower.monkey;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class EvolutionTest {

    private Evolution e;

    @BeforeEach
    public void before() {
        this.e = new Evolution(EvolType.Range, 100, 25.0);
    }

    @Test
    public void testGetEvol() {
        assertEquals(EvolType.Range, this.e.getEvol());
    }

    @Test
    public void testGetPrice() {
        assertEquals(100, this.e.getPrice());
    }

    @Test
    public void testGetValue() {
        assertEquals(25, this.e.getValue());
    }

    @Test
    public void testGetAppliedValueNotUsed() {
        // isUsed = 0 donc appliedValue = 0
        assertEquals(0, this.e.getAppliedValue(10));
    }

    @Test
    public void testGetAppliedValueUsed() {
        this.e.updateUse(); // isUsed = 1
        assertEquals(250, this.e.getAppliedValue(10)); // 10 * 25 * 1 ?
    }

    @Test
    public void testUpdateUseToggle() {
        assertEquals(0, this.e.getisUsed());
        this.e.updateUse();
        assertEquals(1, this.e.getisUsed());
        this.e.updateUse();
        assertEquals(0, this.e.getisUsed());
    }
}