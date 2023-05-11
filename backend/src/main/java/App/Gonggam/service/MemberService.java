package App.Gonggam.service;

import java.sql.*;
import App.Gonggam.model.Member;;

public class MemberService {
    String URL = "jdbc:mysql://selab.hknu.ac.kr:51714/2023_1_pbl3";
    String USERNAME = "pbl3_team5";
    String SQL_PASSWORD = "12345678";

    // DB에 멤버 추가

    public void AddMember(Member newMember) {

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            // 데이터 추가
            String insertSql = "INSERT INTO TEAM5_Member (Id, Password, NicName, Age) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, newMember.getMember_Id());
                insertStmt.setString(2, newMember.getMember_password());
                insertStmt.setString(4, newMember.getMember_NickName());
                insertStmt.setString(5, newMember.getMember_Age());
                insertStmt.executeUpdate();

                System.out.println("데이터가 추가되었습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 특정 멤버의 가계부를 추가하는 방법
    public void AddACountBookInMember(String Member, String AccountBook) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String memberEmail = Member; // 멤버 이메일
            String accountBookName = AccountBook; // 추가할 AccountBook의 이름
            String updateMemberSql = "UPDATE Member SET AccountList = CONCAT(AccountList, ?) WHERE Email = ?";

            try (PreparedStatement updateStmt = conn.prepareStatement(updateMemberSql)) {
                updateStmt.setString(1, "," + accountBookName);
                updateStmt.setString(2, memberEmail);
                updateStmt.executeUpdate();
                System.out.println(memberEmail + "의 AccountList에 " + accountBookName + "이(가) 추가되었습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Email 변경
    public void ChangeEmail(String Member, String Email) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String memberEmail = Member; // 멤버 이메일
            String ChangeEmail = Email; // 추가할 AccountBook의 이름
            String updateMemberSql = "UPDATE Member SET Email = ? WHERE Email = ?"; // SQL 쿼리문
            try (PreparedStatement updateStmt = conn.prepareStatement(updateMemberSql)) {
                updateStmt.setString(1, ChangeEmail);
                updateStmt.setString(2, memberEmail);
                updateStmt.executeUpdate();
                System.out.println(memberEmail + "의 AccountList에 " + ChangeEmail + "이(가) 추가되었습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 이름 변경
    public void ChangeName(String Member, String Name) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String memberEmail = Member; // 멤버 이메일
            String ChangeName = Name; // 추가할 AccountBook의 이름
            String updateMemberSql = "UPDATE Member SET Name = ? WHERE Email = ?"; // SQL 쿼리문
            try (PreparedStatement updateStmt = conn.prepareStatement(updateMemberSql)) {
                updateStmt.setString(1, ChangeName);
                updateStmt.setString(2, memberEmail);
                updateStmt.executeUpdate();
                System.out.println(memberEmail + "의 AccountList에 " + ChangeName + "이(가) 추가되었습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
