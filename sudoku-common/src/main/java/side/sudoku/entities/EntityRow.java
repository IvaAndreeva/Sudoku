package side.sudoku.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EntityRow<T extends Entity> extends ArrayList<T> implements
		Serializable {
	private static final long serialVersionUID = 7502233272205202933L;

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		for (T item : this) {
			buff.append(item.toString());
			buff.append(" ");
		}
		return buff.toString();
	}

	@Override
	public EntityRow<T> clone() {
		EntityRow<T> row = new EntityRow<T>();
		for (T entity : this) {
			row.add((T) entity.clone());
		}
		return row;
	}
}
