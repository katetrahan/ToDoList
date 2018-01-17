package dao;


import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oCategoryDao implements CategoryDao {
    private final Sql2o sql2o;


    public Sql2oCategoryDao(Sql2o sql2o) {
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }


    @Override
    public void add(Category category) {
        String sql = "INSERT INTO categories (category) VALUES (:category)"; //raw sql
        try (Connection con = sql2o.open()) {
            int idCategory = (int) con.createQuery(sql)
                    .bind(category)
                    .executeUpdate()
                    .getKey();
            category.setId(idCategory);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Category> getAllCats() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM categories") //raw sql
                    .executeAndFetch(Category.class); //fetch a list
        }
    }


    @Override
    public List<Task> getAllTasksByCategory(int categoryId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM tasks WHERE categoryId = :categoryId") //raw sql
                    .addParameter("categoryId", categoryId)
                    .executeAndFetch(Task.class); //fetch a list
        }
    }

    @Override
    public Category findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM categories WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Category.class);
        }
    }


    @Override
    public void update(int id, String newCategory) {
        String sql = "UPDATE categories SET category = :category WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("category", newCategory)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void deleteById(int id) {
        String sql = "DELETE from categories WHERE id=:id"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllCategories() {
        String sql = "DELETE from categories"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }










}
