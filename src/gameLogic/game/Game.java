package gameLogic.game;

import gameLogic.bloon.Bloon;
import gameLogic.board.*;
import gameLogic.game.utils.*;
import gameLogic.player.*;
import gameLogic.tower.*;
import gameLogic.tower.monkey.*;
import termDisplay.*;

public class Game {
	private Board board;
	private Player player;
	private int width;
	private int height;        
	private int cellSize;
	private int turn;
	private int timer;
	private TermBuffer display;
	private int boardpath;

    public Game(int width, int height, Board board, Player player, TermBuffer display) {
		this.turn = 1;
		this.player = player;
		this.width = width;
		this.height = height;
		this.cellSize = 10;
        this.board = board;
		this.display = display;
		if(this.board.getClass().getName().equals("gameLogic.board.BoardPath")){
			this.boardpath = 1;
		} else{
			this.boardpath = 0;
		}
    }

    public static Game createGameUserInput() {
        Board b;
        int width = askNumber("Choose your board width (at least 10 or leave it empty to get base value) : ", 10, 100,12);
		int height = askNumber("Choose your board height (at least 4 or leave it empty to get base value) : ", 4, 100,4);
		String boardType = askBoardType();
		if(boardType.equals("free"))
        {
            b = new BoardFree(width, height, 10);
        } 
		else {
            b = new BoardPath(width, height, 10);
        }

        return new Game(width, height, b, new Player(), new TermBuffer(b));
        


    }

    /**
     * Ask the player a question
	 * @param text the question
     * @return the input of the player
     */
    public static String askPlayer(String text){
        System.out.print(text);
        String userString = Input.readString();
        return userString;
    }

    /**
     * Ask the player to select a boardType
     * @return The coordinate of the cell targeted as a string
     */
	public static String askBoardType(){
		String boardChose = "";
		while (!boardChose.toLowerCase().equals("free") && !boardChose.toLowerCase().equals("path")){
			boardChose = askPlayer("Choose your board type (free / path) : ");
			System.out.println(boardChose);
		}
		return boardChose.toLowerCase();
	}

	public static boolean askStop(){
		return askPlayer("Type Stop if you want to cancel : ").toLowerCase().equals("stop");
	}

    /**
     * Ask the player to select a number between a and b
	 * @param text the question
	 * @param a the smallest number
	 * @param b the biggest number
	 * @param baseValue the base value if user chose nothing
     * @return the number chosen OR empty string (empty string is to select the base value)
     */
	public static int askNumber(String text, int a, int b, int baseValue){
		String numberChose = "notChosenyet";
		while (!(numberChose.matches("[0-9]+"))) {
			numberChose = askPlayer(text);
			if(!numberChose.matches("[0-9]+")){
				return baseValue;
			}
			else { 
				if(!(Integer.parseInt(numberChose) >= a && Integer.parseInt(numberChose) <= b)){
					numberChose = "Wrong";
				}
			}
		}
		return Integer.parseInt(numberChose);
	}

    public void bloonOut(Bloon b) {
        this.player.removeLife(b);
        System.out.println(b + " is out");
    }

    public void bloonDestroyed(Bloon b) {
        this.player.addCredits(10);
        System.out.println(b + " is destroyed");
    }

	/**
	 * Ask the player where he wants to place the new monkey
	 *
	 * @param m the new monkey
	 *
	 **/ 
	public void askWherePlaceMonkey(Monkey m){
		baseSySo();
		System.out.println(String.format("Your board width : %d",this.width));
		System.out.println(String.format("Your board height : %d",this.height));
		int x = askNumber(String.format("Choose x coordinate max %d (default value 1) : ",this.width), 1, this.width,1);
		int y = askNumber(String.format("Choose y coordinate max %d (default value 1) : ",this.height), 1, this.height,1);
		m.setX(5+((x-1)*this.cellSize));
		m.setY(5+((y-1)*this.cellSize));
		while (!(this.board.canPlaceMonkey(m))){
			if(askStop()){return;}
			x = askNumber(String.format("Choose x coordinate max %d (default value 1) : ",this.width), 1, this.width,1);
			y = askNumber(String.format("Choose y coordinate max %d (default value 1) : ",this.height), 1, this.height,1);
			m.setX(5+((x-1)*this.cellSize));
			m.setY(5+((y-1)*this.cellSize));
		}
		this.board.addMonkey(m);
	}

