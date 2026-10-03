package livrables;

import gameLogic.bloon.Bloon;
import gameLogic.board.*;
import gameLogic.tower.*;
import gameLogic.tower.monkey.*;
import java.util.Random;

public class Livrable4b {

    public static void main(String[] args) {

        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        int nbBloons = Integer.parseInt(args[2]);
        int cellSize = 10;

        BoardFree b = new BoardFree(width, height, cellSize);

        // création des tours :
        Random rand = new Random();
        for (int i = 0; i < 2; i++) {
            int x = rand.nextInt(0, width*cellSize);
            int y = rand.nextInt(0, height*cellSize);
            BombMonkey m = new BombMonkey(x, y);
            b.addMonkey(m);
        }
        for (int i = 0; i < 2; i++) {
            int x = rand.nextInt(0, width*cellSize);
            int y = rand.nextInt(0, height*cellSize);
            DartMonkey m = new DartMonkey(x, y);
            b.addMonkey(m);
        }
        for (int i = 0; i < 2; i++) {
            int x = rand.nextInt(0, width*cellSize);
            int y = rand.nextInt(0, height*cellSize);
            GlueMonkey m = new GlueMonkey(x, y);
            b.addMonkey(m);
        }
        for (int i = 0; i < 2; i++) {
            int x = rand.nextInt(0, width*cellSize);
            int y = rand.nextInt(0, height*cellSize);
            Gorilla m = new Gorilla(x, y);
            b.addMonkey(m);
        }
        for (int i = 0; i < 2; i++) {
            int x = rand.nextInt(0, width*cellSize);
            int y = rand.nextInt(0, height*cellSize);
            IceMonkey m = new IceMonkey(x, y);
            b.addMonkey(m);
        }
        for (int i = 0; i < 2; i++) {
            int x = rand.nextInt(0, width*cellSize);
            int y = rand.nextInt(0, height*cellSize);
            NeedleTower m = new NeedleTower(x, y);
            b.addMonkey(m);
        }
        for (int i = 0; i < 2; i++) {
            int x = rand.nextInt(0, width*cellSize);
            int y = rand.nextInt(0, height*cellSize);
            SniperMonkey m = new SniperMonkey(x, y);
            b.addMonkey(m);
        }

        System.out.println("Monkey : ");
        for (Monkey m : b.getMonkeys()) {
            System.out.println(m);
        }

        int timer = 0;



        // tour de jeu :
		for (int i = 1; i<11; i++){
			System.out.println("=== Manche numéro " + i + " ===");
			for (int j = 0; j < nbBloons; j++) { // Ajouts des ballons
				b.addBloon(1, 5);
			}

			for (Monkey m : b.getMonkeys()){ // Ajouter ou Retirer les Evolutions au singes
				System.out.println(m);
				if (m.getProj() != ProjType.Ice && m.getProj() != ProjType.Glue){
					for (Evolution e : m.getEvols()){
						if(e.getisUsed() == 1){
							System.out.println(e);
						}
						if(i < 6 && e.getisUsed() == 0){
							e.updateUse();
							break;
						} 
						if(i >= 6 && e.getisUsed() == 1){
							e.updateUse();
							break;
						}
					}
				}
			}

            // tour de jeu :
            while (!b.getBloons().isEmpty()) {

                if (b.allBloonsDestroyed()) {
                    System.out.println("All bloons destroyed !");
                    break;
                }

                System.out.println("=== Tour " + timer + " ===");
                for (Bloon bloon : b.getBloons()) {
                    System.out.println(bloon);
                }

                b.updateGame(timer);

                timer += 1;
            }
		}
    }
    
}
