import java.util.*;

public class Cell {
    int x;
    int y;

    Cell (int x,int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;
        Cell point = (Cell) obj;
        return point.x == this.x && point.y == this.y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    List<Cell> getCellAround() {
        List<Cell> aroundList = new LinkedList<>();
        Cell around;
        for (int i = x - 1; i <= x + 1; i++){
            for (int j = y - 1; j <= y + 1; j++){
                around = new Cell(i, j);
                if (Size.inSize(around)) {
                    if (!around.equals(new Cell(x, y))) {
                        aroundList.add(around);
                    }
                }
            }
        }
        return aroundList;
    }
}