	public void askWhichMonkey(){
		int nbMonkeys = this.board.getMonkeys().size();
		baseSySo();
		System.out.println("1 - " + "➽" + " DartMonkey - cost : \u001B[33m 200 ₦ \u001B[0m");
		System.out.println("2 - " + "\u001B[31m" + "➊" + "\u001B[0m" + " BombMonkey - cost : \u001B[33m 600 ₦ \u001B[0m");
		System.out.println("3 - " + "\u001B[2m" + "\u001B[33m" + "\u001B[3m" + "❖" + "\u001B[0m" + " GlueMonkey - cost : \u001B[33m 500 ₦ \u001B[0m");
		System.out.println("4 - " + "\u001B[34m" + "✦" + "\u001B[0m" + " Gorilla - cost : \u001B[33m 1200 ₦ \u001B[0m");
		System.out.println("5 - " + "\u001B[36m" + "\u001B[3m" + "❄" + "\u001B[0m" + " IceMonkey - cost : \u001B[33m 400 ₦ \u001B[0m");
		System.out.println("6 - " + "\u001B[35m"  + "✸" + "\u001B[0m" + " NeedleTower - cost : \u001B[33m 350 ₦ \u001B[0m");
		System.out.println("7 - " + "\u001B[3m" + "\u001B[32m" + "✪" + "\u001B[0m" + " SniperMonkey - cost : \u001B[33m 200 ₦ \u001B[0m");
		Monkey m = selectMonkey(askNumber("Select a monkey (default 1) : ",1,7,1));
		while(!(this.player.enoughCreditToBuy(m.getPrice()))){
			if(askStop()){return;}
			m = selectMonkey(askNumber("Select a monkey (default 1) : ",1,7,1));
		}
		askWherePlaceMonkey(m);
		if(nbMonkeys < this.board.getMonkeys().size()){
			this.player.buyMonkey(m);
		}
	}

	public Monkey selectMonkey(int s){
		switch(s){
			case 2:
				return new BombMonkey(0,0);
			case 1:
				return new DartMonkey(0,0);
			case 3:
				return new GlueMonkey(0,0);
			case 4:
				return new Gorilla(0,0);
			case 5:
				return new IceMonkey(0,0);
			case 6:
				return new NeedleTower(0,0);
			case 7:
				return new SniperMonkey(0,0);
			default :
				return new DartMonkey(0,0);
		}
	}

	public void askWhichMonkeyToSell(){
		baseSySo();
		int i = 1;
		for(Monkey m : this.board.getMonkeys()){
			System.out.println(i + " - " +m.toString());
			i++;
		}
		int chosenMonkey = askNumber("Choose a Monkey to sell from 1 to " + this.board.getMonkeys().size() + " (default value 1) : ",1, this.board.getMonkeys().size(), 1);
		if(askStop()){return;}
		sellMonkey(this.board.getMonkeys().get(chosenMonkey-1));
	}

	public void sellMonkey(Monkey m){
		int newMoney = m.getPrice()/2;
		if(m.getEvols().length > 0){
			for(Evolution e : m.getEvols()){
				if(e.getisUsed() == 1){
					newMoney += e.getPrice()/2;
				}
			}
		}
		this.player.addCredits(newMoney);
		this.board.getMonkeys().remove(m);
	}


	public void askWhichMonkeyToBuyOrSellEvols(){
		baseSySo();
		int i = 1;
		for(Monkey m : this.board.getMonkeys()){
			System.out.println(i + " - " +m.toString());
			i++;
		}
		int chosenMonkey = askNumber("Choose a Monkey to evolve from 1 to " + this.board.getMonkeys().size() + " (default value 1) : ",1, this.board.getMonkeys().size(), 1);
		while((this.board.getMonkeys().get(chosenMonkey-1).getEvols().length == 0)){
			if(this.board.getMonkeys().get(chosenMonkey-1).getEvols().length == 0){
				System.out.println("this monkey doesnt have any evolution");
			}
			if(askStop()){return;}
			chosenMonkey = askNumber("Choose a Monkey to evolve from 1 to " + this.board.getMonkeys().size() + " (default value 1) : ",1, this.board.getMonkeys().size(), 1);
		}
		int bs = askNumber("Choose if you want to buy or sell, type 1 if you want to buy, 0 to sell (default value 1) : ", 0,1,1);
		askWhichEvolution(this.board.getMonkeys().get(chosenMonkey-1), bs);
	}

