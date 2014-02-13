package server.database;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* Database Tester. 
* 
* @author <Authors name> 
* @since <pre>Feb 12, 2014</pre> 
* @version 1.0 
*/ 
public class DatabaseTest { 

    private Database db;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: initialize() 
* 
*/ 
@Test
public void testInitialize() throws Exception { 
    Database.initialize();
} 

/** 
* 
* Method: getBatchesDAO() 
* 
*/ 
@Test
public void testGetBatchesDAO() throws Exception { 
    db = new Database();
    BatchesDAO batchesDAO = new BatchesDAO(db);
    Assert.assertEquals(batchesDAO.getClass(), db.getBatchesDAO().getClass());
} 

/** 
* 
* Method: getFieldsDAO() 
* 
*/ 
@Test
public void testGetFieldsDAO() throws Exception {
    db = new Database();
    FieldsDAO fieldsDAO = new FieldsDAO(db);
    Assert.assertEquals(fieldsDAO.getClass(), db.getFieldsDAO().getClass());
}

/** 
* 
* Method: getProjectsDAO() 
* 
*/ 
@Test
public void testGetProjectsDAO() throws Exception {
    db = new Database();
    ProjectsDAO projectsDAO = new ProjectsDAO(db);
    Assert.assertEquals(projectsDAO.getClass(), db.getProjectsDAO().getClass());
}

/** 
* 
* Method: getUsersDAO() 
* 
*/ 
@Test
public void testGetUsersDAO() throws Exception {
    db = new Database();
    UsersDAO usersDAO = new UsersDAO(db);
    Assert.assertEquals(usersDAO.getClass(), db.getUsersDAO().getClass());
} 

/** 
* 
* Method: getValuesDAO() 
* 
*/ 
@Test
public void testGetValuesDAO() throws Exception {
    db = new Database();
    ValuesDAO valuesDAO = new ValuesDAO(db);
    Assert.assertEquals(valuesDAO.getClass(), db.getValuesDAO().getClass());
} 

/** 
* 
* Method: getConnection() 
* 
*/ 
@Test
public void testGetConnection() throws Exception { 
    db = new Database();
    Assert.assertNull(db.getConnection());
} 

/** 
* 
* Method: startTransaction() 
* 
*/ 
@Test
public void testStartTransaction() throws Exception { 
    db = new Database();
    db.startTransaction();
    Assert.assertNotNull(db.getConnection());
    db.endTransaction(false);
} 

/** 
* 
* Method: endTransaction(boolean commit) 
* 
*/ 
@Test
public void testEndTransaction() throws Exception { 
//TODO: Test goes here... 
} 


} 
