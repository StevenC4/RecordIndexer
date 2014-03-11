package client.communication;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import server.Server;
import shared.communication.*;
import shared.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ClientCommunicator Tester.
 *
 * @author <Authors name>
 * @since <pre>Mar 7, 2014</pre>
 * @version 1.0
 */
public class ClientCommunicatorTest {

    private Server server;
    ClientCommunicator clientCommunicator;
    ProjectsManager projectsManager = new ProjectsManager();
    BatchesManager batchesManager = new BatchesManager();
    FieldsManager fieldsManager = new FieldsManager();
    ValuesManager valuesManager = new ValuesManager();
    UsersManager usersManager = new UsersManager();

    List<User> users = new ArrayList<User>();
    List<Batch> batches = new ArrayList<Batch>();
    List<Project> projects = new ArrayList<Project>();
    List<Field> fields = new ArrayList<Field>();
    List<Value> values = new ArrayList<Value>();

    User userInvalid = new User(1, "username", "password", "firstname", "lastname", "email", 1, 1);

    @Before
    public void before() throws Exception {
        String[] args = {};
        server = Server.main1(args);
        clientCommunicator = new ClientCommunicator();

        users.add(new User(1, "username1", "password3", "firstname2", "lastname1", "email2", 5, -1));
        users.add(new User(2, "username2", "password2", "firstname1", "lastname3", "email3", 6, 2));
        users.add(new User(3, "username3", "password1", "firstname3", "lastname2", "email1", 7, -1));
        usersManager.addList(users);

        projects.add(new Project(1, "title1", 5, 5, 9));
        projects.add(new Project(2, "title3", 2, 8, 2));
        projects.add(new Project(3, "title2", 5, 7, 4));
        projects.add(new Project(4, "title4", 4, 2, 8));
        projectsManager.addList(projects);

        batches.add(new Batch(1, 1, "1.jpg", "new"));
        batches.add(new Batch(2, 1, "5.png", "new"));
        batches.add(new Batch(3, 2, "2.bmp", "new"));
        batches.add(new Batch(4, 3, "4.ico", "new"));
        batches.add(new Batch(5, 4, "7.jpg", "new"));
        batches.add(new Batch(6, 4, "9.ppm", "new"));
        batches.add(new Batch(7, 5, "6.bmp", "completed"));
        batchesManager.addList(batches);

        fields.add(new Field(1, "title1", 12, 1, 350, 15, "help8.html", "knownData.html"));
        fields.add(new Field(2, "title2", 13, 1, 350, 15, "help7.html", "knownData1.html"));
        fields.add(new Field(3, "title3", 14, 2, 350, 15, "help6.html", "knownData2.html"));
        fields.add(new Field(4, "title4", 15, 2, 350, 15, "help5.html", "knownData3.html"));
        fields.add(new Field(5, "title5", 16, 3, 350, 15, "help4.html", "knownData4.html"));
        fields.add(new Field(6, "title6", 17, 3, 350, 15, "help3.html", "knownData5.html"));
        fields.add(new Field(7, "title7", 18, 4, 350, 15, "help2.html", "knownData6.html"));
        fields.add(new Field(8, "title8", 19, 4, 350, 15, "help1.html", "knownData7.html"));
        fieldsManager.addList(fields);

        values.add(new Value(1, 1, 1, 1, 1, 1, "Abby"));
        values.add(new Value(2, 1, 2, 1, 1, 2, "Brittany"));
        values.add(new Value(3, 1, 1, 1, 2, 3, "Celeste"));
        values.add(new Value(4, 1, 2, 1, 2, 4, "Daniel"));
        values.add(new Value(5, 2, 3, 3, 3, 5, "Edward"));
        values.add(new Value(6, 2, 4, 3, 3, 6, "Fagner"));
        values.add(new Value(7, 2, 3, 3, 4, 7, "George"));
        values.add(new Value(8, 2, 4, 3, 4, 8, "Harry"));
        values.add(new Value(9, 3, 5, 4, 5, 9, "Ingrid"));
        values.add(new Value(10, 3, 6, 4, 5, 10, "Josie"));
        values.add(new Value(11, 3, 5, 4, 6, 11, "Karry"));
        values.add(new Value(12, 3, 6, 4, 6, 12, "Larry"));
        values.add(new Value(13, 4, 7, 5, 7, 13, "Monty"));
        values.add(new Value(14, 4, 8, 5, 7, 14, "Norton"));
        values.add(new Value(15, 4, 7, 6, 8, 15, "Oliver"));
        values.add(new Value(16, 4, 8, 6, 8, 16, "Patty"));
        valuesManager.addList(values);
    }

