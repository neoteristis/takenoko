package com.takenoko.actions;

import com.takenoko.actions.annotations.ActionAnnotation;
import com.takenoko.actions.annotations.ActionType;
import com.takenoko.engine.Board;
import com.takenoko.engine.BotManager;
import com.takenoko.layers.tile.ImprovementType;
import java.util.List;

@ActionAnnotation(ActionType.FORCED)
public class DrawImprovementAction implements Action {

    ImprovementType improvementType;

    public DrawImprovementAction(ImprovementType improvementType) {
        this.improvementType = improvementType;
    }

    @Override
    public ActionResult execute(Board board, BotManager botManager) {
        if (!board.hasImprovementInDeck(improvementType)) {
            botManager.displayMessage("No more " + improvementType + " in the deck");
            return new ActionResult(List.of(ChooseAndApplyWeatherAction.class));
        }
        board.drawImprovement(improvementType);
        botManager.displayMessage(botManager.getName() + " got improvement" + improvementType);
        return new ActionResult(
                List.of(ApplyImprovementAction.class, StoreImprovementAction.class), 1);
    }
}
