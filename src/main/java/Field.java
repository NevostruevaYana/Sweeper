public class Field {
    private Components[][] matrix;

    Field(Components components){
        matrix = new Components[Size.getSize().x][Size.getSize().y];
        for (int i = 0; i < Size.getSize().x; i++){
            for (int j = 0; j < Size.getSize().y; j++){
                matrix[i][j] = components;
            }
        }
    }

    Components get(Cell p){
        if (Size.inSize(p))
            return matrix[p.x][p.y];
        return null;
    }

    void set(Cell p, Components c){
        if (Size.inSize(p))
            matrix[p.x][p.y] = c;
    }
}
