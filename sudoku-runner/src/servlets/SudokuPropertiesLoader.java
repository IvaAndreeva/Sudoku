package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import side.sudoku.properties.SudokuAvailableDifficulties;
import side.sudoku.properties.SudokuAvailableSizes;

public class SudokuPropertiesLoader extends HttpServlet {
	private static final long serialVersionUID = 8828333414947691483L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("sizes", SudokuAvailableSizes.sizes);
		req.setAttribute("difficulties",
				SudokuAvailableDifficulties.getDifficulties());
		req.getRequestDispatcher("/WEB-INF/pages/welcome/welcome.jsp").forward(
				req, resp);
	}
}
