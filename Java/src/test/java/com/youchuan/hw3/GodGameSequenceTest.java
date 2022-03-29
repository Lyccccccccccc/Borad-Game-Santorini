package com.youchuan.hw3;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GodGameSequenceTest {
    private GodGame godGame;

    @Before
    public void setUp() {
        godGame = new GodGame(0, 0, 0, 4, 4, 0, 4, 4);
    }

    @Test
    public void testGodGameSequence() {
        int cnt = 0;
        int count = 0;
        while (true) {
            godGame.selectGod(new Demeter());
            godGame.selectGod(new Minotaur());
            if (godGame.getNextPlayer() == 0) {
                if (cnt == 0) {
                    godGame.move(1, 1, 0);
                    godGame.build(0, 2, 0);
                    godGame.build(0, 1, 0);
                }
                if (cnt == 1) {
                    godGame.move(0, 2, 0);
                    godGame.build(0, 1, 0);
                    godGame.build(0, 1, 0);
                }
                if (cnt == 2) {
                    godGame.move(0, 1, 0);
                    godGame.build(0, 2, 0);
                    godGame.build(0, 2, 0);
                }
                if (cnt == 3) {
                    godGame.move(0, 2, 0);
                    godGame.build(0, 1, 0);
                    godGame.build(0, 1, 0);
                }
                if (cnt == 4) {
                    godGame.move(0, 1, 0);
                    break;
                }
                cnt++;
            }
            if (godGame.getNextPlayer() == 1) {
                count++;
                // skip the other player
                godGame.getPlayers().get(godGame.getNextPlayer()).setAction(2);
                godGame.isTurnEnd();
            }
        }
        assertEquals(4, cnt);
        assertEquals(4, count);
    }
}
