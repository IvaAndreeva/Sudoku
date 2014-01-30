package side.sudoku.initializers;

import side.sudoku.entities.Block;
import side.sudoku.entities.Coordinate;
import side.sudoku.entities.EntityGrid;
import side.sudoku.entities.EntityRow;
import side.sudoku.entities.Size;

public class BlockInitializer {

	public static EntityGrid<Block> initializeBlocksWithSize(Size size,
			Size cellsXcellsSize) {
		EntityGrid<Block> result = new EntityGrid<Block>();
		for (int row = 0; row < size.horizontalCount; row++) {
			EntityRow<Block> rowBlocks = new EntityRow<Block>();
			for (int column = 0; column < size.verticalCount; column++) {
				rowBlocks.add(new Block(new Coordinate(row, column),
						cellsXcellsSize));
			}
			result.add(rowBlocks);
		}
		return result;
	}
}
