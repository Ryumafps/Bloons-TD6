package gameLogic.tower.monkey;
import java.util.List;

import gameLogic.board.Cell;
import gameLogic.board.CellOutOfBoundsException;
import gameLogic.tower.monkey.Evolution;
import termDisplay.Drawable;
import termDisplay.TermBuffer;

public abstract class Monkey implements Drawable {
	
	protected int x;
	protected int y;
	protected int price;
	protected ProjType proj;
	protected double dammage;
	protected double range;
	protected double shotSpeed;
	protected Evolution[] evolutions;
	protected double coolDown;

	/** Build a Monkey based on its coordinate, price, projectile, range and shotspeed
	 *
	 * @param x the x coordinate of this Monkey
	 * @param y the y coordinate of this Monkey
	 * @param price the price of this monkey
	 * @param proj the projectile of this monkey
	 * @param range the range of this monkey 
	 * @param shotSpeed the shotspeed of this monkey
	 **/ 
	public Monkey(int x, int y, int price, ProjType proj, double range, double shotSpeed, double dammage){
		this.x = x;
		this.y = y;
		this.price = price;
		this.proj = proj;
		this.range = range;
		this.shotSpeed = shotSpeed;
		this.dammage = dammage;
		this.coolDown = 0;
		this.evolutions = new Evolution[0];
	}

	/** return the X coordinate of this monkey
	 *
	 * @return the X coordinate of this monkey
	 **/
	public int getX(){
		return this.x;
	}

	/** return the Y coordinate of this monkey
	 *
	 * @return the Y coordinate of this monkey
	 **/
	public int getY(){
		return this.y;
	}

	/**
	 * change the x value of this monkey
	 * @param nx the new x value 
	 **/
	public void setX(int nx){
		this.x = nx;
	}

	/**
	 * change the y value of this monkey
	 * @param ny the new y value 
	 **/
	public void setY(int ny){
		this.y = ny;
	}

    public void draw(TermBuffer buf,  java.util.List<String> workBuf) {
        buf.setTower(this.getCell(buf.getCellSize()), "X", workBuf);
    }

	/** return the price of this monkey
	 *
	 * @return the price of this monkey
	 **/
	public int getPrice(){
		return this.price;
	}

	/** return the proj of this monkey
	 *
	 * @return the proj of this monkey
	 **/
	public ProjType getProj(){
		return this.proj;
	}

	/** return the Amount of projectile shooted by this monkey
	 *
	 * @return the Amount of projectile shooted by this monkey
	 **/
	public double getProjAmount(){
		return 1;
	}

	/** return the dammage of this monkey
	 *
	 * @return the dammage of this monkey
	 **/
	public double getDammage(){
		return this.dammage;
	}

	/** return the Range of this monkey
	 *
	 * @return the Range of this monkey
	 **/
	public double getRange(){
		return this.range;
	}

	/** return the shotSpeed of this monkey
	 *
	 * @return the shotSpeed of this monkey
	 **/
	public double getShotSpeed(){
		return this.shotSpeed;
	}

	/** return the evolutions of this monkey
	 *
	 * @return the evolutions of this monkey
	 **/
	public Evolution[] getEvols(){
		return this.evolutions;
	}

    /**
     * The bloon is located on a cell
     * @param cellSize the size of one cell
     * @return the cell the bloon is located on
     */
    public Cell getCell(int cellSize) {
        return new Cell((x-cellSize/2)/cellSize, (y-cellSize/2)/cellSize);
    }

	/** return a thrownProj based on this monkey's data and the bloons position
	 *
	 * @return a trownProj based on this monkey's data
	 **/
	public ThrownProj shoot(int bloonX, int bloonY){
		resetTimer();
		System.out.println(this + " shoot !");
		return new ThrownProj(this.proj, this.dammage, 1, this.x, this.y, bloonX, bloonY, this.range);
	}

	/** return true if the bloon is in this monkey's range
	 * 
	 * @param bloonX the bloon X coordinate
	 * @param bloonY the bloon Y coordinate
	 * @param cellSize the size of the board cells 
	 * @return true if the bloon is in this monkey's range
	 **/ 
	public boolean inRange(int bloonX, int bloonY, int cellSize){
		return (Math.floor(Math.sqrt(Math.pow(Math.abs(this.x - bloonX),2) + Math.pow(Math.abs(this.y - bloonY),2))) <=(this.range));
	}

	public void updateTimer() {
		if (this.coolDown > 0) {
			this.coolDown--;
		}
	}

	public boolean canShoot() {
		return this.coolDown == 0;
	}

	public void resetTimer() {
		this.coolDown = this.shotSpeed / 100.0; // Remet le compteur au max apres que le singe a tier 
	}

	public String toString() {
		return "Monkey(" + this.x + ", " + this.y + ")";
	}

}
