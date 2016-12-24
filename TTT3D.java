/*
Assignment #4: 3D Tic Tac Toe
This program sets up a 4x4x4 Tic Tac Toe board and plays intelligently 
against the player according to a prioritized list of actions. Can start from
a blank board or a setup file with moves already in place.

Used Bailey's source for some of the methods:
Various routines for working with the key arrays: board, lines and sums in one
possible implementation of the TTT3D project.
(Author: Delbert Bailey)

Author: Priya Padmanaban (ppadman2@ucsc.edu)
*/


import java.io.*;
import java.util.Random;
import java.util.Scanner;

class TTT3D
{
  //sets up the board and winning combinations
  static int[][][] board = new int[4][4][4];
  static int[] sums = new int[76];
  static final int[][][] lines = {
			{{0,0,0},{0,0,1},{0,0,2},{0,0,3}},  //lev 0; row 0   rows in each level     
			{{0,1,0},{0,1,1},{0,1,2},{0,1,3}},  //       row 1     
			{{0,2,0},{0,2,1},{0,2,2},{0,2,3}},  //       row 2     
			{{0,3,0},{0,3,1},{0,3,2},{0,3,3}},  //       row 3     
			{{1,0,0},{1,0,1},{1,0,2},{1,0,3}},  //lev 1; row 0     
			{{1,1,0},{1,1,1},{1,1,2},{1,1,3}},  //       row 1     
			{{1,2,0},{1,2,1},{1,2,2},{1,2,3}},  //       row 2     
			{{1,3,0},{1,3,1},{1,3,2},{1,3,3}},  //       row 3     
			{{2,0,0},{2,0,1},{2,0,2},{2,0,3}},  //lev 2; row 0     
			{{2,1,0},{2,1,1},{2,1,2},{2,1,3}},  //       row 1     
			{{2,2,0},{2,2,1},{2,2,2},{2,2,3}},  //       row 2       
			{{2,3,0},{2,3,1},{2,3,2},{2,3,3}},  //       row 3     
			{{3,0,0},{3,0,1},{3,0,2},{3,0,3}},  //lev 3; row 0     
			{{3,1,0},{3,1,1},{3,1,2},{3,1,3}},  //       row 1 
			{{3,2,0},{3,2,1},{3,2,2},{3,2,3}},  //       row 2       
			{{3,3,0},{3,3,1},{3,3,2},{3,3,3}},  //       row 3           
			{{0,0,0},{0,1,0},{0,2,0},{0,3,0}},  //lev 0; col 0   columns in each level  
			{{0,0,1},{0,1,1},{0,2,1},{0,3,1}},  //       col 1    
			{{0,0,2},{0,1,2},{0,2,2},{0,3,2}},  //       col 2    
			{{0,0,3},{0,1,3},{0,2,3},{0,3,3}},  //       col 3    
			{{1,0,0},{1,1,0},{1,2,0},{1,3,0}},  //lev 1; col 0     
			{{1,0,1},{1,1,1},{1,2,1},{1,3,1}},  //       col 1    
			{{1,0,2},{1,1,2},{1,2,2},{1,3,2}},  //       col 2    
			{{1,0,3},{1,1,3},{1,2,3},{1,3,3}},  //       col 3    
			{{2,0,0},{2,1,0},{2,2,0},{2,3,0}},  //lev 2; col 0     
			{{2,0,1},{2,1,1},{2,2,1},{2,3,1}},  //       col 1    
			{{2,0,2},{2,1,2},{2,2,2},{2,3,2}},  //       col 2    
			{{2,0,3},{2,1,3},{2,2,3},{2,3,3}},  //       col 3    
			{{3,0,0},{3,1,0},{3,2,0},{3,3,0}},  //lev 3; col 0     
			{{3,0,1},{3,1,1},{3,2,1},{3,3,1}},  //       col 1
			{{3,0,2},{3,1,2},{3,2,2},{3,3,2}},  //       col 2
			{{3,0,3},{3,1,3},{3,2,3},{3,3,3}},  //       col 3
		        {{0,0,0},{1,0,0},{2,0,0},{3,0,0}},  //cols in vert plane in front
		        {{0,0,1},{1,0,1},{2,0,1},{3,0,1}},
		        {{0,0,2},{1,0,2},{2,0,2},{3,0,2}},
		        {{0,0,3},{1,0,3},{2,0,3},{3,0,3}},
		        {{0,1,0},{1,1,0},{2,1,0},{3,1,0}},  //cols in vert plane one back
		        {{0,1,1},{1,1,1},{2,1,1},{3,1,1}},
		        {{0,1,2},{1,1,2},{2,1,2},{3,1,2}},
		        {{0,1,3},{1,1,3},{2,1,3},{3,1,3}},
		        {{0,2,0},{1,2,0},{2,2,0},{3,2,0}},  //cols in vert plane two back
		        {{0,2,1},{1,2,1},{2,2,1},{3,2,1}},
		        {{0,2,2},{1,2,2},{2,2,2},{3,2,2}},
		        {{0,2,3},{1,2,3},{2,2,3},{3,2,3}},
		        {{0,3,0},{1,3,0},{2,3,0},{3,3,0}},  //cols in vert plane in rear
		        {{0,3,1},{1,3,1},{2,3,1},{3,3,1}},
		        {{0,3,2},{1,3,2},{2,3,2},{3,3,2}},
		        {{0,3,3},{1,3,3},{2,3,3},{3,3,3}},
		        {{0,0,0},{0,1,1},{0,2,2},{0,3,3}},  //diags in lev 0
		        {{0,3,0},{0,2,1},{0,1,2},{0,0,3}},
		        {{1,0,0},{1,1,1},{1,2,2},{1,3,3}},  //diags in lev 1
		        {{1,3,0},{1,2,1},{1,1,2},{1,0,3}},
		        {{2,0,0},{2,1,1},{2,2,2},{2,3,3}},  //diags in lev 2
		        {{2,3,0},{2,2,1},{2,1,2},{2,0,3}},
		        {{3,0,0},{3,1,1},{3,2,2},{3,3,3}},  //diags in lev 3
		        {{3,3,0},{3,2,1},{3,1,2},{3,0,3}},
		        {{0,0,0},{1,0,1},{2,0,2},{3,0,3}},  //diags in vert plane in front
		        {{3,0,0},{2,0,1},{1,0,2},{0,0,3}},
		        {{0,1,0},{1,1,1},{2,1,2},{3,1,3}},  //diags in vert plane one back
		        {{3,1,0},{2,1,1},{1,1,2},{0,1,3}},
		        {{0,2,0},{1,2,1},{2,2,2},{3,2,3}},  //diags in vert plane two back
		        {{3,2,0},{2,2,1},{1,2,2},{0,2,3}},
		        {{0,3,0},{1,3,1},{2,3,2},{3,3,3}},  //diags in vert plane in rear
		        {{3,3,0},{2,3,1},{1,3,2},{0,3,3}},
		        {{0,0,0},{1,1,0},{2,2,0},{3,3,0}},  //diags left slice      
		        {{3,0,0},{2,1,0},{1,2,0},{0,3,0}},        
		        {{0,0,1},{1,1,1},{2,2,1},{3,3,1}},  //diags slice one to right
		        {{3,0,1},{2,1,1},{1,2,1},{0,3,1}},        
		        {{0,0,2},{1,1,2},{2,2,2},{3,3,2}},  //diags slice two to right      
		        {{3,0,2},{2,1,2},{1,2,2},{0,3,2}},        
		        {{0,0,3},{1,1,3},{2,2,3},{3,3,3}},  //diags right slice      
		        {{3,0,3},{2,1,3},{1,2,3},{0,3,3}},        
		        {{0,0,0},{1,1,1},{2,2,2},{3,3,3}},  //cube vertex diags
		        {{3,0,0},{2,1,1},{1,2,2},{0,3,3}},
		        {{0,3,0},{1,2,1},{2,1,2},{3,0,3}},
		        {{3,3,0},{2,2,1},{1,1,2},{0,0,3}}        
		    };
  
