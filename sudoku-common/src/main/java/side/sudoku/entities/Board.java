package side.sudoku.entities;

import java.util.ArrayList;
import java.util.List;

import side.sudoku.initializers.BlockInitializer;

public class Board extends Entity {
	private static final long serialVersionUID = 1890146531708613632L;
	public SudokuSize size;
	public EntityGrid<Block> blocksGrid;

	public Board() {
	}

	public Board(Size blocksXblocksSize, Size cellsXcellsSize) {
		size = new SudokuSize(blocksXblocksSize.verticalCount,
				blocksXblocksSize.horizontalCount,
				cellsXcellsSize.verticalCount, cellsXcellsSize.horizontalCount);
		this.blocksGrid = BlockInitializer.initializeBlocksWithSize(
				blocksXblocksSize, cellsXcellsSize);
	}

	public Block getBlockOnLocation(Coordinate locationOnBoard) {
		return blocksGrid.get(
				locationOnBoard.x / size.cellsXcells.horizontalCount).get(
				locationOnBoard.y / size.cellsXcells.verticalCount);
	}

	public Cell getCellOnLocation(Coordinate locationOnBoard) {
		Block blockHoldingTheCell = getBlockOnLocation(locationOnBoard);
		return blockHoldingTheCell.getCellOnLocation(new Coordinate(
				locationOnBoard.x % size.cellsXcells.horizontalCount,
				locationOnBoard.y % size.cellsXcells.verticalCount));
	}

	public EntityGrid<Cell> getTablesOfCells() {
		EntityGrid<Cell> cellGrid = new EntityGrid<Cell>();
		for (EntityRow<Block> blockRow : blocksGrid) {
			for (int i = 0; i < size.cellsXcells.horizontalCount; i++) {
				EntityRow<Cell> cellRow = new EntityRow<Cell>();
				for (Block block : blockRow) {
					cellRow.addAll(block.cellsGrid.get(i));
				}
				cellGrid.add(cellRow);
			}
		}
		return cellGrid;
	}

	public List<Cell> getColumn(int columnInd) {
		List<Cell> column = new ArrayList<Cell>();
		for (EntityRow<Cell> row : getTablesOfCells()) {
			column.add(row.get(columnInd));
		}
		return column;
	}

	public boolean hasEmptyCell() {
		for (EntityRow<Cell> row : getTablesOfCells()) {
			for (Cell cell : row) {
				if (!cell.isGuessedCorrectly()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean thereIsEmptyCell() {
		for (EntityRow<Cell> row : getTablesOfCells()) {
			for (Cell cell : row) {
				if (cell.getValueToShow().equals(Cell.INITIAL_VALUE)
						|| cell.getValueToShow().equals("")) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		for (int blockRow = 0; blockRow < size.blocksXblocks.horizontalCount; blockRow++) {
			for (int cellRow = 0; cellRow < size.cellsXcells.horizontalCount; cellRow++) {
				for (int blockColumn = 0; blockColumn < size.blocksXblocks.verticalCount; blockColumn++) {
					strBuff.append(blocksGrid.get(blockRow).get(blockColumn)
							.getCellRowAsString(cellRow));
					strBuff.append("\t");
				}
				strBuff.append("\n");
			}
			strBuff.append("\n");
		}
		return strBuff.toString();
	}

	public Size getWholeSize() {
		return new Size(size.blocksXblocks.verticalCount
				* size.cellsXcells.verticalCount,
				size.blocksXblocks.horizontalCount
						* size.cellsXcells.horizontalCount);
	}

	public Board clone() {
		Board board = new Board();
		board.blocksGrid = blocksGrid.clone();
		board.size = size.clone();
		return board;
	}

	public List<Cell> getEmptyCells() {
		List<Cell> emptyCells = new ArrayList<Cell>();
		for (EntityRow<Cell> row : getTablesOfCells()) {
			for (Cell cell : row) {
				if (!cell.isGuessedCorrectly()) {
					emptyCells.add(cell);
				}
			}
		}
		return emptyCells;
	}

	public int getEmptyCellsCount() {
		int count = 0;
		for (EntityRow<Cell> row : getTablesOfCells()) {
			for (Cell cell : row) {
				if (!cell.isGuessedCorrectly()) {
					count++;
				}
			}
		}
		return count;
	}
}
