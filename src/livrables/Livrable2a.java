package livrables;

import gameLogic.bloon.Bloon;
import gameLogic.board.*;
import java.util.List;


public class Livrable2a {
    
    public static void main(String[] args) {
        BoardPath b = new BoardPath(Integer.parseInt(args[0]), Integer.parseInt(args[1]), 10);
        for (int i = 0; i < Integer.parseInt(args[2]); i++) {
            b.addBloon(1, 5);
        }

        for (Cell c: b.getPath()) {
            System.out.println(c);
        }
        int timer = 0;
        while (!b.getBloons().isEmpty()) {
            List<Bloon> sortis = b.moveBloons(1);

            // pour vérifier
            System.out.println("=== Tour " + timer + "===");
            for (Bloon bloon : b.getBloons()) {
                System.out.println(bloon);
            }

            // donne le temps de sortie
            for (Bloon bloon : sortis) {
                System.out.println(bloon + " => out at time : " + timer);
            }
            timer += 1;
        }
    }
}
