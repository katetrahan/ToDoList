package dao;

import java.util.List;
import models.*;

public interface CategoryDao {


    //create
    void add (Category category);

    //read
    List<Category> getAllCats();
    List<Task> getAllTasksByCategory(int categoryId);

    Category findById(int id);
//
//    //update
  void update(int id, String name);
//
//    //delete
  void deleteById(int id);
   void clearAllCategories();






}