    @After
    public void after() throws Exception {
        server.stop();
        projectsManager.deleteAllProjects();
        batchesManager.deleteAllBatches();
        fieldsManager.deleteAllFields();
        valuesManager.deleteAllValues();
        usersManager.deleteAllUsers();
    }

    /**
     *
     * Method: ValidateUser(ValidateUser_Params params)
     *
     */
    @Test
    public void testValidateUser() throws Exception {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            ValidateUser_Params params = new ValidateUser_Params();
            params.setUser(user);
            ValidateUser_Result result = clientCommunicator.ValidateUser(params);
            StringBuilder sb = new StringBuilder();
            sb.append("TRUE\n");
            sb.append(user.getFirstName()).append("\n");
            sb.append(user.getLastName()).append("\n");
            sb.append(user.getIndexedRecords()).append("\n");
            assertEquals(sb.toString(), result.toString());
        }

        ValidateUser_Params params = new ValidateUser_Params();
        params.setUser(new User(4, "username4", "password6", "firstname4", "lastname8", "email4", 2, 3));
        ValidateUser_Result result = clientCommunicator.ValidateUser(params);
        assertEquals("FALSE\n", result.toString());
    }

    /**
     *
     * Method: GetProjects(ValidateUser_Params params)
     *
     */
    @Test
    public void testGetProjects() throws Exception {
        User user = users.get(0);

        ValidateUser_Params param = new ValidateUser_Params();
        param.setUser(user);
        GetProjects_Result result = clientCommunicator.GetProjects(param);
        StringBuilder sb = new StringBuilder();
        for (Project project : projects) {
            sb.append(project.getProjectId()).append("\n");
            sb.append(project.getTitle()).append("\n");
        }
        assertEquals(sb.toString(), result.toString());
        param.setUser(userInvalid);
        result = clientCommunicator.GetProjects(param);
        assertEquals("FAILED\n", result.toString());
    }

    /**
     *
     * Method: GetSampleImage(GetSampleImage_Params params)
     *
     */
    @Test
    public void testGetSampleImage() throws Exception {
        User user = users.get(0);

        GetSampleImage_Params params = new GetSampleImage_Params();
        params.setUser(user);
        params.setProjectId(1);
        GetSampleImage_Result result = clientCommunicator.GetSampleImage(params);
        assertEquals(batches.get(0).getPath() + "\n", result.toString());

        params.setProjectId(4);
        result = clientCommunicator.GetSampleImage(params);
        assertEquals(batches.get(4).getPath() + "\n", result.toString());

        params.setUser(new User(1, "username", "password", "firstname", "lastname", "email", 1, 1));
        result = clientCommunicator.GetSampleImage(params);
        assertEquals("FAILED\n", result.toString());
    }

    /**
     *
     * Method: DownloadBatch(DownloadBatch_Params params)
     *
     */
    @Test
    public void testDownloadBatch() throws Exception {
        User user1 = users.get(0);

        DownloadBatch_Params params = new DownloadBatch_Params();
        params.setUser(user1);
        params.setProjectId(1);
        DownloadBatch_Result result = clientCommunicator.DownloadBatch(params);
        assertNotEquals("FAILED\n", result.toString());

        params.setProjectId(2);
        result = clientCommunicator.DownloadBatch(params);
        assertEquals("FAILED\n", result.toString());
    }

    /**
     *
     * Method: SubmitBatch(SubmitBatch_Params params)
     *
     */
    @Test
    public void testSubmitBatch() throws Exception {
        User user = users.get(0);

        DownloadBatch_Params paramsDownload = new DownloadBatch_Params();
        paramsDownload.setUser(user);
        paramsDownload.setProjectId(1);
        clientCommunicator.DownloadBatch(paramsDownload);

        SubmitBatch_Params paramsSubmit = new SubmitBatch_Params();
        paramsSubmit.setUser(user);
        paramsSubmit.setBatchId(1);
        paramsSubmit.setFieldValues("a,b;c,d;e,f;g,h;i,j");
        SubmitBatch_Result result = clientCommunicator.SubmitBatch(paramsSubmit);
        assertEquals("TRUE\n", result.toString());

        result = clientCommunicator.SubmitBatch(paramsSubmit);
        assertEquals("FAILED\n", result.toString());

        paramsSubmit.setUser(userInvalid);
        result = clientCommunicator.SubmitBatch(paramsSubmit);
        assertEquals("FAILED\n", result.toString());
    }

    /**
     *
     * Method: GetFields(GetFields_Params params)
     *
     */
    @Test
    public void testGetFields() throws Exception {
        User user = users.get(0);
        GetFields_Params params = new GetFields_Params();
        params.setUser(user);
        params.setProjectId(0);
        GetFields_Result result = clientCommunicator.GetFields(params);
        assertEquals("FAILED\n", result.toString());

        params.setProjectId(1);
        result = clientCommunicator.GetFields(params);
        StringBuilder sb = new StringBuilder();
        sb.append(fields.get(0).getProjectId()).append("\n");
        sb.append(fields.get(0).getFieldId()).append("\n");
        sb.append(fields.get(0).getTitle()).append("\n");
        sb.append(fields.get(1).getProjectId()).append("\n");
        sb.append(fields.get(1).getFieldId()).append("\n");
        sb.append(fields.get(1).getTitle()).append("\n");
        assertEquals(sb.toString(), result.toString());

        params.setProjectId("");
        result = clientCommunicator.GetFields(params);
        sb = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            sb.append(fields.get(i).getProjectId()).append("\n");
            sb.append(fields.get(i).getFieldId()).append("\n");
            sb.append(fields.get(i).getTitle()).append("\n");
        }
        assertEquals(sb.toString(), result.toString());

        params.setUser(userInvalid);
        result = clientCommunicator.GetFields(params);
        assertEquals("FAILED\n", result.toString());

    }

    /**
     *
     * Method: Search(Search_Params params)
     *
     */
    @Test
    public void testSearch() throws Exception {
        User user = users.get(0);
        Search_Params params = new Search_Params();
        params.setUser(user);
        params.setFields("1,3,5");
        params.setSearchValues("Brittany,Daniel,Fagner");
        Search_Result result = clientCommunicator.Search(params);
        assertEquals("FAILED\n", result.toString());

        params.setFields("2,4,6");
        result = clientCommunicator.Search(params);
        StringBuilder sb = new StringBuilder();
        sb.append(values.get(1).getBatchId()).append("\n");
        sb.append("1.jpg").append("\n");
        sb.append(values.get(1).getRecordId()).append("\n");
        sb.append(values.get(1).getFieldId()).append("\n");
        sb.append(values.get(3).getBatchId()).append("\n");
        sb.append("1.jpg").append("\n");
        sb.append(values.get(3).getRecordId()).append("\n");
        sb.append(values.get(3).getFieldId()).append("\n");
        sb.append(values.get(5).getBatchId()).append("\n");
        sb.append("2.bmp").append("\n");
        sb.append(values.get(5).getRecordId()).append("\n");
        sb.append(values.get(5).getFieldId()).append("\n");
        assertEquals(sb.toString(), result.toString());

        params.setUser(userInvalid);
        result = clientCommunicator.Search(params);
        assertEquals("FAILED\n", result.toString());
    }

    /**
     *
     * Method: DownloadFile(DownloadFile_Params params)
     *
     */
    @Test
    public void testDownloadFile() throws Exception {
    }

/*
    *//**
     *
     * Method: doGet(String urlPath)
     *
     *//*
    @Test
    public void testDoGet() throws Exception {*/
//TODO: Test goes here...
/*
try {
   Method method = ClientCommunicator.getClass().getMethod("doGet", String.class);
   method.setAccessible(true);
   method.invoke(<Object>, <Parameters>);
} catch(NoSuchMethodException e) {
} catch(IllegalAccessException e) {
} catch(InvocationTargetException e) {
}

    }*/
}
