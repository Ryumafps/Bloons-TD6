package gameLogic.tower;

import gameLogic.tower.monkey.*;
import termDisplay.TermBuffer;

public class BombMonkey extends Monkey{

	//protected int x;
	//protected int y;
	//protected int price;
	//protected ProjType proj;
	//protected double dammage;
	// protected int range;
	//protected double shotSpeed;
	protected int projAmount;
	//protected Evolution[] evolutions;

	/** Build a BombMonkey based on its coordinate, price, projectile, range and shotspeed
	 *
	 * @param x the x coordinate of this Monkey
	 * @param y the y coordinate of this Monkey
	 * @param proj the projectile of this monkey
	 **/ 
	public BombMonkey(int x, int y){
		super(x, y, 600, ProjType.Bomb, 10, 1800, 2);
		this.projAmount = 1;
		Evolution e1 = new Evolution(EvolType.Range, 250, 0.5);
		Evolution e2 = new Evolution(EvolType.ShotSpeed, 300, 0.25);
		Evolution e3 = new Evolution(EvolType.Attack, 200, 1);
		Evolution e4 = new Evolution(EvolType.Proje, 400, 1);
		this.evolutions = new Evolution[]{e1, e2, e3, e4};
	}

	public void draw(TermBuffer buf,  java.util.List<String> workBuf) {
		String name = "\u001B[31m" + "➊" + "\u001B[0m";
		if(this.evolutions[3].getisUsed() == 1){
			name = "\u001B[33m" + "➋" + "\u001B[0m";
		}
		if(this.evolutions[0].getisUsed() == 1){
			name = "\u001B[3m" + name;
		}
		if(this.evolutions[1].getisUsed() == 1){
			name = "\u001B[2m" + name;
		}
		if(this.evolutions[2].getisUsed() == 1){
			name = "\u001B[4m" + name;
		}
		buf.setTower(this.getCell(buf.getCellSize()), name, workBuf);
	}
	
	/** return the dammage of this monkey
	 *
	 * @return the dammage of this monkey
	 **/
	public double getDammage(){
		return super.getDammage() + this.evolutions[2].getAppliedValue(1);
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
		return this.projAmount + this.evolutions[3].getAppliedValue(1);
	}

	/** return the evolutions of this monkey
	 *
	 * @return the evolutions of this monkey
	 **/
	public Evolution[] getEvols(){
		return this.evolutions;
	}

	public String toString() {
		String name = "\u001B[31m" + "➊" + "\u001B[0m";
		if(this.evolutions[3].getisUsed() == 1){
			name = "\u001B[33m" + "➋" + "\u001B[0m";
		}
		if(this.evolutions[0].getisUsed() == 1){
			name = "\u001B[3m" + name;
		}
		if(this.evolutions[1].getisUsed() == 1){
			name = "\u001B[2m" + name;
		}
		if(this.evolutions[2].getisUsed() == 1){
			name = "\u001B[4m" + name;
		}
		return  name + " Bomb"+ super.toString();
	}

}
