import java.util.*;
import java.util.Scanner;
/**
 *Array Builder
 *
 * The objective of the program is to build an array containing alphabets in
 * a special order.It asks for base letter, dimensions of array and then prints
 * a result.
 *
 *@author Tanuj Yadav,tyadav@purdue.edu,Section G06
 *
 *@version  10/14/16
 *
 */

public class ArrayBuilder 
{
	public char baseLetter;
	public int n;
	public int m;
	public char[][] letterArray;
	
	public ArrayBuilder(char baseLetter, int n, int m)
	{
		this.baseLetter = baseLetter;
		this.n = n;
		this.m = m;
						
	}
	
	public void build()
	{
		letterArray = new char[this.n][this.m];
		this.letterArray[0][0] = this.baseLetter;
		int asciiVal = this.letterArray[0][0];
		int firstRow = asciiVal;
		if(asciiVal >=65 && asciiVal <=90)
		{
			for(int i = 0; i<this.n; i++)
			{
				    if(i+1<n)
				    {
					if(firstRow==90)
					{
						firstRow = 64;
						this.letterArray[i+1][0] = (char)(++firstRow);
					}
					else
					this.letterArray[i+1][0] = (char)(++firstRow);
				}
				for(int j =1; j<this.m; j++)
				{
					if(asciiVal==90)
					{
						asciiVal = 64;
					}
					this.letterArray[i][j] = (char)(++asciiVal);
					
				}
				asciiVal = firstRow;
			}
		}
		else if(asciiVal >=97 && asciiVal <=122)
		{
			for(int i = 0; i<this.n; i++)
			{
				if(i+1<n)
				{
					   if(firstRow==122)
					   {
						firstRow = 96;
						this.letterArray[i+1][0] = (char)(++firstRow);
					}
					else
					this.letterArray[i+1][0] = (char)(++firstRow);
				}
				for(int j =1; j<this.m; j++)
				{
					if(asciiVal==122)
					{
						asciiVal = 98;
					}
					this.letterArray[i][j] = (char)(++asciiVal);					
				}
				asciiVal = firstRow;
			}
		}		
	}
	
	public void printLetterArray()
	{
		char[][] newArray = this.getLetterArray();
		for(int i = 0; i < this.n; i++)
		{
			for(int j = 0; j < this.m; j++)
			{
				System.out.print(newArray[i][j]);
			}
			System.out.print("\n");
		}
	}

	public char[][] getLetterArray()
	{
		return this.letterArray;
	}

	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		System.out.println("Enter base letter: ");
		char c = in.next().charAt(0);
		System.out.println("Enter the number of rows: ");
		int rows = in.nextInt();
		System.out.println("Enter the number of columns: ");
		int columns = in.nextInt();
		
		ArrayBuilder object = new ArrayBuilder(c,rows,columns);
		object.build();
		object.getLetterArray();
		object.printLetterArray();		
	}
}
