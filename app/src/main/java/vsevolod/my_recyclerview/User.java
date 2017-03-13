package vsevolod.my_recyclerview;

/**
 * Created by Vsevolod on 08.03.17.
 */

public class User {
    private String name;
    private String phoneNumber;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User(String name, String phoneNumber) {

        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
