package gameLogic.tower.monkey;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ThrownProjTest {

    private ThrownProj dart;
    private ThrownProj bomb;
    private ThrownProj ice;
    private ThrownProj glue;

    @BeforeEach
    public void before() {
        this.dart = new ThrownProj(ProjType.Dart, 1, 1, 0.0, 0.0, 50.0, 50.0, 100);
        this.bomb = new ThrownProj(ProjType.Bomb, 2, 1, 0.0, 0.0, 50.0, 50.0, 100);
        this.ice  = new ThrownProj(ProjType.Ice,  0, 1, 0.0, 0.0, 50.0, 50.0, 100);
        this.glue = new ThrownProj(ProjType.Glue, 0, 1, 0.0, 0.0, 50.0, 50.0, 100);
    }

    @Test
    public void testGetDammage() {
        assertEquals(1, this.dart.getDammage());
    }

    @Test
    public void testIsABomb() {
        assertTrue(this.bomb.isABomb());
        assertFalse(this.dart.isABomb());
    }

    @Test
    public void testIsFreezing() {
        assertTrue(this.ice.isFreezing());
        assertFalse(this.dart.isFreezing());
    }

    @Test
    public void testIsSlowing() {
        assertTrue(this.glue.isSlowing());
        assertFalse(this.dart.isSlowing());
    }

    @Test
    public void testOnTargetCoordinatesAfterUpdate() {
        this.dart.update();
        assertTrue(this.dart.onTargetCoordinates());
    }

    @Test
    public void testBombTickerInitial() {
        assertEquals(2, this.bomb.getBombTicker());
    }

    @Test
    public void testBombExplode() {
        assertFalse(this.bomb.bombExplode());
        this.bomb.updateBombTicker();
        this.bomb.updateBombTicker();
        assertTrue(this.bomb.bombExplode());
    }

    @Test
    public void testIsOnRange() {
        assertTrue(this.dart.isOnRange(10));
    }
}