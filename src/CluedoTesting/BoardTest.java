package CluedoTesting;

import Cluedo.Board;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setUp() throws Exception{
        board = new Board();
    }
    @Test
    @DisplayName("Testing creation of Board")
    public void testBoardCreation(){

    }
}
