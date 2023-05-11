package App.Gonggam.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "AccountBook")
public class AccountBook {
    private String AccountBook_Name;
    private Boolean AccountBook_public;
    private Member AccountBook_main_manager;
    private List<Member> AccountBook_list_member;
    private List<Member> AccountBook_sub_manager;

    public AccountBook(String Name, Boolean public_type, Member main_manager) {
        this.AccountBook_Name = Name;
        this.AccountBook_public = public_type;
        this.AccountBook_main_manager = main_manager;
    }

    public void setAccountBook_Name(String Name) {
        this.AccountBook_Name = Name;
    }

    public String getAccountBook_Name() {
        return AccountBook_Name;
    }

    public void setAccountBook_public(Boolean public_type) {
        this.AccountBook_public = public_type;
    }

    public Boolean getAccountBook_public() {
        return AccountBook_public;
    }

    public void setAccountBook_main_manager(Member main_manager) {
        this.AccountBook_main_manager = main_manager;
    }

    public Member getAccountBook_main_manager() {
        return AccountBook_main_manager;
    }
}
