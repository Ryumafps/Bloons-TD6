package gameLogic.board;

import gameLogic.bloon.Bloon;
import gameLogic.tower.DartMonkey;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardPathTest {

    private BoardPath b;

    @BeforeEach
    public void before() {
        this.b = new BoardPath(40, 30, 10);
    }

    @Test 
    public void testGetBloonsEmpty() {
        assertEquals(new ArrayList<>(), b.getBloons());
    }

    @Test 
    public void testAddBloon() {
        int minSpeed = 1;
        int maxSpeed = 3;
        b.addBloon(minSpeed, maxSpeed);
        assertEquals(1, b.getBloons().size());

        //Path objetPath = b.getPath();
        //Cell firstCell = objetPath.getPath().get(0);
        Cell firstCell = b.getPath().get(0);

        Bloon b1 = b.getBloons().get(0);
        assertEquals(firstCell, b1.getCell(10));
    }

    @Test 
    public void testGetNeighboursWithFourNeighbours() {
        Cell c = new Cell(3, 4);

        List<Cell> expectedNeighbours = new ArrayList<>();
        expectedNeighbours.add(new Cell(3, 3));
        expectedNeighbours.add(new Cell(2, 4));
        expectedNeighbours.add(new Cell(4, 4));
        expectedNeighbours.add(new Cell(3, 5));
        
        List<Cell> computedNeighbours = b.getNeighbours(c);
        assertEquals(expectedNeighbours, computedNeighbours);
    }

    @Test 
    public void testGetNeighboursWithThreeNeighbours() {
        Cell c = new Cell(3, 0);

        List<Cell> expectedNeighbours = new ArrayList<>();
        expectedNeighbours.add(new Cell(2, 0));
        expectedNeighbours.add(new Cell(4, 0));
        expectedNeighbours.add(new Cell(3, 1));
        
        List<Cell> computedNeighbours = b.getNeighbours(c);
        assertEquals(expectedNeighbours, computedNeighbours);
    }

    @Test 
    public void testGetNeighboursWithTwoNeighbours() {
        Cell c = new Cell(0, 0);

        List<Cell> expectedNeighbours = new ArrayList<>();
        expectedNeighbours.add(new Cell(1, 0));
        expectedNeighbours.add(new Cell(0, 1));
        
        List<Cell> computedNeighbours = b.getNeighbours(c);
        assertEquals(expectedNeighbours, computedNeighbours);
    }

    @Test 
    public void testCreatePathContiguousPath() {
        Path path = this.b.getPath();
        // jusqu'à l'avant dernier pour toujours avoir un suivant
        for (int i = 0; i < path.size()-1; i++) {
            assertEquals(1, Cell.manhattanDistance(path.get(i), path.get(i+1)));
        }
    }


    @Test
    public void testAddMonkeyOnPathRefused(){
        Cell pathCell = this.b.getPath().get(0);
        int monkeyX = pathCell.getX() * 10 + 5;
        int monkeyY = pathCell.getY() * 10 + 5;
        DartMonkey monkey = new DartMonkey(monkeyX, monkeyY);
        this.b.addMonkey(monkey);
        assertFalse(this.b.getDrawable().contains(monkey));
    }
}
