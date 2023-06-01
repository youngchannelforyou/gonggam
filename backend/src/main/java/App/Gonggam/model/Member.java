package App.Gonggam.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/* 
한 멤버의 정보를 담는 Member 클래스입니다.
처음 시작될 때 생성되는 Member 테이블에 대한 한 행의 데이터를 담는 클래스.
@@ 표시는 필수로 있어야 하는 항목 X null
*/
@Entity
@Table(name = "Member")
public class Member {
    private String MemberId; // @@ 멤버의 id
    private String MemberPassword; // @@ 멤버의 pw
    private String MemberNickName; // @@ 멤버의 닉네임
    private ArrayList<String> MemberMAccountBook = new ArrayList<String>(); // 멤버가 관리하는 가계부
    private ArrayList<String> MemberPAccountBook = new ArrayList<String>(); // 멤버가 속한 가계부
    private String MemberToken; // 멤버의 토큰

    @JsonCreator // json 사용시
    public Member(
            @JsonProperty("Id") String member_id,
            @JsonProperty("Password") String member_password,
            @JsonProperty("NickName") String member_nickname,
            @JsonProperty("Token") String member_token) {
        MemberId = member_id;
        MemberPassword = member_password;
        MemberNickName = member_nickname;
        MemberToken = member_token;
    }

    public Member(
            String member_id,
            String member_password,
            String member_nickname,
            ArrayList<String> member_maccountBook,
            ArrayList<String> member_paccountBook,
            String member_token) {
        MemberNickName = member_nickname;
        MemberId = member_id;
        MemberPassword = member_password;
        MemberMAccountBook = member_maccountBook;
        MemberPAccountBook = member_paccountBook;
        MemberToken = member_token;
    }

    public ArrayList<String> getMemberPAccountBook() {
        return MemberPAccountBook;
    }

    public void setMemberPAccountBook(ArrayList<String> memberPAccountBook) {
        MemberPAccountBook = memberPAccountBook;
    }

    public String getMemberNickName() {
        return MemberNickName;
    }

    public void setMemberNickName(String NickName) {
        this.MemberNickName = NickName;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String Id) {
        this.MemberId = Id;
    }

    public String getMemberPassword() {
        return MemberPassword;
    }

    public void setMemberPassword(String password) {
        this.MemberPassword = password;
    }

    public ArrayList<String> getMemberMAccountBook() {
        return MemberMAccountBook;
    }

    public void setMemberMAccountBook(ArrayList<String> accountBook) {
        this.MemberMAccountBook = accountBook;
    }

    public void addMember_accountBook(String accountBook) {
        this.MemberMAccountBook.add(accountBook);
    }

    public void delMember_accountBook(Integer loc) {
        this.MemberMAccountBook.remove(loc);
    }

    public String getMemberToken() {
        return MemberToken;
    }

    public void setMemberToken(String member_token) {
        MemberToken = member_token;
    }
}
