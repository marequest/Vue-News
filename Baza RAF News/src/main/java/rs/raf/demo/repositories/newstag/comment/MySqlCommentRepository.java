package rs.raf.demo.repositories.newstag.comment;

import rs.raf.demo.entities.Comment;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {

    @Override
    public Comment addComment(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO comments (autor, tekst, vreme_kreiranja, vest_komentara) VALUES(?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, comment.getAutor());
            preparedStatement.setString(2, comment.getTekst());
            preparedStatement.setDate(3, comment.getVremeKreiranja());
            preparedStatement.setInt(4, comment.getVestKomentara());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                comment.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comment;
    }

    @Override
    public List<Comment> allComments() {
        List<Comment> comments = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from comments");
            while (resultSet.next()) {
                comments.add(new Comment(resultSet.getInt("id"),
                        resultSet.getString("autor"),
                        resultSet.getString("tekst"),
                        resultSet.getDate("vreme_kreiranja"),
                        resultSet.getInt("vest_komentara")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comments;
    }

    @Override
    public Comment findComment(Integer id) {
        Comment comment = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM comments where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int commentId = resultSet.getInt("id");
                String autor = resultSet.getString("autor");
                String tekst = resultSet.getString("tekst");
                Date vreme_kreiranja = resultSet.getDate("vreme_kreiranja");
                Integer vest_komentara = resultSet.getInt("vest_komentara");
                comment = new Comment(commentId, autor, tekst, vreme_kreiranja, vest_komentara);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comment;
    }

    @Override
    public void deleteComment(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM comments where id = ?");
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
