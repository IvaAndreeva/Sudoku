package side.sudoku.generators;

import side.sudoku.entities.Block;
import side.sudoku.entities.Board;
import side.sudoku.entities.Cell;
import side.sudoku.entities.Coordinate;

public class SudokuValuesGenerator {
	public static void generateValuesOnBoardWithDifficulty(Board board,
			int difficulty) {
		System.out.println("Generating ...");
		for (int blockRow = 0; blockRow < board.size.blocksXblocks.horizontalCount; blockRow++) {
			for (int blockColumn = 0; blockColumn < board.size.blocksXblocks.verticalCount; blockColumn++) {
				generateBlock(board,
						board.blocksGrid.get(blockRow).get(blockColumn));
			}
		}
	}

	private static void generateBlock(Board board, Block block) {
		int i = 1;
		boolean canChange = false;
		for (int cellRow = 0; cellRow < board.size.cellsXcells.horizontalCount; cellRow++) {
			for (int cellColumn = 0; cellColumn < board.size.cellsXcells.verticalCount; cellColumn++, i++) {
				Cell newCell = new Cell(new Integer(i), canChange);
				block.setCellOnLocation(newCell, new Coordinate(cellRow,
						cellColumn));
				newCell.setCanChange(canChange);
				canChange = !canChange;
			}
		}
	}
}
