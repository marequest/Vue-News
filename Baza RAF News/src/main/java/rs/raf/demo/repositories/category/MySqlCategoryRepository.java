package rs.raf.demo.repositories.category;

import rs.raf.demo.entities.Category;
import rs.raf.demo.entities.helper.UserHelper;
import rs.raf.demo.repositories.MySqlAbstractRepository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCategoryRepository extends MySqlAbstractRepository implements CategoryRepository {

    @Override
    public Category addCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO test_schema.categories (naziv, opis) VALUES(?, ?)", generatedColumns);
            preparedStatement.setString(1, category.getNaziv());
            preparedStatement.setString(2, category.getOpis());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                category.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return category;
    }

    @Override
    public List<Category> allCategories() {
        List<Category> categories = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from test_schema.categories");
            while (resultSet.next()) {
                categories.add(new Category(resultSet.getInt("id"), resultSet.getString("naziv"), resultSet.getString("opis")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return categories;
    }

    @Override
    public Category findCategory(Integer id) {
        Category category = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM test_schema.categories where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int categoryId = resultSet.getInt("id");
                String title = resultSet.getString("naziv");
                String content = resultSet.getString("opis");
                category = new Category(categoryId, title, content);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return category;
    }

    @Override
    public void putCategory(Category category) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE test_schema.categories SET naziv = ?, opis = ? WHERE id = ?");
            preparedStatement.setString(1, category.getNaziv());
            preparedStatement.setString(2, category.getOpis());
            preparedStatement.setInt(3, category.getId());
            preparedStatement.executeUpdate();
        }  catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void deleteCategory(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM test_schema.categories where id = ?");
            preparedStatement.setInt(1, id);
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
