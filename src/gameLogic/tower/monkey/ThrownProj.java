package gameLogic.tower.monkey;

public class ThrownProj{
	protected ProjType proj;
	protected double dmg;
	protected int speed;
	protected double x;
	protected double y;
	protected double baseX;
	protected double baseY;
	protected double angle;
	protected double targetX;
	protected double targetY;
	protected int bombTicker;
	protected double maxRange;

	/** build a ThrownProj based on its projType, speed, x and y coordinate and angle
	 *
	 * @param dmg the dammage of this projectile
	 * @param speed the speed of this projectile
	 * @param x the x coordinate 
	 * @param y the y coordinate
	 * @param angle the angle (radian)
	 **/
	public ThrownProj(ProjType proj,double dmg, int speed, double x, double y, double targetX, double targetY, double maxRange){
		this.proj = proj;
		this.dmg = dmg;
		this.x = x;
		this.y = y;
		this.baseX = x;
		this.baseY = y;
		this.targetX = targetX;
		this.targetY = targetY;
		this.angle = Math.atan2((targetY-this.y),(targetX-this.x));
		// this.angle = this.angle*this.angle;
		this.speed = speed;
		if(this.angle > Math.toRadians(90) && this.angle <= Math.toRadians(270)){
			this.speed = -speed;
		}
		this.bombTicker = 2;
		this.maxRange = maxRange;
	}

	/** return this ThrownProj's projectile 
	 *
	 * @return this ThrownProj projectile 
	 **/
	public ProjType getProj(){
		return this.proj;
	}

	/** return this ThrownProj's dammage
	 *
	 * @return this ThrownProj's dammage
	 **/
	public double getDammage(){
		return this.dmg;
	}

	/** return this ThrownProj's speed
	 *
	 * @return this ThrownProj's speed
	 **/
	public int getSpeed(){
		return this.speed;
	}

	/** return this ThrownProj's range
	 *
	 * @return this ThrownProj's range
	 **/
	public double getRange(){
		return this.maxRange;
	}

	/** return this ThrownProj's X coordinate
	 *
	 * @return this ThrownProj X coordinate
	 **/
	public double getX(){
		return this.x;
	}

	/** return this ThrownProj's Y coordinate
	 *
	 * @return this ThrownProj Y coordinate
	 **/
	public double getY(){
		return this.y;
	}

	/** return this ThrownProj's angle (in radian) 
	 *
	 * @return this ThrownProj's angle
	 **/
	public double getAngle(){
		return this.angle;
	}

	/** return True if this thrownProj is on its target coordinate
	 *
	 * @return True if this thrownProj is on its target coordinate
	 **/
	public boolean onTargetCoordinates(){
		return (this.x == this.targetX && this.y == this.targetY);
	}

	/** return true if this thrownProj is a bomb
	 *
	 * @return true if this thrownProj is a bomb
	 **/
	public boolean isABomb(){
		return ((this.proj == ProjType.Bomb) || (this.proj == ProjType.ExtraBomb));
	}

	/** return this TrownProj's bombticker status
	 *
	 * @return this ThrownProj's bombticker status
	 **/
	public int getBombTicker(){
		return this.bombTicker;
	}

	/** return true if this TrownPron explode
	 *
	 * @return true if this TrownPron explode
	 **/
	public boolean bombExplode(){
		return this.bombTicker == 0;
	}

	public void updateBombTicker(){
		this.bombTicker = this.bombTicker-1;
		if (this.bombTicker < 0){
			this.bombTicker = 0;
		}
	}

	/** update the current coordinate of this projectile 
	 **/
	public void updateCoordinate(){
		/*
		double m = Math.tan(this.angle);
		if ((m - Math.toRadians(90)) < 0.01){
			this.y = this.y + this.speed;
		} else {
			this.x = this.x + this.speed;
			double b = this.y - (m*this.x);
			this.y = (m*this.x)+b;
		}
			*/
		this.x = targetX;
		this.y = targetY;
	}

	/**
	 * return true if this proj is still on range
	 *
	 * @param cellSize the cellsize 
	 * @return true if this proj is still on range
	 **/
	public boolean isOnRange(int cellSize){
		return (Math.floor(Math.sqrt(Math.pow(Math.abs(this.x - this.baseX),2) + Math.pow(Math.abs(this.y - this.baseY),2))) <=(this.maxRange/cellSize)); 
	}

	/** update the current coordinate of this projectile if not a bomb on its target cell, else change bomb status
	 **/
	public void update(){
		if (this.proj == ProjType.Bomb && onTargetCoordinates()){
			updateBombTicker();
		} else {
			updateCoordinate();
		}
	}

	public boolean isFreezing() {
		return this.proj == ProjType.Ice;
	}
	public boolean isSlowing() {
		return this.proj == ProjType.Glue;
	}
}

