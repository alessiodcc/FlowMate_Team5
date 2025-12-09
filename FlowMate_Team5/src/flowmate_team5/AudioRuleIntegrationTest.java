package flowmate_team5;


/**
 * Integration Test 4.4 (Audio Action).
 * Verifies that the Rule Engine correctly triggers the PlayAudioAction.
 */
public class AudioRuleIntegrationTest {

    public static void main(String[] args) {
        System.out.println("=== Starting Audio Rule Integration Test ===");

        // 1. Setup: Create a Trigger that simulates a True condition
        Trigger simulationTrigger = new Trigger() {
            @Override
            public boolean isTriggered() {
                System.out.println("   [System] Checking trigger condition... (Result: TRUE)");
                return true;
            }
        };

        // 2. Setup: Instantiate Ester's Audio Action
        // Use a dummy path for testing, or a real path like "C:/Windows/Media/chimes.wav"
        String testFilePath = "test_audio.wav";
        Action audioAction = new PlayAudioAction(testFilePath);

        // 3. Integration: Bind the Trigger and Action using the Rule entity
        Rule audioRule = new Rule("Test Audio Rule", simulationTrigger, audioAction);

        // 4. Execution: Manually invoke the check cycle
        System.out.println("   [System] Requesting Rule Check...");

        // This 'check()' method is the connection
        audioRule.check();

        System.out.println("=== Test Complete. If you see 'Playing Audio' above, it is DONE. ===");
    }
}