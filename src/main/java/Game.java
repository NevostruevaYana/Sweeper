import javafx.util.Pair;

import java.util.*;

class Game {

    private BombField bombField;
    private GameField gameField;
    private List<Cell> closedList = new LinkedList<>();
    private List<Cell> openedList = new LinkedList<>();

    Game(){

    }

    void play(){
        bombField = new BombField();
        gameField = new GameField();
    }

    Components getComponents(Cell point){
        if (gameField.get(point) == Components.ZERO)
            return bombField.get(point);
        else
            return gameField.get(point);
    }

    void pressRight(Cell p) {
        if (gameField.get(p) == Components.CLOSED)
            gameField.set(p, Components.FLAG);
        else
            if (gameField.get(p) == Components.FLAG)
                gameField.set(p, Components.CLOSED);
    }

    void pressLeft(Cell p) {
        open(p);
    }

    void resolve(){
        startToDecision();
    }

    private void open(Cell p) {
        switch (gameField.get(p)) {
            case FLAG: return;
            case CLOSED:
                switch (bombField.get(p)) {
                    case BOMB: gameOver(p);
                    case ZERO: openPointsAround(p);
                    default: openTheCage(p);
                }
        }
    }

    int getClosed(){
        return gameField.countClosed();
    }

    int getFlagged(){
        return gameField.countFlagged();
    }

    private void openTheCage(Cell p) {
        gameField.set(p, bombField.get(p));
    }

    private void gameOver(Cell p){
        bombField.set(p, Components.DETONATEDBOMB);
        for (int i = 0; i < Size.getSize().x; i++){
            for (int j = 0; j < Size.getSize().y; j++){
                Cell point = new Cell(i, j);
                if (gameField.get(point) == Components.FLAG && bombField.get(point) != Components.BOMB)
                    bombField.set(point, Components.NOBOMB);
                openTheCage(point);
            }
        }
        Size.setState(State.DEFEAT);
    }

    private void openPointsAround(Cell p) {
        openTheCage(p);
        for (Cell around: p.getCellAround()) {
            open(around);
        }
    }

    void startToDecision() {
        Cell p;
        while (true) {
            p = Size.getRandomPoint();
            open(p);
            if (gameField.get(p) == Components.ZERO) {
                break;
            }
        }
        closedList.add(p);

        analysis(p);
    }

    private void u(List<Cell> o){
        for (Cell c: o){

            List<Cell> set = new LinkedList<>();
            for (Cell k: c.getCellAround())
                if (an(k))
                    set.add(k);
            openedList.addAll(set);
            if (gameField.get(c) == Components.ZERO || gameField.get(c) == Components.FLAG)
                putInClosedList(c);
        }
    }

    private boolean an(Cell cell){
        if (!openedList.contains(cell) && !closedList.contains(cell) && gameField.get(cell) != Components.CLOSED)
            return true;
        return false;
    }

    private void analysis(Cell p) {
        for (Cell current: p.getCellAround()){
            if (gameField.get(current) == Components.ZERO && gameField.get(current) == Components.FLAG && !closedList.contains(current)) {
                putInClosedList(current);
                analysis(current);
            } else {
                if (gameField.get(current) != Components.CLOSED)
                    if (!openedList.contains(current))
                        openedList.add(current);
                s(current);
            }
        }
    }

    private void s(Cell current) {
        if ((gameField.get(current).ordinal() - getClosedNumber(current).getValue() == getClosedNumber(current).getKey()))
            for (Cell around: current.getCellAround())
                if ((gameField.get(around) == Components.CLOSED))
                    gameField.set(around, Components.FLAG);
        if (gameField.get(current).ordinal() == getClosedNumber(current).getValue())
            for (Cell around: current.getCellAround())
                if ((gameField.get(around) == Components.CLOSED))
                    open(around);
    }


    Pair<Integer, Integer> getClosedNumber(Cell p) {
        int countClosed = 0;
        int countFlagged = 0;
        for (Cell around: p.getCellAround()) {
            if (gameField.get(around) == Components.CLOSED) {
                countClosed++;
            }
            if (gameField.get(around) == Components.FLAG) {
                countFlagged++;
            }
        }
        return new Pair(countClosed, countFlagged);
    }

    private void putInClosedList(Cell cell) {
        if (openedList.contains(cell))
            openedList.remove(cell);
        closedList.add(cell);
    }

}
