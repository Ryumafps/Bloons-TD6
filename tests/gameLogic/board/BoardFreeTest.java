package gameLogic.board;

import gameLogic.bloon.*;
import gameLogic.game.Game;

import gameLogic.player.Player;
import gameLogic.tower.DartMonkey;

import java.util.List;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardFreeTest {
    private BoardFree b;
    private Game g;

    @BeforeEach
    public void before() {
        this.b = new BoardFree(40, 30, 10);
        //this.g = new Game(10, 10, null, new Player(), null);

    }

    @Test
    public void testAddBloon() {
        this.b.addBloon(1, 5);
        assertEquals(1, this.b.getBloons().size());
    }

    @Test
    public void testAddMultipleBloons() {
        this.b.addBloon(1, 5);
        this.b.addBloon(1, 5);
        this.b.addBloon(1, 5);
        assertEquals(3, this.b.getBloons().size());
    }

    @Test
    public void testBloonSpawnsOnEdge() {
        this.b.addBloon(1, 5);
        Bloon bloon = this.b.getBloons().get(0);
        int x = bloon.getX() / 10;
        int y = bloon.getY() / 10;
        boolean isOnEdge = (x == 0 || x == 39 || y == 0 || y == 29);
        assertTrue(isOnEdge);
    }

    @Test
    public void testMoveBloonsSortisDroite() {
        Bloon bloon = new Bloon(1, 5, 396, 50, Direction.DROITE);
        this.b.getBloons().add(bloon);
        assertEquals(1, this.b.getBloons().size());
        List<Bloon> sortis = this.b.moveBloons(1, (Bloon dum) -> {}, (Bloon dum) -> {});
        assertEquals(1, sortis.size());
        assertEquals(0, this.b.getBloons().size());
    }

    @Test
    public void testMoveBloonsNotSortis() {
        Bloon b = new Bloon(1, 5, 50, 50, Direction.DROITE);
        this.b.getBloons().add(b);
        List<Bloon> sortis = this.b.moveBloons(1, (Bloon dum) -> {}, (Bloon dum) -> {});
        assertEquals(0, sortis.size());
        assertEquals(1, this.b.getBloons().size());
    }

    @Test
    public void testGetDirectionGauche() {
        Cell c = new Cell(39, 5);
        assertEquals(Direction.GAUCHE, this.b.getDirection(c));
    }

    @Test
    public void testGetDirectionDroite() {
        Cell c = new Cell(0, 5);
        assertEquals(Direction.DROITE, this.b.getDirection(c));
    }

    @Test
    public void testGetDirectionBas() {
        Cell c = new Cell(5, 0);
        assertEquals(Direction.BAS, this.b.getDirection(c));
    }

    @Test
    public void testGetDirectionHaut() {
        Cell c = new Cell(5, 29);
        assertEquals(Direction.HAUT, this.b.getDirection(c));
    }

    @Test
    public void testSortBloonsByProgress() {
        Bloon proche = new Bloon(1, 1, 380, 50, Direction.DROITE);
        Bloon loin = new Bloon(1, 1, 100, 50, Direction.DROITE);
        this.b.getBloons().add(loin);
        this.b.getBloons().add(proche);
        this.b.sortBloonsByProgress();
        assertEquals(proche, this.b.getBloons().get(0));
        assertEquals(loin, this.b.getBloons().get(1));
    }

    @Test
    public void testCanPlaceMonkeyEmpty() {
        DartMonkey m = new DartMonkey(50, 50);
        assertTrue(this.b.canPlaceMonkey(m));
    }

    @Test
    public void testCanPlaceMonkeyTooClose() {
        DartMonkey m1 = new DartMonkey(50, 50);
        DartMonkey m2 = new DartMonkey(51, 50);
        this.b.addMonkey(m1);
        assertFalse(this.b.canPlaceMonkey(m2));
    }

    @Test
    public void testCanPlaceMonkeyFarEnough() {
        DartMonkey m1 = new DartMonkey(50, 50);
        DartMonkey m2 = new DartMonkey(100, 100);
        this.b.addMonkey(m1);
        assertTrue(this.b.canPlaceMonkey(m2));
    }
}
