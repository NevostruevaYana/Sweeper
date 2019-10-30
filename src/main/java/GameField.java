class GameField {

    private Field gameField;

    GameField() {
        gameField = new Field(Components.CLOSED);
    }

    int countClosed() {
        int count = 0;
        for (int i = 0; i < Size.getSize().x; i++){
            for (int j = 0; j < Size.getSize().y; j++){
                if (gameField.get(new Cell(i, j)) == Components.CLOSED)
                    count++;
            }
        }
        return count;
    }

    int countFlagged() {
        int count = 0;
        for (int i = 0; i < Size.getSize().x; i++){
            for (int j = 0; j < Size.getSize().y; j++){
                if (gameField.get(new Cell(i, j)) == Components.FLAG)
                    count++;
            }
        }
        return count;
    }

    Components get(Cell cell) {
        return gameField.get(cell);
    }

    void set(Cell cell, Components c) {
        gameField.set(cell, c);
    }

}
