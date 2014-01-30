package side.sudoku.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventBus implements Serializable {
	private static final long serialVersionUID = 2453699780920818045L;

	private class EventListeners extends
			HashMap<EventName, ArrayList<EventHandler>> implements Serializable {
		private static final long serialVersionUID = -3154941676663115780L;

		public void addEventHandler(EventName event, EventHandler handler) {
			ArrayList<EventHandler> handlersForEvent = this.get(event);
			if (handlersForEvent == null) {
				handlersForEvent = new ArrayList<EventHandler>();
				this.put(event, handlersForEvent);
			}
			handlersForEvent.add(handler);
		}
	}

	private EventListeners eventListeners;

	public EventBus() {
		eventListeners = new EventListeners();
	}

	public void subscribeForEvent(EventName event, EventHandler handler) {
		eventListeners.addEventHandler(event, handler);
	}

	public void triggerEvent(EventName event, List<Object> args) {
		ArrayList<EventHandler> handlersForEvent = eventListeners.get(event);
		if (handlersForEvent != null) {
			for (EventHandler handler : handlersForEvent) {
				handler.handleEvent(event, args);
			}
		}
	}
}
