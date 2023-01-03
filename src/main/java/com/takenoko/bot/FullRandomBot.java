package com.takenoko.bot;

import com.takenoko.actors.panda.MovePandaAction;
import com.takenoko.engine.Board;
import com.takenoko.layers.tile.PlaceTileAction;
import com.takenoko.layers.tile.Tile;
import com.takenoko.vector.PositionVector;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FullRandomBot implements Bot {
    Random random;

    public FullRandomBot() {
        random = new Random();
    }

    @Override
    public Action chooseAction(Board board) {
        List<Action> actions = new ArrayList<>();

        // Add all possible random actions types
        actions.add(getRandomPlaceTileAction(board));
        actions.add(getRandomMovePandaAction(board));
        actions.removeIf(Objects::isNull);
        return actions.get(random.nextInt(actions.size()));
    }

    private Action getRandomPlaceTileAction(Board board) {
        List<Tile> availableTiles = board.getAvailableTiles();
        List<PositionVector> availableTilePositions = board.getAvailableTilePositions();
        if (availableTiles.isEmpty() || availableTilePositions.isEmpty()) {
            return null;
        }
        return new PlaceTileAction(
                availableTiles.get(random.nextInt(availableTiles.size())),
                availableTilePositions.get(random.nextInt(availableTilePositions.size())));
    }

    private Action getRandomMovePandaAction(Board board) {
        List<PositionVector> pandaPossibleMoves = board.getPandaPossibleMoves();
        if (pandaPossibleMoves.isEmpty()) {
            return null;
        }
        return new MovePandaAction(
                pandaPossibleMoves.get(random.nextInt(pandaPossibleMoves.size())));
    }
}
