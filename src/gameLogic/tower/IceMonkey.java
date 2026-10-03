package gameLogic.tower;

import gameLogic.tower.monkey.*;
import termDisplay.TermBuffer;

public class IceMonkey extends Monkey{

	//protected int x;
	//protected int y;
	//protected int price;
	//protected ProjType proj;
	//protected double dammage;
	// protected int range;
	//protected double shotSpeed;
	//protected Evolution[] evolutions;

	/** Build a IceMonkey based on its coordinate, price, projectile, range and shotspeed
	 *
	 * @param x the x coordinate of this Monkey
	 * @param y the y coordinate of this Monkey
	 * @param proj the projectile of this monkey
	 **/ 
	public IceMonkey(int x, int y){
		super(x, y, 400, ProjType.Ice, 20, 1500, 0);
	}

    public void draw(TermBuffer buf,  java.util.List<String> workBuf) {
		String name = "\u001B[36m" + "\u001B[3m" + "❄" + "\u001B[0m";
        buf.setTower(this.getCell(buf.getCellSize()), name, workBuf);
    }

	public String toString() {
		return "\u001B[36m" + "\u001B[3m" + "❄" + "\u001B[0m" + " Ice"+ super.toString();
	}
}
