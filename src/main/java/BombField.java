class BombField {

    private Field bombField;

    BombField() {
        initBombMatrix();
    }

    private void initBombMatrix() {
        bombField = new Field(Components.ZERO);
        for (int i = 0; i < Size.getBombNumber(); i++) {
            Cell p;
            while (true){
                p = Size.getRandomPoint();
                if (Components.BOMB == bombField.get(p)) {
                    continue;
                }
                break;
            }
            Cell point = p;
            bombField.set(point, Components.BOMB);
            for (Cell around : point.getCellAround()) {
                if (bombField.get(around) != Components.BOMB)
                    bombField.set(around, Components.values()[bombField.get(around).ordinal() + 1]);
            }
        }
    }

    Components get(Cell cell) {
        return bombField.get(cell);
    }

    void set(Cell cell, Components c) {
        bombField.set(cell, c);
    }

}
