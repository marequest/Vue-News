package rs.raf.demo.services;



import rs.raf.demo.entities.News;
import rs.raf.demo.entities.helper.UserHelper;
import rs.raf.demo.repositories.news.NewsRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsService {

    public NewsService() {
        System.out.println(this);
    }

    @Inject
    private NewsRepository newsRepository;

    public News addNews(News news) {
        return this.newsRepository.addNews(news);
    }

    public List<News> allNews() {
        return this.newsRepository.allNews();
    }

    public News findNews(Integer id) {
        return this.newsRepository.findNews(id);
    }

    public void putNews(News news) {
        this.newsRepository.putNews(news);
    }

    public void deleteNews(Integer id) {
        this.newsRepository.deleteNews(id);
    }
}
