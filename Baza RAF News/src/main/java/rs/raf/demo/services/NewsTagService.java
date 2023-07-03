package rs.raf.demo.services;


import rs.raf.demo.entities.NewsTag;
import rs.raf.demo.entities.Tag;
import rs.raf.demo.repositories.newstag.NewsTagRepository;
import rs.raf.demo.repositories.tag.TagRepository;

import javax.inject.Inject;
import java.util.List;

public class NewsTagService {

    public NewsTagService() {
        System.out.println(this);
    }

    @Inject
    private NewsTagRepository tagRepository;

    public NewsTag addNewsTag(NewsTag tag) {
        return this.tagRepository.addNewsTag(tag);
    }

    public List<NewsTag> allNewsTags() {
        return this.tagRepository.allNewsTags();
    }

    public NewsTag findNewsTag(Integer id) {
        return this.tagRepository.findNewsTag(id);
    }

    public void deleteNewsTag(Integer id) {
        this.tagRepository.deleteNewsTag(id);
    }
}
