package com.youchuan.hw3;

import java.util.Objects;

public class Position {
    private int x;
    private int y;
    private int level;
    private int occupied;
    public static final int HASHCODE_CALCULATE1 = 17;
    public static final int HASHCODE_CALCULATE2 = 31;

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
     * Package-private setter for the level of the position.
     * @param i set the level of the position to i
     */
    void setLevel(int i) {
        level = i;
    }

    /**
     * Package-private setter for the occupancy of the position.
     * @param i set the occupancy of the position to i
     */
    void setOccupied(int i) {
        occupied = i;
    }

    /**
     * Override equals method for comparing positions.
     * @param o the other position
     * @return true for same position, false for not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position other = (Position) o;
        return this.getX() == other.getX() && this.getY() == other.getY() && this.getLevel() == other.getLevel() && this.getOccupied() == other.getOccupied();
    }

    /**
     * Override hashCode method to support equals.
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = HASHCODE_CALCULATE1;
        result = HASHCODE_CALCULATE2 * result + this.getX();
        result = HASHCODE_CALCULATE2 * result + this.getY();
        result = HASHCODE_CALCULATE2 * result + this.getLevel();
        result = HASHCODE_CALCULATE2 * result + this.getOccupied();
        return result;
    }
}
