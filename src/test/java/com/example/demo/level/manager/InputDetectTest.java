package com.example.demo.level.manager;

import com.example.demo.JavaFXTestBase;
import com.example.demo.actors.friends.UserPlane;
import com.example.demo.actors.shared.ActiveActorDestructible;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InputDetectTest extends JavaFXTestBase {

    private UserPlane userPlane;
    private Group root;
    private List<ActiveActorDestructible> userProjectiles;
    private InputDetect inputDetect;

    @BeforeEach
    public void setUp() {
        // Initialize dependencies
        userPlane = new UserPlane(5);
        root = new Group();
        userProjectiles = new ArrayList<>();

        // Initialize InputDetect
        inputDetect = new InputDetect(userPlane, root, userProjectiles);
    }

    @Test
    public void testHandlePressed_MovementKeys() {
        // Simulate pressing UP key
        KeyEvent upPress = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false);
        inputDetect.handlePressed(upPress);
        assertEquals(-1, userPlane.getVerticalVelocityMultiplier(), "UP key should set verticalVelocityMultiplier to -1");

        // Simulate pressing DOWN key
        KeyEvent downPress = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, false, false, false);
        inputDetect.handlePressed(downPress);
        assertEquals(1, userPlane.getVerticalVelocityMultiplier(), "DOWN key should set verticalVelocityMultiplier to 1");

        // Simulate pressing LEFT key
        KeyEvent leftPress = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false);
        inputDetect.handlePressed(leftPress);
        assertEquals(-1, userPlane.getHorizontalVelocityMultiplier(), "LEFT key should set horizontalVelocityMultiplier to -1");

        // Simulate pressing RIGHT key
        KeyEvent rightPress = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false);
        inputDetect.handlePressed(rightPress);
        assertEquals(1, userPlane.getHorizontalVelocityMultiplier(), "RIGHT key should set horizontalVelocityMultiplier to 1");
    }

    @Test
    public void testHandleReleased_MovementKeys() {
        // Simulate releasing UP key
        KeyEvent upRelease = new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.UP, false, false, false, false);
        inputDetect.handleReleased(upRelease);
        assertEquals(0, userPlane.getVerticalVelocityMultiplier(), "UP key release should set verticalVelocityMultiplier to 0");

        // Simulate releasing LEFT key
        KeyEvent leftRelease = new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.LEFT, false, false, false, false);
        inputDetect.handleReleased(leftRelease);
        assertEquals(0, userPlane.getHorizontalVelocityMultiplier(), "LEFT key release should set horizontalVelocityMultiplier to 0");
    }

    @Test
    public void testHandlePressed_SpaceKey() {
        // Simulate pressing SPACE key
        KeyEvent spacePress = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SPACE, false, false, false, false);
        inputDetect.handlePressed(spacePress);

        // Verify projectile creation
        assertEquals(1, userProjectiles.size(), "Pressing SPACE should add a projectile to the userProjectiles list");
        assertTrue(root.getChildren().contains(userProjectiles.get(0)), "The created projectile should be added to the scene graph");
    }

    @Test
    public void testHandleReleased_SpaceKey() {
        // Simulate pressing SPACE key first
        KeyEvent spacePress = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SPACE, false, false, false, false);
        inputDetect.handlePressed(spacePress);

        // Simulate releasing SPACE key
        KeyEvent spaceRelease = new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.SPACE, false, false, false, false);
        inputDetect.handleReleased(spaceRelease);

        // Verify isSpacePressed is reset
        assertFalse(inputDetect.isSpacePressed(), "Releasing SPACE should reset isSpacePressed to false");
    }

}
