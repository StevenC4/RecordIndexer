package server.database;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

public class DatabaseTest { 

    private Database db;

@Before
public void before() throws Exception { 
    db = new Database();
    Database.initialize();
}

@After
public void after() throws Exception {
    db = null;
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
    BatchesDAO batchesDAO = new BatchesDAO(db);
    assertEquals(batchesDAO.getClass(), db.getBatchesDAO().getClass());
} 

/** 
* 
* Method: getFieldsDAO() 
* 
*/ 
@Test
public void testGetFieldsDAO() throws Exception {
    FieldsDAO fieldsDAO = new FieldsDAO(db);
    assertEquals(fieldsDAO.getClass(), db.getFieldsDAO().getClass());
}

/** 
* 
* Method: getProjectsDAO() 
* 
*/ 
@Test
public void testGetProjectsDAO() throws Exception {
    ProjectsDAO projectsDAO = new ProjectsDAO(db);
    assertEquals(projectsDAO.getClass(), db.getProjectsDAO().getClass());
}

/** 
* 
* Method: getUsersDAO() 
* 
*/ 
@Test
public void testGetUsersDAO() throws Exception {
    UsersDAO usersDAO = new UsersDAO(db);
    assertEquals(usersDAO.getClass(), db.getUsersDAO().getClass());
} 

/** 
* 
* Method: getValuesDAO() 
* 
*/ 
@Test
public void testGetValuesDAO() throws Exception {
    ValuesDAO valuesDAO = new ValuesDAO(db);
    assertEquals(valuesDAO.getClass(), db.getValuesDAO().getClass());
} 

/** 
* 
* Method: getConnection() 
* 
*/ 
@Test
public void testGetConnection() throws Exception { 
    assertNull(db.getConnection());
    db.startTransaction();
    assertNotNull(db.getConnection());
    db.endTransaction(false);
    assertNull(db.getConnection());
}

/** 
* 
* Method: startTransaction() 
* 
*/ 
@Test
public void testStartTransaction() throws Exception { 
    db.startTransaction();
    assertNotNull(db.getConnection());
    assertFalse(db.getConnection().isClosed());
    db.endTransaction(false);
} 

/** 
* 
* Method: endTransaction(boolean commit) 
* 
*/ 
@Test
public void testEndTransaction() throws Exception { 
    db.startTransaction();
    db.endTransaction(true);
    assertNull(db.getConnection());
    db.startTransaction();
    db.endTransaction(false);
    assertNull(db.getConnection());
}


} 
