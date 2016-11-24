package matrix;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DenseMatrix extends Matrix {
    double[][] matrix;
    int temp;
    public DenseMatrix(String name) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(new File(name)));
            ArrayList<String> listmatrix = new ArrayList();
            String line;
            while ((line = reader.readLine()) != null) {
                listmatrix.add(line);
            }
            this.row = listmatrix.size();
            this.column = listmatrix.get(0).split(" ").length;
            double[][] matrix = new double[row][column];
            for (int i = 0; i < row; i++) {
                String[] temp = listmatrix.get(i).split(" ");
                for (int j = 0; j < column; j++)
                    matrix[i][j] = Double.parseDouble(temp[j]);
            }
            this.matrix = matrix;
            reader.close();
        } catch (IOException e) {}
    }

   public DenseMatrix (int row, int column) {
        this.row = row;
        this.column = column;
        this.matrix = new double[row][column];
    }

    public DenseMatrix transponation(DenseMatrix matrix2) {
        DenseMatrix temp = new DenseMatrix(matrix2.column, matrix2.row);
        for (int j = 0; j < matrix2.row; j++)
            for (int i = 0; i < matrix2.column; i++)
                temp.matrix[i][j]=matrix2.matrix[j][i];
        return temp;
    }

    public Matrix mul(Matrix matrix2) {
        if (matrix2 instanceof DenseMatrix)
            try {
                return (this.mul((DenseMatrix) matrix2));
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                return null;
        }
        else return (this.mul((SparseMatrix) matrix2));
    }

    public DenseMatrix mul(DenseMatrix matrix2) throws InterruptedException {
        final int temp=Runtime.getRuntime().availableProcessors();
        Thread thread[]=new Thread[temp];
        DenseMatrix matrix1=this;
        matrix2 = matrix2.transponation(matrix2);
        DenseMatrix matrix = new DenseMatrix(matrix1.row, matrix2.column);
        mulParal t = new mulParal(this.matrix,matrix2.matrix,matrix.matrix,matrix1.row,matrix2.column,matrix1.column);
        for (int i=0;i<temp;i++) {
            thread[i]=new Thread(t);
            thread[i].start();
        }
        for (int i=0;i<temp;i++)
            thread[i].join();
        return matrix;
    }
    public class mulParal implements Runnable {
        int num=0;
        double[][] matrix1;
        double[][] matrix2;
        double[][] matrix;
        int matrix1row, matrix2column, matrix1column;


        public mulParal(double[][] matrix1, double[][] matrix2, double[][] matrix, int matrix1row, int matrix2column,int matrix1column) {
            this.matrix1 = matrix1;
            this.matrix2 = matrix2;
            this.matrix = matrix;
            this.matrix1column=matrix1column;
            this.matrix1row=matrix1row;
            this.matrix2column=matrix2column;
        }

        public void run() {
            for (int i = next(); i < matrix1row; i = next()) {
                for (int j = 0; j < matrix2column; j++) {
                    for (int k = 0; k < matrix1column; k++) {
                        matrix[i][j] += matrix1[i][k] * matrix2[j][k];
                    }
                }

            }
        }
        public int next(){
            synchronized (this) {
                return num++;
            }
        }
    }

    public SparseMatrix mul (SparseMatrix matrix2){
        DenseMatrix matrix1 = this;
        if (matrix1.column != matrix2.row) throw new RuntimeException("Перемножить нельзя");
        ConcurrentHashMap<Integer, Column> matrix = new ConcurrentHashMap<Integer, Column>();
        SparseMatrix tempmatrix2=matrix2.transponation(matrix2);
        double temp=0;
        boolean bool=false;
        for (int i=0;i<matrix1.row;i++) {
            Column value=new Column();
            for (HashMap.Entry<Integer,Column> coordinate2: tempmatrix2.matrix.entrySet()){
                for (int k=0;k<matrix1.column;k++)
                    temp+=matrix1.matrix[i][k]*tempmatrix2.matrix.get(coordinate2.getKey()).get(k);
                if (temp!=0) {
                    value.put(coordinate2.getKey(), temp);
                    temp=0;
                    bool=true;
                }
            }
            if (bool)
                matrix.put(i,value);
        }
        return new SparseMatrix(matrix,matrix1.row,matrix2.column);
    }



    public void outDense(BufferedWriter writer) {
        try {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    writer.write(matrix[i][j] + " ");

                }
                writer.write("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public double getElement (int i,int j){
        return matrix[i][j];
    }
}