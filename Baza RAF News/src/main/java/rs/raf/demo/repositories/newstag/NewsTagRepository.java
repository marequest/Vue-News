package rs.raf.demo.repositories.newstag;


import rs.raf.demo.entities.NewsTag;

import java.util.List;

public interface NewsTagRepository {
    public NewsTag addNewsTag(NewsTag newsTag);
    public List<NewsTag> allNewsTags();
    public NewsTag findNewsTag(Integer id);
    public void deleteNewsTag(Integer id);
}
