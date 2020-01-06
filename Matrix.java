import java.util.*;
/**
 * Matrix
 * The aim of the program is to find if a Matrix is diagonal,symmetric,upper triangular, tridiagonal.
 *
 * @author Tanuj Yadav, tyadav@purdue.edu, Lab Section- G06
 *
 * @version 10/07/16
*/

public class Matrix
{
            public boolean isSymmetric(int[][] matrix )
            {
                for(int row = 0; row < matrix.length; row++){
                   for(int column=0; column< matrix.length; column++){

                if(matrix[row][column]!= matrix[column][row]){
                        return false;
                }
              }
            }
                 return true;
              }

              public boolean isDiagonal(int[][] matrix)
              {
                int p= 0, q = 0;
                for(int i=0; i< matrix.length; i++){
                        for(int j=0; j < matrix.length; j++){
                                if(i!=j && matrix[i][j] != 0){
                                        p = 1;
                                        break;
                                }
                                if(i==j && matrix[i][j] == 0){
                                        q++;
                                }
                        }
                }
                if(p==0 && q < matrix.length){
                        return true;
            }
                else{
                return false;
            }
              }

              public boolean isIdentity(int[][] matrix)
              {
                for(int i=0; i<matrix.length;i++){
                        for(int j=0; j<matrix.length;j++){
                                if(matrix[i][j] == matrix [i][j])
                                {
                                        return true;
                                }
 }
                }
                return false;
              }

              public boolean isUpperTriangular(int[][] matrix)
          {
                int t = 0;
                for(int i=0; i < matrix.length; i++)
                {
                        for(int j=0; j < i; j++)
                        {
                                if(matrix[i][j]!=0)
                                {
                                        t=1;
                                        break;
                                }
                        }
                }
                if(t==0){
                        return true;
            }
            else{
                return false;
             }
          }

          public boolean isTriDiagonal(int[][] matrix)
        {
        for (int x = 0; x < matrix.length; x++)
         {
            for (int y = 0; y < matrix[x].length; y++)
              {
                int cell = matrix[x][y];

                if ((x==y) || (x-1==y) || (x+1==y))
                   {
                    if (cell == 0) {
                        return false;
                    }
                } else
                 {
                    if (cell != 0)
                  {
                        return false;
                    }
                }
            }
        return true;
     }
       return true;
  }
     public static void main(String[] args)
    {
        int[][]matrix={{0,1,0,0},{0,0,1,0},{0,0,0,1},{0,0,0,0}};
        Matrix obj = new Matrix();
        System.out.println("Symmetric:"+obj.isSymmetric(matrix));
        System.out.println("Diagonal:"+obj.isDiagonal(matrix));
        System.out.println("Identity:"+obj.isIdentity(matrix));
        System.out.println("Upper Triangular:"+obj.isUpperTriangular(matrix));
        System.out.println("TriDiagonal:"+obj.isTriDiagonal(matrix));
    }
}

                                                                                                                                                                   107,1         76%
