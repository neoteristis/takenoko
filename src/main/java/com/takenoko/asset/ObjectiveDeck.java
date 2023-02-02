package com.takenoko.asset;

import com.takenoko.layers.tile.ImprovementType;
import com.takenoko.layers.tile.TileColor;
import com.takenoko.objective.*;
import com.takenoko.shape.PatternFactory;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/** The deck containing all the different objectives. */
public class ObjectiveDeck extends ArrayList<Objective> {
    private final Random random;
    private transient Objective lastDrawnObjective;

    public ObjectiveDeck() {
        this(new SecureRandom());
    }

    public ObjectiveDeck(Random random) {
        this.random = random;
        lastDrawnObjective = null;
        // --- Panda objectives ---
        for (int i = 0; i < 5; i++) {
            add(new PandaObjective(Map.of(TileColor.GREEN, 2), 3)); // 2 GREEN
        }
        for (int i = 0; i < 4; i++) {
            add(new PandaObjective(Map.of(TileColor.YELLOW, 2), 4)); // 2 YELLOW
        }

        for (int i = 0; i < 3; i++) {
            add(new PandaObjective(Map.of(TileColor.PINK, 2), 5)); // 2 PINK
        }

        for (int i = 0; i < 3; i++) {
            add(
                    new PandaObjective(
                            Map.of(
                                    TileColor.GREEN, 1,
                                    TileColor.YELLOW, 1,
                                    TileColor.PINK, 1),
                            6)); // 1 GREEN, 1 YELLOW, 1 PINK
        }
        // --- Pattern objectives ---
        // LINE
        add(new PatternObjective(PatternFactory.LINE.createPattern(TileColor.GREEN), 2)); // GREEN
        add(new PatternObjective(PatternFactory.LINE.createPattern(TileColor.YELLOW), 3)); // YELLOW
        add(new PatternObjective(PatternFactory.LINE.createPattern(TileColor.PINK), 4)); // PINK
        // CURVED LINE
        add(new PatternObjective(PatternFactory.CURVE.createPattern(TileColor.GREEN), 2)); // GREEN
        add(
                new PatternObjective(
                        PatternFactory.CURVE.createPattern(TileColor.YELLOW), 3)); // YELLOW
        add(new PatternObjective(PatternFactory.CURVE.createPattern(TileColor.PINK), 4)); // PINK
        // UNIFORM SQUARE
        add(
                new PatternObjective(
                        PatternFactory.TRIANGLE.createPattern(TileColor.GREEN), 2)); // GREEN
        add(
                new PatternObjective(
                        PatternFactory.TRIANGLE.createPattern(TileColor.YELLOW), 3)); // YELLOW
        add(new PatternObjective(PatternFactory.TRIANGLE.createPattern(TileColor.PINK), 4)); // PINK
        // UNIFORM RECTANGLE
        add(
                new PatternObjective(
                        PatternFactory.DIAMOND.createPattern(TileColor.GREEN), 3)); // GREEN
        add(
                new PatternObjective(
                        PatternFactory.DIAMOND.createPattern(TileColor.YELLOW), 4)); // YELLOW
        add(new PatternObjective(PatternFactory.DIAMOND.createPattern(TileColor.PINK), 5)); // PINK
        // MIXED COLORS
        add(
                new PatternObjective(
                        PatternFactory.MIXED_COLORS_DIAMOND.createPattern(TileColor.YELLOW),
                        3)); // GREEN + YELLOW
        add(
                new PatternObjective(
                        PatternFactory.MIXED_COLORS_DIAMOND.createPattern(TileColor.GREEN),
                        4)); // GREEN + PINK
        add(
                new PatternObjective(
                        PatternFactory.MIXED_COLORS_DIAMOND.createPattern(TileColor.PINK),
                        5)); // PINK + YELLOW

        // --- Gardener objectives ---
        // WITHOUT IMPROVEMENT
        // SINGLE COLUMN
        add(new SingleGardenerObjective(4, TileColor.GREEN, 5)); // 4 GREEN
        add(new SingleGardenerObjective(4, TileColor.YELLOW, 6)); // 4 YELLOW
        add(new SingleGardenerObjective(4, TileColor.PINK, 7)); // 4 PINK

        // WITH IMPROVEMENT
        // GREEN
        add(
                new SingleGardenerObjective(
                        4, TileColor.GREEN, ImprovementType.ENCLOSURE, 4)); // 4 GREEN
        add(new SingleGardenerObjective(4, TileColor.GREEN, ImprovementType.POOL, 4)); // 4 GREEN
        add(
                new SingleGardenerObjective(
                        4, TileColor.GREEN, ImprovementType.FERTILIZER, 3)); // 4 GREEN
        // YELLOW
        add(
                new SingleGardenerObjective(
                        4, TileColor.YELLOW, ImprovementType.ENCLOSURE, 5)); // 4 YELLOW
        add(new SingleGardenerObjective(4, TileColor.YELLOW, ImprovementType.POOL, 5)); // 4 YELLOW
        add(
                new SingleGardenerObjective(
                        4, TileColor.YELLOW, ImprovementType.FERTILIZER, 4)); // 4 YELLOW
        // PINK
        add(new SingleGardenerObjective(4, TileColor.PINK, ImprovementType.ENCLOSURE, 6)); // 4 PINK
        add(new SingleGardenerObjective(4, TileColor.PINK, ImprovementType.POOL, 6)); // 4 PINK
        add(
                new SingleGardenerObjective(
                        4, TileColor.PINK, ImprovementType.FERTILIZER, 5)); // 4 PINK

        // IMPROVEMENT AGNOSTIC
        // MULTIPLE COLUMNS
        add(
                new MultipleGardenerObjective(
                        new SingleGardenerObjective(3, TileColor.PINK, 0), 2, 6)); // 3 PINK
        add(
                new MultipleGardenerObjective(
                        new SingleGardenerObjective(3, TileColor.YELLOW, 0), 3, 7)); // 3 YELLOW
        add(
                new MultipleGardenerObjective(
                        new SingleGardenerObjective(3, TileColor.GREEN, 0), 4, 8)); // 3 GREEN
    }

    public void draw() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot draw from an empty deck");
        }
        lastDrawnObjective = get(random.nextInt(size()));
        remove(lastDrawnObjective);
    }

    public Objective peek() {
        return lastDrawnObjective;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ObjectiveDeck that = (ObjectiveDeck) o;
        return Objects.equals(random, that.random)
                && Objects.equals(lastDrawnObjective, that.lastDrawnObjective);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), random, lastDrawnObjective);
    }
}