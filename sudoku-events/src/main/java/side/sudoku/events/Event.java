package side.sudoku.events;

import java.io.Serializable;

public class Event implements Serializable {
	private static final long serialVersionUID = 7153175348512374056L;
	private EventName name;

	public Event(EventName name) {
		this.name = name;
	}

	public EventName getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Event && name.equals(((Event) obj).getName());
	}
}
