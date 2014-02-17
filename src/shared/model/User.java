package shared.model;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/11/14
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class User {

    /**
     * The unique user ID
     */
    private int userId;

    /**
     * The user's username
     */
    private String username;

    /**
     * The user's password
     */
    private String password;

    /**
     * The user's first name
     */
    private String firstName;

    /**
     * The user's last name
     */
    private String lastName;

    /**
     * The user's email address
     */
    private String email;

    /**
     * The number of records indexed by the user
     */
    private int indexedRecords;

    /**
     * Instantiates a new User with default values.
     */
    public User() {
        setUserId(-1);
        setUsername(null);
        setPassword(null);
        setFirstName(null);
        setLastName(null);
        setEmail(null);
        setIndexedRecords(-1);
    }

    /**
     * Instantiates a new User.
     *
     * @param userId the user's unique id
     * @param username the user's username
     * @param password the user's password
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param email the user's email address
     * @param indexedRecords the number records indexed by the user
     */
    public User(int userId, String username, String password, String firstName, String lastName, String email, int indexedRecords) {
        setUserId(userId);
        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setIndexedRecords(indexedRecords);
    }

    /**
     * Gets the user's unique id.
     *
     * @return the user's unique id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user's unique id.
     *
     * @param userId the user's unique id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's username.
     *
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username the user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's password.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's first name.
     *
     * @return the the user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name.
     *
     * @param firstName the user's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the user's last name.
     *
     * @return the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name.
     *
     * @param lastName the user's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the user's email address.
     *
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the number of records indexed by the user.
     *
     * @return the number of records indexed by the user
     */
    public int getIndexedRecords() {
        return indexedRecords;
    }

    /**
     * Sets the number of records indexed by the user.
     *
     * @param indexedRecords the number of records indexed by the user
     */
    public void setIndexedRecords(int indexedRecords) {
        this.indexedRecords = indexedRecords;
    }
}
