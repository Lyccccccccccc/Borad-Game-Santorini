package com.youchuan.hw3;

public class Position {
    private int x;
    private int y;
    private int level;
    private int occupied;

    /**
     * The constructor for initializing the position with x, y values and level, occupancy.
     * @param xPos the x value for the position
     * @param yPos the y value for the position
     * @param l the level of the position
     * @param o the occupancy of the position
     */
    public Position(int xPos, int yPos, int l, int o) {
        x = xPos;
        y = yPos;
        level = l;
        occupied = o;
    }

    /**
     * Getter for x value of the position.
     * @return x value of the position
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for y value of the position.
     * @return y value of the position
     */
    public int getY() {
        return y;
    }

    /**
     * Getter for level of the position.
     * @return level of the position
     */
    public int getLevel() {
        return level;
    }

    /**
     * Getter for the occupancy of the position.
     * @return the occupancy of the position
     */
    public int getOccupied() { return occupied; }

    /**
     * Setter for the level of the position.
     * @param i set the level of the position to i
     */
    public void setLevel(int i) {
        level = i;
    }

    /**
     * Setter for the occupancy of the position.
     * @param i set the occupancy of the position to i
     */
    public void setOccupied(int i) {
        occupied = i;
    }
}
