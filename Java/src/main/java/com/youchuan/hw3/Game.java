package com.youchuan.hw3;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int nextPlayer;
    private Position[][] board;
    private List<Player> players;
    public static final int DEFAULT_SIZE = 5;
    public static final int MIN_INDEX = 0;
    public static final int MAX_INDEX = 4;
    public static final int DEFAULT_OCCUPANCY = 0;
    public static final int DEFAULT_LEVEL = 0;
    public static final int BLOCK_HIGHEST_LEVEL = 3;
    public static final int DOME_LEVEL = 4;

    /**
     * The constructor for initializing the game with valid eight x and y values (range from 0 to 4).
     * @param x1A x1 value for worker1, playerA
     * @param y1A y1 value for worker1, playerA
     * @param x2A x2 value for worker2, playerA
     * @param y2A y2 value for worker2, playerA
     * @param x1B x1 value for worker1, playerB
     * @param y1B y1 value for worker1, playerB
     * @param x2B x2 value for worker2, playerB
     * @param y2B y2 value for worker2, playerB
     */
    public Game(int x1A, int y1A, int x2A, int y2A, int x1B, int y1B, int x2B, int y2B) {
        nextPlayer = 0;
        initiateBoard();
        players = new ArrayList<>(2);
        initiatePlayer(x1A, y1A, x2A, y2A);
        initiatePlayer(x1B, y1B, x2B, y2B);
    }

    private void initiatePlayer(int x1, int y1, int x2, int y2) {
        if (checkPos(x1, y1) && checkPos(x2, y2)) {
            players.add(new Player(x1, y1, x2, y2));
            board[x1][y1].setOccupied(1);
            board[x2][y2].setOccupied(1);
        }
    }

    private void initiateBoard() {
        board = new Position[DEFAULT_SIZE][DEFAULT_SIZE];
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            for (int j = 0; j < DEFAULT_SIZE; j++) {
                board[i][j] = new Position(i, j, DEFAULT_LEVEL, DEFAULT_OCCUPANCY);
            }
        }
    }

    /**
     * Getter for the play board.
     * @return 2-d array of position
     */
    public Position[][] getBoard() {
        return board;
    }

    /**
     * Getter for the next player index.
     * @return the index of the next player
     */
    public int getNextPlayer() {
        return nextPlayer;
    }

    /**
     * Getter for the players in the game.
     * @return a list of players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * The move method takes in x and y values, player index and the index of worker.
     * It validates and moves the player's selected worker to (x, y) position.
     * @param x Expected x position to go to
     * @param y Expected y position to go to
     * @param playerIndex the player who is in his/her turn
     * @param workerIndex the selected worker's index in the player's worker list
     */
    public void move(int x, int y, int playerIndex, int workerIndex) {
        Player currPlayer = players.get(playerIndex);
        Position currWorker = currPlayer.getWorkers().get(workerIndex);
        if (checkPos(x, y) && validMove(x, y, currWorker) && board[x][y].getLevel() != DOME_LEVEL) {
            board[currWorker.getX()][currWorker.getY()].setOccupied(DEFAULT_OCCUPANCY);
            board[x][y].setOccupied(1);
            currPlayer.setWorker(workerIndex, board[x][y]);
            currPlayer.setAction(currPlayer.getAction() + 1);
            System.out.println("Move succeeded!");
        } else {
            System.out.println("Oops! Please select a valid position!");
        }
    }

    private void build(int x, int y, int playerIndex, int workerIndex, int buildLevel) {
        Player currPlayer = players.get(playerIndex);
        Position currWorker = currPlayer.getWorkers().get(workerIndex);
        if (checkPos(x, y) && validSurrounding(x, y, currWorker)) {
            if (board[x][y].getLevel() == DOME_LEVEL) {
                System.out.println("Oops! Please select a valid position!");
            } else {
                board[x][y].setLevel(buildLevel);
                currPlayer.setAction(currPlayer.getAction() + 1);
                System.out.println("Build succeeded!");
            }
        } else {
            System.out.println("Oops! Please select a valid position!");
        }
    }

    /**
     * The buildDome method takes in x and y values, player index and the index of worker.
     * It validates and builds a dome at (x, y) position around the player's selected worker.
     * @param x Expected x position to build dome
     * @param y Expected y position to build dome
     * @param playerIndex the player who is in his/her turn
     * @param workerIndex the selected worker's index in the player's worker list
     */
    public void buildDome(int x, int y, int playerIndex, int workerIndex) {
        if (board[x][y].getLevel() != BLOCK_HIGHEST_LEVEL) {
            System.out.println("Oops! Please select a valid position!");
        } else {
            build(x, y, playerIndex, workerIndex, DOME_LEVEL);
        }
    }

    /**
     * The buildBlock method takes in x and y values, player index and the index of worker.
     * It validates and builds a block at (x, y) position around the player's selected worker.
     * @param x Expected x position to build block
     * @param y Expected y position to build block
     * @param playerIndex the player who is in his/her turn
     * @param workerIndex the selected worker's index in the player's worker list
     */
    public void buildBlock(int x, int y, int playerIndex, int workerIndex) {
        if (board[x][y].getLevel() == BLOCK_HIGHEST_LEVEL || board[x][y].getLevel() == DOME_LEVEL) {
            System.out.println("Oops! Please select a valid position!");
        } else {
            build(x, y, playerIndex, workerIndex, board[x][y].getLevel() + 1);
        }
    }

    /**
     * Checks the player's turn is ended or not and takes turns to continue the game.
     * @return always true for continuing the game
     */
    public boolean isTurnEnd() {
        if (players.get(nextPlayer).getAction() == 2) {
            players.get(nextPlayer).setAction(0);
            nextPlayer = 1 - nextPlayer;
        }
        return true;
    }

    private boolean validSurrounding(int x, int y, Position currPos) {
        return Math.max(x, currPos.getX() - 1) == Math.min(x, currPos.getX() + 1) && Math.max(y, currPos.getY() - 1) == Math.min(y, currPos.getY() + 1);
    }

    private boolean validMove(int x, int y, Position currPos) {
        return currPos.getLevel() - board[x][y].getLevel() >= -1 && !(currPos.getX() == x && currPos.getY() == y) && validSurrounding(x, y, currPos);
    }

    private boolean checkPos(int x, int y) {
        return (Math.max(x, MIN_INDEX) == Math.min(x, MAX_INDEX) && Math.max(y, MIN_INDEX) == Math.min(y, MAX_INDEX)
                && board[x][y].getOccupied() == DEFAULT_OCCUPANCY);
    }

    /**
     * Checks if the game is over or not. Print the winner.
     * @return true for game over and false for not
     */
    public boolean isGameOver() {
        if (players.get(0).getWorkerLevel().contains(BLOCK_HIGHEST_LEVEL)) {
            System.out.println("Game Over. Player A wins!!!");
            return true;
        }
        else if (players.get(1).getWorkerLevel().contains(BLOCK_HIGHEST_LEVEL)) {
            System.out.println("Game Over. Player B wins!!!");
            return true;
        } else {
            return false;
        }
    }
}
