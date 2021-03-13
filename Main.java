import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Main {
  public static void main(String[] args) {
    // PLAY THE GAME (comment below to test)
    // TextAdventure ta = new TextAdventure();
    // ta.run();

    // TEST THE GAME (comment below to play)
    // after solving all the failures for TreasureTest.class
    // change the following line to run MonsterTest.class
    // then WeaponTest.class
    // then RoomTest.class
    Result result = JUnitCore.runClasses(TreasureTest.class);
    System.out.println();
    System.out.println("TESTS RUN: " + result.getRunCount());
    System.out.println("FAILURES:  " + result.getFailureCount());
    if (result.getFailures().size() == 0) {
      System.out.println("\n * * Nothing left to fix! * *\n");
    } else {
      System.out.println("\nFix the following: \n");
      int count = 1;
      for (Failure failure : result.getFailures()) {
        System.out.println(" " + count + " --> " + failure.toString() + "\n");
        count++;
      }
    }


  }
}