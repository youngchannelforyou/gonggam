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
    private String Member_Age;
    private String Member_Id;
    private String Member_password;
    private ArrayList<AccountBook> Member_accountBook = new ArrayList<AccountBook>();

    @JsonCreator
    public Member(
            @JsonProperty("NickName") String member_NickName,
            @JsonProperty("Age") String member_Age,
            @JsonProperty("Id") String member_Id,
            @JsonProperty("password") String member_password) {
        Member_NickName = member_NickName;
        Member_Age = member_Age;
        Member_Id = member_Id;
        Member_password = member_password;
    }

    public String getMember_NickName() {
        return Member_NickName;
    }

    public void setMember_NickName(String NickName) {
        this.Member_NickName = NickName;
    }

    public String getMember_Age() {
        return Member_Age;
    }

    public void setMember_Age(String Age) {
        this.Member_Age = Age;
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

    public ArrayList<AccountBook> getMember_accountBook() {
        return Member_accountBook;
    }

    public void setMember_accountBook(ArrayList<AccountBook> accountBook) {
        this.Member_accountBook = accountBook;
    }

    public void addMember_accountBook(AccountBook accountBook) {
        this.Member_accountBook.add(accountBook);
    }

    public void delMember_accountBook(Integer loc) {
        this.Member_accountBook.remove(loc);
    }

}
