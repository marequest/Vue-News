package rs.raf.demo.repositories.user;

import rs.raf.demo.entities.User;
import rs.raf.demo.entities.helper.UserHelper;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUsersRepository extends MySqlAbstractRepository implements UserRepository {

    @Override
    public User addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO users (ime, prezime, username, tip, password, isActive) VALUES(?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, user.getIme());
            preparedStatement.setString(2, user.getPrezime());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getTip());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setBoolean(6, user.getActive());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                users.add(new User(resultSet.getInt("id"),
                        resultSet.getString("ime"),
                        resultSet.getString("prezime"),
                        resultSet.getString("username"),
                        resultSet.getString("tip"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("isActive")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return users;
    }

    @Override
    public void putUser(UserHelper user) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE users SET ime = ?, prezime = ?, username = ?, tip = ?, isActive = ? WHERE username = ?");
            preparedStatement.setString(1, user.getIme());
            preparedStatement.setString(2, user.getPrezime());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getTip());
            preparedStatement.setBoolean(5, user.getActive());
            preparedStatement.setString(6, user.getUsername());
            preparedStatement.executeUpdate();
        }  catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public User findUser(String username) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users where username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int id = resultSet.getInt("id");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String userUsername = resultSet.getString("username");
                String tip = resultSet.getString("tip");
                String password = resultSet.getString("password");
                Boolean isActive = resultSet.getBoolean("isActive");
                user = new User(id, ime, prezime, userUsername, tip, password, isActive);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public void deleteUser(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM users where username = ?");
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
