package com.youchuan.hw3;

import static com.youchuan.hw3.Game.BLOCK_HIGHEST_LEVEL;

public interface God {
    String getName();

    default void checkWin(int x, int y, Player currPlayer, int workerIndex) {
        if (Game.getBoard()[x][y].getLevel() == BLOCK_HIGHEST_LEVEL) {
            Game.setWinner(currPlayer);
            System.out.println("Game Over. Player " + Game.getNextPlayer() + " wins!!!");
        }
    }

    default void occupiedMove(int x, int y, Player currPlayer, int workerIndex) {
        System.out.println("Oops! Please select a valid position!");
    }

    default boolean checkBuild(int x, int y, Player currPlayer) {
        currPlayer.setAction(currPlayer.getAction() + 1);
        System.out.println("Build succeeded!");
        return true;
    }
}
