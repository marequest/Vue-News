package rs.raf.demo.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class NewsTag {

    private Integer id;

    private Integer news_id;

    private Integer tag_id;

    public NewsTag() {
    }

    public NewsTag(Integer id, Integer news_id, Integer tag_id) {
        this.id = id;
        this.news_id = news_id;
        this.tag_id = tag_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNews_id() {
        return news_id;
    }

    public void setNews_id(Integer news_id) {
        this.news_id = news_id;
    }

    public Integer getTag_id() {
        return tag_id;
    }

    public void setTag_id(Integer tag_id) {
        this.tag_id = tag_id;
    }
}
