package side.sudoku.solvers;

import java.util.List;
import java.util.ArrayList;

import side.sudoku.entities.Block;
import side.sudoku.entities.Board;
import side.sudoku.entities.Cell;
import side.sudoku.entities.EntityRow;
import side.sudoku.tools.SudokuGeneratorTools;

public class SudokuSolver {
	public static boolean solutionIsUnique(Board board) {
		SudokuGeneratorTools
				.generateAvailableValues(board.size.cellsXcells.verticalCount
						* board.size.cellsXcells.horizontalCount);
		Board workingBoard = board.clone();
		return solve(workingBoard);
	}

	private static boolean solve(Board board) {
		int guessesOnCellsWithoutCorrectGuess = 0;
		int unknownCellsCount = board.getEmptyCellsCount();
		while (board.hasEmptyCell()
				&& guessesOnCellsWithoutCorrectGuess != unknownCellsCount) {
			List<Cell> emptyCells = board.getEmptyCells();
			Cell nextEmpty = emptyCells.get(SudokuGeneratorTools
					.getRandomIndexMax(emptyCells.size()));
			List<Integer> fittingValues = getFittingValuesForCell(board,
					nextEmpty);
			if (fittingValues.size() == 1) {
				nextEmpty.updateIfCorrect(fittingValues.get(0));
				guessesOnCellsWithoutCorrectGuess = 0;
				unknownCellsCount = board.getEmptyCellsCount();
			} else {
				guessesOnCellsWithoutCorrectGuess++;
			}
		}
		return !board.hasEmptyCell();
	}

	private static List<Integer> getFittingValuesForCell(Board board, Cell cell) {
		List<Integer> fits = new ArrayList<Integer>();
		EntityRow<Cell> row = board.getTablesOfCells().get(
				cell.locationOnBoard.x);
		List<Cell> column = board.getColumn(cell.locationOnBoard.y);
		Block block = board.getBlockOnLocation(cell.locationOnBoard);
		for (Integer value : SudokuGeneratorTools.AVAILABLE_VALUES) {
			if (SudokuGeneratorTools.valueSatisfiesRulesExcludeCheckingCell(
					row, column, block, value, cell)) {
				fits.add(value);
			}
		}
		return fits;
	}

	public static boolean solutionMatchesDifficulty(Board board, int difficulty) {
		System.out.println("Checking for difficulty ...");
		Board workingBoard = board.clone();
		int cellsThatCouldBeGuessedRigthAwayCount = 0;
		int allCellsCount = workingBoard.getWholeSize().horizontalCount
				* workingBoard.getWholeSize().verticalCount;
		int maxCountGuessableCells = getMaxCountGuessableCells(allCellsCount,
				difficulty);
		int minCountGuessableCells = getMinCountGuessableCells(allCellsCount,
				difficulty);
		int roundsWithLessGuessableCells = workingBoard.getEmptyCellsCount() / 5;
		for (int i = 0; i < roundsWithLessGuessableCells; i++) {
			List<Cell> emptyCells = workingBoard.getEmptyCells();
			cellsThatCouldBeGuessedRigthAwayCount = 0;
			for (Cell cell : emptyCells) {
				List<Integer> fittingValues = getFittingValuesForCell(
						workingBoard, cell);
				if (fittingValues.size() == 1) {
					cellsThatCouldBeGuessedRigthAwayCount++;
				}
			}

			System.out.println("Max: " + maxCountGuessableCells + ":Min:"
					+ minCountGuessableCells + ":"
					+ cellsThatCouldBeGuessedRigthAwayCount + ":"
					+ emptyCells.size());
			if (cellsThatCouldBeGuessedRigthAwayCount > maxCountGuessableCells
					|| cellsThatCouldBeGuessedRigthAwayCount < minCountGuessableCells) {
				return false;
			}

			Cell nextEmpty = emptyCells.get(SudokuGeneratorTools
					.getRandomIndexMax(emptyCells.size()));
			solveCell(nextEmpty);
		}
		return true;
	}

	private static void solveCell(Cell cell) {
		for (Integer value : SudokuGeneratorTools.AVAILABLE_VALUES) {
			if (cell.isValueCorrect(value)) {
				cell.updateIfCorrect(value);
				return;
			}
		}
	}

	private static int getMaxCountGuessableCells(int allCellsCount,
			int difficulty) {
		switch (difficulty) {
		case 1:
			return allCellsCount / 7 + 1;
		case 2:
			return allCellsCount / 10 + 1;
		case 3:
			return 4;
		default:
			return allCellsCount / 7 + 1;
		}
	}

	private static int getMinCountGuessableCells(int allCellsCount,
			int difficulty) {
		switch (difficulty) {
		case 1:
			return allCellsCount / 30 + 1;
		case 2:
			return allCellsCount / 35 + 1;
		case 3:
			return 1;
		default:
			return allCellsCount / 30 + 1;
		}
	}
}
