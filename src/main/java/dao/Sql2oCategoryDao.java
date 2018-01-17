package dao;


import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oCategoryDao implements CategoryDao{
    private final Sql2o sql2o;


    public Sql2oCategoryDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }


    @Override
    public void add(Category category){
        String sql = "INSERT INTO category (category) VALUES (:category)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int idCategory = (int) con.createQuery(sql) //make a new variable
                    .bind(category) //map my argument onto the query so we can use information from it
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            category.setId(idCategory); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Category> getAllCats() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM category") //raw sql
                    .executeAndFetch(Category.class); //fetch a list
        }
    }




}
