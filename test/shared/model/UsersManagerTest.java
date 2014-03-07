package shared.model;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 3/6/14
 * Time: 5:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class UsersManagerTest {

    UsersManager usersManager;

    @Before
    public void before() throws Exception {
        usersManager = new UsersManager();
        UsersManager.initialize();
        usersManager.deleteAllUsers();
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: updateUser(User user)
     *
     */
    @Test
    public void testUpdateUser() throws Exception {
        usersManager.deleteAllUsers();
        User userBefore = new User(1, "username", "password", "firstname", "lastname", "email", 5, 2);
        User userAfter = new User(1, "username", "password", "firstname", "lastname", "email", 10, -1);
        usersManager.addUser(userBefore);
        User userReturned = usersManager.getAllUsers().get(0);
        assertEquals(userBefore, userReturned);

        userReturned.setIndexedRecords(userAfter.getIndexedRecords());
        userReturned.setCurrentBatch(userAfter.getCurrentBatch());
        usersManager.updateUser(userReturned);
        userReturned = usersManager.getAllUsers().get(0);
        assertEquals(userAfter, userReturned);
    }

    /**
     *
     * Method: validateUser(String username, String password)
     *
     */
    @Test
    public void testValidateUser() throws Exception {
        usersManager.deleteAllUsers();
        User user1 = new User(1, "username1", "password3", "firstname2", "lastname1", "email2", 5, 2);
        User user2 = new User(2, "username2", "password2", "firstname1", "lastname3", "email3", 6, 3);
        User user3 = new User(3, "username3", "password1", "firstname3", "lastname2", "email1", 7, 4);
        usersManager.addUser(user1);
        usersManager.addUser(user2);
        usersManager.addUser(user3);
        assertTrue(usersManager.validateUser(user1.getUsername(), user1.getPassword()));
        assertTrue(usersManager.validateUser(user2.getUsername(), user2.getPassword()));
        assertTrue(usersManager.validateUser(user3.getUsername(), user3.getPassword()));
        assertFalse(usersManager.validateUser(user1.getUsername(), user2.getPassword()));
    }

    /**
     *
     * Method: deleteAllUsers()
     *
     */
    @Test
    public void testDeleteAllUsers() throws Exception {
        usersManager.addUser(new User(5, "username5", "password5", "firstname5", "lastname5", "email5", 7, 9));
        usersManager.deleteAllUsers();
        assertEquals(0, usersManager.getAllUsers().size());
    }

    /**
     *
     * Method: addList(List<User> userList)
     *
     */
    @Test
    public void testAddList() throws Exception {
        usersManager.deleteAllUsers();
        List<User> users = new ArrayList<User>();
        users.add(new User(1, "username1", "password3", "firstname2", "lastname1", "email2", 5, 2));
        users.add(new User(2, "username2", "password2", "firstname1", "lastname3", "email3", 6, 3));
        users.add(new User(3, "username3", "password1", "firstname3", "lastname2", "email1", 7, 4));
        users.add(new User(4, "username5", "password5", "firstname5", "lastname5", "email5", 7, 9));
        usersManager.addList(users);
        List<User> returnedList = usersManager.getAllUsers();
        assertEquals(users, returnedList);
    }

    /**
     *
     * Method: getCurrentUser(User user)
     *
     */
    @Test
    public void testGetCurrentBatch() throws Exception {
        usersManager.deleteAllUsers();
        List<User> users = new ArrayList<User>();
        User user1 = new User(1, "username1", "password3", "firstname2", "lastname1", "email2", 5, 2);
        User user2 = new User(2, "username2", "password2", "firstname1", "lastname3", "email3", 6, 3);
        User user3 = new User(3, "username3", "password1", "firstname3", "lastname2", "email1", 7, 4);
        usersManager.addUser(user1);
        usersManager.addUser(user2);
        usersManager.addUser(user3);
        int currentBatch = usersManager.getCurrentBatch(user2);
        assertEquals(user2.getCurrentBatch(), currentBatch);
    }

    /**
     *
     * Method: getUserByUsername(String username)
     *
     */
    @Test
    public void testGetUserByUsername() throws Exception {
        usersManager.deleteAllUsers();
        List<User> users = new ArrayList<User>();
        User user1 = new User(1, "username1", "password3", "firstname2", "lastname1", "email2", 5, 2);
        User user2 = new User(2, "username2", "password2", "firstname1", "lastname3", "email3", 6, 3);
        User user3 = new User(3, "username3", "password1", "firstname3", "lastname2", "email1", 7, 4);
        usersManager.addUser(user1);
        usersManager.addUser(user2);
        usersManager.addUser(user3);
        User userReturned = usersManager.getUserByUsername(user3.getUsername());
        assertEquals(user3, userReturned);
    }
}
