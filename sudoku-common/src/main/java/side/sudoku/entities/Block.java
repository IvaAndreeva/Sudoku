package side.sudoku.entities;

import side.sudoku.initializers.CellInitializer;

public class Block extends Entity {
	private static final long serialVersionUID = -2272913000724439595L;
	public Coordinate locationOnBoard;
	public EntityGrid<Cell> cellsGrid;

	public Block() {
	}

	public Block(Coordinate locationOnBoard, Size cellsXcellsSize) {
		this.locationOnBoard = locationOnBoard;
		this.cellsGrid = CellInitializer
				.initializeCellsWithSize(cellsXcellsSize);
	}

	public Cell getCellOnLocation(Coordinate location) {
		return cellsGrid.getEntityOnLocation(location);
	}

	public void setCellOnLocation(Cell cell, Coordinate locationInBlock) {
		cellsGrid.setEntityOnLocation(cell, locationInBlock);
	}

	@Override
	public String toString() {
		return cellsGrid.toString();
	}

	public String getCellRowAsString(int row) {
		return cellsGrid.get(row).toString();
	}

	public boolean contains(Object value) {
		for (EntityRow<Cell> row : cellsGrid) {
			for (Cell cell : row) {
				if (cell.isValueCorrect(value)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean contains(Object value, Cell excludeCell) {
		for (EntityRow<Cell> row : cellsGrid) {
			for (Cell cell : row) {
				if (cell != excludeCell && cell.getValueToShow().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected Block clone() {
		Block block = new Block();
		block.locationOnBoard = locationOnBoard.clone();
		block.cellsGrid = cellsGrid.clone();
		return block;
	}
}
