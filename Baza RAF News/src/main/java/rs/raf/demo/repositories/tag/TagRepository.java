package rs.raf.demo.repositories.tag;


import rs.raf.demo.entities.Tag;

import java.util.List;

public interface TagRepository {
    public Tag addTag(Tag tag);
    public List<Tag> allTags();
    public Tag findTag(Integer id);
    public void deleteTag(Integer id);
}
