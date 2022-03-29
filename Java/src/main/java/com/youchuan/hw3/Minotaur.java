package com.youchuan.hw3;

import java.util.List;

public class Minotaur implements God{
    /**
     * Getter for the name of the god.
     * @return String the name of the god
     */
    @Override
    public String getName() {
        return "Minotaur";
    }

    private boolean validMino(Position curr, Position oppo) {
        return Game.checkPos(2 * oppo.getX() - curr.getX(), 2 * oppo.getY() - curr.getY())
                && Game.notOccupied(2 * oppo.getX() - curr.getX(), 2 * oppo.getY() - curr.getY());
    }

    /**
     * Method for implementing occupied move of Minotaur's god power.
     * @param x x position to move
     * @param y y position to move
     * @param currPlayer current player
     * @param workerIndex selected worker index
     */
    @Override
    public void occupiedMove(int x, int y, Player currPlayer, int workerIndex) {
        Player oppoPlayer = Game.getPlayers().get(1 - Game.getNextPlayer());
        List<Position> oppoWorkers = oppoPlayer.getWorkers();
        Position currWorker = currPlayer.getWorkers().get(workerIndex);
        if (oppoWorkers.contains(Game.getBoard()[x][y]) && validMino(currWorker, Game.getBoard()[x][y])) {
            int oppoWorkerIndex = oppoWorkers.indexOf(Game.getBoard()[x][y]);
            Game.getBoard()[currWorker.getX()][currWorker.getY()].setOccupied(Game.DEFAULT_OCCUPANCY);
            Game.getBoard()[2 * x - currWorker.getX()][2 * y - currWorker.getY()].setOccupied(1);
            currPlayer.setWorker(workerIndex, Game.getBoard()[x][y]);
            oppoPlayer.setWorker(oppoWorkerIndex, Game.getBoard()[2 * x - currWorker.getX()][2 * y - currWorker.getY()]);
            currPlayer.setAction(currPlayer.getAction() + 1);
            System.out.println("Move succeeded!");
        }
    }
}
