package gameLogic.bloon;

import org.junit.jupiter.api.*;

import gameLogic.board.Cell;

import static org.junit.jupiter.api.Assertions.*;

public class BloonTest {

    private Bloon b1;
    private Bloon b2;
    private Bloon b3;
    private Bloon b4;

    @BeforeEach
    public void before() {
        this.b1 = new Bloon(3, 2, 1, 1, Direction.DROITE);
        this.b2 = new Bloon(3, 2, 10, 1, Direction.GAUCHE);
        this.b3 = new Bloon(3, 2, 1, 2, Direction.HAUT);
        this.b4 = new Bloon(3, 2, 1, 10, Direction.BAS);
    }

    @Test
    public void testMoveDroite() {
        this.b1.move(1);

        assertEquals(3, this.b1.getX());
        assertEquals(1, this.b1.getY());
    }

    @Test
    public void testMoveGauche() {
        this.b2.move(1);

        assertEquals(8, this.b2.getX());
        assertEquals(1, this.b2.getY());
    }

    @Test
    public void testMoveHaut() {
        this.b3.move(1);

        assertEquals(1, this.b3.getX());
        assertEquals(0, this.b3.getY());
    }

    @Test
    public void testMoveBas() {
        this.b4.move(1);

        assertEquals(1, this.b4.getX());
        assertEquals(12, this.b4.getY());
    }

    @Test
    public void testCantMoveIfFrozen() {
        this.b1.freeze();
        this.b1.move(1);

        assertEquals(1, this.b1.getX());
        assertEquals(1, this.b1.getY());
    }

    @Test 
    public void testSlowedMove() {
		this.b1.slow();
		this.b1.move(1);

		assertEquals(this.b1.getX(), 2);
		assertEquals(this.b1.getY(), 1);
    }


    @Test 
    public void testDammageAndDestroyed() {
		assertEquals(this.b1.getHealth(), 3);
		this.b1.takeDamage(1.0);
		assertEquals(this.b1.getHealth(), 2);
		this.b1.takeDamage(2.0);
		assertEquals(this.b1.getHealth(), 0);
		assertTrue(this.b1.isDestroyed());
    }

    @Test
    public void testResetStatus() {
        this.b1.slow();
        assertEquals(Status.SLOWED, this.b1.getStatus());
        assertEquals(1, this.b1.getSpeed());
        this.b1.resetStatus();
        assertEquals(Status.NORMAL, this.b1.getStatus());
        assertEquals(2, this.b1.getSpeed());
    }

    @Test
    public void testFreezeExpires() {
        this.b1.freeze();
        assertEquals(Status.FROZEN, this.b1.getStatus());
        for (int i = 0; i < 30; i++) {
            this.b1.updateStatus(1f);
        }
        assertEquals(Status.NORMAL, this.b1.getStatus());
        assertEquals(2, this.b1.getSpeed());
    }

    @Test
    public void testSlowExpires() {
        this.b1.slow();
        assertEquals(Status.SLOWED, this.b1.getStatus());
        assertEquals(1, this.b1.getSpeed());
        for (int i = 0; i < 50; i++) {
            this.b1.updateStatus(1f);
        }
        assertEquals(Status.NORMAL, this.b1.getStatus());
        assertEquals(2, this.b1.getSpeed());
    }

    @Test
    public void testIsNotInBoundTrue() {
        assertFalse(this.b1.isNotInBound(10, 10, 10));
    }

    @Test
    public void testIsNotInBoundFalse() {
        Bloon hors = new Bloon(1, 1, -1, 5, Direction.DROITE);
        assertTrue(hors.isNotInBound(10, 10, 10));
    }

    @Test
    public void testIsNotInBoundRight() {
        Bloon hors = new Bloon(1, 1, 100, 5, Direction.DROITE);
        assertTrue(hors.isNotInBound(10, 10, 10));
    }

    @Test
    public void testGetCell() {
        Bloon b = new Bloon(1, 1, 15, 25, Direction.DROITE);
        Cell c = b.getCell(10);
        assertEquals(1, c.getX());
        assertEquals(2, c.getY());
    }
}
