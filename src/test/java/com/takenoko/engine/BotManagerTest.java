package com.takenoko.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.takenoko.actors.panda.MovePandaAction;
import com.takenoko.bot.Bot;
import com.takenoko.bot.TilePlacingAndPandaMovingBot;
import com.takenoko.bot.TilePlacingBot;
import com.takenoko.inventory.Inventory;
import com.takenoko.layers.tile.PlaceTileAction;
import com.takenoko.layers.tile.Tile;
import com.takenoko.objective.Objective;
import com.takenoko.objective.TwoAdjacentTilesObjective;
import com.takenoko.ui.ConsoleUserInterface;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.*;

public class BotManagerTest {
    TilePlacingBot tilePlacingBot;
    BotManager botManager;

    @BeforeEach
    void setUp() {
        tilePlacingBot = new TilePlacingBot();
        botManager = new BotManager(tilePlacingBot);
    }

    @AfterEach
    void tearDown() {
        tilePlacingBot = null;
        botManager = null;
    }

    @Nested
    @DisplayName("Method getObjectiveDescription")
    class TestGetObjectiveDescription {
        @Test
        @DisplayName("The objective is to have two adjacent tiles, returns the correct description")
        void
                getObjectiveDescription_WhenObjectiveIsToHaveTwoAdjacentTiles_ThenReturnsCorrectDescription() {
            Objective objective = new TwoAdjacentTilesObjective();
            botManager.setObjective(objective);
            assertThat(botManager.getObjectiveDescription()).isEqualTo(objective.toString());
        }

        @Test
        @DisplayName("When there is no objective, returns correctly")
        void getObjectiveDescription_WhenThereIsNoObjective_ThenReturnsCorrectly() {
            botManager.setObjective(null);
            assertThat(botManager.getObjectiveDescription()).isEqualTo("No current objective");
        }
    }

    @Nested
    @DisplayName("Method getNumberOfActions")
    class TestGetNumberOfActions {
        @Test
        @DisplayName("Default number of actions should be 2")
        void getNumberOfActions_ThenReturns2() {
            assertThat(botManager.getNumberOfActions()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("Method verifyObjective and objectiveIsAchieved")
    class TestVerifyObjective {
        @Test
        @DisplayName("By default when board does not satisfy objective, objective is not achieved")
        void verifyObjective_whenObjectiveIsNotAchieved_thenReturnFalse() {
            Objective objective = mock(Objective.class);
            when(objective.isAchieved()).thenReturn(false);
            botManager.setObjective(objective);
            botManager.verifyObjective(mock(Board.class));
            assertThat(botManager.isObjectiveAchieved()).isFalse();
        }

        @Test
        @DisplayName("When board satisfies objective, objective is achieved")
        void verifyObjective_whenObjectiveIsAchieved_thenReturnTrue() {
            Objective objective = mock(Objective.class);
            when(objective.isAchieved()).thenReturn(true);
            botManager.setObjective(objective);
            botManager.verifyObjective(mock(Board.class));
            assertThat(botManager.isObjectiveAchieved()).isTrue();
        }
    }

    @Nested
    @DisplayName("Method playBot")
    class playTilePlacingBot {}

    @Nested
    @DisplayName("Method getEatenBambooCounter")
    class TestGetEatenBambooCounter {
        @Test
        @DisplayName("When initialized, a botManager's bamboo counter is equal to zero")
        void getEatenBambooCounter_WhenInitialized_BambooCounterIsEqualToZero() {
            assertThat(botManager.getEatenBambooCounter()).isZero();
        }

        @Test
        @DisplayName(
                "When the botManager makes the panda eat a bamboo, the bamboo counter is at least"
                        + " one")
        void
                getEatenBambooCounter_WhenTheBotManagerMakesThePandaEatABamboo_TheBambooCounterIsAtLeastOne() {
            Board board = spy(new Board());
            TilePlacingAndPandaMovingBot bot = mock(TilePlacingAndPandaMovingBot.class);
            BotManager botManager = spy(new BotManager(bot));
            new PlaceTileAction(new Tile(), new PositionVector(0, -1, 1))
                    .execute(board, botManager);
            new MovePandaAction(new PositionVector(0, -1, 1)).execute(board, botManager);
            assertThat(botManager.getEatenBambooCounter()).isPositive();
        }
    }

    @Nested
    @DisplayName("Method getInventory")
    class TestGetInventory {
        @Test
        @DisplayName("When initialized, a botManager's inventory")
        void getInventory_WhenInitialized_InventoryIsNotNull() {
            Inventory inventory = new Inventory();
            BotManager botManager =
                    new BotManager(
                            0,
                            mock(Objective.class),
                            mock(ConsoleUserInterface.class),
                            "",
                            mock(Bot.class),
                            inventory);
            assertThat(botManager.getInventory()).isEqualTo(inventory);
        }
    }

    @Nested
    @DisplayName("Method getName")
    class TestGetName {
        @Test
        @DisplayName("When initialized, a botManager's name is not null")
        void getName_WhenInitialized_NameIsNotNull() {
            BotManager botManager =
                    new BotManager(
                            0,
                            mock(Objective.class),
                            mock(ConsoleUserInterface.class),
                            "name",
                            mock(Bot.class),
                            mock(Inventory.class));
            assertThat(botManager.getName()).isEqualTo("name");
        }
    }
}
