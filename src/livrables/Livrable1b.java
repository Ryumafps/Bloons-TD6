package livrables;

import gameLogic.board.Board;
import gameLogic.board.BoardFree;

// Plateau sans chemin
public class Livrable1b {

    public static void main(String[] args) {
        
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);

        // Le paramètre `cellSize` n'est pas utile pour le moment, 1 est une valeur arbitraire
        // Il n'y a pas de chemins, les ballons sont libres sur le plateau
        Board b = new BoardFree(width, height, 1);

        String sep = "+" + "---+".repeat(width);

        for (int i = 0; i < height; i++) {
            System.out.println(sep);
            for (int j = 0; j < width; j++) {
                System.out.print("|   ");
            }
            System.out.println("|");
        }
        System.out.println(sep);

        System.out.println("\n======> Les chemins n'existent pas, cf README.md <=======");
    }
}
