package shared.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * User Tester.
 *
 * @author <Authors name>
 * @since <pre>Mar 3, 2014</pre>
 * @version 1.0
 */
public class UserTest {

    User userDefault;
    User userSet;
    int userId;
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
    int indexedRecords;
    int currentBatch;

    @Before
    public void before() throws Exception {
        userDefault = new User();
        userId = 1;
        username = "username";
        password = "password";
        firstName = "first_name";
        lastName = "last_name";
        email = "email";
        indexedRecords = 2;
        currentBatch = 3;
        userSet = new User(userId, username, password, firstName, lastName, email, indexedRecords, currentBatch);
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetUserId() throws Exception {
        assertEquals(-1, userDefault.getUserId());
        assertEquals(userId, userSet.getUserId());
    }

    @Test
    public void testSetUserId() throws Exception {
        userDefault.setUserId(userId);
        assertEquals(userId, userDefault.getUserId());
    }

    @Test
    public void testGetUsername() throws Exception {
        assertNull(userDefault.getUsername());
        assertEquals(username, userSet.getUsername());
    }

    @Test
    public void testSetUsername() throws Exception {
        userDefault.setUsername(username);
        assertEquals(username, userDefault.getUsername());
    }

    @Test
    public void testGetPassword() throws Exception {
        assertNull(userDefault.getPassword());
        assertEquals(password, userSet.getPassword());
    }

    @Test
    public void testSetPassword() throws Exception {
        userDefault.setPassword(password);
        assertEquals(password, userDefault.getPassword());
    }

    @Test
    public void testGetFirstName() throws Exception {
        assertNull(userDefault.getFirstName());
        assertEquals(firstName, userSet.getFirstName());
    }

    @Test
    public void testSetFirstName() throws Exception {
        userDefault.setFirstName(firstName);
        assertEquals(firstName, userDefault.getFirstName());
    }

    @Test
    public void testGetLastName() throws Exception {
        assertNull(userDefault.getLastName());
        assertEquals(lastName, userSet.getLastName());
    }

    @Test
    public void testSetLastName() throws Exception {
        userDefault.setLastName(lastName);
        assertEquals(lastName, userDefault.getLastName());
    }

    @Test
    public void testGetEmail() throws Exception {
        assertNull(userDefault.getEmail());
        assertEquals(email, userSet.getEmail());
    }

    @Test
    public void testSetEmail() throws Exception {
        userDefault.setEmail(email);
        assertEquals(email, userDefault.getEmail());
    }

    @Test
    public void testGetIndexedRecords() throws Exception {
        assertEquals(-1, userDefault.getIndexedRecords());
        assertEquals(indexedRecords, userSet.getIndexedRecords());
    }

    @Test
    public void testSetIndexedRecords() throws Exception {
        userDefault.setIndexedRecords(indexedRecords);
        assertEquals(indexedRecords, userDefault.getIndexedRecords());
    }

    @Test
    public void testGetCurrentBatch() throws Exception {
        assertEquals(-1, userDefault.getCurrentBatch());
        assertEquals(currentBatch, userSet.getCurrentBatch());
    }

    @Test
    public void testSetCurrentBatch() throws Exception {
        userDefault.setCurrentBatch(currentBatch);
        assertEquals(currentBatch, userDefault.getCurrentBatch());
    }
}
