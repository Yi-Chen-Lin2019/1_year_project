package controller;

import java.util.List;

import db.CategoryDB;
import db.DataAccessException;
import model.Category;

public class CategoryCtr {
	private CategoryDB categoryDB = new CategoryDB();
	
	public Category newCategory(Category category) throws DataAccessException {
		return categoryDB.insertCategory(category);
	}
	
	public Category updateCategory(Category category) throws DataAccessException {
		return categoryDB.updateCategory(category);
	}
	
	public boolean deleteCategory(Category category) throws DataAccessException {
		return categoryDB.deleteCategory(category);
	}
	
	public Category getCategoryById(int id) throws DataAccessException {
		return categoryDB.findById(id);
	}
	
	public List<Category> getAllCategories() throws DataAccessException{
		return categoryDB.findAll();
	}
}
