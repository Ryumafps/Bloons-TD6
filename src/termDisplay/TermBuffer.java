package termDisplay;

import java.util.ArrayList;
import java.util.List;

import gameLogic.board.Board;
import gameLogic.bloon.Direction;
import gameLogic.board.Cell;
import gameLogic.board.CellOutOfBoundsException;
import gameLogic.board.InvalidCellException;

public class TermBuffer {

    /* The buffer that will eventually be displayed */
    private List<String> buf;
    /* The width of the buffer */
    private int width;
    /* The height of the buffer */
    private int height;
    /* Drawable objects we want to draw */
    /* Board  we want to display */
    private Board board;
    /* The size of a cell in board */
    private int cellSize;

    /*
     * Constructor for the class. Note that buf will be filled
       after calling the constructor
     * @param width
     * @param height
     * , List<Drawable> objList
    */
    public TermBuffer(Board board) {
        this.buf = new ArrayList<>();
        this.board = board;
        this.width = board.getWidth();
        this.height= board.getHeight();
        this.cellSize = board.getCellSize();
        this.populateBuffer(width, height);
    }


    public List<String> getBuffer() {
        return buf;
    }

    public int getCellSize() {
        return this.cellSize;
    }

    /*
     * Helper function to create the buffer (+-+-+-+-+\n)
     * @param width : the width of the buffer
     */
    private void populateSeparator(int width) {
        for (int i = 0; i < width ; i++) {
            buf.add("+");
            buf.add("-");
            buf.add("-");
            buf.add("-");
        }
        buf.add("+");
        buf.add("\n");
    }

    /*
     * Gets the index of the the cell in the buffer
     * @param cell : the cell we wanna have the index in the buffer
     * @return the index
    */
    public int getIndex(Cell cell) throws CellOutOfBoundsException {
        if (
            cell.getX() < 0 ||
            cell.getX() >= this.width ||
            cell.getY() < 0 ||
            cell.getY() >= this.height
            ) {
            throw new CellOutOfBoundsException(String.format("Cell %s is out of bounds", cell.toString()));
        }
        int realWidth = 4*this.width + 2;
        int realX = 4*cell.getX() + 2;
        int realY = 2*cell.getY() + 1;

        return realY*realWidth + realX;
    }

    /*
     Sets the string s to the cell `cell` representing a tower
     @param cell : the cell we want to draw the tower on
     @param s : the string representing the tower
    */
    public void setTower(Cell cell, String s, List<String> workBuf) {
        try {
            int idx = this.getIndex(cell);
            workBuf.set(idx, s);
        } catch (CellOutOfBoundsException e) {
            return;
        }
    }

    /*
    Clamps the value between min and max
    @param min : the lower bound to clamp to
    @param max: the upper bound to clamp to
    @param val: the value to clamp
     */
    private static int clamp(int min, int max, int val) {
        if (val < min)
            return min;
        if (val > max)
            return max;
        return val;
    }

    /*
    Safely parses the string s
    @param s: the cell to parse
    @returns: -1 if the cell is not valid, 1-9 if it is correct, and 10 if there are more than 10 bloons in the cell
     */
    private static int safeParseInt(String s) {
        if (s.matches("[0-9]+")) {
            return TermBuffer.clamp(1, 10, Integer.parseInt(s) + 1);
        }
        if (s.equals(" ")) {
             return 1;
        }
        if (s.equals("\u001B[31m9\u001B[0m")) {
            return 10;
        }
        return -1;
    }

    /*
     * Increments the cell by one (to represent Bloons)
     * @param cell : The cell we want to increment
     * @throws InvalidCellException : When the cell does not contains a valid value (not space nor a number)
     */
    public void incrementCell(Cell cell, List<String> workBuf) throws  CellOutOfBoundsException {
        // TODO: retravailler pour afficher les tours et les ballons
        int idx = this.getIndex(cell);
        int value;
        int decalage = 0;
        value = safeParseInt(workBuf.get(idx));
        if (value == -1) {
            value = safeParseInt(workBuf.get(idx + 1));
            decalage += 1;
        }
        if (value < 9) {
            workBuf.set(idx + decalage , String.format("%d", value));
        }
        else {
            workBuf.set(idx + decalage , "\u001B[31m9\u001B[0m");
        }
    }


