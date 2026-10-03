package gameLogic.tower;

import gameLogic.tower.monkey.*;
import termDisplay.TermBuffer;

public class GlueMonkey extends Monkey{

	//protected int x;
	//protected int y;
	//protected int price;
	//protected ProjType proj;
	//protected double dammage;
	// protected int range;
	//protected double shotSpeed;
	//protected Evolution[] evolutions;

	/** Build a GlueMonkey based on its coordinate, price, projectile, range and shotspeed
	 *
	 * @param x the x coordinate of this Monkey
	 * @param y the y coordinate of this Monkey
	 * @param proj the projectile of this monkey
	 **/ 
	public GlueMonkey(int x, int y){
		super(x, y, 500, ProjType.Glue, 20, 1500, 0);
	}

    public void draw(TermBuffer buf,  java.util.List<String> workBuf) {
		String name = "\u001B[2m" + "\u001B[33m" + "\u001B[3m" + "❖" + "\u001B[0m";
        buf.setTower(this.getCell(buf.getCellSize()), name, workBuf);
    }

	public String toString() {
		return "\u001B[2m" + "\u001B[33m" + "\u001B[3m" + "❖" + "\u001B[0m" + " Glue"+ super.toString();
	}
}
