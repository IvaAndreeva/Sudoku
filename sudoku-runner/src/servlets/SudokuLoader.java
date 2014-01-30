package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.classes.DBActions;
import side.sudoku.entities.Coordinate;
import side.sudoku.exceptions.SudokuException;
import side.sudoku.game.Sudoku;
import side.sudoku.properties.SudokuAvailableDifficulties;
import side.sudoku.properties.SudokuAvailableSizes;
import sudoku.databasetool.SudokuDBTool;

public class SudokuLoader extends HttpServlet {
	private static final long serialVersionUID = -2812647211419477043L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Sudoku sudoku;
		String sudokuId = req.getParameter("sudoku_id");
		if (sudokuId == null) {
			sudoku = SudokuDBTool.generateSudoku(SudokuAvailableSizes
					.getSizeByString(req.getParameter("size")),
					SudokuAvailableDifficulties.getDifficultyByString(req
							.getParameter("difficulty")));
			sudokuId = String.valueOf(sudoku.id);
		} else {
			sudoku = DBActions.getSudoku(Integer.parseInt(sudokuId));
		}
		loadSudokuBoard(sudokuId, sudoku, req, resp,
				"/WEB-INF/pages/guess/guess.jsp");
	}

	public static void loadSudokuBoard(String sudokuId, Sudoku sudoku,
			HttpServletRequest req, HttpServletResponse resp, String jspFile)
			throws ServletException, IOException {
		req.setAttribute("gameOver", sudoku.gameOver);
		req.setAttribute("gameFinished", sudoku.gameFinished);
		req.setAttribute("cellsGrid", sudoku.getTablesOfCells());
		req.setAttribute("cellsXcells", sudoku.getCellsXCellsSize());
		req.setAttribute("sudokuId", sudokuId);
		req.getRequestDispatcher(jspFile).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			JSONArray guesses = new JSONArray(req.getParameter("data"));
			String id = req.getParameter("sudoku_id");
			Sudoku sudoku = DBActions.getSudoku(Integer.parseInt(id));

			for (int i = 0; i < guesses.length(); i++) {
				JSONObject guess = (JSONObject) guesses.get(i);
				guess(sudoku,
						new Coordinate(guess.getInt("x"), guess.getInt("y")),
						guess.getString("value"));
			}
			DBActions.updateSudoku(Integer.parseInt(id), sudoku);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void guess(Sudoku sudoku, Coordinate location, Object value) {
		try {
			sudoku.guessValueOnLocation(value, location.x, location.y);
		} catch (SudokuException e) {
		}
	}
}