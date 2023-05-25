package App.Gonggam.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

@Entity
@Table(name = "Post")

public class Post {
    private String Post_key;
    private String Post_type;
    private String Post_date;
    private String Post_title;
    private String Post_text;
    private String Post_comment;
    private ArrayList<BufferedImage> Post_image_list = new ArrayList<BufferedImage>();
    private Long Post_used_budget;

    @JsonCreator
    public Post(
            @JsonProperty("Key") String post_key,
            @JsonProperty("Type") String post_type,
            @JsonProperty("Date") String post_date,
            @JsonProperty("Title") String post_title,
            @JsonProperty("Text") String post_text,
            @JsonProperty("Comment") String post_comment,
            @JsonProperty("Image") ArrayList<BufferedImage> post_image,
            @JsonProperty("Used_Budget") Long post_used_budget) {
        Post_key = post_key;
        Post_type = post_type;
        Post_date = post_date;
        Post_title = post_title;
        Post_text = post_text;
        Post_comment = post_comment;
        Post_image_list = post_image;
        Post_used_budget = post_used_budget;
    }

    public Post() {
    }

    public String getPost_key() {
        return Post_key;
    }

    public void setPost_key(String post_key) {
        Post_key = post_key;
    }

    public String getPost_type() {
        return Post_type;
    }

    public void setPost_type(String post_type) {
        Post_type = post_type;
    }

    public String getPost_date() {
        return Post_date;
    }

    public void setPost_date(String post_date) {
        Post_date = post_date;
    }

    public String getPost_title() {
        return Post_title;
    }

    public void setPost_title(String post_title) {
        Post_title = post_title;
    }

    public String getPost_text() {
        return Post_text;
    }

    public void setPost_text(String post_text) {
        Post_text = post_text;
    }

    public String getPost_comment() {
        return Post_comment;
    }

    public void setPost_comment(String post_comment) {
        Post_comment = post_comment;
    }

    public ArrayList<BufferedImage> getPost_image_list() {
        return Post_image_list;
    }

    public void setPost_image_list(ArrayList<BufferedImage> post_image_list) {
        Post_image_list = post_image_list;
    }

    public Long getPost_used_budget() {
        return Post_used_budget;
    }

    public void setPost_used_budget(Long post_used_budget) {
        Post_used_budget = post_used_budget;
    }

}