import java.util.Random;

public enum Direction {
    nord,
    sud,
    est,
    ouest;

    public static Direction random() {
        Random rnd = new Random();
        int r = rnd.nextInt(4);
        switch (r) {
            case 0:  return Direction.nord;
            case 1:  return Direction.sud;
            case 2:  return Direction.est;
            default: return Direction.ouest;
        }
    }
}
