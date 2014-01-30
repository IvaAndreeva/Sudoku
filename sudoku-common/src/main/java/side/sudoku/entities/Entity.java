package side.sudoku.entities;

import java.io.Serializable;

public abstract class Entity implements Serializable {
	private static final long serialVersionUID = -2295479954399219894L;

	protected abstract Entity clone();
}
