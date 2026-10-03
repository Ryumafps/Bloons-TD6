package gameLogic.tower;

import gameLogic.tower.monkey.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class NeedleTowerTest {

    private NeedleTower needle;

    @BeforeEach
    public void before() {
        this.needle = new NeedleTower(0, 0);
    }

    @Test
    public void testBaseStats() {
        assertEquals(350, this.needle.getPrice());
        assertEquals(1.0, this.needle.getDammage());
        assertEquals(10.0, this.needle.getRange());
        assertEquals(1200.0, this.needle.getShotSpeed());
    }

    @Test
    public void testProjType() {
        assertEquals(ProjType.Needle, this.needle.getProj());
    }

    @Test
    public void testProjAmount() {
        assertEquals(8.0, this.needle.getProjAmount());
    }

    @Test
    public void testNbEvolutions() {
        assertEquals(2, this.needle.getEvols().length);
    }

    @Test
    public void testRangeEvolutionNotUsed() {
        assertEquals(10.0, this.needle.getRange());
    }

    @Test
    public void testRangeEvolutionUsed() {
        this.needle.getEvols()[0].updateUse();
        assertEquals(12.0, this.needle.getRange());
    }

    @Test
    public void testShotSpeedEvolutionNotUsed() {
        assertEquals(1200.0, this.needle.getShotSpeed());
    }

    @Test
    public void testShotSpeedEvolutionUsed() {
        this.needle.getEvols()[1].updateUse();
        assertEquals(1500.0, this.needle.getShotSpeed());
    }

    @Test
    public void testEvolutionToggle() {
        this.needle.getEvols()[0].updateUse();
        assertEquals(12.0, this.needle.getRange());
        this.needle.getEvols()[0].updateUse();
        assertEquals(10.0, this.needle.getRange());
    }

    @Test
    public void testCanShootInitially() {
        assertTrue(this.needle.canShoot());
    }

    @Test
    public void testCantShootAfterReset() {
        this.needle.resetTimer();
        assertFalse(this.needle.canShoot());
    }

    @Test
    public void testCanShootAfterCooldown() {
        this.needle.resetTimer();
        int ticks = (int)(this.needle.getShotSpeed() / 100);
        for (int i = 0; i < ticks; i++) {
            this.needle.updateTimer();
        }
        assertTrue(this.needle.canShoot());
    }

    @Test
    public void testShootCreatesProj() {
        ThrownProj proj = this.needle.shoot(50, 50);
        assertEquals(ProjType.Needle, proj.getProj());
        assertEquals(this.needle.getDammage(), proj.getDammage());
        assertEquals(this.needle.getX(), proj.getX());
        assertEquals(this.needle.getY(), proj.getY());
        assertFalse(this.needle.canShoot());
    }

    @Test
    public void testInRangeTrue() {
        NeedleTower n = new NeedleTower(50, 50);
        assertTrue(n.inRange(55, 50, 10));
    }

    @Test
    public void testInRangeFalse() {
        NeedleTower n = new NeedleTower(50, 50);
        assertFalse(n.inRange(50, 80, 10));
    }
}