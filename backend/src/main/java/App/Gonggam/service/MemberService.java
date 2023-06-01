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
                insertStmt.setString(1, newMember.getMemberId());
                insertStmt.setString(2, newMember.getMemberPassword());
                insertStmt.setString(3, newMember.getMemberNickName());
                insertStmt.executeUpdate();

                System.out.println("데이터가 추가되었습니다.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Member LoginMember(String Id) {
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

                        ArrayList<String> manage_accountBook = new ArrayList<String>();
                        ArrayList<String> parti_accountBook = new ArrayList<String>();
                        if (member_accountList != null && !member_accountList.isEmpty()) {
                            String[] accountListArray = member_accountList.split("/");

                            for (String account : accountListArray) {
                                if (account.startsWith("(M)")) {
                                    manage_accountBook.add(account.substring(3));
                                } else if (account.startsWith("(P)")) {
                                    parti_accountBook.add(account.substring(3));
                                }
                            }
                        }

                        String token = UUID.randomUUID().toString();
                        token = token + member_id;

                        String sql = "UPDATE member SET column_name = ? WHERE id = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                            // 쿼리 매개변수 설정
                            stmt.setString(1, token);
                            stmt.setString(2, member_id);

                            // 쿼리 실행
                            int rowsAffected = stmt.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("토큰 값 변경 성공");
                            } else {
                                System.out.println("토큰 값 변경 실패");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        member = new Member(member_id, member_password, member_nickName, manage_accountBook,
                                parti_accountBook, token);

                        System.out.println("검색된 멤버 정보: " + member);
                        return member;
                    } else {
                        System.out.println("멤버를 찾을 수 없습니다.");
                    }
                }

            }
        } catch (

        SQLException e) {
            e.printStackTrace();
        }
        return member;
    }

    // 특정 멤버의 가계부를 추가하는 방법
    public void AddACountBookInMember(String Member, String AccountBook) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String memberEmail = Member; // 멤버 이메일
            String accountBookName = "(M)" + AccountBook; // 추가할 AccountBook의 이름
            String updateMemberSql = "UPDATE Team5_Member SET AccountList = CONCAT(AccountList, ?) WHERE Email = ?";
            String updateaccountBookSql = "UPDATE Team5_Member SET Member = CONCAT(Member, ?) WHERE Name = ?";
            // 멤버 업데이트
            try (PreparedStatement updateStmt = conn.prepareStatement(updateMemberSql)) {
                updateStmt.setString(1, "," + accountBookName);
                updateStmt.setString(2, memberEmail);
                updateStmt.executeUpdate();
                System.out.println(memberEmail + "의 AccountList에 " + accountBookName + "이(가) 추가되었습니다.");
            }
            // 어카운트북 업데이트
            try (PreparedStatement updateStmt = conn.prepareStatement(updateaccountBookSql)) {
                updateStmt.setString(1, "," + memberEmail);
                updateStmt.setString(2, AccountBook);
                updateStmt.executeUpdate();
                System.out.println(AccountBook + "의 Member에 " + memberEmail + "이(가) 추가되었습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}