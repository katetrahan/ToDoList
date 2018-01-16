package dao;

import models.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2oTaskDaoTest {

    private Sql2oTaskDao taskDao; //ignore me for now. We'll create this soon.
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        taskDao = new Sql2oTaskDao(sql2o); //ignore me for now

        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Task task = new Task ("mow the lawn");
        int originalTaskId = task.getId();
        taskDao.add(task);
        assertNotEquals(originalTaskId, task.getId()); //how does this work?
    }

    @Test
    public void existingTasksCanBeFoundById() throws Exception {
        Task task = new Task ("mow the lawn");
        taskDao.add(task); //add to dao (takes care of saving)
        Task foundTask = taskDao.findById(task.getId()); //retrieve
        assertEquals(task, foundTask); //should be the same
    }

    @Test
    public void allTasksCanBeFound() throws Exception {
        Task task = new Task ("test task-1");
        taskDao.add(task);
        assertEquals(1, taskDao.getAll().size());
    }

    @Test
    public void noTasksFoundIfNonePresent() throws Exception {
        assertEquals(0, taskDao.getAll().size());
    }

    @Test
    public void updateChangesTaskContent() throws Exception {
        String initialDescription = "mow the lawn";
        Task task = new Task (initialDescription);
        taskDao.add(task);

        taskDao.update(task.getId(),"brush the cat");
        Task updatedTask = taskDao.findById(task.getId()); //why do I need to refind this?
        assertNotEquals(initialDescription, updatedTask.getDescription());
    }

    @Test
    public void deleteById() throws Exception {
        Task task = new Task ("mow the lawn");
        taskDao.add(task);

        taskDao.deleteTask(task.getId());
        assertEquals(0, taskDao.getAll().size());
    }

    @Test
    public void deleteAllTasks() throws Exception {
        Task taskOne = new Task("task-1");
        Task taskTwo = new Task("task-2");
        taskDao.add(taskOne);
        taskDao.add(taskTwo);

        taskDao.clearAllTasks();
        assertEquals(0, taskDao.getAll().size());
    }


}
