
package gameLogic.board;

import gameLogic.bloon.*;
import gameLogic.game.Game;
import gameLogic.tower.monkey.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import termDisplay.Drawable;

public class BoardPath extends Board {

    /* The path all bloons will follow  */
    private Path path;

    /**
     * Constructor for BoardPath
     * @param width : The width of the board
     * @param height : The height of the board
     * @param cellSize : The size of a cell
     */
    public BoardPath(int width, int height, int cellSize) {
        super(width, height, cellSize);
        List<Cell> pathEdges = this.getStartingAndEndingCells();
        this.path = new Path(pathEdges.get(0), pathEdges.get(1), this);
    }


    public List<Drawable> getDrawable() {
        List<Drawable> res = new ArrayList<>();
        res.add(this.path);
        res.addAll(this.monkeys);
        res.addAll(this.bloons);
        return res;
    }

    /**
     * Getter for the bloons
     * @return the bloons of the board
     */
    public List<Bloon> getBloons() {
        return this.bloons;
    }

    /**
     * Setter for the bloons
     */
    public void setBloons(List<Bloon> newBloons) {
        this.bloons = newBloons;
    }

    /**
     * Getter for the path
     * @return the path
     */
    public Path getPath() {
        return this.path;
    }

    /**
     * Adds a bloon to this.bloons
     * @param minSpeed : the min speed of the bloon
     * @param maxSpeed : the max speed of the bloon
     */
    public void addBloon(int minSpeed, int maxSpeed) {
        Random rand = new Random();
        int speed = rand.nextInt(minSpeed, maxSpeed);
        int health = rand.nextInt(1, 3);
        Bloon b = new Bloon(
            health, 
            speed, 
            this.path.get(0).getX()*cellSize+cellSize/2, 
            this.path.get(0).getY()*cellSize+cellSize/2, 
            Cell.getDirection(this.path.get(0), this.path.get(1)));
            // cellSize/2 => spawn au milieu de la première case
        this.bloons.add(b);
    }


    /**
     * Move all bloons accordingly to the path
     * @param step : time between 2 moveBloons calls (either in "time unit" for terminal, or ms for graphical)
     * @return a list of bloons leaving the board at that step
     */
    public List<Bloon> moveBloons(float step, Consumer<Bloon> onOut, Consumer<Bloon> onDestroyed) {
        List<Bloon> sortis = new ArrayList<>();
        List<Bloon> destroyed = new ArrayList<>();
        for (Bloon b : this.bloons) {
            Cell currentCell = b.getCell(this.cellSize);
            int CurrentCellIndex = this.path.indexOf(currentCell);

            if (CurrentCellIndex == this.path.size() - 1) { // le ballon est sur la dernière case
                sortis.add(b);
            }
            else if (b.isDestroyed()) {
                destroyed.add(b);
            }
            else {
                Cell nextCell = this.path.get(CurrentCellIndex + 1);
                Direction d = Cell.getDirection(currentCell, nextCell);
                b.updateDirection(d);
                b.move(step);
            }
        }

        // on a besoin de 2 boucles car impossible de supprimer des éléments de bloons en itérant dedans
        for (Bloon b : sortis) { // on enlève les ballons sortis
            onOut.accept(b);
            // System.out.println(b + " is out !");
            bloons.remove(b);
        }

        for (Bloon b : destroyed) {
            onDestroyed.accept(b);
            bloons.remove(b);
        }

        return sortis; // liste des ballons sortis du plateau
    }


    /**
     * Helper method for generating the path. Gives two Cells on the edges
     * distant enough to create a path starting from the first cell and
     * ending to the last cell
     *
     * @return The starting and the ending cells of the wannabe path
     */
    private List<Cell> getStartingAndEndingCells() {

        List<Cell> edges = this.getEdges();
        int lenEdges = edges.size();
        Cell starting = new Cell(1, 1);
        Cell ending = new Cell(1, 1);
        Random rand = new Random();
        while (Cell.manhattanDistance(starting, ending) < Math.max(this.width, this.height) ) {
            starting = edges.get(rand.nextInt(lenEdges));
            ending = edges.get(rand.nextInt(lenEdges));
        }
        List<Cell> ret = new ArrayList<>();
        ret.add(starting); ret.add(ending);
        return ret;
    }


    /**
     * Returns all neighbours of cell, excluding cells in path
     * @param cell : the cell we want to know neighbours
     * @param path : the constructing path (because this method is primarily used to generate the path)
     * @return the neigbours, a list of cells
     */
    public List<Cell> getNeighbours(Cell cell, List<Cell> path) {
        /* WARN: les voisins de cell le sont uniquement si
         * ils ne sont pas dans le path */
        return super.getNeighbours(cell)
            .stream()
            .filter(c -> !path.contains(c))
            .collect(Collectors.toCollection(ArrayList::new));
    }



    /**
     * trie this.bloons par ordre de progression sur le chemin
     */
    public void sortBloonsByProgress() {
        this.bloons.sort((b1, b2) -> {
            int index1 = this.path.indexOf(b1.getCell(this.cellSize));
            int index2 = this.path.indexOf(b2.getCell(this.cellSize));
            return Integer.compare(index2, index1);
        });
    }

    public void updateMonkeys() {
        sortBloonsByProgress(); // on trie d'abord
        super.updateMonkeys();  // ensuite on tire
    }

	/**
	 * Add a monkey to the board
	 * @param m the monkey
	 */
	public void addMonkey(Monkey m) {
		boolean canPlace = true;
		Cell monkeyCell = m.getCell(this.cellSize);
		for (Cell cellPath : this.path.getPath()){
			if(cellPath.equals(monkeyCell)){
				canPlace = false;
			}
		}
		if(canPlace){
		super.addMonkey(m);
		}
	}

}