    /*
     * Draws the path given in parameter in workBuf
     * @param path : The list of cells we want to draw as a patht
     * @throws CellOutOfBoundsException : when a cell is out of bounds
    */
    public void setPath(List<Cell> path, List<String> workBuf) {
        int realWidth = 4 * this.width + 2;
        for (int i = 0; i < path.size() - 1; i++) {
            try {
                Cell currentCell = path.get(i);
                int idxInBuf = this.getIndex(currentCell);
                Cell nextCell = path.get(i+1);
                Direction d = Cell.getDirection(currentCell, nextCell);
                if (i == 0) {
                    workBuf.set(idxInBuf + 1, "\u001B[36m"+ "⚐" + "\u001B[0m"); // debut
                }
                if (i  == path.size() - 2) {
                    workBuf.set(this.getIndex(nextCell) + 1, "\u001B[33m" + "⚑" + "\u001B[0m"); // fin
                }
                switch (d) {
                case DROITE:
                    workBuf.set(idxInBuf + 2, " ");
                    break;
                case GAUCHE:
                    workBuf.set(idxInBuf - 2, " ");
                    break;
                case HAUT:
                    workBuf.set(idxInBuf - realWidth, " ");
                    workBuf.set(idxInBuf - realWidth - 1, " ");
                    workBuf.set(idxInBuf - realWidth + 1, " ");
                    break;
                case BAS:
                    workBuf.set(idxInBuf + realWidth, " ");
                    workBuf.set(idxInBuf + realWidth - 1, " ");
                    workBuf.set(idxInBuf + realWidth + 1, " ");
                    break;
                default:
                    break;
                }
            } catch (CellOutOfBoundsException e) {
                System.err.println("Chemin Invalide " + e.getMessage());
            }
        }
    }

    /*
     * Helper private function to create the buffer (| | | | |\n)
     * @param width : the width of the buffer
     */
    private void populateCells(int width) {
        for (int i = 0; i < width ; i++) {
            buf.add("|");
            buf.add(" ");
            buf.add(" ");
            buf.add(" ");
        }
        buf.add("|");
        buf.add("\n");
    }

    /*
     * Populates the buffer
     * @param width : the width of the buffer
     * @param height the height of the buffer
     */
    private void populateBuffer(int width, int height) {
        for (int h = 0; h < height; h++) {
            this.populateSeparator(width);
            this.populateCells(width);
        }

        this.populateSeparator(width);
    }

	private void columnNumber(){
		System.out.print("   " + " ");
		for(int i = 1; i < width+1; i++){
			if(i%2 == 0){
				System.out.print("\u001B[2m");
			}
			if(i < 10){
				System.out.print(" " + i + "  ");
			}
			else if(i >= 10 && i<100){
				System.out.print(" " + i + " ");
			}else{
				System.out.print(i+" ");
			}
			System.out.print("\u001B[0m");
		}
		System.out.print("\n");
	}

    public String toString() {

		columnNumber();

        ArrayList<String> tmp = new ArrayList<>(this.buf);

        List<Drawable> drawables = this.board.getDrawable();

        // L'ordre des drawable est important : d'abord le path (ou pas), les
        // tours puis les bloons, car,  à cause de notre implémentation de BoardFree,
        // un ballon et une tour peuvent être sur la même case, et l'implémentation de
        // incrementCell va gérer ça en supposant cet ordre
        for (Drawable obj : drawables) {
            obj.draw(this, tmp);
        }

        // String res = String.join("", tmp); // ancien affichage

		String res = "   ";

		int nbligne = 1;
		int nbretour = 0;
		for(int i=0; i<tmp.size();i++){
			res += tmp.get(i);
			try {
				tmp.get(i+1);
				if(tmp.get(i).equals("\n")){
					if(nbretour%2 == 0){
						if(nbligne%2 == 0){
							res += "\u001B[2m";
						}
						if(nbligne < 10){
							res += " " + nbligne + " ";
						} else if(nbligne >= 10 && nbligne < 100){
							res += nbligne + " ";
						} else{
							res += nbligne;
						}
						nbligne++;
						res += "\u001B[0m";
					} else{
						res += "   ";
					}
					nbretour++;
				}
			} catch (Exception e){

			}
		}

        return res;
    }

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
