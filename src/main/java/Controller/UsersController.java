package Controller;

import Model.Users;
import Model.UsersDAO;
import java.util.List;

public class UsersController {
    private final UsersDAO usersDAO = new UsersDAO();

    public boolean addUser(String email, String username, String password, String role) {
        Users user = new Users(email, username, password);
        return usersDAO.addUser(user, role);
    }

    public boolean updateUser(String email, String username, String password, String role) {
        Users user = new Users(email, username, password);
        return usersDAO.updateUser(user, role);
    }

    public boolean deleteUser(String email) {
        return usersDAO.deleteUser(email);
    }

    public List<Users> getAllUsers() {
        return usersDAO.getAllUsers();
    }

    public String getUserRole(String email) {
        return usersDAO.getUserRole(email);
    }
}
