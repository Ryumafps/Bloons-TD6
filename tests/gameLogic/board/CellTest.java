package gameLogic.board;

import gameLogic.bloon.Direction;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    private Cell c1;
    private Cell c2;
    private Cell c3;
    
    @BeforeEach
    public void before() {
        this.c1 = new Cell(3, 2);
        this.c2 = new Cell(4, 5);
        this.c3 = new Cell(3, 2);
    }

    @Test 
    public void testManhattanDistance() {
        assertEquals(4, Cell.manhattanDistance(c1, c2));
        assertEquals(0, Cell.manhattanDistance(c1, c3));
    }

    @Test 
    public void testSubstract() {
        Cell expectedCell = new Cell(-1, -3);
        assertEquals(expectedCell, Cell.substract(c1, c2));
        assertEquals(new Cell(0, 0), Cell.substract(c1, c3));
    }

    @Test 
    public void testGetDirection() {
        assertEquals(Direction.DROITE, Cell.getDirection(c1, c2)); // car par défaut
    }

    @Test 
    public void testGetX() {
        assertEquals(3, this.c1.getX());
        assertEquals(4, this.c2.getX());
    }

    @Test 
    public void testGetY() {
        assertEquals(2, this.c1.getY());
        assertEquals(5, this.c2.getY());
    }

    @Test 
    public void testEquals() {
        assertTrue(c1.equals(c3));
        assertTrue(c3.equals(c1));
    }

    @Test 
    public void testNotEquals() {
        assertFalse(c1.equals(c2));
        assertFalse(c2.equals(c3));
    }

    @Test 
    public void testToString() {
        assertEquals("(3, 2)", c1.toString());
        assertEquals("(4, 5)", c2.toString());
    }
}
