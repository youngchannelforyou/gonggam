package App.Gonggam.service;

import java.sql.*;
import App.Gonggam.model.Member;

import java.util.ArrayList;
import java.util.UUID;

public class MemberService {
    String URL = "jdbc:mysql://selab.hknu.ac.kr:51714/2023_pbl3";
    String USERNAME = "pbl3_team5";
    String SQL_PASSWORD = "12345678";

    // DB에 멤버 추가

    public boolean AddMember(Member newMember) {

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            // 데이터 추가
            String insertSql = "INSERT INTO Team5_Member (Id, Password, NickName) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, newMember.getMember_Id());
                insertStmt.setString(2, newMember.getMember_password());
                insertStmt.setString(3, newMember.getMember_NickName());
                insertStmt.executeUpdate();

                System.out.println("데이터가 추가되었습니다.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Member GetMember(String Id) {
        Member member = null;
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            // 데이터 검색
            String selectSql = "SELECT * FROM Team5_Member WHERE Id = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, Id);

                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        // ResultSet에서 데이터 추출
                        String member_id = rs.getString("Id");
                        String member_password = rs.getString("Password");
                        String member_nickName = rs.getString("NickName");
                        String member_accountList = rs.getString("AccountList");

                        ArrayList<String> accountBook = new ArrayList<String>();
                        if (member_accountList != null && !member_accountList.isEmpty()) {
                            String[] accountListArray = member_accountList.split("/");

                            for (String account : accountListArray) {
                                accountBook.add(account);
                            }
                        }

                        String token = UUID.randomUUID().toString();
                        token = token + member_id;

                        member = new Member(member_id, member_password, member_nickName, accountBook, token);

                        System.out.println("검색된 멤버 정보: " + member);
                        return member;
                    } else {
                        System.out.println("멤버를 찾을 수 없습니다.");
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }

    // 특정 멤버의 가계부를 추가하는 방법
    public void AddACountBookInMember(String Member, String AccountBook) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String memberEmail = Member; // 멤버 이메일
            String accountBookName = AccountBook; // 추가할 AccountBook의 이름
            String updateMemberSql = "UPDATE Team5_Member SET AccountList = CONCAT(AccountList, ?) WHERE Email = ?";

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

}

// Email 변경
// public void ChangeEmail(String Member, String Email) {
// try (Connection conn = DriverManager.getConnection(URL, USERNAME,
// SQL_PASSWORD)) {
// String memberEmail = Member; // 멤버 이메일
// String ChangeEmail = Email; // 추가할 AccountBook의 이름
// String updateMemberSql = "UPDATE Team5_Member SET Email = ? WHERE Email = ?";
// // SQL 쿼리문
// try (PreparedStatement updateStmt = conn.prepareStatement(updateMemberSql)) {
// updateStmt.setString(1, ChangeEmail);
// updateStmt.setString(2, memberEmail);
// updateStmt.executeUpdate();
// System.out.println(memberEmail + "의 AccountList에 " + ChangeEmail + "이(가)
// 추가되었습니다.");
// }
// } catch (SQLException e) {
// e.printStackTrace();
// }
// }

// // 이름 변경
// public void ChangeName(String Member, String Name) {
// try (Connection conn = DriverManager.getConnection(URL, USERNAME,
// SQL_PASSWORD)) {
// String memberEmail = Member; // 멤버 이메일
// String ChangeName = Name; // 추가할 AccountBook의 이름
// String updateMemberSql = "UPDATE Team5_Member SET Name = ? WHERE Email = ?";
// // SQL 쿼리문
// try (PreparedStatement updateStmt = conn.prepareStatement(updateMemberSql)) {
// updateStmt.setString(1, ChangeName);
// updateStmt.setString(2, memberEmail);
// updateStmt.executeUpdate();
// System.out.println(memberEmail + "의 AccountList에 " + ChangeName + "이(가)
// 추가되었습니다.");
// }
// } catch (SQLException e) {
// e.printStackTrace();
// }
// }