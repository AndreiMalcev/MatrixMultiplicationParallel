package matrix;


abstract public class Matrix {
    int row, column;

    public abstract Matrix mul(Matrix other);

    public abstract double getElement(int x, int y);

    @Override
    public boolean equals(Object matrix) {
        if (matrix instanceof Matrix) {
            for (int i = 0; i < row; i++)
                for (int j = 0; j < column; j++)
                    if (this.getElement(i, j) != ((Matrix) matrix).getElement(i, j))
                        return false;
        }
        return true;
    }
}
