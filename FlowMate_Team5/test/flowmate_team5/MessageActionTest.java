package flowmate_team5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageActionTest {

    // Verify that valid messages are accepted.
    @Test
    public void testIsValidMessage_CorrectInput_ShouldReturnTrue() {
        String validInput = "Remember to drink water!";
        assertTrue(MessageAction.isValidMessage(validInput), "Validator should accept a correct string.");
    }

    // Verify that null input is rejected.
    @Test
    public void testIsValidMessage_NullInput_ShouldReturnFalse() {
        assertFalse(MessageAction.isValidMessage(null), "Validator must return false for null input.");
    }

    // Verify that empty strings or whitespace-only strings are rejected.
    @Test
    public void testIsValidMessage_EmptyOrWhitespace_ShouldReturnFalse() {
        assertFalse(MessageAction.isValidMessage(""), "Must reject empty strings.");
        assertFalse(MessageAction.isValidMessage("   "), "Must reject whitespace-only strings.");
    }

    // Verify length limit (Boundary Testing > 500 chars).
    @Test
    public void testIsValidMessage_TooLong_ShouldReturnFalse() {
        // Generate a string with 501 characters
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 501; i++) {
            sb.append("a");
        }

        assertFalse(MessageAction.isValidMessage(sb.toString()), "Validator must reject messages > 500 characters.");
    }

    // Structural Test: Verify object initialization.
    @Test
    public void testConstructorAndGetters() {
        String expectedName = "AlertRule";
        String expectedMessage = "Meeting at 10 AM";

        // Assuming the constructor issue has been fixed to accept (name, message)
        MessageAction action = new MessageAction(expectedName, expectedMessage);

        assertEquals(expectedName, action.getName(), "Name must match the constructor input.");
        assertEquals(expectedMessage, action.getMessageToShow(), "Message must match the constructor input.");
    }
}