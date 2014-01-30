package side.sudoku.properties;

import java.util.Arrays;
import java.util.List;

public class SudokuAvailableDifficulties {
	private static final String EASY_DIFF = "Easy";
	private static final String MEDIUM_DIFF = "Medium";
	private static final String HARD_DIFF = "Hard";

	public static Integer getDifficultyByString(String str) {
		if (str.equals(EASY_DIFF)) {
			return 1;
		}
		if (str.equals(MEDIUM_DIFF)) {
			return 2;
		}
		if (str.equals(HARD_DIFF)) {
			return 3;
		}
		return 1;
	}

	public static List<String> getDifficulties() {
		return Arrays.asList(EASY_DIFF, MEDIUM_DIFF, HARD_DIFF);
	}
}
