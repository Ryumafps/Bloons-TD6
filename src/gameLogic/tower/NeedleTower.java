package gameLogic.tower;

import gameLogic.tower.monkey.*;
import termDisplay.TermBuffer;

public class NeedleTower extends Monkey{

	//protected int x;
	//protected int y;
	//protected int price;
	//protected ProjType proj;
	//protected double dammage;
	// protected int range;
	//protected double shotSpeed;
	protected double projAmount;
	//protected Evolution[] evolutions;

	/** Build a NeedleTower based on its coordinate, price, projectile, range and shotspeed
	 *
	 * @param x the x coordinate of this Monkey
	 * @param y the y coordinate of this Monkey
	 * @param proj the projectile of this monkey
	 **/ 
	public NeedleTower(int x, int y){
		super(x, y, 350, ProjType.Needle, 10, 1200, 1);
		this.projAmount = 8;
		Evolution e1 = new Evolution(EvolType.Range, 150, 0.2);
		Evolution e2 = new Evolution(EvolType.ShotSpeed, 200, 0.25);
		this.evolutions = new Evolution[]{e1, e2};
	}

    public void draw(TermBuffer buf,  java.util.List<String> workBuf) {
		String name = "\u001B[35m"  + "✸" + "\u001B[0m";
		if(this.evolutions[0].getisUsed() == 1){
			name = "\u001B[3m" + name;
		}
		if(this.evolutions[1].getisUsed() == 1){
			name = "\u001B[2m" + name;
		}
        buf.setTower(this.getCell(buf.getCellSize()), name, workBuf);
    }

	/** return the Range of this monkey
	 *
	 * @return the Range of this monkey
	 **/
	public double getRange(){
		return super.getRange() + this.evolutions[0].getAppliedValue(this.range);
	}

	/** return the shotSpeed of this monkey
	 *
	 * @return the shotSpeed of this monkey
	 **/
	public double getShotSpeed(){
		return super.getShotSpeed() + this.evolutions[1].getAppliedValue(this.shotSpeed);
	}

	/** return the Amount of projectile shooted by this monkey
	 *
	 * @return the Amount of projectile shooted by this monkey
	 **/
	public double getProjAmount(){
		return this.projAmount;
	}

	/** return the evolutions of this monkey
	 *
	 * @return the evolutions of this monkey
	 **/
	public Evolution[] getEvols(){
		return this.evolutions;
	}

	public String toString() {
		String name = "\u001B[35m"  + "✸" + "\u001B[0m";
		if(this.evolutions[0].getisUsed() == 1){
			name = "\u001B[3m" + name;
		}
		if(this.evolutions[1].getisUsed() == 1){
			name = "\u001B[2m" + name;
		}
		return name + " Needle"+ super.toString();
	}
}
