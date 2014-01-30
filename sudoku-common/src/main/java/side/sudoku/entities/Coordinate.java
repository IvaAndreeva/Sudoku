package side.sudoku.entities;

public class Coordinate extends Entity {
	private static final long serialVersionUID = 4792415817563129278L;
	public int x;
	public int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Coordinate && ((Coordinate) obj).x == x
				&& ((Coordinate) obj).y == y;
	}

	@Override
	protected Coordinate clone() {
		return new Coordinate(x, y);
	}
}
