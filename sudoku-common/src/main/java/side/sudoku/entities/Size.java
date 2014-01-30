package side.sudoku.entities;

public class Size extends Entity {
	private static final long serialVersionUID = 3732753658425147221L;
	public int verticalCount;
	public int horizontalCount;

	public Size(int verticalCount, int horizontalCount) {
		this.verticalCount = verticalCount;
		this.horizontalCount = horizontalCount;
	}

	public int getVerticalCount() {
		return verticalCount;
	}

	public void setVerticalCount(int verticalCount) {
		this.verticalCount = verticalCount;
	}

	public int getHorizontalCount() {
		return horizontalCount;
	}

	public void setHorizontalCount(int horizontalCount) {
		this.horizontalCount = horizontalCount;
	}

	@Override
	protected Size clone() {
		return new Size(verticalCount, horizontalCount);
	}
}
