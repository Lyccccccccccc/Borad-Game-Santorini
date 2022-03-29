package com.youchuan.hw3;

import java.util.HashMap;
import java.util.Map;

public class GodGame extends Game{
    private final Map<Player, God> godCards;

    /**
     * Default constructor.
     */
    public GodGame() {
        super();
        godCards = new HashMap<>();
        for (Player player : getPlayers()) {
            godCards.put(player, null);
        }
    }

    /**
     * The constructor for initializing the game with valid eight x and y values (range from 0 to 4).
     *
     * @param x1A x1 value for worker1, playerA
     * @param y1A y1 value for worker1, playerA
     * @param x2A x2 value for worker2, playerA
     * @param y2A y2 value for worker2, playerA
     * @param x1B x1 value for worker1, playerB
     * @param y1B y1 value for worker1, playerB
     * @param x2B x2 value for worker2, playerB
     * @param y2B y2 value for worker2, playerB
     */
    public GodGame(int x1A, int y1A, int x2A, int y2A, int x1B, int y1B, int x2B, int y2B) {
        super(x1A, y1A, x2A, y2A, x1B, y1B, x2B, y2B);
        godCards = new HashMap<>();
        for (Player player : getPlayers()) {
            godCards.put(player, null);
        }
    }

    /**
     * Package-private method for getting god cards.
     * @return the map of god cards
     */
    Map<Player, God> getGodCards() {
        return godCards;
    }

    /**
     * Method for selecting a god.
     * @param god selected god card
     */
    public void selectGod(God god) {
        Player player = getPlayers().get(getNextPlayer());
        Player oppoPlayer = getPlayers().get(1 - getNextPlayer());
        if (getNextPlayer() == 0) {
            if (godCards.get(player) == null) {
                godCards.put(player, god);
                takeTurns();
            } else {
                System.out.println("Oops! You have already selected a god.");
            }
        } else {
            if (godCards.get(player) == null) {
                if (god.getName().equals("NoGod")) {
                    godCards.put(player, god);
                    takeTurns();
                } else if (godCards.get(oppoPlayer).getName().equals(god.getName())) {
                    System.out.println("Oops! You have to select a different god.");
                } else {
                    godCards.put(player, god);
                    takeTurns();
                }
            } else {
                System.out.println("Oops! You have already selected a god.");
            }
        }
    }

    @Override
    public void build(int x, int y, int workerIndex) {
        Player currPlayer = getPlayers().get(getNextPlayer());
        God playerGod = godCards.get(currPlayer);
        if (getWinner() != null || currPlayer.getAction() != 1) {
            return;
        }
        Position currWorker = currPlayer.getWorkers().get(workerIndex);
        if (checkPos(x, y) && notOccupied(x, y) && validSurrounding(x, y, currWorker)) {
            if (getBoard()[x][y].getLevel() == DOME_LEVEL) {
                System.out.println("Oops! Please select a valid position!");
            } else {
                if (playerGod.checkBuild(x, y, currPlayer)) {
                    getBoard()[x][y].setLevel(getBoard()[x][y].getLevel() + 1);
                }
                isTurnEnd();
            }
        } else {
            System.out.println("Oops! Please select a valid position!");
        }
    }

    @Override
    public void move(int x, int y, int workerIndex) {
        Player currPlayer = getPlayers().get(getNextPlayer());
        God playerGod = godCards.get(currPlayer);
        if (getWinner() != null || currPlayer.getAction() != 0) {
            return;
        }
        Position currWorker = currPlayer.getWorkers().get(workerIndex);
        if (checkPos(x, y) && validMove(x, y, currWorker) && getBoard()[x][y].getLevel() != DOME_LEVEL) {
            if (notOccupied(x, y)) {
                playerGod.checkWin(x, y, currPlayer, workerIndex);
                getBoard()[currWorker.getX()][currWorker.getY()].setOccupied(DEFAULT_OCCUPANCY);
                getBoard()[x][y].setOccupied(1);
                currPlayer.setWorker(workerIndex, getBoard()[x][y]);
                currPlayer.setAction(currPlayer.getAction() + 1);
                System.out.println("Move succeeded!");
            } else {
                playerGod.checkWin(x, y, currPlayer, workerIndex);
                playerGod.occupiedMove(x, y, currPlayer, workerIndex);
            }
        } else {
            System.out.println("Oops! Please select a valid position!");
        }
    }
}
