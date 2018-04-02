package sudoku;

import java.util.*;


public class Solver {
	// instance variables
  private Grid             problem;
  private ArrayList<Grid>  solutions;

  /**
   * constructs an object Solver and
   * initialize its instance variable problem with the given variable
   * @param  problem Grid variable problem
   */
  public Solver(Grid problem) {
    this.problem = problem;
  }

  /**
   * concstrucs an object Solver,
   * initialize its instance variable solutions with an empty ArrayList, and
   * call an method solveRecure() by passing its instance variable problem
   */
  public void solve() {
    solutions = new ArrayList<>();
    solveRecurse(problem);
  }

  /**
   * standard backtracking recursive solver
   * @param grid Grid object
   */
  private void solveRecurse(Grid grid) {
    Evaluation eval = evaluate(grid);

    if (eval == Evaluation.ABANDON) {
      // Abandon evaluation of this illegal board.
      return;
    }
    else if (eval == Evaluation.ACCEPT) {
      // A complete and legal solution. Add it to solutions.
      solutions.add(grid);
    }
    else {
      // Here if eval == Evaluation.CONTINUE. Generate all 9 possible next grids.
      // Recursively call solveRecurse() on those grids.
      ArrayList<Grid> grids = new ArrayList<Grid>(grid.next9Grids());
      for(Grid gridMember : grids) {
        solveRecurse(gridMember);
      }

    }
  }

  /**
   * evaluate grid depending on Evaluation enum
   * @param  grid [description]
   * @return ABANDON if the grid is illegal
   *         ACCEPT if the grid is legal and complete
   *         CONTINUE if the grid is legal and imcomplete
   */
  public Evaluation evaluate(Grid grid) {
    if (!grid.isLegal())
      return Evaluation.ABANDON;
    else if (grid.isLegal() && grid.isFull())
      return Evaluation.ACCEPT;
    else
      return Evaluation.CONTINUE;
  }

  /**
   * get solutions that the current class Solver contains
   * @return solutions
   */
  public ArrayList<Grid> getSolutions() {
    return solutions;
  }

  /**
   * tests to see if the current class Solver works
   */
  public static void main(String[] args) {
    Grid g = TestGridSupplier.getPuzzle1();    // or any other puzzle
    Solver solver = new Solver(g);
    System.out.println("Will solve\n" + g);
    solver.solve();
    for (int i = 0; i < solver.getSolutions().size(); i++) {
      System.out.println(solver.getSolutions().get(i));
    }
  }
}
