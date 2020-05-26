package db;

import java.util.List;

import model.Category;

public interface CategoryDBIF {
	List<Category> findAll() throws DataAccessException;

	Category findById(int id) throws DataAccessException;

	Category updateCategory(Category category) throws DataAccessException;

	boolean deleteCategory(Category category) throws DataAccessException;

	Category insertCategory(Category category) throws DataAccessException;
}
