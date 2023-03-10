package com.takenoko.actions;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the result of an action.
 *
 * @param availableActions the availableActions available to the bot after the action
 * @param cost the cost of the action
 */
public record ActionResult(List<Class<? extends Action>> availableActions, int cost) {
    public ActionResult(List<Class<? extends Action>> availableActions) {
        this(availableActions, 0);
    }

    public ActionResult() {
        this(new ArrayList<>(), 0);
    }

    public ActionResult(int cost) {
        this(new ArrayList<>(), cost);
    }
}
