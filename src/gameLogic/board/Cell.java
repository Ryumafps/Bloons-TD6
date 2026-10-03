package gameLogic.board;

import gameLogic.bloon.Direction;

public class Cell {
    
    // coordinate of the x-axis
    private int x;

    // coordinate of the y-axis
    private int y;

    /**
     * Constructor for a Cell
     * @param x its coordinate on the x-axis
     * @param y its coordinate on the y-axis
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the manhattan distance between a and b
     *
     * @param a : a 2d point
     * @param b : another 2d point
     * @return the manhattan distance between a and b
     */
    public static int manhattanDistance(Cell a, Cell b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    /**
     * Get the difference between 2 cells
     * @param a the first cell
     * @param b the second cell
     * @return a cell having their x difference and their y difference
     */
    public static Cell substract(Cell a, Cell b) {
        return new Cell((a.getX() - b.getX()), (a.getY() - b.getY()));
    }

    /**
     * Get a direction from two adjacents cells (no diagonal)
     * @param a the starting cell
     * @param b the arriving cell
     * @return the direction to go from cell a to cell b
     */
    public static Direction getDirection(Cell a, Cell b) {
        Cell diff = Cell.substract(a, b);
        Direction d = Direction.DROITE;     // pour que ça compile, ça change de toute façon
        
        if (diff.getX() == 0) {     // vertical
            if (diff.getY() < 0) {      // vers le bas
                d = Direction.BAS;
            }
            if (diff.getY() > 0) {      // vers le haut
                d = Direction.HAUT;
            }
        }
        if (diff.getY() == 0) {     // horizontal
            if (diff.getX() < 0) {      // vers la droite
                d = Direction.DROITE;
            }
            if (diff.getX() > 0) {      // vers la gauche
                d = Direction.GAUCHE;
            }
        }
        return d;
    }

    /**
     * Getters for x
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter for y
     * @return y
     */
    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Cell)) {
            return false;
        }
        Cell o = (Cell) other;
        return (this.x == o.getX()) && (this.y == o.getY());
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
