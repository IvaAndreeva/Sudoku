package side.sudoku.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import side.sudoku.entities.Cell;
import side.sudoku.entities.EntityGrid;
import side.sudoku.entities.EntityRow;
import side.sudoku.game.Sudoku;

public class SudokuGrid extends Composite {

	private Map<Text, SudokuCellInfo> textToInfo;

	public SudokuGrid(Composite parent, int style, Sudoku sudoku) {
		super(parent, style);
		createContents(sudoku);
	}

	public void createContents(Sudoku sudoku) {
		textToInfo = new HashMap<Text, SudokuCellInfo>();
		clear();
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = sudoku.getWholeBoardSize().verticalCount
				+ sudoku.getWholeBoardSize().verticalCount
				/ sudoku.getCellsXCellsSize().verticalCount + 1;
		gridLayout.makeColumnsEqualWidth = true;
		setLayout(gridLayout);

		EntityGrid<Cell> tables = sudoku.getTablesOfCells();
		for (int rowInd = 0; rowInd < tables.size(); rowInd++) {
			drawHorzintalSeparatorIfNeeded(rowInd, gridLayout.numColumns,
					sudoku.getCellsXCellsSize().horizontalCount);
			EntityRow<Cell> row = tables.get(rowInd);
			for (int columnInd = 0; columnInd < row.size(); columnInd++) {
				drawVerticalSeparatorIfNeeded(columnInd,
						sudoku.getCellsXCellsSize().verticalCount);
				Cell cell = row.get(columnInd);
				if (!cell.isCanChange()) {
					addLabel(cell.getValueToShow().toString());
				} else {
					Text addedText = addText(cell.getValueToShow().toString());
					textToInfo.put(addedText, new SudokuCellInfo(cell
							.getValueToShow().toString(), rowInd, columnInd));
				}
			}
			drawVerticalSeparatorIfNeeded(row.size(),
					sudoku.getCellsXCellsSize().verticalCount);
		}
		drawHorzintalSeparatorIfNeeded(tables.size(), gridLayout.numColumns,
				sudoku.getCellsXCellsSize().horizontalCount);
	}

	private void clear() {
		for (Control control : getChildren()) {
			control.dispose();
		}
	}

	private void drawHorzintalSeparatorIfNeeded(int rowInd, int numColumns,
			int sudokuCellRows) {
		if (rowInd == 0 || rowInd % sudokuCellRows == 0) {
			for (int i = 0; i < numColumns; i++) {
				Label sep = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				GridData gd = new GridData(20, 20);
				sep.setLayoutData(gd);
			}
		}
	}

	private void drawVerticalSeparatorIfNeeded(int columnInd, int sudokuCellColumns) {
		if (columnInd == 0 || columnInd % sudokuCellColumns == 0) {
			Label sep = new Label(this, SWT.SEPARATOR | SWT.VERTICAL);
			GridData gd = new GridData(20, 20);
			sep.setLayoutData(gd);
		}
	}

	private Label addLabel(String value) {
		Label label = new Label(this, SWT.PUSH);
		label.setText(value);
		GridData gd = new GridData(20, 20);
		label.setLayoutData(gd);
		return label;
	}

	private Text addText(String value) {
		Text text = new Text(this, SWT.SINGLE);
		text.setText(value);
		GridData gd = new GridData(20, 20);
		text.setLayoutData(gd);
		return text;
	}

	public List<SudokuCellInfo> getChangedCells() {
		List<SudokuCellInfo> result = new ArrayList<SudokuCellInfo>();
		for (Text text : textToInfo.keySet()) {
			SudokuCellInfo info = textToInfo.get(text);
			if (!text.getText().equals(info.value)) {
				result.add(new SudokuCellInfo(text.getText(), info.x, info.y));
			}
		}
		return result;
	}

	public void resetChanged() {
		for (Text text : textToInfo.keySet()) {
			SudokuCellInfo info = textToInfo.get(text);
			info.value = text.getText();
		}
	}
}
