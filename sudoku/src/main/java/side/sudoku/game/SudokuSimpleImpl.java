package side.sudoku.game;

import side.sudoku.events.EventBus;
import side.sudoku.events.EventHanlersGenerator;
import side.sudoku.generators.SudokuNumbersGenerator;
import side.sudoku.handlers.GuessHandler;
import side.sudoku.initializers.BoardInitializer;

public class SudokuSimpleImpl extends Sudoku {
	private static final long serialVersionUID = 6528869698486003039L;

	@Override
	public Sudoku create(int verticalBlockNumber, int horizontalBlockNumber,
			int verticalCellNumber, int horizontalCellNumber, int difficulty) {
		board = BoardInitializer
				.initializeBoard(verticalBlockNumber, horizontalBlockNumber,
						verticalCellNumber, horizontalCellNumber);
		board = SudokuNumbersGenerator.generateValuesOnBoardWithDifficulty(board,
				difficulty);
		guessHandler = new GuessHandler();
		eventBus = new EventBus();
		EventHanlersGenerator.hookHandlers(eventBus);
		return this;
	}
}
