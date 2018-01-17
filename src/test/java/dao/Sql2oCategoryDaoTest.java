package dao;

import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class Sql2oCategoryDaoTest {





    private Sql2oTaskDao taskDao;
    private Sql2oCategoryDao categoryDao; //ignore me for now. We'll create this soon.
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        categoryDao = new Sql2oCategoryDao(sql2o); //ignore me for now
        taskDao = new Sql2oTaskDao(sql2o);

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
        int originalCategoryId = category.getId();
        categoryDao.add(category);
        assertNotEquals(originalCategoryId, category.getId()); //how does this work?
    }

    @Test
    public void existingCategoryCanBeFoundById() throws Exception {
        Category category = new Category ("groceries");
        categoryDao.add(category); //add to dao (takes care of saving)
        Category foundTask = categoryDao.findById(category.getId()); //retrieve
        assertEquals(category, foundTask); //should be the same
    }

    @Test
    public void allCategoryCanBeFound() throws Exception {
        Category category = new Category ("chores");
        categoryDao.add(category);
        assertEquals(1, categoryDao.getAllCats().size());
    }

    @Test
    public void noCategoryFoundIfNonePresent() throws Exception {
        assertEquals(0, categoryDao.getAllCats().size());
    }

    @Test
    public void updateChangesCategoryContent() throws Exception {
        String initialDescription = "mow the lawn";
        Category category = new Category (initialDescription);
        categoryDao.add(category);

        categoryDao.update(category.getId(),"brush the cat");
        Category updatedTask = categoryDao.findById(category.getId());
        assertNotEquals(initialDescription, updatedTask.getCategory());
    }

    @Test
    public void getAllCategoryTasks() throws Exception {

        Category category = setupNewCategory();
        categoryDao.add(category);
        int categoryId = category.getId();
        Task newTask = new Task("mow the lawn",categoryId);
        Task otherTask = new Task("pull weeds", categoryId);
        Task thirdTask = new Task("trim hedge", categoryId);
        taskDao.add(newTask);
        taskDao.add(otherTask); //we are not adding task 3 so we can test things precisely.
        assertEquals(2,categoryDao.getAllTasksByCategory(categoryId).size());
        assertTrue(categoryDao.getAllTasksByCategory(categoryId).contains(newTask));
        assertTrue(categoryDao.getAllTasksByCategory(categoryId).contains(otherTask));
        assertFalse(categoryDao.getAllTasksByCategory(categoryId).contains(thirdTask)); //things are accurate!
    }

    public Category setupNewCategory() {
        return new Category("Yardwork");
    }

    @Test
    public void deleteById() throws Exception {
        Category category = new Category ("mow the lawn");
        categoryDao.add(category);

        categoryDao.deleteById(category.getId());
        assertEquals(0, categoryDao.getAllCats().size());
    }

    @Test
    public void clearAllCategories() throws Exception {
        Category category = setupNewCategory();
        Category otherCategory = new Category("Cleaning");
        categoryDao.add(category);
        categoryDao.add(otherCategory);
        int daoSize = categoryDao.getAllCats().size();
        categoryDao.clearAllCategories();
        assertTrue(daoSize > 0 && daoSize > categoryDao.getAllCats().size());

    }



}
