package com.takenoko.objective;

import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.TileColor;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/** The Panda objective. */
public class PandaObjective extends Objective {
    private final Map<TileColor, Integer> bambooTarget;

    public PandaObjective(Map<TileColor, Integer> bambooTarget, int points) {
        super(ObjectiveTypes.PANDA, ObjectiveState.NOT_ACHIEVED, points);
        this.bambooTarget = bambooTarget;
        if (bambooTarget.isEmpty()) {
            throw new IllegalArgumentException("The bamboo target cannot be empty");
        }
    }

    public PandaObjective(PandaObjective pandaObjective) {
        super(pandaObjective.getType(), pandaObjective.getState(), pandaObjective.getPoints());
        this.bambooTarget = new EnumMap<>(TileColor.class);
        this.bambooTarget.putAll(pandaObjective.bambooTarget);
    }

    @Override
    public void verify(Board board, BotManager botManager) {
        boolean matched =
                bambooTarget.entrySet().stream()
                        .allMatch(
                                entry ->
                                        botManager.getInventory().getBambooCount(entry.getKey())
                                                >= entry.getValue());
        if (matched) {
            state = ObjectiveState.ACHIEVED;
        } else state = ObjectiveState.NOT_ACHIEVED;
    }

    @Override
    public void reset() {
        state = ObjectiveState.NOT_ACHIEVED;
    }

    @Override
    public Objective copy() {
        return new PandaObjective(this);
    }

    @Override
    public float getCompletion(Board board, BotManager botManager) {
        return bambooTarget.entrySet().stream()
                        .map(
                                entry ->
                                        (float)
                                                        botManager
                                                                .getInventory()
                                                                .getBambooCount(entry.getKey())
                                                / entry.getValue())
                        .reduce(0f, Float::sum)
                / bambooTarget.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PandaObjective that = (PandaObjective) o;
        return Objects.equals(bambooTarget, that.bambooTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bambooTarget);
    }

    @Override
    public String toString() {
        return "Panda Objective <" + bambooTarget + " high>";
    }

    public Map<TileColor, Integer> getBambooTarget() {
        return new EnumMap<>(bambooTarget);
    }
}
