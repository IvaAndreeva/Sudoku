package side.sudoku.generators;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import side.sudoku.entities.Block;
import side.sudoku.entities.Board;
import side.sudoku.entities.Cell;
import side.sudoku.entities.Coordinate;
import side.sudoku.entities.EntityRow;
import side.sudoku.subtractors.SudokuNumberSubtractor;

public class SudokuNumbersGenerator {
	private static Integer[] AVAILABLE_VALUES;
	private static Map<Cell, List<Integer>> cellToAlreadyTriedValues = new HashMap<Cell, List<Integer>>();

	public static Board generateValuesOnBoardWithDifficulty(Board board,
			int difficulty) {
		generateAvailableValues(board.size.cellsXcells.horizontalCount
				* board.size.cellsXcells.verticalCount);
		System.out.println("Generating ...");
		long time = (new Date()).getTime();
		while (!boardPassTest(board)) {
			System.out.println("Initializing ...");
			initializeBoard(board);
			System.out.println("Generating values ...");
			generateValues(board);
		}
		System.out.println("Subtracting cells...");
		board = SudokuNumberSubtractor
				.subtractCellsFromBoard(board, difficulty);
		System.out.println("Took "
				+ ((double) ((new Date()).getTime() - time) / 1000)
				+ " seconds");
		return board;
	}

	private static void generateAvailableValues(int size) {
		AVAILABLE_VALUES = new Integer[size];
		for (int i = 1; i <= size; i++) {
			AVAILABLE_VALUES[i - 1] = i;
		}
	}

	private static boolean boardPassTest(Board board) {
		return !board.thereIsEmptyCell() && !rowsAreMissingValues(board)
				&& !columnsAreMissingValues(board)
				&& !blocksAreMissingValues(board);
	}

	public static boolean rowsAreMissingValues(Board board) {
		for (EntityRow<Cell> row : board.getTablesOfCells()) {
			if (!listContainsAllAvailableValues(row)) {
				return true;
			}
		}
		return false;
	}

	public static boolean columnsAreMissingValues(Board board) {
		for (int column = 0; column < board.getWholeSize().verticalCount; column++) {
			if (!listContainsAllAvailableValues(board.getColumn(column))) {
				return true;
			}
		}
		return false;
	}

