package App.Gonggam.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

@Entity
@Table(name = "Member")

public class Member {
    private String Member_NickName;
    private String Member_Id;
    private String Member_password;
    private ArrayList<String> Member_accountBook = new ArrayList<String>();
    private String Member_token;

    @JsonCreator
    public Member(
            @JsonProperty("Id") String member_Id,
            @JsonProperty("Password") String member_password,
            @JsonProperty("NickName") String member_NickName,
            @JsonProperty("Token") String member_token) {
        Member_NickName = member_NickName;
        Member_Id = member_Id;
        Member_password = member_password;
        Member_token = member_token;
    }

    public Member(
            String member_Id,
            String member_password,
            String member_NickName,
            ArrayList<String> member_accountBook,
            String member_token) {
        Member_NickName = member_NickName;
        Member_Id = member_Id;
        Member_password = member_password;
        Member_accountBook = member_accountBook;
        Member_token = member_token;
    }

    public String getMember_NickName() {
        return Member_NickName;
    }

    public void setMember_NickName(String NickName) {
        this.Member_NickName = NickName;
    }

    public String getMember_Id() {
        return Member_Id;
    }

    public void setMember_Id(String Id) {
        this.Member_Id = Id;
    }

    public String getMember_password() {
        return Member_password;
    }

    public void setMember_password(String password) {
        this.Member_password = password;
    }

    public ArrayList<String> getMember_accountBook() {
        return Member_accountBook;
    }

    public void setMember_accountBook(ArrayList<String> accountBook) {
        this.Member_accountBook = accountBook;
    }

    public void addMember_accountBook(String accountBook) {
        this.Member_accountBook.add(accountBook);
    }

    public void delMember_accountBook(Integer loc) {
        this.Member_accountBook.remove(loc);
    }

    public String getMember_token() {
        return Member_token;
    }

    public void setMember_token(String member_token) {
        Member_token = member_token;
    }
}
