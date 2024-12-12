# COMP2042 Coursework

## Table of Contents
- [GitHub Repository](#github-repository)
- [Compilation Instructions](#compilation-instructions)
- [Implemented and Working Properly](#implemented-and-working-properly)
- [Implemented but Not Working Properly](#implemented-but-not-working-properly)
- [Features Not Implemented](#features-not-implemented)
- [New Java Classes](#new-java-classes)
- [Modified Java Classes](#modified-java-classes)
- [Unexpected Problems](#unexpected-problems)

## GitHub Repository
- GitHub Link: [Repository Link](https://github.com/Bushixiaoguaiguai/CW2024.git)

## Compilation Instructions

### Prerequisites
Before you begin, ensure you have the following installed:
- **Java Development Kit**: Version 19 or higher. Download it from [Oracle's official website](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html) or choose OpenJDK.
- **Recommended IDEs**: IntelliJ IDEA Version 2023.3 or higher.Download it from [Download IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- **Apache Maven**: Version 4.0.0 or higher. IntelliJ IDEA includes a bundled Maven. However, if you prefer, you can configure it to use a specific Maven version installed on your system. You can download it from [Maven's official site](https://maven.apache.org/download.cgi) and ensure it's added to your PATH.
- **Git**: Installed and configured.

### Project Setup
1. Clone the repository:
    ```bash
    git clone https://github.com/Bushixiaoguaiguai/CW2024.git
    ```
2. Open the cloned repository using IntelliJ IDEA.
3. **JavaFX SDK**: Verify the Project SDK:
- Go to **File → Project Structure → Project Settings → Project SDK**
- Ensure the SDK is set to JDK 19
4. **Maven**: Maven should be automatically set up, if not:
- Navigate to **File > Settings > Build, Execution, Deployment > Build Tools > Maven**
- Ensure the **Maven home directory** is set correctly. If it's blank, select the bundled Maven (**<IDEA_HOME>/plugins/maven/lib/maven3**).

### Running
1. Using terminal:
    ```
    mvn javafx:run
    ```
2. Or running `Main` class file. In the `src` > `main` > `java` > `com.example.demo` package > `Controller`.
3. Or you can use **Maven** tool window:
- Click the Maven icon on the right side of the workshop
- Navigate to **Plugins** > **javafx**
- double click **javafx:run**

## Implemented and Working Properly

### Bug fixed
1. **fix the wrong shield image path** Amend the file extension of the Shield Image from .jpg to .png for correct image loading.

2. **Fix the shiled displaying issue** To make the shield displayed properly in level two, A `ShieldImage` object is created as part of the `Boss` class during initialization. The shield image is loaded and initially hidden. The object is added in the class of `LevelTwo` by `getRoot().getChildren().add(boss.getShieldImage());`. Then the shield will follow the game machenism to display or disapper.

3. **Fix the possible memery leak** When the enemy planes exceed the left boundry of the screen, they will automatically get destroyed to prevent memery leak. However, the `Projectile`s of both `UserPlane`, `EnemyPlane` and `Boss` are not destroyed correctly. So I made a method called `handleProjectilePenetration()` to fix this problem.

### Code refactored
1. **Input detect** Enhanced input handling for UserPlane movement
 - Refactored input handling logic in InputDetect to improve movement controls for UserPlane:
 - Pressing and holding 'Up' moves the plane upward; pressing 'Down' moves it downward.
 - Pressing 'Down' while holding 'Up' (and vice versa) now stops the plane's movement.
 - When both 'Up' and 'Down' are pressed, releasing one key resumes movement in the direction of the other key.
 - Added boolean flags (isUpPressed, isDownPressed) to track key states for better control logic.
 - Updated movement logic to ensure smooth transitions between directions and stops.

2. **Collision detect**Extracted collision detection logic into a dedicated class 
 - Created CollisionDetect class to handle all collision-related logic, including:
 - Detecting collisions between two lists of objects.
 - Checking enemy penetration past the screen boundary.
 - Updated LevelParent to use CollisionDetect for handling collisions, reducing complexity.
 - Removed redundant collision handling methods from LevelParent.
 - Implemented `handleProjectilePenetration` to destroy projectiles that exceed screen boundaries.
 - Ensured compatibility for both user and enemy projectiles.
 - Updated logic to maintain game performance and avoid off-screen entities.

3. **Unit manager** Extracted UnitManager for better code reusability 
 - Created UnitManager class to handle management of all units (friendly, enemy, projectiles) and their interactions with the scene.
 - Moved the following responsibilities from LevelParent to UnitManager:
 - Updating all active units (updateActors).
 - Removing destroyed units from the scene (removeAllDestroyedActors).
 - Managing lists of friendly units, enemy units, user projectiles, and enemy projectiles.
 - Spawning enemy projectiles.
 - Updated LevelParent to delegate unit-related logic to UnitManager, reducing its complexity and improving modularity.
 - Preserved functionality while improving code reusability and maintainability for future levels.

4. **Apply factory design pattern** Simplified and modularized Controller for better level management
 - Introduced LevelFactory to centralize level creation, removing reflection for improved efficiency.
 - Replaced hardcoded level names with LevelType enum for type safety and maintainability.
 - Decoupled level switching logic by delegating observer updates to a unified goToLevel method.
 - Improved exception handling for cleaner error management during level transitions.

5. **Modify LevelParent** Streamlined LevelParent for enhanced modularity and reusability
 - Moved collision handling orchestration to CollisionDetect for better separation of concerns.
 - Centralized unit-related logic into UnitManager, reducing redundancy and improving maintainability.
 - Modularized initialization methods for friendly and enemy units with reusable utility logic.
 - Simplified background setup by separating visual and input handling into distinct methods.
 - Enhanced cleanup process with a protected onCleanup hook for custom level-specific cleanup logic.

### Features Added
1. **Explosion effects** Implemented explosion effects for collisions 
 - Added Explosion class to create and manage explosion animations using sprite sheets.
 - Integrated explosion triggering in CollisionDetect for specific collision scenarios:
 - User projectiles colliding with enemy units.
 - Friendly units colliding with enemy units.
 - Implemented lifecycle management for explosions:
 - Active explosions are tracked and cleaned up automatically after animation ends.
 - Added isFinished() and stop() methods for efficient resource management. 
 - Ensured explosion effects are displayed at the correct position of destroyed actors.

2. **HP bar display**Implemented HpBarDisplay to show Boss health dynamically 
 - Created HpBarDisplay class to visually represent the Boss's health bar.
 - Added a red bar to indicate current health and a gray background to represent the maximum health.
 - Implemented dynamic binding to Boss's health property, ensuring the red bar shrinks proportionally as health decreases.
 - Ensured the health bar is reusable and can be easily positioned within various layout containers.

3. **Screens** Added MainMenu, WinScreen, and GameOver screens for enhanced user experience
 - Implemented `MainMenu` with "Start Game" and "Quit" buttons.
    - Start Game transitions to Level One.
    - Quit button includes confirmation dialog to prevent accidental exits.
 - Created `WinScreen` to display a victory image.
    - Includes "Retry" to restart Level One.
    - Includes "Main Menu" to return to the main menu.
 - Created `GameOverScreen` to display a game-over image.
    - Includes "Retry" to restart Level One.
    - Includes "Main Menu" to return to the main menu.
 - Updated `Controller` to manage transitions between the new screens and levels.
    - Integrated level transitions using `LevelType`.
 - Enhanced overall game flow with modular screen transitions.

4. **Level infinity** Add LevelInfinity and DestroyPlanesCounter features 
 - Implemented `LevelInfinity`, a new game level with infinite spawning of enemies.
    - Added a counter to track and display the number of planes destroyed by the player.
    - Integrated the counter into the top-right corner of the game screen.
 - Created the `DestroyPlanesCounter` class to bind the counter display to `UserPlane`'s `numberOfKills` property.
    - Ensured the counter updates dynamically as the player destroys enemies.

5. **Music and sound effect** Add Sound effect and Background Music Support 
 - Implemented `SoundEffectManager` to handle sound effects for events like user plane shooting and explosions.
 - Added `MusicPlayer` class for playing background music during gameplay.
 - Integrated `SoundEffectManager` with `UserPlane` to play shooting sound.
 - Configured `LevelParent` to initialize and manage sound managers.
 - Ensured proper initialization of sound systems and verified audio playback functionality.

6. **Apply singleton design pattern** Refactor SoundEffectManager and BackgroundManager to Singleton Pattern
 - Refactored `SoundEffectManager` to implement Singleton design pattern.
    - Ensures a single instance is used throughout the application.
    - Added thread-safe lazy initialization for creating the singleton instance.
    - Adjusted related code to use `SoundEffectManager.getInstance()`.
 - Refactored BackgroundManager to implement Singleton design pattern.
    - Centralized background image management across all levels.
    - Enabled consistent reuse and resource optimization.
    - Adjusted background initialization to use BackgroundManager.getInstance().
 - Updated references in level initialization to adapt to the singleton changes.
 - Tested for compatibility with existing game level transitions and effects.

7. **Mute or unmute game music and effect**  Add mute/unmute buttons and decorate MainMenu 
 - Added mute/unmute buttons to toggle background music, shoot sound, and explosion sound individually.
    - Buttons dynamically update labels to reflect mute state (e.g., "Mute BGM" or "Unmute BGM").
    - Integrated volume controls with BackGroundMusicManager and SoundEffectManager.
 - Enhanced MainMenu visuals:
    - Added background image for a more immersive look.
    - Improved button styling with padding and font size.
 - Organized UI layout:
    - Grouped mute buttons in an HBox for better alignment.
    - Placed all buttons in a vertically centered VBox for a clean and consistent layout.
 - Maintained functionality for existing start game, infinity mode, and quit buttons.

8. **Raise game frame rate** Adjust game to 60 FPS and balance speeds 
 - Changed game frame rate from 20 FPS to 60 FPS by modifying MILLISECOND_DELAY in LevelParent.java.
 - Adjusted player and enemy movement speeds to maintain balance at higher frame rate
 - Reduced projectile speeds to align with new frame rate
 - Ensured consistent gameplay experience at the new frame rate.

9. **Heart drop** Add HeartDrop feature and integrate with game mechanics 
 - Implemented `HeartDrop` class:
    - Displays a heart image at a specific position.
    - Moves at a constant speed and interacts with the UserPlane.
 - Added `HeartDrop` generation:
    - EnemyPlane destruction triggers a chance to spawn a HeartDrop at its position.
    - Configurable spawn probability (default 20%).
 - Integrated `HeartDrop` logic in LevelInfinity:
    - Added management for HeartDrops, including movement and collision detection.
    - Updated HeartDrops are removed if collected by the player or leave the screen.
 - Updated `UserPlane` and `HeartDisplay`:
    - UserPlane now notifies observers of health changes using the Observer pattern.
    - HeartDisplay dynamically updates to reflect health changes (hearts added or removed).
 - Added logging for debugging:
    - Logs HeartDrop generation position and collection events.


## Implemented but Not Working Properly
1. **Background of main menu**
 - It is not displayed correctly.

## Features Not Implemented
1. **Weapon power-up**
 - Similar to heart drop machanism, when user shoot down an enemy plane, there is a chance that a energy ball drops and move leftwards. If the user plane intersects with the energy ball, the user get the projectile upgrades. The upgrades will finally disapper with the time goes.

 **Reasons not implemented:**
 - The intersects machenism is the same with heart drops.
 - Mechanisms that disappear over time is the same with boss shield disappers.
 - The form of upgrade is hard to choose.

2. **Pause Menu**
 - The user can pause the game whenever he/she wants.

## New Java Classes

### 1. Class: `UnitManager`
- **Location**: `com.example.demo.level.manager.UnitManager`
- **Purpose**: The `UnitManager` class is designed to manage all units in a JavaFX-based game. This includes friendly units, enemy units, and projectiles. Its key responsibilities include updating the state of actors, handling unit destruction, generating enemy attacks, and tracking the player's kill count.

#### Key Methods
- **Constructor**:
  - `UnitManager(Group root)`:
    Initializes the `UnitManager` with a root node for JavaFX, setting up the data structures to track various units in the game.

- **Actor Management**:
  - `updateActors()`:
    Updates the state of all actors in the game, ensuring they perform their defined behaviors.
  
  - `addEnemyUnit(ActiveActorDestructible enemy)`:
    Adds an enemy unit to the scene and the internal list of enemies.

  - `removeAllDestroyedActors()`:
    Removes destroyed actors from the game, including friendly units, enemies, and projectiles.

  - `removeDestroyedActors(List<ActiveActorDestructible> actors)` *(private)*:
    Helper method to identify and remove destroyed actors from a given list.

- **Projectile Management**:
  - `generateEnemyFire()`:
    Triggers enemy units to fire projectiles and manages their addition to the game scene.

  - `spawnEnemyProjectile(ActiveActorDestructible projectile)` *(private)*:
    Adds a specific enemy projectile to the game scene.

- **Player Management**:
  - `updateKillCount(UserPlane user, int currentNumberOfEnemies)`:
    Updates the kill count of the user's plane based on the number of eliminated enemies.

- **Accessors**:
  - `getFriendlyUnits()`:
    Returns a list of friendly units.
  
  - `getEnemyUnits()`:
    Returns a list of enemy units.
  
  - `getEnemyProjectiles()`:
    Returns a list of enemy projectiles.
  
  - `getUserProjectiles()`:
    Returns a list of projectiles fired by the user.

#### Key Attributes
- `Group root`: Represents the JavaFX scene graph's root node.
- `List<ActiveActorDestructible> friendlyUnits`: Stores all friendly units in the game.
- `List<ActiveActorDestructible> enemyUnits`: Tracks all enemy units.
- `List<ActiveActorDestructible> userProjectiles`: Stores projectiles fired by the user.
- `List<ActiveActorDestructible> enemyProjectiles`: Stores projectiles fired by enemies.


### 2. Class: `InputDetect`
- **Location**: `com.example.demo.level.manager.InputDetect`
- **Purpose**: The `InputDetect` class manages user input to control the player's plane and handle actions such as movement and firing projectiles.

#### Key Methods
- **Constructor**:
  - `InputDetect(UserPlane user, Group root, List<ActiveActorDestructible> userProjectiles)`:
    Initializes the `InputDetect` object with the user's plane, the JavaFX scene root, and a list of projectiles fired by the user.

- **Input Handling**:
  - `handlePressed(KeyEvent e)`:
    Handles key press events to control the user's plane or initiate specific actions.  
    - **Supported Keys**:
      - `UP`: Moves the plane upward.
      - `DOWN`: Moves the plane downward.
      - `LEFT`: Moves the plane leftward.
      - `RIGHT`: Moves the plane rightward.
      - `SPACE`: Fires a projectile if the space key is pressed.

  - `handleReleased(KeyEvent e)`:
    Handles key release events to stop the user's plane or reset actions.  
    - **Supported Keys**:
      - `UP` or `DOWN`: Stops vertical movement.
      - `LEFT` or `RIGHT`: Stops horizontal movement.
      - `SPACE`: Resets the projectile firing state.

- **Projectile Management**:
  - `fireProjectile()` *(private)*:
    Fires a projectile from the user's plane, adds it to the scene graph, and tracks it in the list of user projectiles.

- **State Check**:
  - `isSpacePressed()`:
    Returns whether the space key is currently pressed.

#### Key Attributes
- `UserPlane user`: Represents the player's plane and handles movement or projectile firing.
- `Group root`: The JavaFX scene graph's root node.
- `List<ActiveActorDestructible> userProjectiles`: Tracks projectiles fired by the user.
- `boolean isSpacePressed`: Tracks the state of the space key to prevent rapid firing.

### 3. Class: `CollisionDetect`
- **Location**: `com.example.demo.level.manager.CollisionDetect`
- **Purpose**: The `CollisionDetect` class provides methods for handling collision detection, processing game logic for collisions, explosions, and boundary violations, as well as updating heart drops.

#### Key Methods
- **Constructor**:
  - `CollisionDetect(Group root, HeartDisplay heartDisplay)`:
    Initializes the `CollisionDetect` object with the JavaFX scene root, a list to track active explosions, and the heart display for the player's health.

- **Collision Handling**:
  - `handleAllCollisions(...)`:
    Handles all types of collisions in the game, including projectiles, friendly units, and enemy units. Also checks for boundary violations and updates explosions.

  - `handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2)` *(private)*:
    Processes collisions between two lists of actors, applying damage to both upon collision.

  - `handleCollisionsWithExplosion(...)` *(private)*:
    Handles collisions that trigger explosions, applying damage to actors and adding explosions and potential heart drops to the scene.

- **Explosion Management**:
  - `updateExplosions()`:
    Removes finished explosions from the active list and ensures they are no longer rendered.

- **Boundary Handling**:
  - `handleEnemyPenetration(...)`:
    Detects enemies passing the screen boundary and processes their impact on the player's health and game state.

  - `handleProjectilePenetration(...)`:
    Removes projectiles that have exceeded the screen boundaries.

- **Heart Drops**:
  - `updateHeartDrops(UserPlane userPlane)`:
    Updates heart drop positions, handles their interaction with the user's plane, and removes off-screen heart drops.

#### Key Attributes
- `Group root`: Represents the JavaFX scene graph's root node.
- `List<Explosion> activeExplosions`: Tracks active explosions in the game.
- `List<HeartDrop> heartDrops`: Manages heart drops spawned during the game.
- `HeartDisplay heartDisplay`: Displays and updates the player's health status.
- `SoundEffectManager soundEffectManager`: Plays sound effects, including explosion sounds.
- `static final double HEART_DROP_PROBABILITY`: Probability of spawning a heart drop after a collision.

### 4. Class: `SoundEffectManager`
- **Location**: `com.example.demo.level.manager.SoundEffectManager`
- **Purpose**: The `SoundEffectManager` class is responsible for managing sound effects in the game, such as explosion and shooting sounds. It follows a singleton pattern to ensure that only one instance handles all sound effects.

#### Key Methods
- **Singleton Instance**:
  - `getInstance()`:
    Returns the singleton instance of `SoundEffectManager`. Initializes the instance if it has not been created.

- **Sound Playback**:
  - `playExplosionSound()`:
    Plays the explosion sound effect.

  - `playShootSound()`:
    Plays the shooting sound effect.

- **Volume Control**:
  - `setExplosionVolume(double volume)`:
    Sets the volume for the explosion sound effect. Volume ranges from 0.0 (mute) to 1.0 (maximum volume).

  - `setShootVolume(double volume)`:
    Sets the volume for the shooting sound effect. Volume ranges from 0.0 (mute) to 1.0 (maximum volume).

  - `getExplosionVolume()`:
    Returns the current volume of the explosion sound effect.

  - `getShootVolume()`:
    Returns the current volume of the shooting sound effect.

#### Key Attributes
- `static SoundEffectManager instance`: The singleton instance of the `SoundEffectManager`.
- `SoundEffectPlayer explosionSound`: Handles the playback of the explosion sound effect.
- `SoundEffectPlayer shootSound`: Handles the playback of the shooting sound effect.
- `double explosionVolume`: Stores the current volume level for the explosion sound.
- `double shootVolume`: Stores the current volume level for the shooting sound.

### 5. Class: `BackgroundMusicManager`
- **Location**: `com.example.demo.level.manager.BackgroundMusicManager`
- **Purpose**: The `BackgroundMusicManager` class manages background music playback for the game. It provides methods to play, stop, and adjust the volume of the background music, ensuring continuous looping for immersive gameplay. The class follows the Singleton design pattern to guarantee a single instance controls the music.

#### Key Methods
- **Singleton Instance**:
  - `getInstance()`:
    Returns the singleton instance of the `BackgroundMusicManager`. Initializes the instance if it has not been created.

- **Music Playback**:
  - `play()`:
    Starts playing the background music. The music loops indefinitely if it has been initialized correctly.

  - `stop()`:
    Stops the background music.

- **Volume Control**:
  - `setVolume(double volume)`:
    Sets the volume for the background music. Volume ranges from 0.0 (mute) to 1.0 (maximum volume).

  - `getVolume()`:
    Retrieves the current volume level of the background music.

#### Key Attributes
- `static BackgroundMusicManager instance`: The singleton instance of the `BackgroundMusicManager`.
- `MediaPlayer mediaPlayer`: Handles the playback of the background music. It loops indefinitely if properly initialized.

### 6. Class: `GameOverScreen`
- **Location**: `com.example.demo.level.screens.GameOverScreen`
- **Purpose**: The `GameOverScreen` class provides a graphical interface to display the "Game Over" message, along with options to retry the game or return to the main menu.

#### Key Methods
- **Constructor**:
  - `GameOverScreen(double screenWidth, double screenHeight, Runnable retryCallback, Runnable mainMenuCallback)`:
    Initializes the screen with a "Game Over" image and buttons for retrying the game or navigating back to the main menu.

- **Utility**:
  - `getScene()`:
    Returns the `Scene` object for the game over screen.

- **Private Helper**:
  - `createButton(String text, Runnable action)`:
    Creates a styled button with a specified text and action.

#### Key Attributes
- `Scene scene`: Represents the JavaFX scene for the game over screen.

### 7. Class: `MainMenu`
- **Location**: `com.example.demo.level.screens.MainMenu`
- **Purpose**: The `MainMenu` class provides the main menu for the game, including options to start the game, access infinity mode, quit, and control sound settings.

#### Key Methods
- **Constructor**:
  - `MainMenu(double screenWidth, double screenHeight, Runnable startGameCallback, Runnable infinityModeCallback, Runnable quitCallback)`:
    Sets up the main menu with buttons for gameplay modes, quit functionality, and sound controls.

- **Utility**:
  - `getScene()`:
    Returns the `Scene` object for the main menu.

- **Private Helpers**:
  - `createButton(String text, Runnable action)`:
    Creates a styled button with a specified text and action.

  - `createMuteButton(String text, boolean[] isMuted, Runnable setVolumeAction)`:
    Creates a mute/unmute button for sound effects, with toggle functionality.

#### Key Attributes
- `Scene scene`: Represents the JavaFX scene for the main menu.

### 8. Class: `WinScreen`
- **Location**: `com.example.demo.level.screens.WinScreen`
- **Purpose**: The `WinScreen` class provides a graphical interface to display the "Victory" message, along with options to try infinity mode or return to the main menu.

#### Key Methods
- **Constructor**:
  - `WinScreen(double screenWidth, double screenHeight, Runnable infinityModeCallback, Runnable mainMenuCallback)`:
    Initializes the screen with a victory image and buttons for trying infinity mode or navigating back to the main menu.

- **Utility**:
  - `getScene()`:
    Returns the `Scene` object for the win screen.

- **Private Helper**:
  - `createButton(String text, Runnable action)`:
    Creates a styled button with a specified text and action.

#### Key Attributes
- `Scene scene`: Represents the JavaFX scene for the win screen.

### 9. Class: `LevelFactory`
- **Location**: `com.example.demo.level.LevelFactory`
- **Purpose**: The `LevelFactory` class is a factory for dynamically creating game levels based on their `LevelType`. It allows for centralized registration and instantiation of different game levels.

#### Key Methods
- **Constructor**:
  - `LevelFactory(double screenWidth, double screenHeight)`:
    Initializes the factory and registers predefined levels such as `LEVEL_ONE`, `LEVEL_TWO`, and `LEVEL_INFINITY`.

- **Level Registration**:
  - `registerLevel(LevelType levelType, Supplier<LevelParent> levelSupplier)` *(private)*:
    Registers a new level type along with its corresponding supplier for instantiation.

- **Level Creation**:
  - `createLevel(LevelType levelType)`:
    Creates and returns a level instance based on the specified `LevelType`. Throws an exception if the level type is not supported.

#### Key Attributes
- `Map<LevelType, Supplier<LevelParent>> levelMap`: Maps level types to their respective suppliers for creation.

### 10. Class: `LevelInfinity`
- **Location**: `com.example.demo.level.LevelInfinity`
- **Purpose**: Represents the infinite mode of the game where enemies spawn continuously until the player's plane is destroyed.

#### Key Methods
- **Constructor**:
  - `LevelInfinity(double screenHeight, double screenWidth)`:
    Initializes the infinity level with the specified screen dimensions and pre-configured settings.

- **Game Logic**:
  - `checkIfGameOver()`:
    Checks whether the game is over by determining if the player's plane has been destroyed.

  - `spawnEnemyUnits()`:
    Spawns enemy planes dynamically based on spawn probability and the number of existing enemies.

  - `initializeFriendlyUnits()`:
    Sets up the player's plane and initializes the destroy planes counter.

  - `onCleanup()`:
    Cleans up resources and level-specific elements during level transitions.

- **View Management**:
  - `instantiateLevelView()`:
    Creates and returns a `LevelView` object for the level.

#### Key Attributes
- `String BACKGROUND_IMAGE_NAME`: Path to the background image used in the level.
- `int PLAYER_INITIAL_HEALTH`: Initial health of the player's plane.
- `int TOTAL_ENEMIES`: Total number of enemies allowed at any time.
- `double ENEMY_SPAWN_PROBABILITY`: Probability for spawning an enemy.
- `DestroyPlanesCounter destroyPlanesCounter`: Counter for tracking destroyed planes.

### 11. Enum: `LevelType`
- **Location**: `com.example.demo.level.LevelType`
- **Purpose**: Defines the various types of levels and game states in the application.

#### Enum Values
- `LEVEL_ONE`: Represents the first level of the game.
- `LEVEL_TWO`: Represents the second level of the game.
- `LEVEL_INFINITY`: Represents an endless level mode.
- `MAIN_MENU`: Represents the main menu of the game.
- `WIN`: Represents the game state when the player wins.
- `GAME_OVER`: Represents the game state when the player loses.

### 12. Class: `HpBarDisplay`
- **Location**: `com.example.demo.display.HpBarDisplay`
- **Purpose**: The `HpBarDisplay` class provides a visual health bar for a boss character. The bar dynamically adjusts its width based on the boss's current health.

#### Key Methods
- **Constructor**:
  - `HpBarDisplay(Boss boss)`:
    Initializes the health bar and binds its width to the boss's current health.

- **Utility**:
  - `bindHpBar(Boss boss)` *(private)*:
    Dynamically updates the health bar width in proportion to the boss's health.

  - `getBarWidth()`:
    Returns the fixed width of the health bar.

#### Key Attributes
- `Rectangle background`: Represents the background of the health bar.
- `Rectangle hpBar`: Represents the visible portion of the health bar that reflects the boss's current health.
- `static final int BAR_WIDTH`: The fixed width of the health bar.
- `static final int BAR_HEIGHT`: The fixed height of the health bar.

### 13. Class: `SoundEffectPlayer`
- **Location**: `com.example.demo.display.SoundEffectPlayer`
- **Purpose**: Plays sound effects in the game using audio files, with options to control playback and volume.

#### Key Methods
- **Constructor**:
  - `SoundEffectPlayer(String soundFilePath)`:
    Loads the specified sound file for playback.

- **Sound Management**:
  - `play()`:
    Plays the sound effect.

  - `setVolume(double volume)`:
    Adjusts the volume of the sound effect. Values range from 0.0 (mute) to 1.0 (maximum volume).

#### Key Attributes
- `AudioClip audioClip`: Represents the audio file loaded for playback.

### 14. Class: `DestroyPlanesCounter`
- **Location**: `com.example.demo.display.DestroyPlanesCounter`
- **Purpose**: Displays the number of planes destroyed by the user, updating dynamically as the user's kill count changes.

#### Key Methods
- **Constructor**:
  - `DestroyPlanesCounter(UserPlane userPlane)`:
    Initializes the counter and binds it to the `UserPlane`'s kill count property.

#### Key Attributes
- `Label counterLabel`: Displays the number of planes destroyed.

### 15. Class: `Explosion`
- **Location**: `com.example.demo.display.Explosion`
- **Purpose**: Represents an animated explosion using a sprite sheet, with functionality to start, stop, and clean up the animation.

#### Key Methods
- **Constructor**:
  - `Explosion(String texturePath, double x, double y, double width, double height, double totalAnimationTime, Parent root)`:
    Initializes the explosion animation with the specified sprite sheet, dimensions, and root node.

- **Animation Management**:
  - `start()`:
    Starts the explosion animation and adds it to the scene.

  - `stop()`:
    Stops the animation and cleans up the explosion.

  - `isFinished()`:
    Checks whether the animation has completed.

- **Cleanup**:
  - `cleanup()` *(private)*:
    Removes the explosion from the scene and marks it as finished.

#### Key Attributes
- `ImageView explosionImageView`: Displays the explosion animation.
- `Timeline explosionTimeline`: Handles the frame-by-frame animation of the explosion.
- `Parent root`: The root node of the scene where the explosion is added.
- `boolean isFinished`: Tracks whether the animation has completed.
- `boolean isPlaying`: Tracks whether the animation is currently playing.

### 16. Class: `HeartDrop`
- **Location**: `com.example.demo.actors.friends.HeartDrop`
- **Purpose**: The `HeartDrop` class represents a collectible heart in the game that increases the player's health. It extends the `ActiveActor` class and defines its movement and interactions.

#### Key Methods
- **Constructor**:
  - `HeartDrop(double initialX, double initialY)`:
    Initializes the heart drop with the specified position, sets its image, size, and initial vertical movement speed.

- **Movement**:
  - `updatePosition()`:
    Updates the heart drop's position by moving it horizontally and vertically. It bounces vertically between the top and bottom screen bounds (0 and 720).

#### Key Attributes
- `static final String HEART_IMAGE_PATH`: Path to the heart image.
- `static final int HEART_SIZE`: Size of the heart in pixels.
- `static final double MOVE_SPEED`: Horizontal movement speed of the heart.
- `double verticalSpeed`: Speed of vertical movement, which reverses direction upon reaching screen bounds.

## Modified Java Classes

### 1. Class: `Main`
**Changes**:
1. Replaced explicit dimensions (SCREEN_WIDTH and SCREEN_HEIGHT) setup with direct initialization in the controller call:
   - **Original**: `stage.setHeight(SCREEN_HEIGHT);` and `stage.setWidth(SCREEN_WIDTH);`
   - **Modified**: `controller.goToLevel(LevelType.MAIN_MENU);`

2. Removed `myController.launchGame()` and replaced with `controller.goToLevel(LevelType.MAIN_MENU)`:
   - **Original**: 
     ```java
     myController = new Controller(stage);
     myController.launchGame();
     ```
   - **Modified**:
     ```java
     Controller controller = new Controller(stage);
     controller.goToLevel(LevelType.MAIN_MENU);
     ```

**Reason**:
- Simplified the logic by directly transitioning to the main menu using `goToLevel`.
- Removed unused constants for cleaner code structure and to align with the new dynamic controller handling.

### 2. Class: `Controller`
**Changes**:
1. **Replaced Reflection-Based Level Loading**:
   - **Original**:
     ```java
     private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
         InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
         Class<?> myClass = Class.forName(className);
         Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
         LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
         myLevel.addObserver(this);
         Scene scene = myLevel.initializeScene();
         stage.setScene(scene);
         myLevel.startGame();
     }
     ```
   - **Modified**:
     ```java
     public void goToLevel(LevelType levelType) {
         try {
             if (currentLevel != null) {
                 currentLevel.cleanup();
             }
             switch (levelType) {
                 case MAIN_MENU -> showMainMenu();
                 case WIN -> showWinScreen();
                 case GAME_OVER -> showGameOverScreen();
                 default -> {
                     currentLevel = levelFactory.createLevel(levelType);
                     currentLevel.addObserver((observable, arg) -> {
                         if (arg instanceof LevelType nextLevelType) {
                             handleLevelTransition(nextLevelType);
                         } else {
                             handleException(new IllegalArgumentException("Invalid level transition argument: " + arg));
                         }
                     });
                     stage.setScene(currentLevel.initializeScene());
                     currentLevel.startGame();
                 }
             }
         } catch (Exception e) {
             handleException(e);
         }
     }
     ```

2. **Added Main Menu, Win Screen, and Game Over Screen Handling**:
   - **Modified**:
     - Added `showMainMenu()`, `showWinScreen()`, and `showGameOverScreen()` methods to manage transitions to UI screens.

3. **Background Music Management**:
   - **Modified**:
     - Integrated `BackgroundMusicManager` initialization and playback in the `Controller` constructor:
       ```java
       backGroundMusicManager = BackgroundMusicManager.getInstance();
       backGroundMusicManager.setVolume(0.5);
       backGroundMusicManager.play();
       ```

4. **Exception Handling**:
   - Enhanced error reporting with the `handleException()` method to provide user-friendly alerts and log errors.

**Reason**:
- The reflection-based level loading was replaced with a cleaner, more type-safe approach using a `LevelFactory` and `LevelType` enumeration.
- Added UI management for the main menu, win screen, and game over screen for a more interactive and structured game flow.
- Introduced background music management to enhance the gaming experience.
- Improved exception handling for better debugging and error recovery.

### 3. Class: `LevelView`
**Changes**:
1. **Removed `WinImage` and `GameOverImage`**:
   - **Original**:
     ```java
     private final WinImage winImage;
     private final GameOverImage gameOverImage;
     ```
   - **Modified**:
     These fields and their related methods (`showWinImage` and `showGameOverImage`) were removed.

2. **Simplified Constructor**:
   - **Original**:
     ```java
     public LevelView(Group root, int heartsToDisplay) {
         this.root = root;
         this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
         this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
         this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION);
     }
     ```
   - **Modified**:
     ```java
     public LevelView(Group root, int heartsToDisplay) {
         this.root = root;
         this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
     }
     ```

3. **Removed Methods for Displaying Win and Game Over Images**:
   - **Original**:
     ```java
     public void showWinImage() {
         root.getChildren().add(winImage);
         winImage.showWinImage();
     }

     public void showGameOverImage() {
         root.getChildren().add(gameOverImage);
     }
     ```
   - **Modified**:
     These methods were removed.

**Reason**:
- Transitioned the responsibility for managing win and game-over screens to a higher-level component, likely the `Controller`.
- Simplified the `LevelView` class to focus solely on managing the player's health display, aligning with single-responsibility principles.

### 4. Class: `LevelViewLevelTwo`
**Changes**:
1. **Removed Shield Show/Hide Methods**:
   - **Original**:
     ```java
     public void showShield() {
         shieldImage.showShield();
     }

     public void hideShield() {
         shieldImage.hideShield();
     }
     ```
   - **Modified**:
     These methods were removed.

**Reason**:
- The shield's visibility management was delegated to `Boss` and simplified for this level, aligning with specific level requirements and reducing redundancy.

### 5. Class: `LevelParent`
**Changes**:
1. **Integrated Managers (`UnitManager`, `InputDetect`, `CollisionDetect`)**:
   - **Original**:
     The original implementation directly managed friendly units, enemy units, projectiles, and collisions within `LevelParent`.
   - **Modified**:
     Delegated these responsibilities to `UnitManager`, `InputDetect`, and `CollisionDetect`:
     ```java
     private final InputDetect inputDetect;
     private final CollisionDetect collisionDetect;
     private final UnitManager unitManager;
     ```

2. **Updated Enemy and Projectile Management**:
   - **Original**:
     Methods like `removeAllDestroyedActors`, `handleCollisions`, and `generateEnemyFire` directly manipulated collections of game objects.
   - **Modified**:
     These functionalities are now handled by `UnitManager`:
     ```java
     unitManager.removeAllDestroyedActors();
     unitManager.generateEnemyFire();
     collisionDetect.handleAllCollisions(friendlyUnits, enemyUnits, userProjectiles, enemyProjectiles, user, screenWidth);
     ```

3. **Enhanced Input Handling**:
   - **Original**:
     Background key press and release handlers were managed manually:
     ```java
     background.setOnKeyPressed(new EventHandler<KeyEvent>() { ... });
     background.setOnKeyReleased(new EventHandler<KeyEvent>() { ... });
     ```
   - **Modified**:
     Input handling is now managed by `InputDetect`:
     ```java
     setupInputHandling();
     ```

4. **Introduced Cleanup Logic**:
   - **Modified**:
     Added a `cleanup` method for releasing resources and clearing the scene:
     ```java
     public void cleanup() {
         stopTimeline();
         clearResources();
         onCleanup();
     }
     ```

5. **Improved Heart Display Management**:
   - Integrated `HeartDisplay` with `CollisionDetect` for dynamic updates:
     ```java
     HeartDisplay heartDisplay = levelView.getHeartDisplay();
     this.collisionDetect = new CollisionDetect(root, heartDisplay);
     ```

**Reason**:
- The introduction of dedicated managers (`UnitManager`, `InputDetect`, `CollisionDetect`) simplifies the `LevelParent` class, aligning with the single-responsibility principle.
- Enhanced modularity allows for easier maintenance and testing.
- Added cleanup logic ensures proper resource management, preventing memory leaks during level transitions.

### 6. Class: `LevelOne`
**Changes**:
1. **Refactored Next Level Transition**:
   - **Original**:
     ```java
     private static final String NEXT_LEVEL = "com.example.demo.LevelTwo";
     ```
   - **Modified**:
     ```java
     goToNextLevel(LevelType.LEVEL_TWO);
     ```

2. **Removed Hardcoded Class Reference**:
   - **Original**:
     ```java
     goToNextLevel(NEXT_LEVEL);
     ```
   - **Modified**:
     ```java
     goToNextLevel(LevelType.LEVEL_TWO);
     ```

**Reason**:
- Improved maintainability by using `LevelType` enumeration instead of hardcoding class references as strings. This ensures type safety and aligns with the updated level transition system.

### 7. Class: `LevelTwo`
**Changes**:
1. **Introduced Health Bar Display**:
   - **Original**:
     ```java
     private final Boss boss;
     private LevelViewLevelTwo levelView;
     ```
   - **Modified**:
     ```java
     private final Boss boss;
     private HpBarDisplay hpBarDisplay;
     ```

2. **Enhanced `initializeEnemyUnits`**:
   - **Original**:
     ```java
     protected void initializeFriendlyUnits() {
         getRoot().getChildren().add(getUser());
     }
     ```
   - **Modified**:
     ```java
     protected void initializeEnemyUnits() {
         getRoot().getChildren().add(boss.getShieldImage());
         hpBarDisplay = new HpBarDisplay(boss);
         hpBarDisplay.setLayoutX((getScreenWidth() - HpBarDisplay.getBarWidth()) / 2);
         hpBarDisplay.setLayoutY(0);
         getRoot().getChildren().add(hpBarDisplay);
     }
     ```

3. **Added Cleanup for Boss-Specific Elements**:
   - **Modified**:
     Added the `onCleanup` method:
     ```java
     @Override
     protected void onCleanup() {
         getRoot().getChildren().remove(boss.getShieldImage());
         getRoot().getChildren().remove(hpBarDisplay);
         System.out.println("LevelTwo-specific cleanup complete.");
     }
     ```

**Reason**:
- Introduced a health bar (`HpBarDisplay`) to visually represent the boss's health, improving gameplay clarity.
- Extended `initializeEnemyUnits` to add boss-specific visuals (shield image and health bar) for an immersive experience.
- Added cleanup logic to ensure resources associated with the boss are properly removed during transitions.

### 8. Class: `HeartDisplay`
**Changes**:
1. **Added `addHeart` Method**:
   - **Original**:
     No method to add hearts back to the display.
   - **Modified**:
     ```java
     public void addHeart() {
         ImageView heart = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm()));
         heart.setFitHeight(HEART_HEIGHT);
         heart.setPreserveRatio(true);
         container.getChildren().add(heart);
     }
     ```

2. **Improved Resource Handling**:
   - Used `Objects.requireNonNull` to ensure that the heart image resource is properly validated:
     ```java
     new Image(Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm());
     ```

**Reason**:
- The `addHeart` method was introduced to allow dynamic updates to the player's health display, enabling health restoration during gameplay.
- Improved resource validation ensures stability and avoids null pointer exceptions when loading the heart image.

### 9&10. Class: `WinImage` `GameOverImage`
**Changes**:
1. **Removed Visibility Control**:
   - **Original**:
     Included methods to control visibility, such as `showWinImage()`:
     ```java
     public void showWinImage() {
         this.setVisible(true);
     }
     ```
     ```java
     public void showGameOverImage() {
         this.setVisible(true);
     }
     ```
   - **Modified**:
     Visibility control (`setVisible`) was removed.

2. **Adjusted Image Dimensions**:
   - **Original**:
     ```java
     private static final int HEIGHT = 500;
     private static final int WIDTH = 600;
     ```
   - **Modified**:
     ```java
     private static final int HEIGHT = 250;
     private static final int WIDTH = 300;
     ```

3. **Improved Resource Validation**:
   - Used `Objects.requireNonNull` to ensure the image resource is properly validated:
     ```java
     new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm());
     ```

**Reason**:
- Visibility control was removed, delegate this responsibility to the `WinScreen` and `GameOverScreen`.
- Adjusted dimensions to better fit updated UI design.
- Improved resource validation to avoid potential null pointer exceptions when loading the image.

### 11. Class: `ShieldImage`
**Changes**:
1. **Updated Image Resource Path**:
   - **Original**:
     ```java
     this.setImage(new Image(getClass().getResource("/com/example/demo/images/shield.jpg").toExternalForm()));
     ```
   - **Modified**:
     ```java
     this.setImage(new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/shield.png")).toExternalForm()));
     ```

2. **Improved Resource Validation**:
   - Used `Objects.requireNonNull` to validate the shield image resource:
     ```java
     new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/images/shield.png")).toExternalForm());
     ```

**Reason**:
- Updated the shield image to use the correct `.png` file instead of `.jpg`.
- Improved resource validation to ensure the image is properly loaded and avoid potential null pointer exceptions.

## Unexpected Problems
