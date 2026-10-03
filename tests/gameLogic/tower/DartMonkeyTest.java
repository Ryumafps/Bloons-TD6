package gameLogic.tower;

import gameLogic.tower.monkey.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DartMonkeyTest {

    private DartMonkey m;

    @BeforeEach
    public void before() {
        this.m = new DartMonkey(0, 0);
    }

    @Test
    public void testBaseStats() {
        assertEquals(200, this.m.getPrice());
        assertEquals(1, this.m.getDammage());
        assertEquals(20, this.m.getRange());
        assertEquals(1000, this.m.getShotSpeed());
    }

    @Test
    public void testRangeEvolutionNotUsed() {
        assertEquals(20, this.m.getRange()); // pas d'evolution
    }

    @Test
    public void testRangeEvolutionUsed() {
        this.m.getEvols()[0].updateUse(); // active evolution range
        assertEquals(25, this.m.getRange()); // 20 + ceil(20 × 0.25 × 1) = 25
    }

    @Test
    public void testAttackEvolutionNotUsed() {
        assertEquals(1, this.m.getDammage());
    }

    @Test
    public void testAttackEvolutionUsed() {
        this.m.getEvols()[2].updateUse(); // active evolution attack
        assertEquals(2, this.m.getDammage()); // 1 + 1*1*1
    }

    @Test
    public void testShotSpeedEvolutionNotUsed() {
        assertEquals(1000, this.m.getShotSpeed());
    }

    @Test
    public void testShotSpeedEvolutionUsed() {
        this.m.getEvols()[1].updateUse(); // active evolution shotspeed
        assertEquals(1250, this.m.getShotSpeed());
    }

    @Test
    public void testCanShootInitially() {
        assertTrue(this.m.canShoot());
    }

    @Test
    public void testCantShootAfterReset() {
        this.m.resetTimer();
        assertFalse(this.m.canShoot());
    }

    @Test
    public void testCanShootAfterCooldown() {
        this.m.resetTimer();
        for (int i = 0; i < this.m.getShotSpeed()/100; i++) {
            this.m.updateTimer();
        }
        assertTrue(this.m.canShoot());
    }
}