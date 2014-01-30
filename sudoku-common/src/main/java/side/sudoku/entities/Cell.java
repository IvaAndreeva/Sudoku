package side.sudoku.entities;

public class Cell extends Entity {
	private static final long serialVersionUID = -3508780675685472242L;
	public static String INITIAL_VALUE = "X";
	private Object value;
	private Object valueToShow = "";
	private int guessesMadeOnCell = 0;
	private boolean canChange = true;
	public Coordinate locationOnBoard;

	public Cell() {
	}

	public Cell(Object value, boolean isHidden, Coordinate locationOnBoard) {
		this.value = value;
		if (!isHidden) {
			valueToShow = value;
		}
		this.locationOnBoard = locationOnBoard;
	}

	public Cell(Object value, boolean isHidden) {
		this.value = value;
		if (!isHidden) {
			valueToShow = value;
		}
	}

	public void updateIfCorrect(Object value) {
		if (isValueCorrect(value)) {
			valueToShow = value;
		}
	}

	public boolean isValueCorrect(Object value) {
		return String.valueOf(this.value).equals(String.valueOf(value));
	}

	public void incrementGuessesMadeOnCell() {
		guessesMadeOnCell++;
	}

	public int getGuessesMadeOnCell() {
		return guessesMadeOnCell;
	}

	public boolean isGuessedCorrectly() {
		return String.valueOf(this.value).equals(String.valueOf(valueToShow));
	}

	@Override
	public String toString() {
		return getValueToShow().toString();
	}

	public boolean isCanChange() {
		return canChange;
	}

	public void setCanChange(boolean canChange) {
		this.canChange = canChange;
	}

	public void setCanChangeUpdateValueToShow(boolean canChange) {
		setCanChange(canChange);
		if (!canChange) {
			this.valueToShow = value;
		} else {
			this.valueToShow = "";
		}
	}

	public void setValueToShow(Object value) {
		this.valueToShow = value;
	}

	public Object getValueToShow() {
		return valueToShow;
	}

	public void setValueOnlyWhenInitial(Object value) {
		if (this.value.equals(INITIAL_VALUE)) {
			this.value = value;
			valueToShow = value;
		}
	}

	public Object resetValue() {
		Object result = this.value;
		this.value = INITIAL_VALUE;
		this.valueToShow = INITIAL_VALUE;
		return result;
	}

	@Override
	protected Cell clone() {
		Cell cell = new Cell();
		cell.canChange = canChange;
		cell.guessesMadeOnCell = guessesMadeOnCell;
		cell.value = value;
		cell.valueToShow = valueToShow;
		cell.locationOnBoard = locationOnBoard.clone();
		return cell;
	}
}
