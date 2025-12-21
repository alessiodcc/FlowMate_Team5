package flowmate_team5;

import flowmate_team5.factory.creators.MessageActionCreator;
import flowmate_team5.models.actions.MessageAction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/* Unit tests verifying the validation logic and behavior of the MessageAction class. */
public class MessageActionTest {

    /* Verifies that the validator accepts a properly formatted message string. */
    @Test
    public void testIsValidMessage_CorrectInput_ShouldReturnTrue() {
        String validInput = "Remember to drink water!";
        // Assert that the static validation method returns true for valid input
        assertTrue(MessageAction.isValidMessage(validInput), "Validator should accept a correct string.");
    }

    /* Ensures that the validator correctly rejects null input references. */
    @Test
    public void testIsValidMessage_NullInput_ShouldReturnFalse() {
        // Assert that passing null results in a false validation outcome
        assertFalse(MessageAction.isValidMessage(null), "Validator must return false for null input.");
    }

    /* Verifies that empty strings or strings containing only whitespace are rejected. */
    @Test
    public void testIsValidMessage_EmptyOrWhitespace_ShouldReturnFalse() {
        // Check rejection for completely empty strings
        assertFalse(MessageAction.isValidMessage(""), "Must reject empty strings.");
        // Check rejection for strings containing only whitespace characters
        assertFalse(MessageAction.isValidMessage("   "), "Must reject whitespace-only strings.");
    }

    /* Tests the boundary condition ensuring messages exceeding 500 characters are rejected. */
    @Test
    public void testIsValidMessage_TooLong_ShouldReturnFalse() {
        // Generate a string exceeding the maximum defined length
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 501; i++) {
            sb.append("a");
        }
        // Verify that the oversized string fails validation
        assertFalse(MessageAction.isValidMessage(sb.toString()), "Validator must reject messages > 500 characters.");
    }

    /* Verifies the object lifecycle including factory instantiation and property configuration. */
    @Test
    public void testFactoryAndSetters() {
        String expectedMessage = "Meeting at 10 AM";

        // Instantiate the action using the specific factory creator
        MessageActionCreator creator = new MessageActionCreator();
        MessageAction action = (MessageAction) creator.createAction();

        // Set the message content using the class setter
        action.setMessageToShow(expectedMessage);

        // Confirm that the retrieved value matches the input
        assertEquals(expectedMessage, action.getMessageToShow(), "Message must match the configured input.");
    }
}