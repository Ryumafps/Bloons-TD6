package livrables;

import gameLogic.bloon.Bloon;
import gameLogic.board.*;
import gameLogic.tower.*;
import gameLogic.tower.monkey.*;
import gameLogic.game.*;
import java.util.Random;

public class Livrable5 {

    public static void main(String[] args) {
		Game game = Game.createGameUserInput();
		while (true){
			game.playerTurn();
		}
	}
}
