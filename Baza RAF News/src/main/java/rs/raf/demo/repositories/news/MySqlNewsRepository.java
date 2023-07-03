package rs.raf.demo.repositories.news;

import rs.raf.demo.entities.News;
import rs.raf.demo.entities.helper.UserHelper;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlNewsRepository extends MySqlAbstractRepository implements NewsRepository {

    @Override
    public News addNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO news (naslov, tekst, vreme_kreiranja, broj_poseta, autor_vesti, kategorija_vesti, ime_autora_vesti) VALUES(?, ?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, news.getNaslov());
            preparedStatement.setString(2, news.getTekst());
            preparedStatement.setDate(3, news.getVremeKreiranja());
            preparedStatement.setInt(4, news.getBrojPoseta());
            preparedStatement.setInt(5, news.getAutorVesti());
            preparedStatement.setInt(6, news.getKategorijaVesti());
            preparedStatement.setString(7, news.getIme_autora_vesti());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                news.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public List<News> allNews() {
        List<News> news = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from news");
            while (resultSet.next()) {
                news.add(new News(resultSet.getInt("id"),
                        resultSet.getString("naslov"),
                        resultSet.getString("tekst"),
                        resultSet.getDate("vreme_kreiranja"),
                        resultSet.getInt("broj_poseta"),
                        resultSet.getInt("autor_vesti"),
                        resultSet.getInt("kategorija_vesti"),
                        resultSet.getString("ime_autora_vesti")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public News findNews(Integer id) {
        News news = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM news where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int newsId = resultSet.getInt("id");
                String naslov = resultSet.getString("naslov");
                String tekst = resultSet.getString("tekst");
                Date vremeKreiranja = resultSet.getDate("vreme_kreiranja");
                Integer brojPoseta = resultSet.getInt("broj_poseta");
                Integer autorVesti = resultSet.getInt("autor_vesti");
                Integer kategorijaVesti = resultSet.getInt("kategorija_vesti");
                String imeAutoraVesti = resultSet.getString("ime_autora_vesti");
                news = new News(newsId, naslov, tekst, vremeKreiranja, brojPoseta, autorVesti, kategorijaVesti, imeAutoraVesti);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return news;
    }

    @Override
    public void putNews(News news) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE news SET naslov = ?, tekst = ?, broj_poseta = ?, autor_vesti = ?, kategorija_vesti = ?, ime_autora_vesti = ? WHERE id = ?");
            preparedStatement.setString(1, news.getNaslov());
            preparedStatement.setString(2, news.getTekst());
            preparedStatement.setInt(3, news.getBrojPoseta());
            preparedStatement.setInt(4, news.getAutorVesti());
            preparedStatement.setInt(5, news.getKategorijaVesti());
            preparedStatement.setString(6, news.getIme_autora_vesti());
            preparedStatement.setInt(7, news.getId());
            preparedStatement.executeUpdate();
        }  catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void deleteNews(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM news where id = ?");
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