  //prints the board
  static void printBoardRaw()
  {
    for (int i = 3; i >= 0; i--)
    {
      for (int j = 3; j >= 0; j--)
      {
        for (int k = 0; k < 4; k++) {
          System.out.printf("%1d ", new Object[] { Integer.valueOf(board[i][j][k]) });
        }
        System.out.printf("\n", new Object[0]);
      }
      System.out.printf("\n", new Object[0]);
    }
  }
  
  //assigns values for an empty space, a player move, and a computer move
  static char marker(int paramInt)
  {
    switch (paramInt)
    {
    case 0: 
      return '_';
    case 1: 
      return 'O';
    case 5: 
      return 'X';
    }
    return '?';
  }
  
  //prints the relevant move in the appropriate position
  static void printBoardMarkers()
  {
    for (int i = 3; i >= 0; i--)
    {
      System.out.printf("\n", new Object[0]);
      for (int j = 3; j >= 0; j--)
      {
        for (int k = j; k >= 0; k--) {
          System.out.printf(" ", new Object[0]);
        }
        System.out.printf("%d%1d  ", new Object[] { Integer.valueOf(i), Integer.valueOf(j) });
        for (int k = 0; k < 4; k++) {
          System.out.printf("%1c ", new Object[] { Character.valueOf(marker(board[i][j][k])) });
        }
        System.out.printf("\n", new Object[0]);
      }
    }
    System.out.printf("\n   0 1 2 3\n", new Object[0]);
  }
  
