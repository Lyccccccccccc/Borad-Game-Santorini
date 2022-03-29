package com.youchuan.hw3;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GodGameTest {
    private GodGame godGame;

    @Before
    public void setUp() {
        godGame = new GodGame(0, 0, 0, 4, 1, 3, 1, 4);
    }

    @Test
    public void testSelectGod() {
        godGame.selectGod(new Pan());
        assertEquals(1, Game.getNextPlayer());
    }

    @Test
    public void testPanWin1() {
        God pan = new Pan();
        Player currPlayer = Game.getPlayers().get(0);
        Position currWorker = currPlayer.getWorkers().get(0);
        currWorker.setLevel(2);
        pan.checkWin(1, 1, currPlayer, 0);
        assertEquals(godGame.getWinner(), Game.getPlayers().get(0));
    }

    @Test
    public void testPanWin2() {
        God pan = new Pan();
        Player currPlayer = Game.getPlayers().get(0);
        Game.getBoard()[1][1].setLevel(3);
        pan.checkWin(1, 1, currPlayer, 0);
        assertEquals(godGame.getWinner(), Game.getPlayers().get(0));
    }

    @Test
    public void testPanMove() {
        godGame.selectGod(new Pan());
        godGame.takeTurns();
        Player currPlayer = Game.getPlayers().get(0);
        Position currWorker = currPlayer.getWorkers().get(0);
        currWorker.setLevel(2);
        godGame.move(1, 1, 0);
        assertEquals(godGame.getWinner(), Game.getPlayers().get(0));
    }

    @Test
    public void testMinotaur1() {
        God mino = new Minotaur();
        Player currPlayer = Game.getPlayers().get(0);
        Player oppoPlayer = Game.getPlayers().get(1);
        mino.occupiedMove(1, 3, currPlayer, 1);
        Position currWorker = currPlayer.getWorkers().get(1);
        Position oppoWorker = oppoPlayer.getWorkers().get(0);
        assertEquals(0, Game.getBoard()[0][4].getOccupied());
        assertEquals(1, Game.getBoard()[1][3].getOccupied());
        assertEquals(1, Game.getBoard()[2][2].getOccupied());
        assertEquals(Game.getBoard()[1][3], currWorker);
        assertEquals(Game.getBoard()[2][2], oppoWorker);
    }

    @Test
    public void testMinotaur2() {
        God mino = new Minotaur();
        Player currPlayer = Game.getPlayers().get(0);
        Player oppoPlayer = Game.getPlayers().get(1);
        mino.occupiedMove(1, 4, currPlayer, 1);
        Position currWorker = currPlayer.getWorkers().get(1);
        Position oppoWorker = oppoPlayer.getWorkers().get(1);
        assertEquals(0, Game.getBoard()[0][4].getOccupied());
        assertEquals(1, Game.getBoard()[1][4].getOccupied());
        assertEquals(1, Game.getBoard()[2][4].getOccupied());
        assertEquals(Game.getBoard()[1][4], currWorker);
        assertEquals(Game.getBoard()[2][4], oppoWorker);
    }

    @Test
    public void testMinotaurMove() {
        godGame.selectGod(new Minotaur());
        godGame.takeTurns();
        Player currPlayer = Game.getPlayers().get(0);
        Player oppoPlayer = Game.getPlayers().get(1);
        godGame.move(1, 3, 1);
        Position currWorker = currPlayer.getWorkers().get(1);
        Position oppoWorker = oppoPlayer.getWorkers().get(0);
        assertEquals(0, Game.getBoard()[0][4].getOccupied());
        assertEquals(Game.getBoard()[1][3], currWorker);
        assertEquals(Game.getBoard()[2][2], oppoWorker);
    }

    @Test
    public void testDemeter1() {
        God demeter = new Demeter();
        Player currPlayer = Game.getPlayers().get(0);
        assertTrue(demeter.checkBuild(1, 1, currPlayer));
        assertFalse(demeter.checkBuild(1, 1, currPlayer));
    }

    @Test
    public void testDemeter2() {
        God demeter = new Demeter();
        Player currPlayer = Game.getPlayers().get(0);
        assertTrue(demeter.checkBuild(1, 1, currPlayer));
        assertTrue(demeter.checkBuild(1, 0, currPlayer));
    }

    @Test
    public void testDemeterBuildOnce() {
        godGame.selectGod(new Demeter());
        godGame.takeTurns();
        Player currPlayer = Game.getPlayers().get(0);
        currPlayer.setAction(1);
        godGame.build(1, 1, 0);
        godGame.build(1, 1, 0);
        assertEquals(1, Game.getBoard()[1][1].getLevel());
        assertEquals(0, currPlayer.getAction());
    }

    @Test
    public void testDemeterBuildTwice() {
        godGame.selectGod(new Demeter());
        godGame.takeTurns();
        Player currPlayer = Game.getPlayers().get(0);
        currPlayer.setAction(1);
        godGame.build(1, 1, 0);
        godGame.build(1, 0, 0);
        assertEquals(1, Game.getBoard()[1][1].getLevel());
        assertEquals(1, Game.getBoard()[1][0].getLevel());
        assertEquals(0, currPlayer.getAction());
    }
}
