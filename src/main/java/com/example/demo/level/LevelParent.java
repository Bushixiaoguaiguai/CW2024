package com.example.demo.level;

import java.util.*;

import com.example.demo.actors.shared.ActiveActorDestructible;
import com.example.demo.actors.friends.UserPlane;
import com.example.demo.display.HeartDisplay;
import com.example.demo.level.manager.InputDetect;
import com.example.demo.level.manager.CollisionDetect;
import com.example.demo.level.manager.UnitManager;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.util.Duration;

/**
 * Abstract base class for all game levels. This class defines the shared behavior and properties for game levels, including
 * initialization of the scene, timeline management, and unit handling.
 */
public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 50;
	private static final int MILLISECOND_DELAY = 16;

	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

	private int currentNumberOfEnemies;
	private final LevelView levelView;

	private final InputDetect inputDetect;
	private final CollisionDetect collisionDetect;
	private final UnitManager unitManager;

	/**
	 * Constructs a new game level with the specified parameters.
	 *
	 * @param backgroundImageName the name of the background image file.
	 * @param screenHeight the height of the game screen.
	 * @param screenWidth the width of the game screen.
	 * @param playerInitialHealth the initial health of the player's plane.
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);

		this.unitManager = new UnitManager(root);

		this.friendlyUnits = unitManager.getFriendlyUnits();
		this.enemyUnits = unitManager.getEnemyUnits();
		this.userProjectiles = unitManager.getUserProjectiles();
		this.enemyProjectiles = unitManager.getEnemyProjectiles();

		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		HeartDisplay heartDisplay = levelView.getHeartDisplay();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);

		inputDetect = new InputDetect(user, root, userProjectiles);
		this.collisionDetect = new CollisionDetect(root, heartDisplay);
	}

	/**
	 * Initializes friendly units for the level.
	 */
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Initializes enemy units for the level.
	 * This method is to be implemented by subclasses.
	 */
	protected void initializeEnemyUnits() {
	}

	/**
	 * Checks if the game is over. This method must be implemented by subclasses.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Spawns enemy units in the level. This method must be implemented by subclasses.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Instantiates the level view for the current level. This method must be implemented by subclasses.
	 *
	 * @return a {@link LevelView} instance for the level.
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Initializes the scene for the level, including background, units, and input handling.
	 *
	 * @return the initialized {@link Scene}.
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		initializeEnemyUnits();
		setupInputHandling();
		levelView.showHeartDisplay();
		return scene;
	}

	/**
	 * Starts the game by focusing on the background and starting the timeline.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * Transitions to the next level.
	 *
	 * @param levelType the type of the next level.
	 */
	public void goToNextLevel(LevelType levelType) {
		setChanged();
		notifyObservers(levelType);
	}

	/**
	 * Updates the scene, including spawning enemies, handling collisions, and updating the level view.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleCollision();
		updateUnits();
		updateHeartDrops();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	/**
	 * Updates all units by removing destroyed units and updating active units.
	 */
	void updateUnits() {
		unitManager.removeAllDestroyedActors();
		unitManager.updateActors();
	}

	/**
	 * Initializes the game loop timeline.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Initializes the background of the scene.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		root.getChildren().add(background);
	}

	/**
	 * Sets up input handling for the player's controls.
	 */
	private void setupInputHandling() {
		background.setOnKeyPressed(inputDetect::handlePressed);
		background.setOnKeyReleased(inputDetect::handleReleased);
	}

	/**
	 * Handles all collisions in the game.
	 */
	private void handleCollision() {
		collisionDetect.handleAllCollisions(friendlyUnits, enemyUnits, userProjectiles, enemyProjectiles, user, screenWidth);
	}

	/**
	 * Updates the level view based on the player's health.
	 */
	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * Generates fire for enemy units.
	 */
	private void generateEnemyFire() {
		unitManager.generateEnemyFire();
	}

	/**
	 * Updates the kill count for the user.
	 */
	private void updateKillCount() {
		unitManager.updateKillCount(user, currentNumberOfEnemies);
	}

	/**
	 * Updates the number of enemies in the level.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	/**
	 * Updates heart drops for the player's health.
	 */
	private void updateHeartDrops() {
		collisionDetect.updateHeartDrops(user);
	}

	/**
	 * Cleans up resources and stops the timeline. Allows subclasses to add custom cleanup logic.
	 */
	public void cleanup() {
		stopTimeline();
		clearResources();
		onCleanup();
	}

	/**
	 * Stops the timeline and clears its keyframes.
	 */
	private void stopTimeline() {
		if (timeline != null) {
			timeline.stop();
			timeline.getKeyFrames().clear();
		}
	}

	/**
	 * Clears resources used in the level.
	 */
	private void clearResources() {
		root.getChildren().clear();
		background.setOnKeyPressed(null);
		background.setOnKeyReleased(null);
		friendlyUnits.clear();
		enemyUnits.clear();
		userProjectiles.clear();
		enemyProjectiles.clear();
	}

	/**
	 * Hook for custom cleanup logic in subclasses.
	 */
	protected void onCleanup() {
	}

	// Getters for level properties and game elements
	public Timeline getTimeline() {
		return timeline;
	}

	public List<ActiveActorDestructible> getFriendlyUnits() {
		return friendlyUnits;
	}

	public List<ActiveActorDestructible> getEnemyUnits() {
		return enemyUnits;
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		unitManager.addEnemyUnit(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	protected void winGame() {
		timeline.stop();
		goToNextLevel(LevelType.WIN);
	}

	protected void loseGame() {
		timeline.stop();
		goToNextLevel(LevelType.GAME_OVER);
	}
}
