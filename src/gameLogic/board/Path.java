package gameLogic.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import termDisplay.Drawable;
import termDisplay.TermBuffer;

public class Path implements Drawable {

    private List<Cell> path;

    public Path(Cell startingCell, Cell endingCell, BoardPath board) {
        this.path = this.generatePath(startingCell, endingCell, board);
    }

    public Cell get(int idx) {
        return this.path.get(idx);
    }

    public List<Cell> getPath() {
        return this.path;
    }

    public int indexOf(Cell cell) {
        return this.path.indexOf(cell);
    }

    public int size() {
        return this.path.size();
    }

    public void draw(TermBuffer buf,  List<String> workBuf) {
        buf.setPath(this.path, workBuf);
    }

    /*
     * Create a new path
     * @param startingCell
     * @param endingCell
     * @param board
     * @return
    */
    private List<Cell> generatePath(Cell startingCell, Cell endingCell, BoardPath board) {

        Cell currentCell = startingCell;
        List<Cell> res = new ArrayList<>();
        res.add(startingCell);

        while (!currentCell.equals(endingCell)) {
            List<Cell> neighbours = board.getNeighbours(currentCell, res);
            if (neighbours.size() == 0) { // pourrait poser des problemes pour grand plateau ? normalement non
                // HACK: On reset et on recommence :D
                res = new ArrayList<>();
                res.add(startingCell);
                currentCell = startingCell;
            // NOTE: Valeur arbitraire, à mettre dans un config si jamais cela est fait
            } else if (res.size() > 3*board.width) {
                res = res.subList(0, res.size() / 2);
                currentCell = res.get(res.size() - 1);
            } else {
                Random rand = new Random();
                Cell newCell = neighbours.get(rand.nextInt(neighbours.size()));
                res.add(newCell);
                currentCell = newCell;
            }
        }

        return res;
    }

}
