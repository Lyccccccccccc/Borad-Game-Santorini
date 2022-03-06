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
        while (game.isTurnEnd()) {
            int actionPlayer = game.getNextPlayer();
            if (actionPlayer == 0) {
                if (cnt == 0) {
                    if (game.getPlayers().get(actionPlayer).getAction() == 0) {
                        game.move(1, 1, actionPlayer, 0);
                        if (game.isGameOver()) {
                            break;
                        }
                    }
                    if (game.getPlayers().get(actionPlayer).getAction() == 1) {
                        game.buildBlock(0, 2, actionPlayer, 0);
                    }
                }
                if (cnt == 1) {
                    if (game.getPlayers().get(actionPlayer).getAction() == 0) {
                        game.move(0, 2, actionPlayer, 0);
                        if (game.isGameOver()) {
                            break;
                        }
                    }
                    if (game.getPlayers().get(actionPlayer).getAction() == 1) {
                        game.buildBlock(0, 1, actionPlayer, 0);
                    }
                }
                if (cnt == 2) {
                    if (game.getPlayers().get(actionPlayer).getAction() == 0) {
                        game.move(0, 1, actionPlayer, 0);
                        if (game.isGameOver()) {
                            break;
                        }
                    }
                    if (game.getPlayers().get(actionPlayer).getAction() == 1) {
                        game.buildBlock(0, 2, actionPlayer, 0);
                    }
                }
                if (cnt == 3) {
                    if (game.getPlayers().get(actionPlayer).getAction() == 0) {
                        game.move(0, 2, actionPlayer, 0);
                        if (game.isGameOver()) {
                            break;
                        }
                    }
                    if (game.getPlayers().get(actionPlayer).getAction() == 1) {
                        game.buildBlock(0, 1, actionPlayer, 0);
                    }
                }
                if (cnt == 4) {
                    if (game.getPlayers().get(actionPlayer).getAction() == 0) {
                        game.move(0, 1, actionPlayer, 0);
                        if (game.isGameOver()) {
                            break;
                        }
                    }
                    if (game.getPlayers().get(actionPlayer).getAction() == 1) {
                        game.buildBlock(0, 2, actionPlayer, 0);
                    }
                }
                if (cnt == 5) {
                    if (game.getPlayers().get(actionPlayer).getAction() == 0) {
                        game.move(0, 2, actionPlayer, 0);
                        if (game.isGameOver()) {
                            break;
                        }
                    }
                }
                cnt++;
            }
            if (actionPlayer == 1) {
                count++;
                // skip the other player
                game.getPlayers().get(actionPlayer).setAction(2);
            }
        }
        assertEquals(5, cnt);
        assertEquals(5, count);
    }
}
