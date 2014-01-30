package side.sudoku.game;

import java.io.Serializable;
import java.util.Arrays;

import side.sudoku.entities.Board;
import side.sudoku.entities.Cell;
import side.sudoku.entities.Coordinate;
import side.sudoku.entities.EntityGrid;
import side.sudoku.entities.Size;
import side.sudoku.entities.SudokuSize;
import side.sudoku.events.EventBus;
import side.sudoku.events.EventName;
import side.sudoku.exceptions.SudokuException;
import side.sudoku.exceptions.SudokuTooManyGuessesOnLocation;
import side.sudoku.handlers.GuessHandler;

public abstract class Sudoku implements Serializable {
	private static final long serialVersionUID = -5064165320541011481L;
	public int id;
	public boolean gameOver = false;
	public boolean gameFinished = false;
	protected GuessHandler guessHandler;
	protected Board board;
	public EventBus eventBus;

	private static final int MAX_GUESSES_ON_CELL = 2;

	public abstract Sudoku create(int verticalBlockNumber,
			int horizontalBlockNumber, int verticalCellNumber,
			int horizontalCellNumber, int difficulty);

	public Sudoku create(SudokuSize size, int difficulty) {
		return create(size.blocksXblocks.verticalCount,
				size.blocksXblocks.horizontalCount,
				size.cellsXcells.verticalCount,
				size.cellsXcells.horizontalCount, difficulty);
	}

	public String showBoard() {
		return board.toString();
	}

	public void guessValueOnLocation(Object value, int x, int y)
			throws SudokuException {
		validateGuessCountOnCell(new Coordinate(x, y));
		Cell cellToGuess = board.getCellOnLocation(new Coordinate(x, y));
		boolean guessedRight = guessHandler.isGuessCorrectForCellOnBoard(value,
				cellToGuess);
		eventBus.triggerEvent(EventName.GUESS,
				Arrays.asList((Object) cellToGuess, MAX_GUESSES_ON_CELL));
		if (guessedRight) {
			eventBus.triggerEvent(EventName.CORRECT_GUESS,
					Arrays.asList((Object) cellToGuess, value));
		} else {
			eventBus.triggerEvent(EventName.INCORRECT_GUESS,
					Arrays.asList(value));
			if (!hasMoreGuessesOnCell(new Coordinate(x, y))) {
				eventBus.triggerEvent(EventName.GAME_OVER,
						Arrays.asList((Object) new Coordinate(x, y)));
				gameOver = true;
			}
		}
		if (noMoreCellsToGuess()) {
			gameFinished = true;
			eventBus.triggerEvent(EventName.GAME_FINISHED,
					Arrays.asList((Object) new Coordinate(x, y)));
		}
		eventBus.triggerEvent(EventName.UPDATE_BOARD,
				Arrays.asList((Object) showBoard()));
	}

	private void validateGuessCountOnCell(Coordinate cellCoordinates)
			throws SudokuTooManyGuessesOnLocation {
		if (!hasMoreGuessesOnCell(cellCoordinates)) {
			throw new SudokuTooManyGuessesOnLocation(cellCoordinates,
					MAX_GUESSES_ON_CELL);
		}
	}

	private boolean hasMoreGuessesOnCell(Coordinate cellCoordinates) {
		Cell cellToGuess = board.getCellOnLocation(cellCoordinates);
		return cellToGuess.getGuessesMadeOnCell() < MAX_GUESSES_ON_CELL;
	}

	private boolean noMoreCellsToGuess() {
		return !board.hasEmptyCell();
	}

	public Size getCellsXCellsSize() {
		return board.size.cellsXcells;
	}

	public SudokuSize getSize() {
		return board.size;
	}

	public Size getWholeBoardSize() {
		return board.getWholeSize();
	}

	public EntityGrid<Cell> getTablesOfCells() {
		return board.getTablesOfCells();
	}
}
