package rs.raf.demo.repositories.user;

import rs.raf.demo.entities.User;
import rs.raf.demo.entities.helper.UserHelper;

import java.util.List;

public interface UserRepository {
    public User addUser(User user);
    public List<User> allUsers();
    public void putUser(UserHelper user);
    public User findUser(String username);
    public void deleteUser(String username);
}
