package com.youchuan.hw3;

import static com.youchuan.hw3.Game.BLOCK_HIGHEST_LEVEL;

public class Pan implements God{
    /**
     * Getter for the name of the god.
     * @return String the name of the god
     */
    @Override
    public String getName() {
        return "Pan";
    }

    /**
     * Method for checking winning for Pan's god power after move
     * @param x x position to move
     * @param y y position to move
     * @param currPlayer current player
     * @param workerIndex selected worker index
     */
    @Override
    public void checkWin(int x, int y, Player currPlayer, int workerIndex) {
        Position currWorker = currPlayer.getWorkers().get(workerIndex);
        int currLevel = currWorker.getLevel();
        int levelDiff = currLevel - Game.getBoard()[x][y].getLevel();
        if (levelDiff > 1) {
            Game.setWinner(currPlayer);
            System.out.println("Game Over. Player " + Game.getNextPlayer() + " wins!!!");
        }
        if (Game.getBoard()[x][y].getLevel() == BLOCK_HIGHEST_LEVEL) {
            Game.setWinner(currPlayer);
            System.out.println("Game Over. Player " + Game.getNextPlayer() + " wins!!!");
        }
    }
}
