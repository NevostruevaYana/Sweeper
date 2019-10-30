import java.util.Random;

class Size {

    private static Cell size;
    private static int bombNumber;
    private static State state;
    private static Random rnd = new Random();

    static Cell getSize() {
        return size;
    }

    static void setSize(Cell mySize, int myBombNumber, State myState) {
        size = mySize;
        bombNumber = myBombNumber;
        state = myState;
    }

    static int getBombNumber() {
        return bombNumber;
    }

    static Cell getRandomPoint() {
        return new Cell(rnd.nextInt(size.x), rnd.nextInt(size.y));
    }

    static boolean inSize(Cell p) {
        return p.x >= 0 && p.x < size.x && p.y >= 0 && p.y < size.y;
    }

    static State getState() {
      return state;
    }

    static void setState(State myState) {
        state = myState;
    }

}
