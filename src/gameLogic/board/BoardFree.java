package gameLogic.board;

import gameLogic.bloon.*;
import gameLogic.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class BoardFree extends Board {

    /**
     * Constructor of a board without path
     * @param width width of the board
     * @param height height of the board
     * @param cellSize size of a cell in the board
     */
    public BoardFree(int width, int height, int cellSize) {
        super(width, height, cellSize);
    }

    /**
     * Getter for the bloons
     * @return the bloons
     */
    public List<Bloon> getBloons() {
        return bloons;
    }

    /**
     * Setter for the bloons
     */
    public void setBloons(List<Bloon> bloons) {
        this.bloons = bloons;
    }

    /**
     * Mooves the bloons on the path
     * @param step the time between two movements
     * @return the list of the bloons out of the board
     */
    public List<Bloon> moveBloons(float step, Consumer<Bloon> onOut, Consumer<Bloon> onDestroyed) {
        List<Bloon> sortis = new ArrayList<>();
        List<Bloon> destroyed = new ArrayList<>();

        for (Bloon b : bloons) {
            // System.out.println(b);
            b.move(step);
            if (b.isNotInBound(width, height, cellSize)) {
                sortis.add(b);
            }
            else if (b.isDestroyed()) {
                destroyed.add(b);
            }
        }

        for (Bloon b : sortis) { // on enlève les ballons sortis
            onOut.accept(b);
            bloons.remove(b);
        }

        for (Bloon b : destroyed) {
            onDestroyed.accept(b);
            bloons.remove(b);
        }

        return sortis; // liste des ballons sortis du plateau
    }

    /**
     * Get the direction of a bloon on a cell
     * @param cell the cell tested
     * @return the direction
     */
    public Direction getDirection(Cell cell) {
        int x = cell.getX(); 
        int y = cell.getY();
        Direction d;

        if (x == 0) {
            d = Direction.DROITE;
        }
        else if (x == width-1) {
            d = Direction.GAUCHE;
        }
        else if (y == 0) {
            d = Direction.BAS;
        }
        else {
            d = Direction.HAUT;
        }
        return d;
    }

    /**
     * Adds a bloon to this.bloons
     *
     * @param minSpeed : the min speed of the bloon
     * @param maxSpeed : the max speed of the bloon
    */
    public void addBloon(int minSpeed, int maxSpeed) {
        Random rand = new Random();
        int speed = rand.nextInt(minSpeed, maxSpeed);   // random speed
        int health = rand.nextInt(1, 4);    // random health
        List<Cell> edges = this.getEdges();
        int index = rand.nextInt(edges.size());     // random edge cell
        Cell cell = edges.get(index);
        Direction d = this.getDirection(cell);      // random direction depending on the cell

        Bloon b = new Bloon(health, speed, cell.getX()*cellSize, cell.getY()*cellSize, d);
        this.bloons.add(b);
    }

    private int distanceToEdge(Bloon bloon) {
        switch (bloon.getDirection()) {
            case DROITE: return this.width * this.cellSize - bloon.getX();
            case GAUCHE: return bloon.getX();
            case BAS:    return this.height * this.cellSize - bloon.getY();
            case HAUT:   return bloon.getY();
            default:     return Integer.MAX_VALUE;
        }
    }

    public void sortBloonsByProgress() {
        this.bloons.sort((b1, b2) -> {
            int distA = distanceToEdge(b1);
            int distB = distanceToEdge(b2);
            return Integer.compare(distA, distB); // le plus proche de la sortie en premier
        });
    }

    public void updateMonkeys() {
        sortBloonsByProgress(); // on trie d'abord
        super.updateMonkeys();  // ensuite on tire
    }
}
