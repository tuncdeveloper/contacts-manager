package domain;

public class TelefonDomain {

    private int id ;
    private String name;
    private String surname;
    private String phoneNumber;

    public TelefonDomain(){

    }

    public TelefonDomain(int id, String name, String surname, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String toString() {
        return id + " -) " +
                "" + name + " " +
                "" + surname + "     " +
                "Phone Number: " + phoneNumber;
    }

}
