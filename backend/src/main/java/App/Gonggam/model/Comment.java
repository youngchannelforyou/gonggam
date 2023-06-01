package App.Gonggam.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * 댓글을 담는 Comment 클래스입니다.
 * 하나의 가계부는 하나의 댓글 테이블을 가지며 해당 테이블의 한 행 데이터에 맞춘 클래스입니다.
 * 
 * @@ 표시는 필수로 있어야 하는 항목 X null
 */

@Entity
@Table(name = "AccountBook")
public class Comment {
    private Long CommentId; // 댓글 인식하는 id auto로 생성
    private String CommentMember; // 댓글을 작성한 멤버
    private int CommentPost; // 해당 댓글의 게시물 post 외래키
    private String CommentDate; // 댓글 생성 날짜
    private int CommentType; // 댓글이 대댓글인지 확인 최초 댓글 0 아니면 해당 댓글의 ID
    private String CommentText; // 댓글의 내용

    @JsonCreator
    public Comment(
            @JsonProperty("Id") Long comment_id,
            @JsonProperty("Member") String comment_member,
            @JsonProperty("Post") int comment_post,
            @JsonProperty("Date") String comment_date,
            @JsonProperty("Type") int comment_type,
            @JsonProperty("Text") String comment_text) {
        CommentId = comment_id;
        CommentMember = comment_member;
        CommentPost = comment_post;
        CommentDate = comment_date;
        CommentType = comment_type;
        CommentText = comment_text;
    }

    public Comment() {
    }

    public String getCommentMember() {
        return CommentMember;
    }

    public void setCommentMember(String commentMember) {
        CommentMember = commentMember;
    }

    public Long getCommentId() {
        return CommentId;
    }

    public void setCommentId(Long commentId) {
        CommentId = commentId;
    }

    public int getCommentPost() {
        return CommentPost;
    }

    public void setCommentPost(int commentPost) {
        CommentPost = commentPost;
    }

    public String getCommentDate() {
        return CommentDate;
    }

    public void setCommentDate(String commentDate) {
        CommentDate = commentDate;
    }

    public int getCommentType() {
        return CommentType;
    }

    public void setCommentType(int commentType) {
        CommentType = commentType;
    }

    public String getCommentText() {
        return CommentText;
    }

    public void setCommentText(String commentText) {
        CommentText = commentText;
    }
}
