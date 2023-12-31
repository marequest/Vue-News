package rs.raf.demo.services;


import rs.raf.demo.entities.Tag;
import rs.raf.demo.repositories.tag.TagRepository;

import javax.inject.Inject;
import java.util.List;

public class TagService {

    public TagService() {
        System.out.println(this);
    }

    @Inject
    private TagRepository tagRepository;

    public Tag addTag(Tag tag) {
        return this.tagRepository.addTag(tag);
    }

    public List<Tag> allTags() {
        return this.tagRepository.allTags();
    }

    public Tag findTag(Integer id) {
        return this.tagRepository.findTag(id);
    }

    public void deleteTag(Integer id) {
        this.tagRepository.deleteTag(id);
    }
}
