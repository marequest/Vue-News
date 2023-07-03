package rs.raf.demo.repositories.newstag;


import rs.raf.demo.entities.NewsTag;
import rs.raf.demo.entities.Tag;
import rs.raf.demo.repositories.MySqlAbstractRepository;
import rs.raf.demo.repositories.tag.TagRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlNewsTagRepository extends MySqlAbstractRepository implements NewsTagRepository {

    @Override
    public NewsTag addNewsTag(NewsTag newsTag) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO news_tag (news_id, tag_id) VALUES(?, ?)", generatedColumns);
            preparedStatement.setInt(1, newsTag.getNews_id());
            preparedStatement.setInt(2, newsTag.getTag_id());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                newsTag.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return newsTag;
    }

    @Override
    public List<NewsTag> allNewsTags() {
        List<NewsTag> newsTags = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from news_tag");
            while (resultSet.next()) {
                newsTags.add(new NewsTag(resultSet.getInt("id"),
                        resultSet.getInt("news_id"),
                        resultSet.getInt("tag_id")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return newsTags;
    }

    @Override
    public NewsTag findNewsTag(Integer id) {
        NewsTag newsTag = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news_tag where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int tagId = resultSet.getInt("id");
                int news_id = resultSet.getInt("news_id");
                int tag_id = resultSet.getInt("tag_id");
                newsTag = new NewsTag(tagId, news_id, tag_id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return newsTag;
    }

    @Override
    public void deleteNewsTag(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM news_tag where id = ?");
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
