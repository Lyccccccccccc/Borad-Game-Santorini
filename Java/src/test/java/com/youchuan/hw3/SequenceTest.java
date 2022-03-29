package com.youchuan.hw3;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SequenceTest {
    private Game game;

    @Before
    public void setUp() {
        game = new Game(0, 0, 0, 4, 4, 0, 4, 4);
    }

    @Test
    public void testGameSequence() {
        int cnt = 0;
        int count = 0;
        while (true) {
            if (game.getNextPlayer() == 0) {
                if (cnt == 0) {
                    game.move(1, 1, 0);
                    game.build(0, 2, 0);
                }
                if (cnt == 1) {
                    game.move(0, 2, 0);
                    game.build(0, 1, 0);
                }
                if (cnt == 2) {
                    game.move(0, 1, 0);
                    game.build(0, 2, 0);
                }
                if (cnt == 3) {
                    game.move(0, 2, 0);
                    game.build(0, 1, 0);
                }
                if (cnt == 4) {
                    game.move(0, 1, 0);
                    game.build(0, 2, 0);
                }
                if (cnt == 5) {
                    game.move(0, 2, 0);
                    break;
                }
                cnt++;
            }
            if (game.getNextPlayer() == 1) {
                count++;
                // skip the other player
                game.getPlayers().get(game.getNextPlayer()).setAction(2);
                game.isTurnEnd();
            }
        }
        assertEquals(5, cnt);
        assertEquals(5, count);
    }
}
