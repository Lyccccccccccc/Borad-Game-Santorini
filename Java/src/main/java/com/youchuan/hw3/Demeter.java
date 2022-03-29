package com.youchuan.hw3;

public class Demeter implements God{
    private Position prevBuild;
    private int nBuild = 0;

    /**
     * Getter for the name of the god.
     * @return String the name of the god
     */
    @Override
    public String getName() {
        return "Demeter";
    }

    /**
     * Method for checking the build for Demeter's god power.
     * @param x x position to build
     * @param y y position to build
     * @param currPlayer current player
     * @return true for Demeter's first build and Demeter's second different build, false for second same build
     */
    @Override
    public boolean checkBuild(int x, int y, Player currPlayer) {
        if (nBuild == 0) {
            System.out.println("Build succeeded!");
            prevBuild = Game.getBoard()[x][y];
            nBuild = 1;
            return true;
        }
        if (nBuild == 1 && prevBuild == Game.getBoard()[x][y]) {
            currPlayer.setAction(currPlayer.getAction() + 1);
            nBuild = 0;
            return false;
        }
        if (nBuild == 1 && prevBuild != Game.getBoard()[x][y]) {
            currPlayer.setAction(currPlayer.getAction() + 1);
            System.out.println("Build succeeded!");
            nBuild = 0;
            return true;
        }
        return false;
    }
}
