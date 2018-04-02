package sudoku;

import java.util.*;

public class Grid {
	// instance variable values
	// values represents 9 x 9 grid
  private int[][]  values;

  /**
   * Construct an object Grid
   * Transform a string array into two dimensional int array
   * Note that the Object must have a two dimensional array instance named values
   * @param  rows string array
   */
  public Grid(String[] rows) {
    // construct two dimensional int array size of 9 x 9
    values = new int[9][9];
    for (int j = 0; j < 9; j++) {
    	// copy a string in the index of j
      String row = rows[j];
      // convert and copy string to a character array
      char[] charray = row.toCharArray();
      for (int i = 0; i < 9; i++) {
      	// copy a char in the index of i
        char ch = charray[i];
        if (ch != '.')
          values[j][i] = ch - '0';
          // int value is assigned
          // int value = '3' - '0';
          // Then, the variable value stores 3 as its value
      }
    }
  }

  /**
   * convert two dimensional int array into string and
   * returns a string converted from int array
   * Note that the Object must have a two dimensional array instance named values
   * @return s a string converted from the two dimensional int array
   */
  public String toString() {
    String s = "";
    for (int j = 0; j < 9; j++) {
      for (int i = 0; i < 9; i++) {
        int n = values[j][i];
        if (n == 0)
          s += '.';
        else
          s += (char)('0' + n);
      }
      s += "\n";
    }
    return s;
  }
  
  /**
   * Copy constructor. Duplicate its values
   * This will be called 9 times in next9Grids() method
   * @param  src existing Grid object
   */
  Grid(Grid src) {
    values = new int[9][9];
    for (int j = 0; j < 9; j++)
      for (int i = 0; i < 9; i++)
        values[j][i] = src.values[j][i];
  }


  //
  //
  //
  // Finds an empty member of values[][].
  // Returns an array list of 9 grids that look like the current grid,
  // except the empty member contains 1, 2, 3 .... 9.
  // Returns null if the current grid is full.
  public ArrayList<Grid> next9Grids() {
    int xOfNextEmptyCell = -1;
    int yOfNextEmptyCell = -1;

    // Find x,y of an empty cell.
    if (this.isFull())
      return null;
    else {
      for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
          if (this.values[i][j] == 0) {
            xOfNextEmptyCell = i;
            yOfNextEmptyCell = j;
            i = 9;
            j = 9;
          }
        }
      }
    }
    // Construct array list to contain 9 new grids.
    ArrayList<Grid> grids = new ArrayList<Grid>();
    for (int i = 0; i < 9; i++) {
      grids.add(new Grid(this));
    }
    // Create 9 new grids as described in the comments above. Add them to grids.
    for (int i = 0; i < 9; i++) {
      grids.get(i).values[xOfNextEmptyCell][yOfNextEmptyCell] = i + 1;
    }
    return grids;
  }


  /**
   * Checks every rows, columns, and blocks are legal
   * Row, column, or block is legal when there is no repeated number between 1 - 9
   * Note that the Object must have a two dimensional array instance named values
   */
  public boolean isLegal() {
    int count4row    = 0;
    int count4column = 0;
    int count4block  = 0;
    // Check every row. If you find an illegal row, return false.
    int[] ithRow = new int[9];
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        ithRow[j] = values[i][j];
      }
      if (containsNonZeroRepeat(ithRow) == true)
        count4row++;
    }
    // Check every column. If you find an illegal column, return false.
    int[] ithColumn = new int[9];
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        ithColumn[j] = values[j][i];
      }
      if (containsNonZeroRepeat(ithColumn) == true)
        count4column++;
    }
    // Check every block. If you find an illegal block, return false.
    int[] block = new int[9];
    for (int i = 3; i <= 9; i = i + 3) {
      for (int j = 3; j <= 9; j = j + 3) {
        block = TwoDArrayToLinear(i, j);
        if (containsNonZeroRepeat(block) == true)
          count4block++;
      }
    }
    // All rows/columns/blocks are legal.
    return ((count4row == 9) && (count4column == 9) && (count4block == 9)) ? true : false;
  }


  /**
   * linearizes a two dimensional array that is 3 x 3 grid into an one dimensional array
   * ---
   * Transform a two dimensional grid into a linear array.
   * Note that the Object must have a two dimensional array instance named values.
   * ---
   * @param  offsetRow offset of row to extract from the values
   * @param  offsetCol offset of column to extract from the values
   * @return           a linear array
   */
  public int[] TwoDArrayToLinear(int offsetRow, int offsetCol) {
    // declare an array linearized, size of 9
    int[] linearArray = new int[9];
    int index = 0;

    for (int row = offsetRow - 3; row < offsetRow; row++) {
      for (int col = offsetCol - 3; col < offsetCol; col++) {
        // copy the value at [row][col] position
        // to the linearArray
        linearArray[index] = this.values[row][col];
        index++;
      }
    }
    return linearArray;
  }

  // cost can be saved by using Set
  public boolean containsNonZeroRepeat(int[] value) {
    // int count = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = i + 1; j < 9; j++) {
        if (value[i] == value[j] && value[i] > 0 && value[j] > 0)
        return false;
      }
    }
    return true;
  }

  //
  //
  // Returns true if every cell member of values[][] is a digit from 1-9.
  //
  public boolean isFull() {
    boolean isFull = false;
    for(int i = 0; i < 9; i++) {
      if (values[i][0] < 1 || values[i][0] > 9)
        return false;
      for(int j = 0; j < 9; j++) {
        if (values[i][j] < 1 || values[i][j] > 9)
          return false;
      }
    }
    return true;
  }


  //
  //
  // Returns true if x is a Grid and, for every (i,j),
  // x.values[i][j] == this.values[i][j].
  //
  public boolean equals(Object x) {
    boolean equiv = false;
    int countEqual = 0;
    Grid that = (Grid)x;

    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (this.values[i][j] == that.values[i][j])
          countEqual++;
      }
    }
    if (countEqual == 81)
      equiv = true;
    return equiv;
  }

  /**
   * main method to test the current class Grid
   */
  public static void main(String[] args) {
    Grid grid1 = new Grid(TestGridSupplier.getSolution1());
    Grid grid2 = new Grid(TestGridSupplier.getPuzzle2());
    System.out.println("Is grid1 full? " + grid1.isFull());
    System.out.println("Is grid2 full? " + grid2.isFull());
  }
}
