package com.takenoko.engine;

import com.takenoko.actions.Action;
import com.takenoko.actions.ChooseIfApplyWeatherAction;
import com.takenoko.bot.Bot;
import com.takenoko.bot.TilePlacingBot;
import com.takenoko.inventory.Inventory;
import com.takenoko.objective.Objective;
import com.takenoko.ui.ConsoleUserInterface;
import com.takenoko.weather.Weather;
import java.util.List;
import java.util.UUID;

/**
 * This class is used to manage one bot. It is responsible for managing all of its attributes :
 *
 * <ul>
 *   <li>name
 *   <li>objective
 *   <li>number of actions
 *   <li>bamboos eaten counter
 * </ul>
 */
public class BotManager {
    private static final ConsoleUserInterface DEFAULT_CONSOLE_USER_INTERFACE =
            new ConsoleUserInterface();
    private static final String DEFAULT_NAME = "Joe";
    private static final Bot DEFAULT_BOT = new TilePlacingBot();
    // ATTRIBUTES
    private final ConsoleUserInterface consoleUserInterface;
    private final BotState botState;
    private final String name;
    private final Bot bot;
    private int score;
    private final UUID botId;

    /**
     * Constructor for the class
     *
     * @param consoleUserInterface the console user interface
     * @param name the name of the bot
     * @param bot the bot
     * @param botState the bot state
     */
    protected BotManager(
            ConsoleUserInterface consoleUserInterface, String name, Bot bot, BotState botState) {
        this.botState = botState;
        this.consoleUserInterface = consoleUserInterface;
        this.name = name;
        this.bot = bot;
        this.botId = UUID.randomUUID();
        this.score = 0;
    }

    /** Default constructor for the class */
    protected BotManager() {
        this(DEFAULT_CONSOLE_USER_INTERFACE, DEFAULT_NAME, DEFAULT_BOT, new BotState());
    }

    /**
     * Constructor for the class but this time specifying which bot algorithm must be used
     *
     * @param bot the bot
     */
    public BotManager(Bot bot) {
        this(DEFAULT_CONSOLE_USER_INTERFACE, DEFAULT_NAME, bot, new BotState());
    }

    /**
     * Ask for the bot to choose an action based on his algorithm and then execute the returned
     * action. Objectives are also verified in order to know if the bot has won.
     *
     * @param board the board of the game
     */
    public void playBot(Board board) {
        Weather rolledWeather = board.rollWeather();
        botState.setRolledWeather(rolledWeather);
        botState.setAvailableActions(List.of(ChooseIfApplyWeatherAction.class));
        for (int i = 0; i < botState.getNumberOfActions(); i++) {
            Action action = bot.chooseAction(board.copy(), botState.copy());
            action.execute(board, this);
            verifyObjective(board);
            if (isObjectiveAchieved()) {
                return;
            }
        }
    }

    /**
     * @return the objective description or "No current objective" if there is no objective
     */
    public String getObjectiveDescription() {
        if (botState.getObjective() != null) {
            return botState.getObjective().toString();
        }
        return "No current objective";
    }

    /**
     * @return number of actions the bot can do in a turn
     */
    protected int getNumberOfActions() {
        return botState.getNumberOfActions();
    }

    /**
     * @param message the message to display
     */
    public void displayMessage(String message) {
        consoleUserInterface.displayMessage(message);
    }

    /**
     * @return boolean to know is the objective is achieved or not
     */
    public boolean isObjectiveAchieved() {
        if (botState.getObjective() != null) {
            return botState.getObjective().isAchieved();
        }
        return false;
    }

    /**
     * Verify the objective using the game board
     *
     * @param board current board game
     */
    public void verifyObjective(Board board) {
        if (botState.getObjective() != null) {
            botState.getObjective().verify(board, this);
        }
    }

    /**
     * Change or set the bot objective
     *
     * @param objective the new objective
     */
    public void setObjective(Objective objective) {
        this.botState.setObjective(objective);
    }

    /**
     * @return the bot name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the number of bamboo eaten by the bot
     */
    public int getEatenBambooCounter() {
        return botState.getEatenBambooCounter();
    }

    /**
     * Return the bot inventory
     *
     * @return the bot inventory
     */
    public Inventory getInventory() {
        return botState.getInventory();
    }

    /**
     * @return the bot id
     */
    public UUID getBotId() {
        return botId;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore(int score) {
        this.score += score;
    }

    public Weather getRolledWeather() {
        return botState.getRolledWeather();
    }

    public void addAction() {
        botState.addAction();
    }

    public void setRolledWeather(Weather rolledWeather) {
        botState.setRolledWeather(rolledWeather);
    }

    public void reset() {
        this.botState.reset();
    }
}
