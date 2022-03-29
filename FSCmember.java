package mocsbank;

public class FSCmember extends MocsBankAccount{
    /* Data Memebers */
    private int ID;
    private String firstName;
    private String lastName;

    /* Constructors*/
    public FSCmember(int ID, String firstName, String lastName) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /* Setters for data members */
    public void setID (int ID) {
        this.ID = ID;
    }
    public void setfirstName (String name) {
        this.firstName = firstName;
    }
    public void setlastName (String name) {
        this.lastName = lastName;
    }

    /* Getters for data members */
    public int getID(){
        return ID;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
