package side.sudoku.events;

import java.io.Serializable;
import java.util.List;

public interface EventHandler extends Serializable {
	void handleEvent(EventName event, List<Object> args);
}
