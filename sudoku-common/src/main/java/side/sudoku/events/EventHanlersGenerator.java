package side.sudoku.events;

import java.io.Serializable;
import java.util.List;

import side.sudoku.entities.Cell;
import side.sudoku.entities.Coordinate;

public class EventHanlersGenerator implements Serializable {
	private static final long serialVersionUID = -6167635090613207984L;

	public static void hookHandlers(EventBus eventBus) {
		eventBus.subscribeForEvent(EventName.GUESS, new EventHandler() {
			public void handleEvent(EventName event, List<Object> args) {
				Cell cell = (Cell) args.get(0);
				cell.incrementGuessesMadeOnCell();
				if (cell.getGuessesMadeOnCell() == (Integer) args.get(1)) {
					cell.setCanChange(false);
				}
			}
		});
		eventBus.subscribeForEvent(EventName.UPDATE_BOARD, new EventHandler() {
			public void handleEvent(EventName event, List<Object> args) {
			}
		});
		eventBus.subscribeForEvent(EventName.CORRECT_GUESS, new EventHandler() {
			public void handleEvent(EventName event, List<Object> args) {
				Cell correctGuessedCell = (Cell) args.get(0);
				correctGuessedCell.updateIfCorrect(args.get(1));
			}
		});

		eventBus.subscribeForEvent(EventName.GAME_OVER, new EventHandler() {
			public void handleEvent(EventName event, List<Object> args) {
				Coordinate location = (Coordinate) args.get(0);
				System.out
						.println("GAME OVER " + location.x + " " + location.y);
			}
		});
	}
}
