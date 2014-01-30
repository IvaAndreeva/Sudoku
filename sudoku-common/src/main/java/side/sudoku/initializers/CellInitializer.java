package side.sudoku.initializers;

import side.sudoku.entities.Cell;
import side.sudoku.entities.EntityGrid;
import side.sudoku.entities.EntityRow;
import side.sudoku.entities.Size;

public class CellInitializer {
	public static EntityGrid<Cell> initializeCellsWithSize(Size size) {
		EntityGrid<Cell> result = new EntityGrid<Cell>();
		for (int row = 0; row < size.horizontalCount; row++) {
			EntityRow<Cell> rowCells = new EntityRow<Cell>();
			for (int column = 0; column < size.verticalCount; column++) {
				rowCells.add(new Cell(column, true));
			}
			result.add(rowCells);
		}
		return result;
	}
}
