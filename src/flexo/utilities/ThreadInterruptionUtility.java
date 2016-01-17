package flexo.utilities;

public class ThreadInterruptionUtility {

    public static void checkIfInterrupted() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

}