  static void printSums()
  {
    for (int i = 0; i < sums.length; i++) {
      System.out.println("line " + i + "is " + sums[i]);
    }
  }
  
  //returns true if empty, false if not
  static boolean isEmpty(int[] paramArrayOfInt)
  {
    if (board[paramArrayOfInt[0]][paramArrayOfInt[1]][paramArrayOfInt[2]] == 0) {
      return true;
    }
    return false;
  }
 
  //the following 6 methods were adapted from Bailey's template, pretty self explanatory
  
  static void move(int[] paramArrayOfInt, int paramInt)
  {
    move(paramArrayOfInt[0], paramArrayOfInt[1], paramArrayOfInt[2], paramInt);
  }
  
  static void move(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    board[paramInt1][paramInt2][paramInt3] = paramInt4;
  }
  
  static void setAll(int paramInt)
  {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 4; k++) {
          move(i, j, k, paramInt);
        }
      }
    }
  }
  
  static boolean isEqual(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    for (int i = 0; i < paramArrayOfInt1.length; i++) {
      if (paramArrayOfInt1[i] != paramArrayOfInt2[i]) {
        return false;
      }
    }
    return true;
  }
  
  static int[] findEmptyCel(int[][] paramArrayOfInt)
  {
    for (int i = 0; i < 4; i++) {
      if (isEmpty(paramArrayOfInt[i])) {
        return paramArrayOfInt[i];
      }
    }
    return null;
  }
  
  static int[] findComMtCel(int[][] paramArrayOfInt1, int[][] paramArrayOfInt2)
  {
    for (int i = 0; i < paramArrayOfInt1.length; i++) {
      for (int j = 0; j < paramArrayOfInt1.length; j++) {
        if ((isEqual(paramArrayOfInt1[i], paramArrayOfInt2[j])) && (isEmpty(paramArrayOfInt1[i])) && (isEmpty(paramArrayOfInt2[j]))) {
          return paramArrayOfInt1[i];
        }
      }
    }
    return null;
  }

  //computes the sum of the line
  static void compLineSums()
  {
    for (int i = 0; i < sums.length; i++)
    {
      sums[i] = 0;
      for (int j = 0; j < 4; j++) {
        sums[i] += board[lines[i][j][0]][lines[i][j][1]][lines[i][j][2]];
      }
    }
  }
  
  //checks win condition against sum of the line
  static int findLineSum(int paramInt)
  {
    for (int i = 0; i < sums.length; i++) {
      if (sums[i] == paramInt) {
        return i;
      }
    }
    return -1;
  }
  
  //checks for forks
  static int[] findFork(int paramInt, int[][][] paramArrayOfInt, int[] paramArrayOfInt1)
  {
    int[] arrayOfInt = new int[3];
    for (int i = 0; i < paramArrayOfInt1.length - 1; i++) {
      if (paramArrayOfInt1[i] == 2 * paramInt) {
        for (int j = i + 1; j < paramArrayOfInt1.length; j++) {
          if ((paramArrayOfInt1[j] == 2 * paramInt) && ((arrayOfInt = findComMtCel(paramArrayOfInt[i], paramArrayOfInt[j])) != null)) {
            return arrayOfInt;
          }
        }
      }
    }
    return null;
  }

  //main method  
  public static void main(String[] paramArrayOfString)
    throws FileNotFoundException
  {
    Scanner localScanner1 = new Scanner(System.in);
    Random localRandom = new Random(1234L);
    int[] arrayOfInt1 = new int[3];
    int[] arrayOfInt2 = new int[3];
    int[] arrayOfInt3 = new int[3];
    int i = 0;
    int k;
    int m;
    int n;
    int i1;
    if (paramArrayOfString.length != 0)
    {
      Scanner localScanner2 = new Scanner(new FileInputStream(paramArrayOfString[0]));
      k = localScanner2.nextInt();
      for (m = 0; m < k; m++)
      {
        n = localScanner2.nextInt() % 4;
        i1 = localScanner2.nextInt() % 4;
        int i2 = localScanner2.nextInt() % 4;
        int i3 = localScanner2.nextInt();
        move(n, i1, i2, i3);
      }
    }
    else
    {
      setAll(0);
    }
    int j = 0;
    while (j == 0)
    {
      printBoardMarkers();
      
      k = 0;
      while (k == 0)
      {
        System.out.println("Type your move as one three digit number (levelrowcolumn).");
        m = localScanner1.nextInt();
        arrayOfInt2[2] = (m % 10 % 4);
        arrayOfInt2[1] = (m / 10 % 10 % 4);
        arrayOfInt2[0] = (m / 100 % 4);
        if (isEmpty(arrayOfInt2))
        {
          k = 1;
          move(arrayOfInt2, 5);
        }
        else
        {
          System.out.println("Sorry, that cell is occupied.");
        }
      }
      compLineSums();
      if (findLineSum(20) != -1)
      {
        printBoardMarkers();
        System.out.println("Congratulations, you won!");
        j = 1;
      }
      else if ((i = findLineSum(3)) != -1)
      {
        move(findEmptyCel(lines[i]), 1);
        printBoardMarkers();
        System.out.println("Computer wins again!");
        j = 1;
      }
      else if ((i = findLineSum(15)) != -1)
      {
        move(findEmptyCel(lines[i]), 1);
      }
      else if ((arrayOfInt1 = findFork(1, lines, sums)) != null)
      {
        move(arrayOfInt1, 1);
      }
      else if ((arrayOfInt1 = findFork(5, lines, sums)) != null)
      {
        move(arrayOfInt1, 1);
      }
      else
      {
        m = localRandom.nextInt(sums.length);
        n = 0;
        for (i1 = 0; (i1 < sums.length) && (n == 0); i1++)
        {
          switch (sums[m])
          {
          case 0: 
          case 1: 
          case 2: 
          case 5: 
          case 10: 
            n = 1;
            move(findEmptyCel(lines[m]), 1);
          }
          m = (m + 1) % sums.length;
        }
        if (n == 0)
        {
          System.out.println("Game is a draw.");
          j = 1;
        }
      }
    }
  }
}
