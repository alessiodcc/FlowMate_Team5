package flowmate_team5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageActionTest {

    // Verify that valid messages are accepted (Static method, no factory needed)
    @Test
    public void testIsValidMessage_CorrectInput_ShouldReturnTrue() {
        String validInput = "Remember to drink water!";
        assertTrue(MessageAction.isValidMessage(validInput), "Validator should accept a correct string.");
    }

    // Verify that null input is rejected
    @Test
    public void testIsValidMessage_NullInput_ShouldReturnFalse() {
        assertFalse(MessageAction.isValidMessage(null), "Validator must return false for null input.");
    }

    // Verify that empty strings are rejected
    @Test
    public void testIsValidMessage_EmptyOrWhitespace_ShouldReturnFalse() {
        assertFalse(MessageAction.isValidMessage(""), "Must reject empty strings.");
        assertFalse(MessageAction.isValidMessage("   "), "Must reject whitespace-only strings.");
    }

    // Verify length limit > 500 chars
    @Test
    public void testIsValidMessage_TooLong_ShouldReturnFalse() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 501; i++) {
            sb.append("a");
        }
        assertFalse(MessageAction.isValidMessage(sb.toString()), "Validator must reject messages > 500 characters.");
    }

    // Verify object initialization using Factory
    @Test
    public void testFactoryAndSetters() {
        String expectedMessage = "Meeting at 10 AM";

        // Use Creator to instantiate
        MessageActionCreator creator = new MessageActionCreator();
        MessageAction action = (MessageAction) creator.createAction();

        // Configure using setter
        action.setMessageToShow(expectedMessage);

        assertEquals(expectedMessage, action.getMessageToShow(), "Message must match the configured input.");
    }
}