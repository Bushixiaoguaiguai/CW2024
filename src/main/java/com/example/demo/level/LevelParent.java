package com.example.demo.level;

import java.util.*;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.FighterPlane;
import com.example.demo.actors.UserPlane;
import com.example.demo.manager.InputDetect;
import com.example.demo.manager.CollisionDetect;
import com.example.demo.manager.UnitManager;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.util.Duration;

public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 50;
	private static final int MILLISECOND_DELAY = 50;
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
	private LevelView levelView;

	private final InputDetect inputDetect;
	private final CollisionDetect collisionDetect;
	private final UnitManager unitManager;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);

		this.unitManager = new UnitManager(root);
		unitManager.getFriendlyUnits().add(user);

		this.friendlyUnits = unitManager.getFriendlyUnits();
		this.enemyUnits = unitManager.getEnemyUnits();
		this.userProjectiles = unitManager.getUserProjectiles();
		this.enemyProjectiles = unitManager.getEnemyProjectiles();

		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);

		inputDetect = new InputDetect(user, root, userProjectiles);
		this.collisionDetect = new CollisionDetect(root);
	}

	protected void initializeFriendlyUnits(){
		getRoot().getChildren().add(getUser());
	};

	protected void initializeEnemyUnits(){
	}

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		initializeEnemyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void goToNextLevel(LevelType levelType) {
		setChanged();
		notifyObservers(levelType);
	}

	private void updateScene() {
		spawnEnemyUnits();
		unitManager.generateEnemyFire();
		updateNumberOfEnemies();
		handleCollision();
		updateUnits();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	private void updateUnits(){
		unitManager.removeAllDestroyedActors();
		unitManager.updateActors();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);

		background.setOnKeyPressed(inputDetect::handlePressed);
		background.setOnKeyReleased(inputDetect::handleReleased);

		root.getChildren().add(background);
	}

	private void handleCollision(){
		collisionDetect.handleEnemyPenetration(enemyUnits, user, screenWidth);
		collisionDetect.handleAllCollisions(friendlyUnits, enemyUnits, userProjectiles, enemyProjectiles, user, screenWidth);
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
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
		if (!enemyUnits.contains(enemy)) {
			enemyUnits.add(enemy);
			root.getChildren().add(enemy);
		}
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

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	public void cleanup() {
		if (timeline != null) {
			timeline.stop();
			timeline.getKeyFrames().clear();
		}

		root.getChildren().clear();

		background.setOnKeyPressed(null);
		background.setOnKeyReleased(null);

		friendlyUnits.clear();
		enemyUnits.clear();
		userProjectiles.clear();
		enemyProjectiles.clear();

		System.out.println("Level cleaned up.");
	}
}
