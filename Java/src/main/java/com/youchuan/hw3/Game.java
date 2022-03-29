package com.youchuan.hw3;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static int nextPlayer;
    private static Position[][] board;
    private static List<Player> players;
    private static Player winner;
    public static final int DEFAULT_SIZE = 5;
    public static final int MIN_INDEX = 0;
    public static final int MAX_INDEX = 4;
    public static final int DEFAULT_OCCUPANCY = 0;
    public static final int DEFAULT_LEVEL = 0;
    public static final int BLOCK_HIGHEST_LEVEL = 3;
    public static final int DOME_LEVEL = 4;

    /**
     * Default constructor.
     */
    public Game() {
        winner = null;
        nextPlayer = 0;
        initiateBoard();
        players = new ArrayList<>();
    }

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
        winner = null;
        nextPlayer = 0;
        initiateBoard();
        players = new ArrayList<>(2);
        initiatePlayer(x1A, y1A, x2A, y2A);
        initiatePlayer(x1B, y1B, x2B, y2B);
    }

    /**
     * Public method for initiating the player with two worker positions.
     * @param x1 x1 value for worker1
     * @param y1 y1 value for worker1
     * @param x2 x2 value for worker2
     * @param y2 y2 value for worker2
     */
    public void initiatePlayer(int x1, int y1, int x2, int y2) {
        if (checkPos(x1, y1) && notOccupied(x1, y1) && checkPos(x2, y2) && notOccupied(x2, y2)) {
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
     * Package-private getter for the play board.
     * @return 2-d array of position
     */
    static Position[][] getBoard() {
        return board;
    }

    /**
     * Package-private getter for the next player index.
     * @return the index of the next player
     */
    static int getNextPlayer() {
        return nextPlayer;
    }

    /**
     * Package-private getter for the players in the game.
     * @return a list of players
     */
    static List<Player> getPlayers() {
        return players;
    }

    /**
     * Public method for getting the index of the worker.
     * @param x x position of the worker
     * @param y y position of the worker
     * @return the index of the worker in the list
     */
    public int getWorkerIndex(int x, int y) {
        if (board[x][y].getOccupied() == 1) {
            Player player = players.get(nextPlayer);
            if (player.getWorkers().contains(board[x][y])) {
                return player.getWorkers().indexOf(board[x][y]);
            }
        }
        System.out.println("Oops! Please select a valid position!");
        return -1;
    }

    /**
     * The move method takes in x and y values, player index and the index of worker.
     * It validates and moves the player's selected worker to (x, y) position.
     * @param x Expected x position to go to
     * @param y Expected y position to go to
     * @param workerIndex the selected worker's index in the player's worker list
     */
    public void move(int x, int y, int workerIndex) {
        Player currPlayer = players.get(nextPlayer);
        if (winner != null || currPlayer.getAction() != 0) {
            return;
        }
        Position currWorker = currPlayer.getWorkers().get(workerIndex);
        if (checkPos(x, y) && notOccupied(x, y) && validMove(x, y, currWorker) && board[x][y].getLevel() != DOME_LEVEL) {
            board[currWorker.getX()][currWorker.getY()].setOccupied(DEFAULT_OCCUPANCY);
            board[x][y].setOccupied(1);
            currPlayer.setWorker(workerIndex, board[x][y]);
            currPlayer.setAction(currPlayer.getAction() + 1);
            System.out.println("Move succeeded!");
            if (board[x][y].getLevel() == BLOCK_HIGHEST_LEVEL) {
                winner = currPlayer;
                System.out.println("Game Over. Player " + getNextPlayer() + " wins!!!");
            }
        } else {
            System.out.println("Oops! Please select a valid position!");
        }
    }

    public void build(int x, int y, int workerIndex) {
        Player currPlayer = players.get(nextPlayer);
        if (winner != null || currPlayer.getAction() != 1) {
            return;
        }
        Position currWorker = currPlayer.getWorkers().get(workerIndex);
        if (checkPos(x, y) && notOccupied(x, y) && validSurrounding(x, y, currWorker)) {
            if (board[x][y].getLevel() == DOME_LEVEL) {
                System.out.println("Oops! Please select a valid position!");
            } else {
                board[x][y].setLevel(board[x][y].getLevel() + 1);
                currPlayer.setAction(currPlayer.getAction() + 1);
                System.out.println("Build succeeded!");
                isTurnEnd();
            }
        } else {
            System.out.println("Oops! Please select a valid position!");
        }
    }

    /**
     * Package-private method for taking turns.
     */
    void takeTurns() {
        nextPlayer = 1 - nextPlayer;
    }

    /**
     * Package-private method for checking the player's turn is ended or not and takes turns to continue the game.
     */
    void isTurnEnd() {
        if (players.get(nextPlayer).getAction() == 2) {
            players.get(nextPlayer).setAction(0);
            takeTurns();
        }
    }

    /**
     * Checks if the input position is the surrounding of current position.
     * @param x x of the position
     * @param y y of the position
     * @param currPos current position
     * @return true for valid, false for invalid
     */
    boolean validSurrounding(int x, int y, Position currPos) {
        return Math.max(x, currPos.getX() - 1) == Math.min(x, currPos.getX() + 1) && Math.max(y, currPos.getY() - 1) == Math.min(y, currPos.getY() + 1);
    }

    /**
     * Checks if the move is valid, which checks the position is surrounding or not and checks the level is reachable or not.
     * @param x x of the position
     * @param y y of the position
     * @param currPos current position
     * @return true for valid, false for invalid
     */
    boolean validMove(int x, int y, Position currPos) {
        return currPos.getLevel() - board[x][y].getLevel() >= -1 && !(currPos.getX() == x && currPos.getY() == y) && validSurrounding(x, y, currPos);
    }

    /**
     * Checks if the position is inside the board.
     * @param x x of the position
     * @param y y of the position
     * @return true for inside, false for outside
     */
    static boolean checkPos(int x, int y) {
        return Math.max(x, MIN_INDEX) == Math.min(x, MAX_INDEX) && Math.max(y, MIN_INDEX) == Math.min(y, MAX_INDEX);
    }

    /**
     * Checks if the position is vacant or not.
     * @param x x of the position
     * @param y y of the position
     * @return true for not occupied, false for occupied
     */
    static boolean notOccupied(int x, int y) {
        return board[x][y].getOccupied() == DEFAULT_OCCUPANCY;
    }

    /**
     * Package-private method for getting the winner.
     * @return the winner player
     */
    Player getWinner() {
        return winner;
    }

    /**
     * Package-private method for setting the winner.
     * @param winner the winner player
     */
    static void setWinner(Player winner) {
        Game.winner = winner;
    }
}
