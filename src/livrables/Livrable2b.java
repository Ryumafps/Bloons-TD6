package livrables;

import gameLogic.bloon.Bloon;
import gameLogic.board.BoardFree;
import java.util.List;

public class Livrable2b {
    
    public static void main(String[] args) {
        
        BoardFree b = new BoardFree(Integer.parseInt(args[0]), Integer.parseInt(args[1]), 10); 
        for (int i = 0; i < Integer.parseInt(args[2]); i++) {
            b.addBloon(1, 5);
        }

        int timer = 0;
        while (!b.getBloons().isEmpty()) {
            System.out.println("===== Tour " + timer + "=====");
            List<Bloon> sortis = b.moveBloons(1);
            for (Bloon bloon : sortis) {
                System.out.println(bloon + " => out at time : " + timer);
            }
            timer += 1;
        }
    }
}
