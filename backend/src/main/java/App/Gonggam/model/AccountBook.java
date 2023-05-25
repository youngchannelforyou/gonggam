package App.Gonggam.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.ArrayList;
import App.Gonggam.service.MemberService;
import jakarta.persistence.Lob;

@Entity
@Table(name = "AccountBook")

public class AccountBook {
    MemberService memberservice = new MemberService();

    private String AccountBook_Name;
    private String AccountBook_public;
    private String AccountBook_main_manager;
    private long AccountBook_Budget;
    private String AccountBook_Member;
    private String AccountBook_Db;
    // private ArrayList<Member> Member_accountBook = new ArrayList<Member>();
    // private List<Member> AccountBook_list_member;
    // private List<Member> AccountBook_sub_manager;

    @JsonCreator
    public AccountBook(
            @JsonProperty("Name") String Name,
            @JsonProperty("Public") String Public,
            @JsonProperty("Budget") long Budget,
            @JsonProperty("Member") String bookMember,
            @JsonProperty("Manager") String bookManager) {
        AccountBook_Name = Name;
        AccountBook_public = Public;
        // AccountBook_main_manager = memberservice.GetMember(bookManager);
        AccountBook_main_manager = bookManager;
        if (Budget <= 0) {
            AccountBook_Budget = 0;
        } else {
            AccountBook_Budget = Budget;
        }
        if (bookManager != null) {
            // Member_accountBook = GetMemberList(bookMember);
            AccountBook_Member = bookMember;
        }
    }

    public AccountBook() {
    }

    public String getAccountBook_Db() {
        return AccountBook_Db;
    }

    public void setAccountBook_Db(String accountBook_Db) {
        AccountBook_Db = accountBook_Db;
    }

    public String getAccountBook_Name() {
        return AccountBook_Name;
    }

    public void setAccountBook_Name(String accountBook_Name) {
        AccountBook_Name = accountBook_Name;
    }

    public String getAccountBook_public() {
        return AccountBook_public;
    }

    public void setAccountBook_public(String accountBook_public) {
        AccountBook_public = accountBook_public;
    }

    public String getAccountBook_main_manager() {
        return AccountBook_main_manager;
    }

    public void setAccountBook_main_manager(String accountBook_main_manager) {
        AccountBook_main_manager = accountBook_main_manager;
    }

    public long getAccountBook_Budget() {
        return AccountBook_Budget;
    }

    public void setAccountBook_Budget(long accountBook_Budget) {
        AccountBook_Budget = accountBook_Budget;
    }

    public String getAccountBook_Member() {
        return AccountBook_Member;
    }

    public void setAccountBook_Member(String member_accountBook) {
        AccountBook_Member = member_accountBook;
    }

    // 멤버 , 로 구분해서 불러오는 코드
    // public ArrayList<Member> GetMemberList(String memberlist) {
    // String[] fruits = memberlist.split(",");
    // for (String fruit : fruits) {
    // Member member = memberservice.GetMember(fruit);
    // Member_accountBook.add(member);
    // }
    // return Member_accountBook;
    // }
}
