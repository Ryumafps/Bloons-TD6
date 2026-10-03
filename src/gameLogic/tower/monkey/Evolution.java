package gameLogic.tower.monkey;

public class Evolution{
	private EvolType evol;
	private int price;
	private double value;
	private int isUsed;
	
	/**Build an Evolution based on its evolType, its price and value
	 *
	 * @param evol the evoltype of this evolution
	 * @param price the price of this evolution
	 * @param value the value of this evolution
	 **/
	public Evolution(EvolType evol, int price, double value){
		this.evol = evol;
		this.price = price;
		this.value = value;
		this.isUsed = 0;
	}
	
	/**Return the evolType of this Evolution
	 *
	 * @return the evolType of this Evolution
	 **/
	public EvolType getEvol(){
		return this.evol;
	}

	/**Return the price of this Evolution
	 *
	 * @return the price of this Evolution
	 **/
	public int getPrice(){
		return this.price;
	}

	/**Return the value of this Evolution
	 *
	 * @return the value of this Evolution
	 **/
	public double getValue(){
		return this.value;
	}

	/**Calculate and return the applied Value of this Evolution
	 *
	 * @param base the base value used to calculate the applied value
	 * @return the evolType of this Evolution
	 **/
	public double getAppliedValue(double base){
		return Math.ceil(base*this.value*this.isUsed);
	}

	/** Return 1 if this evolution is used, 0 if not
	 *
	 * @return 1 if this evolution is used, 0 if not
	 **/ 
	public int getisUsed(){
		return this.isUsed;
	}

	/** Update the isUsed based on its current state**/
	public void updateUse(){
		if (this.isUsed == 0){
			this.isUsed = 1;
		} else{
			this.isUsed = 0;
		}
	}

	public String toString() {
		return this.price + " ₦ : Evolution "+ this.evol.toString() +" "+ this.value;
	}
}
