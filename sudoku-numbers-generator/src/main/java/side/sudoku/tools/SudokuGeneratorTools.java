package side.sudoku.tools;

import java.util.List;

import side.sudoku.entities.Block;
import side.sudoku.entities.Cell;
import side.sudoku.entities.EntityRow;

public class SudokuGeneratorTools {
	public static Integer[] AVAILABLE_VALUES;

	public static int getRandomIndex(Integer[] list) {
		return getRandomIndexMax(list.length);
	}

	public static int getRandomIndexMax(int max) {
		return (int) (Math.random() * max);
	}

	public static boolean valueSatisfiesRules(EntityRow<Cell> row,
			List<Cell> column, Block block, Object value) {
		return !(listContainsValue(row, value)
				|| listContainsValue(column, value) || block.contains(value));
	}

	public static boolean valueSatisfiesRulesExcludeCheckingCell(
			EntityRow<Cell> row, List<Cell> column, Block block, Object value,
			Cell checkingCell) {
		return !(listContainsValue(row, value, checkingCell)
				|| listContainsValue(column, value, checkingCell) || block
					.contains(value, checkingCell));
	}

	public static boolean listContainsValue(List<Cell> list, Object value) {
		for (Cell cell : list) {
			if (cell.isValueCorrect(value)) {
				return true;
			}
		}
		return false;
	}

	public static boolean listContainsValue(List<Cell> list, Object value,
			Cell excludeCell) {
		for (Cell cell : list) {
			if (cell != excludeCell && cell.getValueToShow().equals(value)) {
				return true;
			}
		}
		return false;
	}

	public static void generateAvailableValues(int size) {
		AVAILABLE_VALUES = new Integer[size];
		for (int i = 1; i <= size; i++) {
			AVAILABLE_VALUES[i - 1] = i;
		}
	}
}
