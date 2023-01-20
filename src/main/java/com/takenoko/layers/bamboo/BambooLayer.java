package com.takenoko.layers.bamboo;

import com.takenoko.engine.Board;
import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.TileType;
import com.takenoko.vector.PositionVector;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/** BambooLayer class. The bamboo layer contains the number of bamboo on each tile. */
public class BambooLayer {
    private final HashMap<PositionVector, LayerBambooStack> bamboo;

    /** Constructor for the BambooLayer class. */
    public BambooLayer() {
        bamboo = new HashMap<>();
    }

    public BambooLayer(BambooLayer bambooLayer) {
        this();
        for (PositionVector positionVector : bambooLayer.bamboo.keySet()) {
            bamboo.put(positionVector, bambooLayer.bamboo.get(positionVector).copy());
        }
    }

    /**
     * Grow bamboo on a tile. By default, the number of bamboo is 1 if the tile is irrigated.
     *
     * @param positionVector the position of the tile
     * @param board the board
     */
    public void growBamboo(PositionVector positionVector, Board board) {
        if (!board.isBambooGrowableAt(positionVector)) {
            throw new IllegalArgumentException("The tile is not growable");
        }

        Optional<ImprovementType> improvement = board.getTileAt(positionVector).getImprovement();

        if (improvement.isPresent() && improvement.get() == ImprovementType.FERTILIZER) {
            if (bamboo.containsKey(positionVector)) {
                if (bamboo.get(positionVector).bambooCount == 3) {
                    bamboo.get(positionVector).growBamboo();
                } else {
                    bamboo.get(positionVector).growBamboo(2);
                }
            } else {
                bamboo.put(positionVector, new LayerBambooStack(2));
            }
        } else {
            if (bamboo.containsKey(positionVector)) {
                bamboo.get(positionVector).growBamboo();
            } else {
                bamboo.put(positionVector, new LayerBambooStack(1));
            }
        }
    }

    /**
     * Get the number of bamboo on a tile.
     *
     * @param positionVector the position of the tile
     * @param board the board
     * @return the number of bamboo on the tile
     */
    public LayerBambooStack getBambooAt(PositionVector positionVector, Board board) {
        if (board.isTile(positionVector)) {
            bamboo.computeIfAbsent(positionVector, k -> new LayerBambooStack(0));
            return bamboo.get(positionVector);
        } else {
            throw new IllegalArgumentException("The position is not a tile");
        }
    }

    /**
     * Eat a bamboo from a tile.
     *
     * @param positionVector the position of the tile
     * @param board the board
     */
    public void eatBamboo(PositionVector positionVector, Board board) {
        if (board.isBambooEatableAt(positionVector)) {
            bamboo.get(positionVector).eatBamboo();
        } else {
            throw new IllegalArgumentException("The tile is not eatable");
        }
    }

    public BambooLayer copy() {
        return new BambooLayer(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BambooLayer that = (BambooLayer) o;
        return bamboo.equals(that.bamboo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bamboo);
    }

    public boolean isEatableAt(PositionVector positionVector, Board board) {
        if (board.isTile(positionVector)
                && board.getTileAt(positionVector).getType() != TileType.POND) {

            Optional<ImprovementType> improvementType =
                    board.getTileAt(positionVector).getImprovement();
            if (improvementType.isPresent()
                    && improvementType.get().equals(ImprovementType.ENCLOSURE)) {
                return false;
            }
            return board.getBambooAt(positionVector).isEatable();
        }
        return false;
    }

    public boolean isGrowableAt(PositionVector positionVector, Board board) {
        return board.isTile(positionVector)
                && board.getTileAt(positionVector).getType() != TileType.POND
                && board.getBambooAt(positionVector).isGrowable();
    }
}
