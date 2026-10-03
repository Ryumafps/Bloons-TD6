package gameLogic.tower.monkey;

import gameLogic.board.Cell;
import gameLogic.player.Player;
import gameLogic.tower.DartMonkey;
import gameLogic.tower.monkey.Evolution;
import gameLogic.tower.monkey.Monkey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MonkeyTest {
    private DartMonkey monkey;

    @BeforeEach
    public void setUp() {
        monkey = new DartMonkey(100, 100);
    }

    @Test
    public void testConstructorAndGetters(){
        assertEquals(100, monkey.getX());
        assertEquals(100, monkey.getY());
        assertEquals(200, monkey.getPrice());
        assertEquals(ProjType.Dart, monkey.getProj());
        assertEquals(20.0, monkey.getRange());
        assertEquals(1.0, monkey.getDammage());
        assertEquals(1000.0, monkey.getShotSpeed());
        assertEquals(1.0, monkey.getProjAmount());
    }

    @Test
    public void testSetters(){
        monkey.setX(150);
        monkey.setY(120);
        assertEquals(150, monkey.getX());
        assertEquals(120, monkey.getY());
    }

    // pas sur
    @Test
    public void testGetCell(){
        int cellSize = 20;
        Cell rCell = new Cell(4,4);
        assertEquals(rCell, monkey.getCell(cellSize));
        assertEquals(rCell.getX(), monkey.getCell(cellSize).getX());
        assertEquals(rCell.getY(), monkey.getCell(cellSize).getY());
    }

    @Test
    public void testInRange(){
        int cellSize = 20;
        assertFalse(monkey.inRange(100, 130, cellSize));
        assertTrue(monkey.inRange(100, 115, cellSize));
        assertTrue(monkey.inRange(114, 114, cellSize));
    }

    @Test
    public void testTimerLogic(){
        assertTrue(monkey.canShoot());
        monkey.resetTimer();
        assertFalse(monkey.canShoot());
        for (int i = 0; i<9; i++){
            monkey.updateTimer();
        }
        assertFalse(monkey.canShoot());
        monkey.updateTimer();
        assertTrue(monkey.canShoot());
    }

    @Test
    public void testShoot(){
        assertTrue(monkey.canShoot());
        ThrownProj proj = monkey.shoot(115, 115);
        assertEquals(monkey.getProj(), proj.getProj());
        assertEquals(monkey.getDammage(), proj.getDammage());
        assertEquals(monkey.getX(), proj.getX());
        assertEquals(monkey.getY(), proj.getY());
        assertFalse(monkey.canShoot());
    }
}