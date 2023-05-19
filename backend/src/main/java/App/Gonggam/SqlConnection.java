package App.Gonggam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlConnection {
    public void Connection() {
        String url = "jdbc:mysql://selab.hknu.ac.kr:51714/2023_pbl3";
        String username = "pbl3_team5";
        String sql_password = "12345678";

        List<String> MemberexistingColumns = new ArrayList<>(); /// DBUG 형

        try (Connection conn = DriverManager.getConnection(url, username, sql_password)) {
            // Member 테이블 생성
            String createMemberTableSql = "CREATE TABLE IF NOT EXISTS Team5_Member ("
                    + "Id VARCHAR(320) NOT NULL, "
                    + "Password VARCHAR(100)  NOT NULL, "
                    + "NickName VARCHAR(100) NOT NULL, "
                    + "AccountList TEXT, "
                    + "PRIMARY KEY (Id))";
            try (PreparedStatement createTableStmt = conn.prepareStatement(createMemberTableSql)) {
                createTableStmt.executeUpdate();
                if (createTableStmt.getUpdateCount() == 0) {
                    System.out.println("Member 테이블이 존재합니다.");
                    String showColumnsSql = "SHOW COLUMNS FROM Team5_Member";
                    try (PreparedStatement showColumnsStmt = conn.prepareStatement(showColumnsSql)) {
                        ResultSet rs = showColumnsStmt.executeQuery();
                        while (rs.next()) {
                            MemberexistingColumns.add(rs.getString("Field"));
                        }
                    }
                } else {
                    System.out.println("Member 테이블이 생성되었습니다.");
                }
            }

            // AccountBook 테이블 생성
            String createAccountBookTableSql = "CREATE TABLE IF NOT EXISTS Team5_AccountBook ("
                    + "Name VARCHAR(320) NOT NULL, "
                    + "Public VARCHAR(100) NOT NULL, "
                    + "Manager TEXT NOT NULL, "
                    + "Budget BIGINT, "
                    + "Member TEXT, "
                    + "PRIMARY KEY (Name))";
            try (PreparedStatement createTableStmt = conn.prepareStatement(createAccountBookTableSql)) {
                createTableStmt.executeUpdate();
                if (createTableStmt.getUpdateCount() == 0) {
                    System.out.println("Team5_AccountBook 테이블이 존재합니다.");
                    String showColumnsSql = "SHOW COLUMNS FROM Team5_Member";
                    try (PreparedStatement showColumnsStmt = conn.prepareStatement(showColumnsSql)) {
                        ResultSet rs = showColumnsStmt.executeQuery();
                        while (rs.next()) {
                            MemberexistingColumns.add(rs.getString("Field"));
                        }
                    }
                } else {
                    System.out.println("Team5_AccountBook 테이블이 생성되었습니다.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// sql 서버 시작 mysql.server start
// sql 서버 종료 mysql.server stop
// sql 종료 exit
// CREATE DATABASE [데이터베이스명];

// // 추가할 컬럼 정보 DBUG
// String newColumnName = "new_column_name";
// String newColumnDataType = "VARCHAR(100)";

// // 추가할 컬럼이 존재하지 않으면 추가
// if (!existingColumns.contains(newColumnName)) {
// String alterTableSql = "ALTER TABLE TEAM5_Member ADD COLUMN " + newColumnName
// + " " + newColumnDataType;
// try