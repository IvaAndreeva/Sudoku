package side.sudoku.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import side.sudoku.entities.SudokuSize;

public class SudokuAvailableSizes {
	public static final List<SudokuSize> sizes = new ArrayList<SudokuSize>(
			Arrays.asList(new SudokuSize(3, 3, 3, 3),
					new SudokuSize(2, 3, 3, 2), new SudokuSize(4, 3, 3, 4),
					new SudokuSize(4, 4, 4, 4)));

	public static SudokuSize getSizeByString(String str) {
		for (SudokuSize size : sizes) {
			if (size.toString().equals(str)) {
				return size;
			}
		}
		return new SudokuSize(3, 3, 3, 3);
	}

}
