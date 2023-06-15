package App.Gonggam.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Entity
@Table(name = "Post")

/*
 * 사용 내역 글을 담는 Post 클래스입니다.
 * 하나의 가계부는 하나의 사용내역 테이블을 가지며 해당 테이블의 한 행 데이터에 맞춘 클래스입니다.
 * 
 * @@ 표시는 필수로 있어야 하는 항목 X null
 */
public class Post {
    private Long PostId; // @@ 사용 내역의 행, 순번을 나타내는 번호입니다.
    private String PostTag; // 사용 내역의 소비된 활동을 나타냄 [ ex) 축제준비, 체육대회 ]
    private boolean PostType; // @@ 사용 내역의 수입 지출을 나타냄 [true = 수입, false = 지출]
    private String PostDate; // @@ 사용된 날짜를 기입 [ ex)2023/01/01 ]
    private String useDate; // @@ 사용된 날짜를 기입 [ ex)2023/01/01 ]
    private String PostTitle; // @@ 사용내역의 제목
    private String PostText; // 내용 내역의 텍스트
    private ArrayList<String> PostImage = new ArrayList<String>(); // 사용내역에 첨부되는 이미지 경로
    // 이미지 경로는 ,로 구분되며 순번에 인식됨 ex) ["path/img1","path/img2","path/img3"]
    private Long PostTotalBudget; // @@ 그때 당시 총액
    private Long PostUsedBudget; // @@ 사용된 금액

    public Post() {
    }

    public Long getPostId() {
        return PostId;
    }

    public void setPostId(Long postId) {
        PostId = postId;
    }

    public String getPostTag() {
        return PostTag;
    }

    public void setPostTag(String postTag) {
        PostTag = postTag;
    }

    public boolean getPostType() {
        return PostType;
    }

    public void setPostType(boolean postType) {
        PostType = postType;
    }

    public String getPostDate() {
        return PostDate;
    }

    public void setPostDate(String postDate) {
        PostDate = postDate;
    }

    public void setPostDate(Date postDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        PostDate = dateFormat.format(postDate);
    }

    public String getPostTitle() {
        return PostTitle;
    }

    public void setPostTitle(String postTitle) {
        PostTitle = postTitle;
    }

    public String getPostText() {
        return PostText;
    }

    public void setPostText(String postText) {
        PostText = postText;
    }

    public ArrayList<String> getPostImage() {
        return PostImage;
    }

    public void setPostImage(ArrayList<String> postImage) {
        PostImage = postImage;
    }

    public Long getPostUsedBudget() {
        return PostUsedBudget;
    }

    public void setPostUsedBudget(Long postUsedBudget) {
        PostUsedBudget = postUsedBudget;
    }

    public Long getPostTotalBudget() {
        return PostTotalBudget;
    }

    public void setPostTotalBudget(Long postTotalBudget) {
        PostTotalBudget = postTotalBudget;
    }

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

}