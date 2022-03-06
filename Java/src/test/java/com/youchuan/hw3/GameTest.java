package com.youchuan.hw3;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {
    private Game game;

    @Before
    public void setUp() {
        game = new Game(0, 0, 0, 4, 4, 0, 4, 4);
    }

    @Test
    public void testValidMove() {
        int workerIndex = 0;
        int ogOccupied = game.getBoard()[0][0].getOccupied();
        game.move(1, 1, game.getNextPlayer(), workerIndex);
        Position newPos = game.getPlayers().get(game.getNextPlayer()).getWorkers().get(workerIndex);
        int expectedX = 1;
        int expectedY = 1;
        int expectedO = 1;
        assertEquals(expectedX, newPos.getX());
        assertEquals(expectedY, newPos.getY());
        assertEquals(expectedO, newPos.getOccupied());
        assertEquals(expectedO, ogOccupied);
        assertEquals(game.getBoard()[0][0].getOccupied(), 0);
    }

    @Test
    public void testInvalidMove1() {
        int workerIndex = 0;
        game.move(2, 2, game.getNextPlayer(), workerIndex);
        Position newPos = game.getPlayers().get(game.getNextPlayer()).getWorkers().get(workerIndex);
        int expectedX = 0;
        int expectedY = 0;
        int expectedO = 1;
        assertEquals(expectedX, newPos.getX());
        assertEquals(expectedY, newPos.getY());
        assertEquals(expectedO, newPos.getOccupied());
        assertEquals(game.getBoard()[2][2].getOccupied(), 0);
    }

    @Test
    public void testInvalidMove2() {
        int workerIndex = 0;
        game.move(-1, -1, game.getNextPlayer(), workerIndex);
        Position newPos = game.getPlayers().get(game.getNextPlayer()).getWorkers().get(workerIndex);
        int expectedX = 0;
        int expectedY = 0;
        int expectedO = 1;
        assertEquals(expectedX, newPos.getX());
        assertEquals(expectedY, newPos.getY());
        assertEquals(expectedO, newPos.getOccupied());
    }

    @Test
    public void testInvalidMove3() {
        int workerIndex = 0;
        game.getBoard()[1][1].setLevel(4);
        game.move(1, 1, game.getNextPlayer(), workerIndex);
        Position newPos = game.getPlayers().get(game.getNextPlayer()).getWorkers().get(workerIndex);
        int expectedX = 0;
        int expectedY = 0;
        int expectedO = 1;
        assertEquals(expectedX, newPos.getX());
        assertEquals(expectedY, newPos.getY());
        assertEquals(expectedO, newPos.getOccupied());
        assertEquals(game.getBoard()[1][1].getOccupied(), 0);
    }

    @Test
    public void testInvalidMove4() {
        int workerIndex = 0;
        game.getBoard()[1][1].setLevel(2);
        game.move(1, 1, game.getNextPlayer(), workerIndex);
        Position newPos = game.getPlayers().get(game.getNextPlayer()).getWorkers().get(workerIndex);
        int expectedX = 0;
        int expectedY = 0;
        int expectedO = 1;
        assertEquals(expectedX, newPos.getX());
        assertEquals(expectedY, newPos.getY());
        assertEquals(expectedO, newPos.getOccupied());
        assertEquals(game.getBoard()[1][1].getOccupied(), 0);
    }

    @Test
    public void testValidBuildBlock() {
        int workerIndex = 0;
        game.buildBlock(1, 1, game.getNextPlayer(), workerIndex);
        int expectedL = 1;
        assertEquals(expectedL, game.getBoard()[1][1].getLevel());
    }

    @Test
    public void testInvalidBuildBlock1() {
        int workerIndex = 0;
        game.buildBlock(2, 2, game.getNextPlayer(), workerIndex);
        int expectedL = 0;
        assertEquals(expectedL, game.getBoard()[2][2].getLevel());
    }

    @Test
    public void testInvalidBuildBlock2() {
        int workerIndex = 0;
        game.getBoard()[1][1].setLevel(3);
        game.buildBlock(1, 1, game.getNextPlayer(), workerIndex);
        int expectedL = 3;
        assertEquals(expectedL, game.getBoard()[1][1].getLevel());
    }

    @Test
    public void testValidBuildDome() {
        int workerIndex = 0;
        game.getBoard()[1][1].setLevel(3);
        game.buildDome(1, 1, game.getNextPlayer(), workerIndex);
        int expectedL = 4;
        assertEquals(expectedL, game.getBoard()[1][1].getLevel());
    }
}
