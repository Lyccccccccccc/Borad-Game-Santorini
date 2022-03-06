package com.youchuan.hw3;

import java.util.ArrayList;
import java.util.List;

import static com.youchuan.hw3.Game.DEFAULT_LEVEL;
import static com.youchuan.hw3.Game.DEFAULT_OCCUPANCY;

public class Player {
    private int action;
    private List<Position> workers;

    /**
     * The constructor for initializing the player with four x and y values.
     * @param x1 x1 value for worker1
     * @param y1 y1 value for worker1
     * @param x2 x2 value for worker2
     * @param y2 y2 value for worker2
     */
    public Player(int x1, int y1, int x2, int y2) {
        if (x1 == x2 && y1 == y2) {
            System.out.println("Oops! Please select different positions for your workers!");
            System.exit(0);
        } else {
            workers = new ArrayList<>(2);
            Position workerPos1 = new Position(x1, y1, DEFAULT_LEVEL, DEFAULT_OCCUPANCY + 1);
            Position workerPos2 = new Position(x2, y2, DEFAULT_LEVEL, DEFAULT_OCCUPANCY + 1);
            workers.add(workerPos1);
            workers.add(workerPos2);
            action = 0;
        }
    }

    /**
     * Setter for action of player.
     * @param act takes integer to indicate the state
     */
    public void setAction(int act) {
        action = act;
    }

    /**
     * Getter for action of player.
     * @return 1,2,0 indicating the player has acted move, build or not
     */
    public int getAction() {
        return action;
    }

    /**
     * Setter for the position of the selected worker.
     * @param workerIndex the selected worker's index in the player's worker list
     * @param goTo the position that the selected worker will go to
     */
    public void setWorker(int workerIndex, Position goTo) {
        workers.set(workerIndex, goTo);
    }

    /**
     * Getter for the workers of the player.
     * @return a list of workers' positions
     */
    public List<Position> getWorkers() {
        return workers;
    }

    /**
     * Getter for the workers' current levels.
     * @return a list of workers' levels
     */
    public List<Integer> getWorkerLevel() {
        List<Integer> workerLevel = new ArrayList<>(2);
        workerLevel.add(workers.get(0).getLevel());
        workerLevel.add(workers.get(1).getLevel());
        return workerLevel;
    }
}
