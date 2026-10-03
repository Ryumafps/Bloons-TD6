package gameLogic.player;

import gameLogic.tower.*;
import gameLogic.tower.monkey.*;
import gameLogic.bloon.*;

public class Player {
    private int credits;
    private int lives;
    public Player() {
        this.credits = 2500;
        this.lives = 20;
    }

    public int getCredits() {
        return this.credits;
    }

    public int getLives() {
        return this.lives;
    }

    public void addCredits (int amount){
        this.credits += amount;
    }

    public void removeLife(Bloon b) {

        this.lives = this.lives-b.getHealth();
    }

    public boolean isAlive() {
        return this.lives > 0;
    }

	public boolean enoughCreditToBuy(int x){
		return this.credits >= x;
	}

    public boolean buyMonkey(Monkey m){
        if (this.credits >= m.getPrice()){
            this.credits -= m.getPrice();
            return true;
        }
        return false;
    }

    public int sellMonkey(Monkey m){
        int refund = m.getPrice() /2;
        this.credits += refund;
        return refund;
    }

    public void buyAndSellEvolution(Evolution e, int bs){
		if(bs == 1){
			this.credits -= e.getPrice();
		} else{
			this.credits += e.getPrice()/2;
		}

    }
}
