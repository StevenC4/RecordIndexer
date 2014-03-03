import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.io.xml.DomDriver;
import net.lingala.zip4j.core.ZipFile;
import server.database.Database;
import shared.model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 2/24/14
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataImporter {

    private static BatchesManager batchesManager;
    private static FieldsManager fieldsManager;
    private static ProjectsManager projectsManager;
    private static UsersManager usersManager;
    private static ValuesManager valuesManager;

    static int projectId;
    static int batchId;
    static int fieldId;
    static int recordId;
    static int valueId;

    public static void main(String[] args) {
        try {
            Database.initialize();

            batchesManager = new BatchesManager();
            fieldsManager = new FieldsManager();
            projectsManager = new ProjectsManager();
            usersManager = new UsersManager();
            valuesManager = new ValuesManager();

            batchesManager.deleteAllBatches();
            fieldsManager.deleteAllFields();
            projectsManager.deleteAllProjects();
            usersManager.deleteAllUsers();
            valuesManager.deleteAllValues();

            String outputFolder = "./project_data";

            ZipFile zipFile = new ZipFile(args[0]);
            zipFile.extractAll(outputFolder);

            Database db = new Database();

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            File file = new File("C:/Dropbox/CS240/P1_Record_Indexer/project_data/Records/Records.xml");
            Document doc = builder.parse(file);
            doc.normalizeDocument();

            NodeList users = doc.getElementsByTagName("user");

            List<User> userList = new ArrayList<User>();
            addUsers(users, userList);

            NodeList projects = doc.getElementsByTagName("project");

            List<Project> projectList = new ArrayList<Project>();
            List<Field> fieldList = new ArrayList<Field>();
            List<Batch> batchList = new ArrayList<Batch>();
            List<Value> valueList = new ArrayList<Value>();

            addProjects(projects, projectList, fieldList, batchList, valueList);

            usersManager.addList(userList);
            projectsManager.addList(projectList);
            fieldsManager.addList(fieldList);
            batchesManager.addList(batchList);
            valuesManager.addList(valueList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUsers(NodeList users, List<User> userList) throws Exception {
        for (int i = 0; i < users.getLength(); i++) {
            Element userElement = (Element)users.item(i);
            String username = ((Element)userElement.getElementsByTagName("username").item(0)).getTextContent();
            String password = ((Element)userElement.getElementsByTagName("password").item(0)).getTextContent();
            String firstname = ((Element)userElement.getElementsByTagName("firstname").item(0)).getTextContent();
            String lastname = ((Element)userElement.getElementsByTagName("lastname").item(0)).getTextContent();
            String email = ((Element)userElement.getElementsByTagName("email").item(0)).getTextContent();
            int indexedrecords = Integer.parseInt(((Element)userElement.getElementsByTagName("indexedrecords").item(0)).getTextContent());

            userList.add(new User(i + 1, username, password, firstname, lastname, email, indexedrecords, -1));
        }
    }

    public static void addProjects(NodeList projects, List<Project> projectList, List<Field> fieldList, List<Batch> batchList, List<Value> valueList) throws Exception {
        projectId = 1;
        batchId = 1;
        fieldId = 1;
        recordId = 1;
        valueId = 1;

        for (int i = 0; i < projects.getLength(); i++) {
            Element projectElement = (Element)projects.item(i);
            String projectTitle = projectElement.getElementsByTagName("title").item(0).getTextContent();
            int recordsPerImage = Integer.parseInt(projectElement.getElementsByTagName("recordsperimage").item(0).getTextContent());
            int firstYCoord = Integer.parseInt(projectElement.getElementsByTagName("firstycoord").item(0).getTextContent());
            int recordHeight = Integer.parseInt(projectElement.getElementsByTagName("recordheight").item(0).getTextContent());

            projectList.add(new Project(projectId, projectTitle, recordsPerImage, firstYCoord, recordHeight));

            NodeList fields = projectElement.getElementsByTagName("field");
            List<Integer> fieldIds = addFields(fields, fieldList);

            NodeList images = projectElement.getElementsByTagName("image");
            addImages(images, fieldIds, batchList, valueList);

            projectId++;
        }
    }

    public static List<Integer> addFields(NodeList fields, List<Field> fieldList) throws Exception {
        List<Integer> fieldIds = new ArrayList<Integer>();

        for (int i = 0; i < fields.getLength(); i++) {
            Element fieldElement = (Element)fields.item(i);
            String fieldTitle = fieldElement.getElementsByTagName("title").item(0).getTextContent();
            int xCoord = Integer.parseInt(fieldElement.getElementsByTagName("xcoord").item(0).getTextContent());
            int width = Integer.parseInt(fieldElement.getElementsByTagName("width").item(0).getTextContent());
            String helpHtml = null;
            if (fieldElement.getElementsByTagName("helphtml").getLength() > 0) {
                helpHtml = fieldElement.getElementsByTagName("helphtml").item(0).getTextContent();
            }
            String knownData = null;
            if (fieldElement.getElementsByTagName("knowndata").getLength() > 0) {
                knownData = fieldElement.getElementsByTagName("knowndata").item(0).getTextContent();
            }

            fieldList.add(new Field(fieldId, fieldTitle, i + 1, projectId, xCoord, width, helpHtml, knownData));

            fieldIds.add(fieldId);
            fieldId++;
        }

        return fieldIds;
    }

    public static void addImages(NodeList images, List<Integer> fieldIds, List<Batch> batchList, List<Value> valueList) throws Exception {
        for (int i = 0; i < images.getLength(); i++) {
            Element imageElement = (Element)images.item(i);
            String imagePath = imageElement.getElementsByTagName("file").item(0).getTextContent();

            batchList.add(new Batch(batchId, projectId, imagePath, "new"));

            NodeList records = imageElement.getElementsByTagName("values");
            addRecords(records, fieldIds, valueList);

            batchId++;
        }
    }

    public static void addRecords(NodeList records, List<Integer> fieldIds, List<Value> valueList) throws Exception {
        for (int i = 0; i < records.getLength(); i++) {
            Element recordsElement = (Element)records.item(i);

            NodeList values = recordsElement.getElementsByTagName("value");
            for (int j = 0; j < values.getLength(); j++) {
                String valueString = values.item(j).getTextContent();

                valueList.add(new Value(valueId, projectId, fieldIds.get(j), batchId, recordId, valueString));
                valueId++;
            }
            recordId++;
        }
    }
}
