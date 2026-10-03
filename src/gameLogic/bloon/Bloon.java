package gameLogic.bloon;

import java.util.List;

import gameLogic.board.Cell;
import termDisplay.Drawable;
import termDisplay.TermBuffer;

/**
 * Class representing a bloon in a Tower Defense game
 */
public class Bloon implements Drawable {

    // the bloon's health
    protected int health;

    // the bloon's speed
    protected int speed;

    // its coordinates
    protected int x;
    protected int y;

    // the direction where the bloon is going
    private Direction direction;

    // its status (Normal, Frozen or Slowed)
    protected Status status;

    // a copy of its speed for calculs
    protected int speedOriginal;
    
    // for how many "tick" it has been slowed
    protected float slowedTimer;

    // for how many "tick" it has been frozen
    protected float frozenTimer;

    // Constantes : how much it will be slowed ; how many "tick" it will be slowed and frozen
    public static final float slowFactor = 0.5f;
    public static final float slowedDuration = 50;
    public static final float frozenDuration = 30;

    /**
     * Bloon constructor
     * @param health initial health point
     * @param speed movement speed
     * @param x initial x position
     * @param y initial y position
     */
    public Bloon(int health, int speed, int x, int y, Direction direction) {
        this.health = health;
        this.speed = speed;
        this.speedOriginal = speed;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.status = Status.NORMAL;
    }

    public int getHealth() {
        return this.health;
    }

    public void draw(TermBuffer buf, List<String> workBuf) {
        try {
            buf.incrementCell(this.getCell(buf.getCellSize()), workBuf);
        } catch (Exception e) {
            return;
        }
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    /**
     * Damage the bloon
     * @param damage damage point(s) taken
     */
    public void takeDamage(double damage) {
        this.health -= damage;
        // System.out.println(this + " took " + damage + " damage");
        if (this.health <= 0) {
            this.health = 0;
            this.status = Status.DESTROYED;
            // System.out.println(this + " is destroyed");
        }
    }


    /**
     * Verify if the bloon is popped
     * @return true if health <= 0, else false
     */
    public boolean isDestroyed() {
        return this.health <= 0;
    }


    /**
     * Freeze the bloon for `frozenDuration`
     */
    public void freeze() {
        this.status = Status.FROZEN;
        System.out.println(this + " is frozen");
        this.frozenTimer = 0; // On reinitialise le timer au moment de l'impact car deja dans updatestatus
    }


    /**
     * Slow the bloon for `slowedDuration` time
     */
    public void slow() {
        if (this.status != Status.FROZEN) { 
            this.status = Status.SLOWED;
            System.out.println(this + " is slowed");
            this.slowedTimer = 0; // On réinitialise le timer ici aussi
            this.speed = (int)Math.floor((float)this.speedOriginal * Bloon.slowFactor);
        }
    }

    public Direction getDirection() {
        return this.direction; // pour le calcul de methode distanceToEdge dans BoardFree
    }


    /**
     * Reset the bloon's status, speed and timers
     */
    public void resetStatus() {
        if (this.status != Status.DESTROYED) {
            this.status = Status.NORMAL;
        }
        this.speed = this.speedOriginal;
        this.frozenTimer = 0;
        this.slowedTimer = 0;
    }


    /**
     * Update the bloon's status
     * appelée à chaque tick du jeu ?
     * @param tick the time between 2 bloon's movements
     */
    public void updateStatus(float tick) {
        switch (this.status) {
            case FROZEN:
                this.frozenTimer += tick;
                if (this.frozenTimer >= Bloon.frozenDuration) {
                    System.out.println(this + " is not frozen anymore");
                    this.resetStatus();
                }
                break;

            case SLOWED:
                this.slowedTimer += tick;
                if (this.slowedTimer >= Bloon.slowedDuration) {
                    this.resetStatus();
                }
                break;

            default:
                break;
        }
    }


    /**
     * Change the direction of the bloon
     * @param newDirection the new direction
     */
    public void updateDirection(Direction newDirection) {
        this.direction = newDirection;
    }


    /**
     * Verify if the bloon can move
     * @return false if it's frozen
     * @param tick the time between 2 bloon's movements
     */
    public boolean canMove(float tick) {
        updateStatus(tick);
        return this.status != Status.FROZEN && this.status != Status.DESTROYED;
    }
    

    /**
     * The bloon is located on a cell
     * @param cellSize the size of one cell
     * @return the cell the bloon is located on
     */
    public Cell getCell(int cellSize) {
        // un ballon en coord(12, 5) n'est pas au milieu de la case (1, 0)
        // le milieu de la case (1, 0) est (15, 5)
        // donc en (12, 5), on fait que le ballon est encore en (0, 0)
        // pour qu'ils restent toujours au milieu de la case
        return new Cell((x-cellSize/2)/cellSize, (y-cellSize/2)/cellSize);
    }


    /** 
     * Verify if the bloon is in the board
     * @param w the width of the board
     * @param h the height of the board
     * @param cellSize the size of a cell in the board
     * @return true if it is out of bound, else false
     */
    public boolean isNotInBound(int w, int h, int cellSize) {
        return (this.x < 0) || (this.x >= w*cellSize) || (this.y < 0) || (this.y >= h*cellSize);
    }


    /**
     * Move the bloon
     * @param tick the time between 2 bloon's movements
     */
    public void move(float tick) {
        if (this.canMove(tick)) {
            switch (direction) {
                case HAUT:
                    this.y -= this.speed;
                    break;
                case BAS:
                    this.y += this.speed;
                    break;
                case GAUCHE:
                    this.x -= this.speed;
                    break;
                case DROITE:
                    this.x += this.speed;
                    break;
            }
        } 
    } 
    

    @Override
    public String toString() {
        return "Bloon(" + x + ", " + y + ") " + 
            "\u001B[31m" +  this.health + " HP " + 
            "\u001B[34m" + this.status + "\u001B[0m";
    }
}
