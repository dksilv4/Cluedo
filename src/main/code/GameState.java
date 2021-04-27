package code;

/**
 * Represents different states within the game to be able to run different code depending on the players actions
 */
public enum GameState {
    AssigningPlayerPieces,
    InPlay,
    MakingAccusation,
    GameOver,
    MakingSuggestion
}
