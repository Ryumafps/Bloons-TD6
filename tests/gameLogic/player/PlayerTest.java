package gameLogic.player;

import gameLogic.bloon.Bloon;
import gameLogic.bloon.Direction;
import gameLogic.tower.DartMonkey;
import gameLogic.tower.monkey.Evolution;
import gameLogic.tower.monkey.Monkey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;
    private DartMonkey dart;

    @BeforeEach
    public void setUp() {
        player = new Player();
        dart = new DartMonkey(50, 50);
    }

    @Test
    public void testInitialCredits() {
        assertEquals(2500, player.getCredits());
    }

    @Test
    public void testInitialLives() {
        assertEquals(20, player.getLives());
    }

    @Test
    public void testIsAliveAtStart() {
        assertTrue(player.isAlive());
    }

    @Test
    public void testAddCredits() {
        player.addCredits(500);
        assertEquals(3000, player.getCredits());
    }

    @Test
    public void testRemoveLife() {
        Bloon b = new Bloon(1, 1, 0, 0, Direction.DROITE);
        player.removeLife(b);
        assertEquals(19, player.getLives());
    }

    @Test
    public void testIsDeadWhenNoLives() {
        Bloon b = new Bloon(20, 1, 0, 0, Direction.DROITE);
        player.removeLife(b);
        assertFalse(player.isAlive());
    }

    @Test
    public void testIsAliveWithOneLive() {
        Bloon b = new Bloon(19, 1, 0, 0, Direction.DROITE);
        player.removeLife(b);
        assertTrue(player.isAlive());
    }

    @Test
    public void testBuyMonkeySuccess() {
        boolean result = player.buyMonkey(dart);
        assertTrue(result);
        assertEquals(2300, player.getCredits());
    }

    @Test
    public void testBuyMonkeyNotEnoughCredits() {
        player.addCredits(-2400);
        boolean result = player.buyMonkey(dart);
        assertFalse(result);
        assertEquals(100, player.getCredits());
    }

    @Test
    public void testBuyMonkeyDeductsCredits() {
        int before = player.getCredits();
        player.buyMonkey(dart);
        assertEquals(before - dart.getPrice(), player.getCredits());
    }

    @Test
    public void testSellMonkeyRefund() {
        player.buyMonkey(dart);
        int creditsBefore = player.getCredits();
        int refund = player.sellMonkey(dart);
        assertEquals(dart.getPrice() / 2, refund);
        assertEquals(creditsBefore + refund, player.getCredits());
    }

    @Test
    public void testBuyEvolutionSuccess() {
        Evolution ev = dart.getEvols()[0];
        player.buyAndSellEvolution(ev, 1);
        assertEquals(2400, player.getCredits());
    }

    @Test
    public void testBuyEvolutionNotEnoughCredits() {
        player.addCredits(-2450);
        Evolution ev = dart.getEvols()[0];
        player.buyAndSellEvolution(ev, 1);
        assertEquals(-50, player.getCredits());
    }

    @Test
    public void testSellEvolution() {
        Evolution ev = dart.getEvols()[0];

        player.buyAndSellEvolution(ev, 1);
        player.buyAndSellEvolution(ev, 0);

        assertEquals(2450, player.getCredits());
    }

    @Test
    public void testEnoughCreditToBuy() {
        assertTrue(player.enoughCreditToBuy(2500));
        assertTrue(player.enoughCreditToBuy(100));
        assertFalse(player.enoughCreditToBuy(2501));
    }

}

