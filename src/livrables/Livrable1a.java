package livrables;

import gameLogic.board.BoardPath;
import gameLogic.board.Cell;
import java.util.List;

// Plateau avec chemin
public class Livrable1a {
    
    public static void main(String[] args) {
        
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);

        // Le paramètre cellSize n'est pas utile pour le moment, 1 est une valeur arbitraire
        BoardPath b = new BoardPath(width, height, 1);
        List<Cell> p = b.getPath();

        System.out.println("Chemin :");
        for (Cell c : p) {
            System.out.print(c + " ");
        }
        System.out.println();

        String sep = "+" + "---+".repeat(width);

        for (int y = 0; y < height; y++) {
            System.out.println(sep);

            for (int x = 0; x < width; x++) {
                System.out.print("| ");
                if (p.contains(new Cell(x, y))) {
                    System.out.print("# ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("|");
        }

        System.out.println(sep);
    }
}
