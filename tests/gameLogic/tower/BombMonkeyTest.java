package gameLogic.tower;

import gameLogic.tower.monkey.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class BombMonkeyTest {

    private BombMonkey bomb;

    @BeforeEach
    public void before() {
        this.bomb = new BombMonkey(0, 0);
    }

    @Test
    public void testBaseStats() {
        assertEquals(600, this.bomb.getPrice());
        assertEquals(2.0, this.bomb.getDammage());
        assertEquals(10.0, this.bomb.getRange());
        assertEquals(1800.0, this.bomb.getShotSpeed());
    }

    @Test
    public void testProjType() {
        assertEquals(ProjType.Bomb, this.bomb.getProj());
    }

    @Test
    public void testProjAmountBase() {
        assertEquals(1.0, this.bomb.getProjAmount());
    }

    @Test
    public void testNbEvolutions() {
        assertEquals(4, this.bomb.getEvols().length);
    }

    @Test
    public void testRangeEvolutionNotUsed() {
        assertEquals(10.0, this.bomb.getRange());
    }

    @Test
    public void testRangeEvolutionUsed() {
        this.bomb.getEvols()[0].updateUse();
        assertEquals(15.0, this.bomb.getRange());
    }

    @Test
    public void testShotSpeedEvolutionNotUsed() {
        assertEquals(1800.0, this.bomb.getShotSpeed());
    }

    @Test
    public void testShotSpeedEvolutionUsed() {
        this.bomb.getEvols()[1].updateUse();
        assertEquals(2250.0, this.bomb.getShotSpeed());
    }

    @Test
    public void testAttackEvolutionNotUsed() {
        assertEquals(2.0, this.bomb.getDammage());
    }

    @Test
    public void testAttackEvolutionUsed() {
        this.bomb.getEvols()[2].updateUse();
        assertEquals(3.0, this.bomb.getDammage());
    }

    @Test
    public void testProjeEvolutionNotUsed() {
        assertEquals(1.0, this.bomb.getProjAmount());
    }

    @Test
    public void testProjeEvolutionUsed() {
        this.bomb.getEvols()[3].updateUse();
        assertEquals(2.0, this.bomb.getProjAmount());
    }

    @Test
    public void testEvolutionToggle() {
        this.bomb.getEvols()[2].updateUse();
        assertEquals(3.0, this.bomb.getDammage());
        this.bomb.getEvols()[2].updateUse();
        assertEquals(2.0, this.bomb.getDammage());
    }

    @Test
    public void testCanShootInitially() {
        assertTrue(this.bomb.canShoot());
    }

    @Test
    public void testCantShootAfterReset() {
        this.bomb.resetTimer();
        assertFalse(this.bomb.canShoot());
    }

    @Test
    public void testCanShootAfterCooldown() {
        this.bomb.resetTimer();
        int ticks = (int)(this.bomb.getShotSpeed() / 100);
        for (int i = 0; i < ticks; i++) {
            this.bomb.updateTimer();
        }
        assertTrue(this.bomb.canShoot());
    }

    @Test
    public void testShootCreatesProj() {
        ThrownProj proj = this.bomb.shoot(50, 50);
        assertEquals(ProjType.Bomb, proj.getProj());
        assertEquals(this.bomb.getDammage(), proj.getDammage());
        assertTrue(proj.isABomb());
        assertFalse(this.bomb.canShoot());
    }

    @Test
    public void testInRangeTrue() {
        BombMonkey b = new BombMonkey(50, 50);
        assertTrue(b.inRange(55, 50, 10));
    }

    @Test
    public void testInRangeFalse() {
        BombMonkey b = new BombMonkey(50, 50);
        assertFalse(b.inRange(50, 80, 10));
    }
}