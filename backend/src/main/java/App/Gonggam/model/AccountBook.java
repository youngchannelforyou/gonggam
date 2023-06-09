package App.Gonggam.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import App.Gonggam.service.MemberService;

/*
한 가계부의 정보를 담는 AccountBook 클래스입니다.
처음 시작될 때 생성되는 AccountBook 테이블에 대한 한 행의 데이터를 담는 클래스.
@@ 표시는 필수로 있어야 하는 항목 X null
*/
@Entity
@Table(name = "AccountBook")
public class AccountBook {
    MemberService memberservice = new MemberService();
    private String AccountBookLogo; // @@ 가계부 로고
    private String AccountBookName; // @@ 가계부 이름
    private boolean AccountBookPublic; // true=> public false = private
    private String AccountBookMainManager; // @@ 실제 매니져
    private ArrayList<String> AccountBookSubManager; // 서브 매니져
    private ArrayList<String> AccountBook_Member; // 멤버
    private long AccountBook_Budget; // 현재 예산
    private long Membercount; // 현재 예산
    private int URL; // @@ 가계부 URL

    public int getURL() {
        return URL;
    }

    public void setURL(int uRL) {
        URL = uRL;
    }

    @JsonCreator
    public AccountBook(
            @JsonProperty("Name") String Name,
            @JsonProperty("Public") Boolean Public,
            @JsonProperty("Budget") long Budget,
            @JsonProperty("Manager") String bookManager) {
        AccountBookName = Name;
        if (Public == null) {
            Public = true;
        }
        AccountBookPublic = Public;
        AccountBookMainManager = bookManager;
        AccountBook_Budget = Budget;
    }

    public AccountBook() {
    }

    public ArrayList<String> getAccountBookSubManager() {
        return AccountBookSubManager;
    }

    public void setAccountBookSubManager(ArrayList<String> accountBookSubManager) {
        AccountBookSubManager = accountBookSubManager;
    }

    public String getAccountBookName() {
        return AccountBookName;
    }

    public void setAccountBookName(String accountBook_Name) {
        AccountBookName = accountBook_Name;
    }

    public boolean getAccountBookPublic() {
        return AccountBookPublic;
    }

    public void setAccountBookPublic(Boolean accountBook_public) {
        AccountBookPublic = accountBook_public;
    }

    public String getAccountBookMainManager() {
        return AccountBookMainManager;
    }

    public void setAccountBookMainManager(String accountBook_main_manager) {
        AccountBookMainManager = accountBook_main_manager;
    }

    public long getAccountBook_Budget() {
        return AccountBook_Budget;
    }

    public void setAccountBook_Budget(long accountBook_Budget) {
        AccountBook_Budget = accountBook_Budget;
    }

    public ArrayList<String> getAccountBook_Member() {
        return AccountBook_Member;
    }

    public void setAccountBook_Member(ArrayList<String> member_accountBook) {
        AccountBook_Member = member_accountBook;
    }

    public long getMembercount() {
        return Membercount;
    }

    public void setMembercount(long membercount) {
        Membercount = membercount;
    }

    public MemberService getMemberservice() {
        return memberservice;
    }

    public void setMemberservice(MemberService memberservice) {
        this.memberservice = memberservice;
    }

    public String getAccountBookLogo() {
        return AccountBookLogo;
    }

    public void setAccountBookLogo(String accountBookLogo) {
        AccountBookLogo = accountBookLogo;
    }

    public void setAccountBookPublic(boolean accountBookPublic) {
        AccountBookPublic = accountBookPublic;
    }
}