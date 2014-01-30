package side.sudoku.exceptions;

import side.sudoku.entities.Coordinate;

public class SudokuTooManyGuessesOnLocation extends SudokuException {
	private Coordinate locationWithTooManyGuesses;
	private int maxGuesses;

	public SudokuTooManyGuessesOnLocation(
			Coordinate locationWithTooManyGuesses, int maxGuesses) {
		this.locationWithTooManyGuesses = locationWithTooManyGuesses;
		this.maxGuesses = maxGuesses;
	}

	@Override
	public String getMessage() {
		return "\n NOT ALLOWED ! Cell on location: " + locationWithTooManyGuesses.x + ","
				+ locationWithTooManyGuesses.y + " max guesses allowed: "
				+ maxGuesses;
	}
}
