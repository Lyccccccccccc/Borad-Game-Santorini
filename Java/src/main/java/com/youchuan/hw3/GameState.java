package com.youchuan.hw3;

import java.util.Arrays;
import java.util.List;

public final class GameState {
    private final Cell[] cells;
    private final Player winner;
    private final int turn;
    public static final int CELL_SIZE = 25;
    public static final int BOUND = 4;
    public static final int COEFFICIENT = 5;

    private GameState(Cell[] cells, Player winner, int turn) {
        this.cells = cells;
        this.turn = turn;
        this.winner = winner;
    }

    public static GameState forGame(Game game) {
        Cell[] cells = getCells(game);
        Player winner = getWinner(game);
        int turn = getTurn();
        return new GameState(cells, winner, turn);
    }

    @Override
    public String toString() {
        if (this.winner == null) {
            return "{ \"cells\": " + Arrays.toString(this.cells) + "," +
                    "\"turn\": " + String.valueOf(this.turn) + "}";
        }
        return "{ \"cells\": " + Arrays.toString(this.cells) + "," +
                "\"turn\": " + String.valueOf(this.turn) + "," +
                "\"winner\": " + String.valueOf(this.turn) + "}";
    }

    private static Cell[] getCells(Game game) {
        Cell[] cells = new Cell[CELL_SIZE];
        Position[][] board = Game.getBoard();
        for (int x = 0; x <= BOUND; x++) {
            for (int y = 0; y <= BOUND; y++) {
                String text = "";
                String link = "/play?x=" + x + "&y=" + y;
                String clazz = "playable";
                List<Player> players = Game.getPlayers();
                if (board[x][y].getOccupied() == 1) {
                    if (players.get(0).getWorkers().contains(board[x][y])) text = "X";
                    else if (players.get(1).getWorkers().contains(board[x][y])) text = "O";
                }
                if (board[x][y].getLevel() != 0) {
                    String level = "Level: " + String.valueOf(board[x][y].getLevel());
                    text = text + "  " + level;
                }
                cells[COEFFICIENT * y + x] = new Cell(text, clazz, link);
            }
        }
        return cells;
    }

    private static Player getWinner(Game game) {
        return game.getWinner();
    }

    private static int getTurn() {
        return Game.getNextPlayer();
    }
}
    class Cell {
        private final String text;
        private final String clazz;
        private final String link;

        Cell(String text, String clazz, String link) {
            this.text = text;
            this.clazz = clazz;
            this.link = link;
        }

        public String getText() {
            return this.text;
        }

        public String getClazz() {
            return this.clazz;
        }

        public String getLink() {
            return this.link;
        }

        @Override
        public String toString() {
            return "{ \"text\": \"" + this.text + "\"," +
                    " \"clazz\": \"" + this.clazz + "\"," +
                    " \"link\": \"" + this.link + "\"}";
        }
}
