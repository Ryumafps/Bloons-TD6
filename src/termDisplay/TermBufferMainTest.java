package termDisplay;

import gameLogic.board.BoardFree;
import gameLogic.board.BoardPath;
import gameLogic.tower.DartMonkey;

public class TermBufferMainTest {

	public static void main(String[] args) {

        BoardPath b2 = new BoardPath(10, 10, 10);
        TermBuffer buf2 = new TermBuffer(b2);
        b2.addBloon(1, 3);

        b2.addMonkey(new DartMonkey(50, 50));

        System.out.println(buf2);

    }
}
