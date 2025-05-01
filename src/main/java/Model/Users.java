package Model;

public class Users {
    private String email;        // Unique identifier for the user (Primary Key)
    private String username;     // Username chosen by the user
    private String password;     // Hashed password

    // Constructor
    public Users(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // To String (Optional for debugging)
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
