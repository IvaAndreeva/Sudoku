package side.sudoku.initializers;

import side.sudoku.entities.Board;
import side.sudoku.entities.Size;

public class BoardInitializer {
	public static Board initializeBoard(int verticalBlockNumber,
			int horizontalBlockNumber, int verticalCellNumber,
			int horizontalCellNumber) {
		return new Board(new Size(verticalBlockNumber, horizontalBlockNumber),
				new Size(verticalCellNumber, horizontalCellNumber));
	}
}
