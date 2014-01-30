package side.sudoku.entities;

public class SudokuSize extends Entity {
	private static final long serialVersionUID = 6093263046097531537L;
	public Size blocksXblocks;
	public Size cellsXcells;

	public SudokuSize() {
	}

	public SudokuSize(int verticalBlockNumber, int horizontalBlockNumber,
			int verticalCellNumber, int horizontalCellNumber) {
		blocksXblocks = new Size(verticalBlockNumber, horizontalBlockNumber);
		cellsXcells = new Size(verticalCellNumber, horizontalCellNumber);
	}

	@Override
	public String toString() {
		return String.valueOf(blocksXblocks.horizontalCount
				* cellsXcells.horizontalCount)
				+ " x "
				+ String.valueOf(blocksXblocks.verticalCount
						* cellsXcells.verticalCount);
	}

	@Override
	protected SudokuSize clone() {
		SudokuSize size = new SudokuSize();
		size.blocksXblocks = blocksXblocks.clone();
		size.cellsXcells = cellsXcells.clone();
		return size;
	}
}
