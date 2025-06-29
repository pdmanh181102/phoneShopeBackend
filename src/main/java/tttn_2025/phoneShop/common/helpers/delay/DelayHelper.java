package tttn_2025.phoneShop.common.helpers.delay;

public class DelayHelper {
    public static void Delay(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
