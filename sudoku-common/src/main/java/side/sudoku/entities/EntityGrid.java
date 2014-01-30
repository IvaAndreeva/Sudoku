package side.sudoku.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class EntityGrid<T extends Entity> extends ArrayList<EntityRow<T>>
		implements Serializable {
	private static final long serialVersionUID = 5483739799834760464L;

	public void setEntityOnLocation(T entity, Coordinate location) {
		get(location.x).set(location.y, entity);
	}

	public T getEntityOnLocation(Coordinate location) {
		return get(location.x).get(location.y);
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		for (EntityRow<T> row : this) {
			buff.append(row.toString());
			buff.append("\n");
		}
		return buff.toString();
	}

	@Override
	public EntityGrid<T> clone() {
		EntityGrid<T> grid = new EntityGrid<T>();
		for (EntityRow<T> row : this) {
			grid.add(row.clone());
		}
		return grid;
	}
}
