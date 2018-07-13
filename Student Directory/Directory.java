/**
 * Student Directory.
 * @author Aakash Bhatia
 */
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
/**
 * Defining class Directory.
 */
public class Directory {
    /**
     * Field variable for temp.
     */
    private Student temp;
    /**
     * Field variable for dummy, value.
     */
    private Student value;
    /**
     * Field variable for fnkey, lnkey.
     */
    private String fnkey, lnkey;
    /**
     * Field variable for empty string.
     */
    private List<Student> empty = new LinkedList<Student>();
    /**
     * Field variable for firstnames.
     */
    private List<Student> firstnames = new LinkedList<Student>();
    /**
     * Field variable for lastnames.
     */
    private List<Student> lastnames = new LinkedList<Student>();
    /**
     * Field variable for AndrewID map.
     */
    private Map<String, Student> idMap = new HashMap<String, Student>();
    /**
     * Field variable for FirstName map.
     */
    private Map<String, List<Student>> fnMap = new HashMap<String, List<Student>>();
    /**
     * Field variable for LastName map.
     */
    private Map<String, List<Student>> lnMap = new HashMap<String, List<Student>>();
    /**
     * Method to add student.
     * @param s contains student information.
     */
    public void addStudent(Student s) {
        if (s == null || s.getAndrewId() == null || idMap.containsKey(s.getAndrewId()) || s.getAndrewId().trim().length() == 0) {
            throw new IllegalArgumentException();
            } else {
                Student scopy = new Student(s.getAndrewId());
                scopy.setFirstName(s.getFirstName());
                scopy.setLastName(s.getLastName());
                scopy.setPhoneNumber(s.getPhoneNumber());
                idMap.put(scopy.getAndrewId(), scopy);
                if (!fnMap.containsKey(scopy.getFirstName())) {
                    firstnames = new LinkedList<Student>();
                    firstnames.add(scopy);
                    fnMap.put(scopy.getFirstName(), firstnames);
                } else {
                    List<Student> listFN = fnMap.get(scopy.getFirstName());
                    listFN.add(scopy);
                    //System.out.println(s.getFirstName());
                    //System.out.println(listFN);
                    fnMap.put(scopy.getFirstName(), listFN);
                }
                if (!lnMap.containsKey(scopy.getLastName())) {
                    lastnames = new LinkedList<Student>();
                    lastnames.add(scopy);
                    lnMap.put(scopy.getLastName(), lastnames);
                } else {
                    List<Student> listLN = lnMap.get(scopy.getLastName());
                    listLN.add(scopy);
                    lnMap.put(scopy.getLastName(), listLN);
                }
            }
        }
    /**
     * Method to delete student.
     * @param andrewId is the key to identify.
     */
    public void deleteStudent(String andrewId) {
        if (andrewId == null) {
            throw new IllegalArgumentException();
        } else if (idMap.containsKey(andrewId)) {
            fnkey = idMap.get(andrewId).getFirstName();
            value = idMap.get(andrewId);
            lnkey = idMap.get(andrewId).getLastName();
            idMap.remove(andrewId);
            fnMap.get(fnkey).remove(value);
            idMap.remove(andrewId);
            lnMap.get(lnkey).remove(value);
        } else {
            throw new IllegalArgumentException();
        }
    }
    /**
     * Method to search student by andrewID.
     * @param andrewId is the key to identify.
     * @return Student information.
     */
    public Student searchByAndrewId(String andrewId) {
        if (andrewId == null || andrewId.trim().length() == 0) {
            throw new IllegalArgumentException();
        } else if (idMap.containsKey(andrewId)) {
            Student dummy = new Student(andrewId);
            value = idMap.get(andrewId);
            dummy.setFirstName(value.getFirstName());
            dummy.setLastName(value.getLastName());
            dummy.setPhoneNumber(value.getPhoneNumber());
            return dummy;
            } else {
                return null;
            }
    }
    /**
     * Method to search student by firstName.
     * @param firstName is the key to identify.
     * @return List of student information.
     */
    public List<Student> searchByFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException();
        } else if (fnMap.containsKey(firstName)) {
            List<Student> list = fnMap.get(firstName);
            List<Student> listdummy = new LinkedList<Student>();
            for (int i = 0; i < list.size(); i = i + 1) {
                Student scopy1 = new Student(list.get(i).getAndrewId());
                scopy1.setFirstName(list.get(i).getFirstName());
                scopy1.setLastName(list.get(i).getLastName());
                scopy1.setPhoneNumber(list.get(i).getPhoneNumber());
                listdummy.add(scopy1);
            }
            return listdummy;
        } else {
            return empty;
        }
    }
    /**
     * Method to search student by lastName.
     * @param lastName is the key to identify.
     * @return List of students information.
     */
    public List<Student> searchByLastName(String lastName) {
        if (lastName == null || lastName.trim().length() == 0) {
            throw new IllegalArgumentException();
        } else if (lnMap.containsKey(lastName)) {
            List<Student> list = lnMap.get(lastName);
            List<Student> listdummy = new LinkedList<Student>();
            for (int i = 0; i < list.size(); i = i + 1) {
                Student scopy1 = new Student(list.get(i).getAndrewId());
                scopy1.setFirstName(list.get(i).getFirstName());
                scopy1.setLastName(list.get(i).getLastName());
                scopy1.setPhoneNumber(list.get(i).getPhoneNumber());
                listdummy.add(scopy1);
            }
            return listdummy;
        } else {
            return empty;
        }
    }
    /**
     * Method to get the size of student directory.
     * @return size of students information.
     */
    public int size() {
        return idMap.size();
    }
    }
