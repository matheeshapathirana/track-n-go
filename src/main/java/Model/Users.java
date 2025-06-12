package Model;

public class Users {
    private String email;
    private String username;
    private String password;
    private int userid;

    public Users(String email, String username, String password, int userid) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.userid = userid;
    }

    public Users(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getUserid() {
        return userid;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userid=" + userid +
                '}';
    }
}
