package sudoku;

import java.util.*;


public class Grid
{
	private int[][]						values;


	//
	//
	// Constructs a Grid instance from a string[] as provided by TestGridSupplier.
	// See TestGridSupplier for examples of input.
	// Dots in input strings represent 0s in values[][].
	//
	public Grid(String[] rows)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
		{
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i=0; i<9; i++)
			{
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
					// int value is assigned
					// int value = '3' - '0';
					// Then, the variable value stores 3 as its value
			}
		}
	}


	public String toString()
	{
		String s = "";
		for (int j=0; j<9; j++)
		{
			for (int i=0; i<9; i++)
			{
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


	//
	// Copy ctor. Duplicates its source. call this 9 times in next9Grids.
	//
	Grid(Grid src)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				values[j][i] = src.values[j][i];
	}


	//
	//
	//
	// Finds an empty member of values[][].
	// Returns an array list of 9 grids that look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9.
	// Returns null if the current grid is full.
	// Don’t change “this” grid. Build 9 new grids.
	//
	//
	// Example: if this grid = 1........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//
	// Then the returned array list would contain:
	//
	// 11.......          12.......          13.......          14.......    and so on     19.......
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	//
	public ArrayList<Grid> next9Grids()
	{
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


	//
	// COMPLETE THIS
	//
	// Returns true if this grid is legal. A grid is legal if no row, column, or
	// 3x3 block contains a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	//
	public boolean isLegal()
	{
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
				block = nthBlockToLinear(i, j);
				if (containsNonZeroRepeat(block) == true)
					count4block++;
			}
		}
		// All rows/columns/blocks are legal.
		return ((count4row == 9) && (count4column == 9) && (count4block == 9)) ? true : false;
	}


	 public int[] nthBlockToLinear(int r, int c) {
		int[] block = new int[9];
		int i = 0;
		for (int j = r - 3; j < r; j++) {
			for (int k = c - 3; k < c; k++) {
				block[i] = values[j][k];
				i++;
			}
		}
		return block;
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
	public boolean isFull()
	{
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
	public boolean equals(Object x)
	{
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

	public static void main(String[] args) {
		Grid grid1 = new Grid(TestGridSupplier.getSolution1());
		Grid grid2 = new Grid(TestGridSupplier.getPuzzle2());
		System.out.println("Is grid1 full? " + grid1.isFull());
		System.out.println("Is grid2 full? " + grid2.isFull());
//		System.out.println(grid1.equals(grid2));
//		System.out.println(grid1.equals(grid1));
//		System.out.println();
//		System.out.println(grid1.isLegal());
//		System.out.println(grid2.next9Grids().get(8));
//		System.out.println(grid2.next9Grids().get(1));
//		for (Grid member : grid2.next9Grids()) {
//			for (int i = 0; i < 9; i++) {
//				for (int j = 0; j < 9; j++) {
//					System.out.print(member.values[i][j]);
//				}
//				System.out.println();
//			}
//			System.out.println();
//			System.out.println();
//		}
	}
}
