package com.youchuan.hw3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import fi.iki.elonen.NanoHTTPD;

public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private GodGame game;
    private final List<Integer> initialX = new ArrayList<>();
    private final List<Integer> initialY = new ArrayList<>();
    private int workerIndex;
    private int moveWorkerIdx;
    public static final int PORT = 8080;

    public App() throws IOException {
        super(PORT);

        this.game = new GodGame();

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        if (uri.equals("/newgame")) {
            this.game = new GodGame();
        } else if (uri.equals("/play")) {
            int x = Integer.parseInt(params.get("x"));
            int y = Integer.parseInt(params.get("y"));
            if (Game.getPlayers().size() == 0) {
                initialize(params);
            } else if (Game.getPlayers().size() == 1) {
                initialize(params);
            } else if (Game.getBoard()[x][y].getOccupied() == 1
                    && Game.getPlayers().get(Game.getNextPlayer()).getWorkers().contains(Game.getBoard()[x][y])){
                workerIndex = game.getWorkerIndex(x, y);
            } else if (Game.getPlayers().get(Game.getNextPlayer()).getAction() == 0) {
                game.move(x, y, workerIndex);
                moveWorkerIdx = workerIndex;
            } else if (Game.getPlayers().get(Game.getNextPlayer()).getAction() == 1) {
                game.build(x, y, moveWorkerIdx);
            }
        } else if (uri.equals("/demeter")) {
            game.selectGod(new Demeter());
        } else if (uri.equals("/minotaur")) {
            game.selectGod(new Minotaur());
        } else if (uri.equals("/pan")) {
            game.selectGod(new Pan());
        } else if (uri.equals("/nogod")) {
            game.selectGod(new NoGod());
        }
        // Extract the view-specific data from the game and apply it to the template.
        GameState gameplay = GameState.forGame(this.game);
        return newFixedLengthResponse(gameplay.toString());
    }

    private void initialize(Map<String, String> params) {
        if (initialX.size() == 0) {
            initialX.add(Integer.parseInt(params.get("x")));
            initialY.add(Integer.parseInt(params.get("y")));
        } else if (initialX.size() == 1) {
            initialX.add(Integer.parseInt(params.get("x")));
            initialY.add(Integer.parseInt(params.get("y")));
            this.game.initiatePlayer(initialX.get(0), initialY.get(0), initialX.get(1), initialY.get(1));
            this.game.takeTurns();
            initialX.clear();
            initialY.clear();
        }
    }
}
