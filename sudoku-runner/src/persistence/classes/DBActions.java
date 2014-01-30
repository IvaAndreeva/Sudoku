package persistence.classes;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.internal.util.SerializationHelper;

import side.sudoku.game.Sudoku;

/**
 * Helper class for using the DB
 * 
 * @author iandreeva
 * 
 */
public class DBActions {
	private static final String SUDOKU_TYPE = "SudokuDB";
	public static Session sessionBase;

	public static void addSudoku(Sudoku sudoku) {
		SudokuDB sudokuDB = new SudokuDB();
		sudokuDB.setContent(SerializationHelper.serialize(sudoku));
		save(sudokuDB);
		sudoku.id = sudokuDB.getId();
	}

	public static void updateSudoku(int id, Sudoku sudoku) {
		SudokuDB sudokuDB = getSudokuDB(id);
		sudokuDB.setContent(SerializationHelper.serialize(sudoku));
		update(sudokuDB);
	}

	public static Sudoku getSudoku(int id) {
		SudokuDB sudokuDB = getSudokuDB(id);
		return (Sudoku) SerializationHelper.deserialize(sudokuDB.getContent());
	}

	private static SudokuDB getSudokuDB(int id) {
		String query = "from SudokuDB s where s.id = :id";
		Query q = getSession().createQuery(query);
		q.setInteger("id", id);
		return (SudokuDB) q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public static List<Sudoku> getSudokus() {
		return getSession().createQuery("from SudokuDB").list();
	}

	public static int deleteSudokus() {
		return deleteEntries(SUDOKU_TYPE);
	}

	// Basic functions
	private static Session openSession() {
		return HibernateUtil.getSessionFactory().openSession();
	}

	public static void save(Object o) {
		Session session = getSession();
		try {
			session.beginTransaction();
			session.save(o);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

	public static void update(Object o) {
		Session session = getSession();
		try {
			session.beginTransaction();
			session.merge(o);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

	private static int deleteEntries(String type) {
		String query = "delete from " + type;
		Session session = getSession();
		session.beginTransaction();
		Query q = session.createQuery(query);
		int result = q.executeUpdate();
		session.getTransaction().commit();
		return result;
	}

	public static void deleteEntry(Object o) {
		Session session = getSession();
		try {
			session.beginTransaction();
			session.delete(o);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

	public static Session getSession() {
		if (sessionBase == null || !sessionBase.isOpen()) {
			sessionBase = openSession();
		} else {
			sessionBase.close();
			sessionBase = openSession();
		}
		return sessionBase;
	}

	public static void clearSudokus() {
		deleteSudokus();
	}
}