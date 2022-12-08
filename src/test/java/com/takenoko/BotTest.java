package com.takenoko;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import org.junit.jupiter.api.*;

class BotTest {
    private Bot bot;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        bot = new Bot();
    }

    @AfterEach
    void tearDown() {
        bot = null;
        board = null;
    }

    @Nested
    @DisplayName("Method placeTile")
    class TestPlaceTile {
        @Test
        @DisplayName("When a valid tile exists, returns the tile")
        void chooseTileToPlace_WhenValidTileExists_ThenReturnsTheTile() {
            Tile tile = bot.chooseTileToPlace(board.getAvailableTiles());
            assertThat(tile).isNotNull();
        }

        @Test
        @DisplayName("When there is no valid tile, throw exception")
        void chooseTileToPlace_WhenThereIsNoValidTile_ThenThrowException() {
            ArrayList<Tile> emptyList = new ArrayList<>();
            assertThatThrownBy(() -> bot.chooseTileToPlace(emptyList))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("No possible tiles");
        }
    }

    @Nested
    @DisplayName("Method getObjective")
    class TestGetObjective {
        @Test
        @DisplayName("should return the place one tile objective")
        void getObjective_ShouldReturnThePlaceOneTileObjective() {
            Objective objective = new PlaceTileObjective(1);
            assertThat(bot.getObjective()).isEqualTo(objective);
        }
    }
}