	public static boolean blocksAreMissingValues(Board board) {
		for (EntityRow<Block> blockRow : board.blocksGrid) {
			for (Block block : blockRow) {
				if (!blockContainsAllAvailableValues(block)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean blockContainsAllAvailableValues(Block block) {
		List<Cell> allCellsInBlock = new ArrayList<Cell>();
		for (EntityRow<Cell> row : block.cellsGrid) {
			allCellsInBlock.addAll(row);
		}
		if (!listContainsAllAvailableValues(allCellsInBlock)) {
			return false;
		}
		return true;
	}

	private static boolean listContainsAllAvailableValues(List<Cell> list) {
		for (Integer value : AVAILABLE_VALUES) {
			boolean listContainsValue = false;
			for (Cell cell : list) {
				if (cell.getValueToShow().toString().equals(value.toString())) {
					listContainsValue = true;
					break;
				}
			}
			if (!listContainsValue) {
				return false;
			}
		}
		return true;
	}

	private static void initializeBoard(Board board) {
		for (int blockRow = 0; blockRow < board.size.blocksXblocks.horizontalCount; blockRow++) {
			for (int blockColumn = 0; blockColumn < board.size.blocksXblocks.verticalCount; blockColumn++) {
				initializeBlock(board,
						board.blocksGrid.get(blockRow).get(blockColumn));
			}
		}
	}

	private static void initializeBlock(Board board, Block block) {
		boolean isHidden = false;
		for (int cellRow = 0; cellRow < board.size.cellsXcells.horizontalCount; cellRow++) {
			for (int cellColumn = 0; cellColumn < board.size.cellsXcells.verticalCount; cellColumn++) {
				Cell newCell = new Cell(Cell.INITIAL_VALUE, isHidden,
						new Coordinate(cellRow + block.locationOnBoard.x
								* board.size.cellsXcells.horizontalCount,
								cellColumn + block.locationOnBoard.y
										* board.size.cellsXcells.verticalCount));
				block.setCellOnLocation(newCell, new Coordinate(cellRow,
						cellColumn));
				newCell.setCanChange(false);
			}
		}
	}

	private static void generateValues(Board board) {
		for (int rowInd = 0; rowInd < board.getTablesOfCells().size(); rowInd++) {
			EntityRow<Cell> row = board.getTablesOfCells().get(rowInd);
			for (int columnInd = 0; columnInd < row.size(); columnInd++) {
				generateValueRecursive(board, rowInd, columnInd);
			}
		}
	}

	private static void generateValueRecursive(Board board, int rowInd,
			int columnInd) {
		EntityRow<Cell> row = board.getTablesOfCells().get(rowInd);
		List<Cell> column = board.getColumn(columnInd);
		Coordinate location = new Coordinate(rowInd, columnInd);
		Block block = board.getBlockOnLocation(location);
		Cell cell = board.getCellOnLocation(location);

		Object valueGenerated = getAcceptableValue(cell, row, column, block);
		while (valueGenerated.equals(-1)) {
			Coordinate oldCoordinate = getPrevCoord(board, rowInd, columnInd);
			revertCell(board, oldCoordinate, row, column, block);
			generateValueRecursive(board, oldCoordinate.x, oldCoordinate.y);

			valueGenerated = getAcceptableValue(cell, row, column, block);
		}
		addToAlreadyTriedValues(cell, valueGenerated);
	}

	private static Object getAcceptableValue(Cell cell, EntityRow<Cell> row,
			List<Cell> column, Block block) {
		Integer[] availableValuesForCell = getAvailableForCell(cell);
		int randomInd = getRandomIndex(availableValuesForCell);
		Object randomValue = availableValuesForCell[randomInd];
		int valuesIterated = 1;
		while (!valueSatisfiesRules(row, column, block, randomValue)
				&& valuesIterated <= availableValuesForCell.length) {
			randomInd = getRandomIndex(availableValuesForCell);
			randomValue = availableValuesForCell[randomInd];
			valuesIterated++;
		}
		if (valueSatisfiesRules(row, column, block, randomValue)) {
			cell.setValueOnlyWhenInitial(randomValue);
			return randomValue;
		} else {
			return -1;
		}
	}

	private static Integer[] getAvailableForCell(Cell cell) {
		if (cellToAlreadyTriedValues.get(cell) == null) {
			return AVAILABLE_VALUES;
		}
		List<Integer> alreadyTriedValues = cellToAlreadyTriedValues.get(cell);
		List<Integer> result = new ArrayList<Integer>();
		for (Integer value : AVAILABLE_VALUES) {
			if (!alreadyTriedValues.contains(value)) {
				result.add(value);
			}
		}
		return result.toArray(new Integer[result.size()]);
	}

	private static int getRandomIndex(Integer[] list) {
		return (int) (Math.random() * list.length);
	}

	private static boolean valueSatisfiesRules(EntityRow<Cell> row,
			List<Cell> column, Block block, Object value) {
		return !(listContainsValue(row, value)
				|| listContainsValue(column, value) || block.contains(value));
	}

	private static boolean listContainsValue(List<Cell> list, Object value) {
		for (Cell cell : list) {
			if (cell.isValueCorrect(value)) {
				return true;
			}
		}
		return false;
	}

	private static Coordinate getPrevCoord(Board board, int row, int column) {
		if (column == 0) {
			return new Coordinate(row - 1,
					board.getWholeSize().horizontalCount - 1);
		} else {
			return new Coordinate(row, column - 1);
		}
	}

	private static void revertCell(Board board, Coordinate coordinate,
			EntityRow<Cell> row, List<Cell> column, Block block) {
		Cell cellToRevert = board.getCellOnLocation(coordinate);
		cellToRevert.resetValue();
		resetTriedValuesForNextCells(board, coordinate);
	}

	private static void resetTriedValuesForNextCells(Board board,
			Coordinate coordinate) {
		Coordinate nextCoord = getNextCoord(board, coordinate.x, coordinate.y);
		for (int row = nextCoord.x; row < board.getWholeSize().horizontalCount; row++) {
			for (int column = nextCoord.y; column < board.getWholeSize().verticalCount; column++) {
				Cell cellToUpdate = board.getCellOnLocation(new Coordinate(row,
						column));
				cellToAlreadyTriedValues.remove(cellToUpdate);
			}
		}
	}

	private static Coordinate getNextCoord(Board board, int row, int column) {
		if (column == board.getWholeSize().horizontalCount - 1) {
			return new Coordinate(row + 1, 0);
		} else {
			return new Coordinate(row, column + 1);
		}
	}

	private static void addToAlreadyTriedValues(Cell cell, Object valueGenerated) {
		if (cellToAlreadyTriedValues.get(cell) == null) {
			cellToAlreadyTriedValues.put(cell, new ArrayList<Integer>());
		}
		cellToAlreadyTriedValues.get(cell).add(
				Integer.valueOf(valueGenerated.toString()));
	}
}