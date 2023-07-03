package rs.raf.demo.repositories.category;

import rs.raf.demo.entities.Category;
import rs.raf.demo.entities.helper.UserHelper;

import java.util.List;

public interface CategoryRepository {
    public Category addCategory(Category category);
    public List<Category> allCategories();
    public Category findCategory(Integer id);
    public void putCategory(Category category);
    public void deleteCategory(Integer id);
}
