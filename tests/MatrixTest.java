import matrix.DenseMatrix;
import matrix.SparseMatrix;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MatrixTest {
    @Test
    public void testmulDD() throws InterruptedException {
        DenseMatrix matrix1 = new DenseMatrix("data/matrix1.txt");
        DenseMatrix matrix2 = new DenseMatrix("data/matrix2.txt");
        DenseMatrix matrix= matrix1.mul(matrix2);
        DenseMatrix matrixresult = new DenseMatrix("data/matrix.txt");
        assertEquals(matrixresult,matrix);
        int st=0;
    }


    @Test
    public void testmulDS(){
        DenseMatrix matrix1 = new DenseMatrix("data/matrix1.txt");
        SparseMatrix matrix2 = new SparseMatrix("data/matrix2.txt");
        SparseMatrix matrix= matrix1.mul(matrix2);
        SparseMatrix matrixresult = new SparseMatrix("data/matrix.txt");
        assertEquals(matrixresult,matrix);
    }
    @Test
    public void testmulSD(){
        SparseMatrix matrix1 = new SparseMatrix("data/matrix1.txt");
        DenseMatrix matrix2 = new DenseMatrix("data/matrix2.txt");
        SparseMatrix matrix= matrix1.mul(matrix2);
        SparseMatrix matrixresult = new SparseMatrix("data/matrix.txt");
        assertEquals(matrixresult,matrix);
    }
    @Test
    public void testmulSS() throws InterruptedException{
        SparseMatrix matrix1 = new SparseMatrix("data/matrix1.txt");
        SparseMatrix matrix2 = new SparseMatrix("data/matrix2.txt");
        SparseMatrix matrix= matrix1.mul(matrix2);
        SparseMatrix matrixresult = new SparseMatrix("data/matrix.txt");
        assertEquals(matrixresult,matrix);
    }
}