	/**
	 * ask which evolution the player wants to buy OR sell
	 *
	 * @param m the chosen monkey
	 * @param bs set to 1 if the player wants to buy and 0 if the player wants to sell;
	 **/
	public void askWhichEvolution(Monkey m, int bs){
		baseSySo();
		int i = 1;
		for(Evolution e : m.getEvols()){
			System.out.println(i + " - " + e.toString());
			i++;
		}
		int chosenEvol = askNumber("Choose an Evolution from 1 to " + m.getEvols().length +" (default value 1) : ", 1, m.getEvols().length, 1);
		while(m.getEvols()[chosenEvol-1].getisUsed() == bs){
			if(askStop()){return;}
			chosenEvol = askNumber("Choose an Evolution from 1 to " + m.getEvols().length +" (default value 1) : ", 1, m.getEvols().length, 1);
		}
		if(this.player.enoughCreditToBuy(m.getEvols()[chosenEvol-1].getPrice())){
			m.getEvols()[chosenEvol-1].updateUse();
			this.player.buyAndSellEvolution(m.getEvols()[chosenEvol-1], bs);
		}
	}
	
	public void baseSySo(){
		System.out.print("\033[H\033[2J");
		System.out.println("-====- turn "+this.turn+" -====-" + String.format("\u001B[31m %d HP \u001B[0m --- \u001B[33m %d ₦ \u001B[0m", this.player.getLives(),this.player.getCredits()));
		if(this.boardpath == 1){
			System.out.println("\u001B[0m" + "Start : " + "\u001B[36m"+ "⚐" + "\u001B[0m" + " -- " + "End : " +"\u001B[33m" + "⚑" + "\u001B[0m");
		}
		System.out.println("\u001B[34m"+ (6*(1+(this.turn/3))) + "\u001B[0m" + " Bloons this round " + "\n");
		System.out.println(this.display);
        
	}


	/**
	 * ask the player to chose an action
	 *
	 * @return 1 if the turn ended else 0
	 **/
	public int askAction(){
		baseSySo();
		System.out.println("1 - buy a new monkey");
		System.out.println("\u001B[2m" + "2 - sell a monkey" + "\u001B[0m");
		System.out.println("3 - buy or sell an evolution");
		System.out.println("\u001B[2m" + "4 - end your turn" + "\u001B[0m");
		int chosenAction = askNumber("Choose an action (default value 4) : ", 1,4,4);
		if (chosenAction == 1){
			askWhichMonkey();
			return 0;
		} else if (chosenAction == 2){
			if(this.board.getMonkeys().size() > 0){
				askWhichMonkeyToSell();
			}
			return 0;
		} else if (chosenAction == 3){
			if(this.board.getMonkeys().size() > 0){
				askWhichMonkeyToBuyOrSellEvols();
			}
			return 0;
		} else{
			return 1;
		}
	}

	public void playerTurn(){
		int action = askAction();
		while (action != 1){
			action = askAction();
		}

		int bloonNumber = 6*(1+(this.turn/3));

		//int bloonNumber = askNumber("Choose how many bloons you want to fight this turn (default value 3) : ", 1, 50,3); // temporaire pour le livrable
		int addedBloons = 0;

		// for (int j = 0; j < bloonNumber; j++) { // Ajouts des ballons	
		// 	this.board.addBloon(1, 5);
		// }

		while ((!this.board.getBloons().isEmpty()) || (addedBloons < bloonNumber)) { // Tour de jeu

			if(addedBloons < bloonNumber){
				this.board.addBloon(1, 5);
				addedBloons++;
			}

			this.board.updateGame(1f, (b) -> this.bloonOut(b), (b) -> this.bloonDestroyed(b));

            try {
                Thread.sleep(250);
            } catch (Exception e) {
            }
			this.timer += 1;
			baseSySo();

			if (this.board.allBloonsDestroyed()) {
				System.out.println("All bloons destroyed !");
				break;
			}

		}

		this.turn = this.turn + 1;
	}

	public void playGame(){
		while(this.player.isAlive()){
			playerTurn();
		}
		System.out.println("Game Over");
	}
}
