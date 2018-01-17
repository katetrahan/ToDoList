package dao;

import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2oCategoryDaoTest {

    private Sql2oCategoryDao categoryDao; //ignore me for now. We'll create this soon.
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        categoryDao = new Sql2oTaskDao(sql2o); //ignore me for now

        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCategorySetsId() throws Exception {
        Category category = new Category ("epicodus");
        int originalCategoryId = category.getIdCategory();
        categoryDao.add(category);
        assertNotEquals(originalTaskId, category.idCategory()); //how does this work?
    }

    @Test
    public void existingCategoryCanBeFoundById() throws Exception {
        Category category = new Category ("groceries");
        categoryDao.add(category); //add to dao (takes care of saving)
        Category foundTask = categoryDao.findById(category.idCategory()); //retrieve
        assertEquals(category, foundTask); //should be the same
    }

    @Test
    public void allCategoryCanBeFound() throws Exception {
        Category category = new Category ("chores");
        categoryDao.add(category);
        assertEquals(1, categoryDao.getAll().size());
    }

    @Test
    public void noCategoryFoundIfNonePresent() throws Exception {
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    public void updateChangesCategoryContent() throws Exception {
        String initialDescription = "mow the lawn";
        Category category = new Category (initialDescription);
        categoryDao.add(category);

        categoryDao.update(category.idCategory(),"brush the cat");
        Category updatedTask = categoryDao.findById(category.idCategory()); //why do I need to refind this?
        assertNotEquals(initialDescription, updatedTask.getCategory());
    }

    @Test
    public void deleteById() throws Exception {
        Category category = new Category ("mow the lawn");
        categoryDao.add(category);

        categoryDao.deleteTask(category.idCategory());
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    public void deleteAllCategories() throws Exception {
        Category categoryOne = new Category("category-1");
        Category categoryTwo = new Category("category-2");
        categoryDao.add(categoryOne);
        categoryDao.add(categoryTwo);

        categoryDao.clearAllTasks();
        assertEquals(0, categoryDao.getAll().size());
    }

}
