package side.sudoku.solver;

public class SudokuCellInfo {
	public String value;
	public int x;
	public int y;

	public SudokuCellInfo(String value, int x, int y) {
		this.value = value;
		this.x = x;
		this.y = y;
	}
}
