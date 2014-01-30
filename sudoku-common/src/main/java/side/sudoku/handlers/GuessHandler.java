package side.sudoku.handlers;

import java.io.Serializable;

import side.sudoku.entities.Cell;

public class GuessHandler implements Serializable {
	private static final long serialVersionUID = 8406871259766802198L;

	public boolean isGuessCorrectForCellOnBoard(Object value, Cell cellToGuess) {
		cellToGuess.setValueToShow(value);
		return cellToGuess.isValueCorrect(value);
	}
}
