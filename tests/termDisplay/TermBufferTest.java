package termDisplay;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import gameLogic.board.*;

public class TermBufferTest {

    private TermBuffer buffer;

	@BeforeEach
    public void before() {
        this.buffer = new TermBuffer(new BoardFree(10, 10, 10));
    }

    @Test
    public void testCreateBuffer() {
        TermBuffer buf = new TermBuffer(new BoardFree(5, 5, 5));
        assertTrue(buf.getBuffer().size() == (4*5 + 2)*(2*5+1));
        buf = new TermBuffer(new BoardFree(10, 2, 5));
        assertTrue(buf.getBuffer().size() == (4*10 + 2)*(2*2+1));
        buf = new TermBuffer(new BoardFree(6, 7, 1));
        assertTrue(buf.getBuffer().size() == (4*6 + 2)*(2*7+1));
        buf = new TermBuffer(new BoardFree(10, 10, 1));
        assertTrue(buf.getBuffer().size() == (4*10 + 2)*(2*10+1));
    }


    @Test
    public void testGetCellInBounds() throws CellOutOfBoundsException {
        // NOTE: (4*w+2)*(2y+1) + (4x+2)
        assertEquals((4*10 +2)*(2*4+1) + (4*3+2), this.buffer.getIndex(new Cell(3, 4)));
        assertEquals((4*10 +2)*(2*5+1) + (4*3+2), this.buffer.getIndex(new Cell(3, 5)));
        assertEquals((4*10 +2)*(2*9+1) + (4*7+2), this.buffer.getIndex(new Cell(7, 9)));
        assertEquals((4*10 +2)*(2*1+1) + (4*4+2), this.buffer.getIndex(new Cell(4, 1)));
        assertEquals((4*10 +2)*(2*6+1) + (4*3+2), this.buffer.getIndex(new Cell(3, 6)));
        assertEquals((4*10 +2)*(2*0+1) + (4*0+2), this.buffer.getIndex(new Cell(0, 0)));
    }

    @Test
    public void testGetCellOutOfBounds() throws CellOutOfBoundsException {
        assertThrows(CellOutOfBoundsException.class, () -> this.buffer.getIndex(new Cell(2, 10)));
        assertThrows(CellOutOfBoundsException.class, () -> this.buffer.getIndex(new Cell(-2, 10)));
        assertThrows(CellOutOfBoundsException.class, () -> this.buffer.getIndex(new Cell(19, 3)));
        assertThrows(CellOutOfBoundsException.class, () -> this.buffer.getIndex(new Cell(2, -1)));
    }

    @Test
    public void testIncrementCellPasses() throws InvalidCellException, CellOutOfBoundsException {
        int idx;
        List<String> tmp = new ArrayList<>(this.buffer.getBuffer());
        this.buffer.incrementCell(new Cell(2, 1), tmp);
        idx = this.buffer.getIndex(new Cell(2, 1));
        assertEquals("1", tmp.get(idx));
        this.buffer.incrementCell(new Cell(2, 1), tmp);
        assertEquals("2", tmp.get(idx));
        this.buffer.incrementCell(new Cell(2, 1), tmp);
        assertEquals("3", tmp.get(idx));
    }

    @Test
    public void testIncrementCellOutOfBounds() throws InvalidCellException, CellOutOfBoundsException {
        Cell cell1 = new Cell(10, 10);
        Cell cell2 = new Cell(-5, 2);
        List<String> tmp = new ArrayList<>(this.buffer.getBuffer());

        assertThrows(CellOutOfBoundsException.class, () ->
            this.buffer.incrementCell(cell1, tmp));
        assertThrows(CellOutOfBoundsException.class, () ->
            this.buffer.incrementCell(cell2, tmp));
    }

    @Test
    public void testSetPathPasses() throws CellOutOfBoundsException {
        List<Cell> path1 = new ArrayList<>();

        path1.add(new Cell(1, 1));
        path1.add(new Cell(1, 2));
        path1.add(new Cell(1, 3));

        List<String> tmp = new ArrayList<>(this.buffer.getBuffer());

        this.buffer.setPath(path1, tmp);

        int idxCell1 = this.buffer.getIndex(new Cell(1, 1));
        int idxCell2 = this.buffer.getIndex(new Cell(1, 2));
        int realWidth = 4 * this.buffer.getWidth() + 2;
        assertEquals(" ", tmp.get(idxCell1 + realWidth));
        assertEquals(" ", tmp.get(idxCell2 + realWidth));
        assertNotEquals(" ", tmp.get(idxCell1 + 2));

    }

}
