package gameLogic.board;

import gameLogic.bloon.*;
import gameLogic.game.Game;
import gameLogic.tower.monkey.*;
import termDisplay.Drawable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Board {

    // number of cells on the x-axis
    protected int width;

    // number of cells on the y-axis
    protected int height;

    // the length of a cell
    protected int cellSize;

	// the bloons on the board
	protected List<Bloon> bloons;

	// the towers (monkeys) on the board
	protected List<Monkey> monkeys;

	protected List<ThrownProj> projectiles;

	protected List<ThrownProj> bombs;

    /**
     * Constructor for a Board
     * @param width its width
     * @param height its height
     * @param cellSize the size of one cell
     */
    public Board(int width, int height, int cellSize) {
        this.cellSize = cellSize;
        this.height = height;
        this.width = width;
		this.bloons = new ArrayList<>();
		this.monkeys = new ArrayList<>();
		this.projectiles = new ArrayList<>();
		this.bombs = new ArrayList<>();
	}

    public List<Drawable> getDrawable() {
        List<Drawable> res = new ArrayList<>();
        res.addAll(this.monkeys);
        res.addAll(this.bloons);
        return res;
    }

    /**
     * Getter for the width
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    public int getCellSize() {
        return this.cellSize;
    }

    /**
     * Setter for the width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Getter for the height
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter for the height
     */
    public void setHeight(int height) {
        this.height = height;
    }

	public List<Monkey> getMonkeys() {
		return this.monkeys;
	}
    

    /**
     * Gets the perimeter Cells of the grid
     *
     * @return the perimeter Cells of the grid
     */
    public List<Cell> getEdges() {
        List<Cell> res = new ArrayList<>();

        for (int col = 0; col < this.width; col++) {
            res.add(new Cell(col, 0));
            if (this.height > 1) {
                res.add(new Cell(col, this.height - 1));
            }
        }

        for (int row = 1; row < this.height - 1; row++) {
            res.add(new Cell(0, row));
            if (this.width > 1) {
                res.add(new Cell(this.width - 1, row));
            }
        }

        return res;
    }


    /**
     * Gets the 4-neighbours of cell
     * @param cell : the cell we want the neighbours of
     * @return the list of neighbours
     */
    public List<Cell> getNeighbours(Cell cell) {
        List<Cell> res = new ArrayList<>();
        for (int j = -1; j < 2; j++) {
            for (int i = -1; i < 2; i++) {
                if (
                    // Ne pas renvoyer cell
                    i != j &&
                    // Ne pas renvoyer les diagonales
                    Math.abs(i) + Math.abs(j) == 1 &&
                    // Ne pas renvoyer les cellules en dehors du plateau
                    0 <= cell.getX() + i && cell.getX() + i < this.width &&
                    0 <= cell.getY() + j && cell.getY() + j < this.height
                    ) {
                    res.add(new Cell(cell.getX()+i, cell.getY()+j));
                }
            }
        }
        return res;
    }

	
    /**
	 * Move all bloons accordingly to the path
	 * @param step : time between 2 moveBloons calls (either in "time unit" for terminal, or ms for graphical)
	 * @return a list of bloons leaving the board at that step
	*/
    public abstract List<Bloon> moveBloons(float step, Consumer<Bloon> onOut, Consumer<Bloon> onDestroyed);

	/**
     * Adds a bloon to this.bloons
     * @param minSpeed : the min speed of the bloon
     * @param maxSpeed : the max speed of the bloon
     */
    public abstract void addBloon(int minSpeed, int maxSpeed);

    /**
     * Getter for the bloons
     * @return the bloons
     */
    public abstract List<Bloon> getBloons();

	/**
	 * Add a monkey to the board
	 * @param m the monkey
	 */
	public void addMonkey(Monkey m) {
		this.monkeys.add(m);
	}
	
	/**
	 * return True if canPlaceMonkey
	 *
	 * @param nm the new monkey
	 * @return true if can place the monkey
	 **/ 
	public boolean canPlaceMonkey(Monkey nm){
		for(Monkey m : this.monkeys){
			if(!(Math.sqrt(Math.pow(m.getX()-nm.getX(),2)+Math.pow(m.getY()-nm.getY(),2))>=(this.cellSize/2))){
				return false;
			}
		}
		return true;
	}


	/**
	 * Remove a monkey to the board
	 * @param m the monkey
	 */
	public void RemoveMonkey(Monkey m) {
		this.monkeys.remove(m);
	}

	/**
	 * Update every monkey -> check every bloon in the list if its in every monkey's range then shoot
	 **/
	public void updateMonkeys() {
		for (Monkey monkey : this.monkeys) {
			monkey.updateTimer();
			if (monkey.canShoot()) {
				int shots = 0;
				double maxShots = monkey.getProjAmount();
				for (Bloon bloon : this.bloons) {
					if (shots >= maxShots) 
						break;
					if (bloon.isDestroyed()) 
						continue;
					if (monkey.inRange(bloon.getX(), bloon.getY(), this.cellSize)) {
						this.projectiles.add(monkey.shoot(bloon.getX(), bloon.getY()));
						shots++;
					}
				}
			}
		}
	}

	/**
	 * apply the effect of the proj
	 *
	 * @param proj the proj
	 **/ 
	public void applyOnHit(ThrownProj proj) {
		double projX = proj.getX();
		double projY = proj.getY();
		for (Bloon bloon : this.bloons) {
			if (bloon.isDestroyed()) continue; // pas besoin dque les bloons detruits prenent dse degats
			int bloonX = bloon.getX();
			int bloonY = bloon.getY();
			if ((projX == bloonX) && (projY == bloonY)) {
				bloon.takeDamage(proj.getDammage());
				if (proj.isFreezing()) {
					bloon.freeze();
				} else if (proj.isSlowing()) {
					bloon.slow();
				}
				break;
			}
		}
	}

	/**
	 * explode every bloons on the bomb's cell and the 8 nearest ones
	 *
	 * @param proj the bomb
	 **/ 
	public void applyOnHitBomb(ThrownProj proj) {
		double projX = proj.getX();
		double projY = proj.getY();
		for (Bloon bloon : this.bloons) {
			int bloonX = bloon.getX();
			int bloonY = bloon.getY();
			if ((Math.floor(Math.sqrt(Math.pow(Math.abs(projX - bloonX),2) + Math.pow(Math.abs(projY - bloonY),2))) <=(proj.getRange()/cellSize))){ 
				bloon.takeDamage(proj.getDammage());
			}
		}
	}

	/**
	 * Update every projectiles -> move every projectiles, apply every onhit effect, and update every bomb ticker
	 **/
	public void updateProjectiles() {
		List<ThrownProj> toRemove = new ArrayList<>();
		for (ThrownProj proj : this.projectiles) {
			if (proj.onTargetCoordinates() && proj.isABomb()) {
				this.bombs.add(proj);
				toRemove.add(proj);
				continue; // on passe au suivant
			}
			proj.update();
			applyOnHit(proj);
			if (!proj.isOnRange(cellSize) || proj.onTargetCoordinates()) {
				toRemove.add(proj); // supprime apres avoir touche
			}
		}
		this.projectiles.removeAll(toRemove); // on supr apres les iteratiosn ici encore
	}

	/**
	 * Update bombs
	 **/
	public void updateBombs() {
		List<ThrownProj> toRemove = new ArrayList<>();
		for (ThrownProj bomb : this.bombs) {
			bomb.update();
			if (bomb.bombExplode()) {
				applyOnHitBomb(bomb);
				toRemove.add(bomb);
			}
		}
		this.bombs.removeAll(toRemove); // on supprime pas pendant les iteration mais apres 
	}

	/**
	 * Update everything (Monkey, proj, bombs, bloons)
	 **/
	public void updateGame(float step, Consumer<Bloon> onOut, Consumer<Bloon> onDestroyed) {
		updateMonkeys();
		updateProjectiles(); // un seul appel par tick - potentielle boucle infinie
		updateBombs();
		moveBloons(step, onOut, onDestroyed); // moveBloons a besoin d'un step
	}

	public boolean allBloonsDestroyed() {
		for (Bloon b : this.bloons) {
			if (b.getStatus() != Status.DESTROYED) {
				return false;
			}
		}
		return true;
	}
}
