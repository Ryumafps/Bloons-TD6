package gameLogic.tower;

import gameLogic.tower.monkey.*;
import termDisplay.TermBuffer;

public class SniperMonkey extends Monkey{

	//protected int x;
	//protected int y;
	//protected int price;
	//protected ProjType proj;
	//protected double dammage;
	// protected int range;
	//protected double shotSpeed;
	//protected Evolution[] evolutions;

	/** Build a SniperMonkey based on its coordinate, price, projectile, range and shotspeed
	 *
	 * @param x the x coordinate of this Monkey
	 * @param y the y coordinate of this Monkey
	 * @param proj the projectile of this monkey
	 **/ 
	public SniperMonkey(int x, int y){
		super(x, y, 500, ProjType.VerySharpDart, 100000, 2000, 4);
		Evolution e1 = new Evolution(EvolType.Attack, 300, 2);
		Evolution e2 = new Evolution(EvolType.ShotSpeed, 200, 0.25);
		this.evolutions = new Evolution[]{e1, e2};
	}

    public void draw(TermBuffer buf,  java.util.List<String> workBuf) {
		String name = "\u001B[3m" + "\u001B[32m" + "✪" + "\u001B[0m";
		if(this.evolutions[0].getisUsed() == 1){
			name = "\u001B[2m" + name;
		}
		if(this.evolutions[1].getisUsed() == 1){
			name = "\u001B[4m" + name;
		}
        buf.setTower(this.getCell(buf.getCellSize()), name, workBuf);
    }

	/** return the dammage of this monkey
	 *
	 * @return the dammage of this monkey
	 **/
	public double getDammage(){
		return super.getDammage() + this.evolutions[0].getAppliedValue(1);
	}

	/** return the shotSpeed of this monkey
	 *
	 * @return the shotSpeed of this monkey
	 **/
	public double getShotSpeed(){
		return super.getShotSpeed() + this.evolutions[1].getAppliedValue(this.shotSpeed);
	}

	/** return the evolutions of this monkey
	 *
	 * @return the evolutions of this monkey
	 **/
	public Evolution[] getEvols(){
		return this.evolutions;
	}

	public String toString() {
		String name = "\u001B[3m" + "\u001B[32m" + "✪" + "\u001B[0m";
		if(this.evolutions[0].getisUsed() == 1){
			name = "\u001B[2m" + name;
		}
		if(this.evolutions[1].getisUsed() == 1){
			name = "\u001B[4m" + name;
		}
		return name + " Sniper"+ super.toString();
	}
}
