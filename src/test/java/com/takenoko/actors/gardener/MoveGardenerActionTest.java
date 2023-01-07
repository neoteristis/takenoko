package com.takenoko.actors.gardener;

import static org.mockito.Mockito.*;

import com.takenoko.actions.MoveGardenerAction;
import com.takenoko.bot.Bot;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.bamboo.LayerBambooStack;
import com.takenoko.vector.PositionVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MoveGardenerActionTest {

    private MoveGardenerAction moveGardenerAction;
    private BotManager botManager;
    private Board board;

    @BeforeEach
    void setUp() {
        moveGardenerAction = new MoveGardenerAction(new PositionVector(-1, 0, 1));
        botManager = new BotManager(mock(Bot.class));
        board = mock(Board.class);
        when(board.moveGardener(any())).thenReturn(new LayerBambooStack(1));
    }

    @Nested
    @DisplayName("Method execute()")
    class TestExecute {
        @Test
        @DisplayName("should move the gardener")
        void shouldMoveTheGardener() {
            when(board.getGardenerPosition()).thenReturn(new PositionVector(0, 0, 0));
            moveGardenerAction.execute(board, botManager);
            verify(board).moveGardener(new PositionVector(-1, 0, 1));
        }
    }
}