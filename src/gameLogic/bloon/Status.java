package gameLogic.bloon;

/**
 * Enumeration representing the different possible states of a ball.
 */
public enum Status {
    NORMAL,    // Normal ball state
    FROZEN,    // Frozen ball (does not move)
    SLOWED,     // Slowed ball (reduced speed)
    DESTROYED, // bloon destroyed (no hp)
    OUT // bloon out of board
}
