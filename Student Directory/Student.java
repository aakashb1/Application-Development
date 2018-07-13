/**
 * Student Directory.
 * @author Aakash Bhatia
 */
public class Student {
    /**
     * Field variable for andrewID.
     */
    private String andrewID;
    /**
     * Field variable for firstName.
     */
    private String firstName;
    /**
     * Field variable for lastName.
     */
    private String lastName;
    /**
     * Field variable for phoneNumber.
     */
    private String phoneNumber;
    /**
     * Constructor for student class.
     * @param andrewID.
     * @param andrewId sets AndrewID
     */
    public Student(String andrewId) {
        if (andrewId == null || andrewId.trim().length() == 0) {
            throw new IllegalArgumentException();
        } else {
            andrewID = andrewId;
        }
    }
    /**
     * Getter method for andrewID.
     * @return andrewID.
     */
    public String getAndrewId() {
        return andrewID;
    }
    /**
     * Getter method for firstName.
     * @return firstName.
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Getter method for lastName.
     * @return lastName.
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Getter method for phoneNumber.
     * @return phoneNumber.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * Setter method for firstName.
     * @param firstName.
     * @param s sets Firstname.
     */
    public void setFirstName(String s) {
        if (s == null || s.trim().length() == 0) {
            throw new IllegalArgumentException();
        } else {
            firstName = s;
        }
    }
    /**
     * Setter method for LastName.
     * @param LastName.
     * @param s sets LastName
     */
    public void setLastName(String s) {
        if (s == null || s.trim().length() == 0) {
            throw new IllegalArgumentException();
        } else {
            lastName = s;
        }
    }
    /**
     * Setter method for PhoneNumber.
     * @param PhoneNumber.
     * @param s sets PhoneNumber
     */
    public void setPhoneNumber(String s) {
        phoneNumber = s;
    }
    /**
     * Returns String of student information.
     * @return String.
     */
    @Override
    public String toString() {
        String value = String.format("%s %s (Andrew ID: %s, Phone Number: %s)", firstName, lastName, andrewID, phoneNumber);
        return value;
    }
}
