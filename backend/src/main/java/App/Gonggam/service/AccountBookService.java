package App.Gonggam.service;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import App.Gonggam.model.AccountBook;
import App.Gonggam.model.Comment;
import App.Gonggam.model.Community;
import App.Gonggam.model.Member;
import App.Gonggam.model.Notice;
import App.Gonggam.model.Post;

public class AccountBookService {
    String URL = "jdbc:mysql://selab.hknu.ac.kr:51714/2023_pbl3";
    String USERNAME = "pbl3_team5";
    String SQL_PASSWORD = "12345678";
    MemberService mService = new MemberService();
    // DB에 멤버 추가

    public boolean addBook(AccountBook newBook) {

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            // 데이터 추가
            String insertSql = "INSERT INTO Team5_AccountBook (Name, Public, Manager, IconImage, Membercount, Budget) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setString(1, newBook.getAccountBookName());
                insertStmt.setBoolean(2, newBook.getAccountBookPublic());
                insertStmt.setString(3, newBook.getAccountBookMainManager());
                insertStmt.setString(4, newBook.getAccountBookLogo());
                insertStmt.setLong(5, newBook.getMembercount());
                insertStmt.setLong(6, 0L);
                insertStmt.executeUpdate();

                System.out.println("데이터가 추가되었습니다.");

                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                String generatedId = null;

                if (generatedKeys.next()) {
                    int generated = generatedKeys.getInt(1);
                    generatedId = String.valueOf(generated);
                } else {
                    throw new SQLException("Failed to retrieve generated key.");
                }

                String Comment_tableName = "Team5_" + generatedId + "_Comment";
                String Budget_tableName = "Team5_" + generatedId + "_Budget";
                String Post_tableName = "Team5_" + generatedId + "_Post";
                String Notice_tableName = "Team5_" + generatedId + "_Notice";
                String Community_tableName = "Team5_" + generatedId + "_Community";

                String selectSql = "SELECT AccountList FROM Team5_Member WHERE Id = ?";
                try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                    selectStmt.setString(1, newBook.getAccountBookMainManager());

                    try (ResultSet rs = selectStmt.executeQuery()) {
                        if (rs.next()) {
                            String existingAccountList = rs.getString("AccountList");
                            String updatedAccountList;
                            if (existingAccountList != null) {
                                // 기존의 AccountList가 비어있지 않다면, 새로운 데이터를 이어서 추가합니다.
                                updatedAccountList = existingAccountList + "/" + "(M)" + generatedId;
                            } else {
                                // 기존의 AccountList가 비어있다면, 새로운 데이터를 그대로 할당합니다.
                                updatedAccountList = "(M)" + generatedId;
                            }

                            String updateSql = "UPDATE Team5_Member SET AccountList = ? WHERE Id = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                                updateStmt.setString(1, updatedAccountList);
                                updateStmt.setString(2, newBook.getAccountBookMainManager());
                                updateStmt.executeUpdate();

                                System.out.println("AccountList가 업데이트되었습니다.");
                            }
                        } else {
                            System.out.println("해당 Id의 멤버가 존재하지 않습니다.");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
                    String sql = "CREATE TABLE " + Notice_tableName + " (" +
                            "Num BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                            "Member VARCHAR(255) NOT NULL, " +
                            "Date TIMESTAMP DEFAULT NOW(), " +
                            "Title TEXT, " +
                            "Text TEXT " +
                            ")";

                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sql);
                    System.out.println("Notice 테이블이 성공적으로 생성되었습니다.");
                } catch (SQLException e) {
                    System.out.println("Notice 테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
                }

                try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
                    String sql = "CREATE TABLE " + Community_tableName + " (" +
                            "Num BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                            "Member VARCHAR(255) NOT NULL, " +
                            "Date TIMESTAMP DEFAULT NOW(), " +
                            "Title TEXT, " +
                            "Text TEXT " +
                            ")";

                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sql);
                    System.out.println("Community 테이블이 성공적으로 생성되었습니다.");
                } catch (SQLException e) {
                    System.out.println("Community 테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
                }

                try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
                    String sql = "CREATE TABLE " + Comment_tableName + " (" +
                            "Num BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                            "Member VARCHAR(255) NOT NULL, " +
                            "PostType VARCHAR(255) NOT NULL, " +
                            "Post BIGINT NOT NULL, " +
                            "Date TIMESTAMP DEFAULT NOW(), " +
                            "Type BIGINT NOT NULL, " +
                            "Text TEXT " +
                            ")";

                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sql);
                    System.out.println("Comment 테이블이 성공적으로 생성되었습니다.");
                } catch (SQLException e) {
                    System.out.println("Comment 테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
                }

                try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
                    String sql = "CREATE TABLE " + Budget_tableName + " (" +
                            "Num BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                            "Date TIMESTAMP DEFAULT NOW(), " +
                            "Budget BIGINT " +
                            ")";
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sql);
                    System.out.println("Budget 테이블이 성공적으로 생성되었습니다.");
                } catch (SQLException e) {
                    System.out.println("Budget 테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
                }

                // 이벤트 생성 코드
                String eventSql = "CREATE EVENT " + generatedId + "_daily_budget_update " +
                        "ON SCHEDULE EVERY 1 MINUTE " +
                        "STARTS CURRENT_TIMESTAMP + INTERVAL 1 DAY " +
                        "DO " +
                        "BEGIN " +
                        "INSERT INTO " + Budget_tableName + " (Date, Budget) " +
                        "SELECT CURDATE(), Budget " +
                        "FROM Team5_AccountBook " +
                        "WHERE URL = '" + generatedId + "'; " +
                        "END;";

                // 이벤트 생성 쿼리 실행
                try (PreparedStatement eventStmt = conn.prepareStatement(eventSql)) {
                    eventStmt.executeUpdate();
                    System.out.println("이벤트가 성공적으로 생성되었습니다.");
                } catch (SQLException e) {
                    System.out.println("이벤트 생성 중 오류가 발생했습니다: " + e.getMessage());
                }

                try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
                    String sql = "CREATE TABLE " + Post_tableName + " (" +
                            "Num BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                            "Tag VARCHAR(255) NOT NULL, " +
                            "Type BOOL NULL, " +
                            "Date TIMESTAMP DEFAULT NOW(), " +
                            "useDate TIMESTAMP, " +
                            "Title VARCHAR(255) NOT NULL, " +
                            "Text TEXT, " +
                            "Image TEXT, " +
                            "Total_Budget BIGINT, " +
                            "Used_Budget BIGINT" +
                            ")";

                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sql);
                    System.out.println("Post 테이블이 성공적으로 생성되었습니다.");
                } catch (SQLException e) {
                    System.out.println("Post 테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String addMember(String Manager, String Member, String TableName) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String selectSql = "SELECT * FROM Team5_AccountBook WHERE URL = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, TableName);

                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        if (rs.getString("Manager").equals(mService.FindMemberUseToken(Manager))) {
                            String Memberid = Member;
                            String existingMember = rs.getString("Member");
                            String updatedMember;
                            if (existingMember != null) {
                                // 기존의 AccountList가 비어있지 않다면, 새로운 데이터를 이어서 추가합니다.
                                updatedMember = existingMember + "/" + Memberid;
                            } else {
                                // 기존의 AccountList가 비어있다면, 새로운 데이터를 그대로 할당합니다.
                                updatedMember = Memberid;
                            }

                            String updateSql = "UPDATE Team5_AccountBook SET Member = ? WHERE URL = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                                updateStmt.setString(1, updatedMember);
                                updateStmt.setString(2, TableName);
                                updateStmt.executeUpdate();

                                System.out.println("Team5_AccountBook가 업데이트되었습니다.");
                            }

                            String selectSql2 = "SELECT * FROM Team5_Member WHERE Id = ?";
                            System.out.println("Memberid.: " + Memberid);
                            try (PreparedStatement selectStmt2 = conn.prepareStatement(selectSql2)) {
                                selectStmt2.setString(1, Memberid);
                                try (ResultSet rs2 = selectStmt2.executeQuery()) {
                                    if (rs2.next()) {
                                        /////
                                        String existingAccountList = rs2.getString("AccountList");
                                        String updatedAccountList;
                                        if (existingAccountList != null) {
                                            // 기존의 AccountList가 비어있지 않다면, 새로운 데이터를 이어서 추가합니다.
                                            updatedAccountList = existingMember + "/" + "(P)" + TableName;
                                        } else {
                                            // 기존의 AccountList가 비어있다면, 새로운 데이터를 그대로 할당합니다.
                                            updatedAccountList = "(P)" + TableName;
                                        }

                                        String updateSql2 = "UPDATE Team5_Member SET AccountList = ? WHERE Id = ?";
                                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql2)) {
                                            updateStmt.setString(1, updatedAccountList);
                                            updateStmt.setString(2, Memberid);
                                            updateStmt.executeUpdate();

                                            System.out.println("Team5_Member가 업데이트되었습니다.");
                                        }

                                        String updateSql3 = "UPDATE Team5_AccountBook SET Membercount = ? WHERE URL = ?";
                                        // SELECT 쿼리 실행

                                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql3)) {
                                            ResultSet rs3 = selectStmt.executeQuery();
                                            if (rs3.next()) {
                                                // 현재 Membercount 값을 가져옴
                                                int memberCount = rs3.getInt("Membercount");

                                                // Membercount 값을 1 증가시킴
                                                int newMemberCount = memberCount + 1;

                                                // UPDATE 쿼리 실행하여 Membercount 값을 증가시킨다
                                                updateStmt.setInt(1, newMemberCount);
                                                updateStmt.setString(2, TableName);
                                                updateStmt.executeUpdate();
                                            }

                                        }

                                        return "정상";

                                    } else {
                                        System.out.println("selectSql2에러.");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    return "서버에러2-1";
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                                return "서버에러2";
                            }

                        } else {
                            System.out.println("관리자 권한 없음.");
                        }
                    } else {
                        System.out.println("관리자 Id의 멤버가 존재하지 않습니다.");
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "서버에러";
            }
        } catch (SQLException e) {
            System.out.println("테이블 이름 에러.");
            return "테이블 에러";
        }
        return "실패";
    }

    public AccountBook getBook(String name) {
        AccountBook book = null;
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String sql = "SELECT * FROM Team5_AccountBook WHERE URL = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);

                // Execute the query
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Create a new AccountBook object and populate it with the retrieved data
                        book = new AccountBook();
                        book.setAccountBookName(rs.getString("Name"));
                        book.setAccountBookPublic(rs.getBoolean("Public"));
                        book.setAccountBookMainManager(rs.getString("Manager"));
                        book.setAccountBookLogo(rs.getString("IconImage"));
                        book.setMembercount(rs.getLong("Membercount"));
                        book.setURL(rs.getInt("URL"));
                        String subManagerString = rs.getString("SubManager");
                        if (subManagerString != null) {
                            ArrayList<String> subManagers = new ArrayList<>(Arrays.asList(subManagerString.split("/")));
                            book.setAccountBookSubManager(subManagers);
                        }
                        book.setAccountBook_Budget(rs.getLong("Budget"));
                        String memberString = rs.getString("Member");

                        if (memberString != null) {
                            ArrayList<String> Member = new ArrayList<>(Arrays.asList(memberString.split("/")));
                            book.setAccountBook_Member(Member);
                        }
                    } else {
                        System.out.println("해당 이름의 계정북이 존재하지 않습니다.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    public List<Map<String, Object>> homeGetNotice(String name, int startIndex, int count) {
        String Community_tableName = "Team5_" + name + "_Notice";

        List<Map<String, Object>> NoticeList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String selectSql = "SELECT * FROM " + Community_tableName + " ORDER BY Date DESC LIMIT ?, ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
                selectStmt.setInt(1, startIndex);
                selectStmt.setInt(2, count);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> Notice = new HashMap<>();
                        Notice.put("Num", resultSet.getLong("Num"));
                        Notice.put("Member", findNickName(resultSet.getString("Member")));
                        Notice.put("Date", dateFormat.format(new Date(resultSet.getTimestamp("Date").getTime())));
                        Notice.put("Title", resultSet.getString("Title"));
                        NoticeList.add(Notice);
                    }
                }
            }
        } catch (SQLException e) {
            // Exception handling
        }
        return NoticeList;
    }

    public String findNickName(String Id) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            // 데이터 검색
            String selectSql = "SELECT * FROM Team5_Member WHERE Id = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, Id);

                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        String member_nickName = rs.getString("NickName");

                        return member_nickName;
                    } else {
                        System.out.println("멤버를 찾을 수 없습니다.");
                    }
                }
            }
        } catch (

        SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Map<String, Object>> homeGetCommunity(String name, int startIndex, int count) {
        String Community_tableName = "Team5_" + name + "_Community";

        List<Map<String, Object>> communityList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String selectSql = "SELECT * FROM " + Community_tableName + " ORDER BY Date DESC LIMIT ?, ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
                selectStmt.setInt(1, startIndex);
                selectStmt.setInt(2, count);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> community = new HashMap<>();
                        community.put("Num", resultSet.getLong("Num"));
                        community.put("Member", findNickName(resultSet.getString("Member")));
                        community.put("Date", dateFormat.format(new Date(resultSet.getTimestamp("Date").getTime())));
                        community.put("Title", resultSet.getString("Title"));
                        communityList.add(community);
                    }
                }
            }
        } catch (SQLException e) {
            // Exception handling
        }
        return communityList;
    }

    public List<Map<String, Object>> FindBook(String newBook) {
        String sql = "SELECT Name, URL FROM Team5_AccountBook WHERE Name LIKE ?";
        List<Map<String, Object>> bookList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + newBook + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Name");
                int url = rs.getInt("URL");

                Map<String, Object> bookMap = new HashMap<>();
                bookMap.put("Name", name);
                bookMap.put("URL", url);

                bookList.add(bookMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(bookList);
        return bookList;
    }

    public String IdToBookName(String Id) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            // 데이터 검색
            String selectSql = "SELECT * FROM Team5_AccountBook WHERE URL = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, Id);

                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        String book_name = rs.getString("Name");

                        return book_name;
                    } else {
                        System.out.println("가계부를 찾을 수 없습니다.");
                    }
                }
            }
        } catch (

        SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Map<String, Object>> homeGetPost(String name, int startIndex, int count) {
        String Community_tableName = "Team5_" + name + "_Post";

        List<Map<String, Object>> NoticeList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String selectSql = "SELECT * FROM " + Community_tableName + " ORDER BY useDate DESC LIMIT ?, ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
                selectStmt.setInt(1, startIndex);
                selectStmt.setInt(2, count);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> Notice = new HashMap<>();
                        Notice.put("Num", resultSet.getLong("Num"));
                        Notice.put("Type", resultSet.getBoolean("Type"));
                        Notice.put("Used_Budget", resultSet.getLong("Used_Budget"));
                        Notice.put("Date", dateFormat.format(new Date(resultSet.getTimestamp("useDate").getTime())));
                        String title = resultSet.getString("Title");
                        if (title.length() > 30) {
                            title = title.substring(0, 30);
                        }
                        Notice.put("Title", title);
                        Notice.put("Text", resultSet.getString("Text"));

                        NoticeList.add(Notice);
                    }
                }
            }
        } catch (SQLException e) {
            // Exception handling
        }
        return NoticeList;
    }

    public List<Map<String, Object>> BoardGetIncomeExpenseByTag(String name, int dateRangeType, String fromDate) {
        String postTableName = "Team5_" + name + "_Post";

        Map<String, Object> result = new HashMap<>();
        Map<String, Long> expenseByTag = new HashMap<>();
        Map<String, Long> incomeByTag = new HashMap<>();
        long totalExpense = 0;
        long totalIncome = 0;

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            // 최신 데이터를 가장 먼저 반환하도록 ORDER BY 구문 수정
            String selectSql = "SELECT * FROM " + postTableName +
                    " WHERE useDate >= ? AND useDate <= ?" +
                    " ORDER BY Date ASC";

            try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
                // 조회 종료 날짜 설정
                LocalDate fromDateObj = LocalDate.parse(fromDate);
                LocalDate toDate = null;

                switch (dateRangeType) {
                    case 1: // 일별
                        toDate = fromDateObj.minusDays(30);
                        break;
                    case 2: // 주별
                        toDate = fromDateObj.minusWeeks(30);
                        break;
                    case 3: // 월별
                        toDate = fromDateObj.minusMonths(30);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid date range type: " + dateRangeType);
                }

                // PreparedStatement에 매개변수 설정
                selectStmt.setDate(1, java.sql.Date.valueOf(toDate));
                selectStmt.setDate(2, java.sql.Date.valueOf(fromDateObj));

                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    while (resultSet.next()) {
                        boolean isExpense = resultSet.getBoolean("Type");
                        String tag = resultSet.getString("Tag");
                        long amount = resultSet.getLong("Used_Budget");

                        if (isExpense) {
                            totalIncome += amount;
                            incomeByTag.put(tag, expenseByTag.getOrDefault(tag, 0L) + amount);
                        } else {
                            totalExpense += amount;
                            expenseByTag.put(tag, expenseByTag.getOrDefault(tag, 0L) + amount);
                        }
                    }
                }
            } catch (SQLException e) {
                // Exception handling
            }
        } catch (SQLException e) {
            // Exception handling
        }

        result.put("Expense", totalExpense);
        result.put("Income", totalIncome);

        // expenseByTag 맵을 배열로 변환
        List<Map<String, Object>> expenseByTagList = new ArrayList<>();
        for (Map.Entry<String, Long> entry : expenseByTag.entrySet()) {
            String tag = entry.getKey();
            Long expense = entry.getValue();

            // 태그와 지출 금액을 맵으로 생성하여 리스트에 추가
            Map<String, Object> tagEntry = new HashMap<>();
            tagEntry.put("tag", tag);
            tagEntry.put("expense", expense);
            expenseByTagList.add(tagEntry);
        }

        // expenseByTagList 정렬
        Collections.sort(expenseByTagList, (a, b) -> {
            Long expenseA = (Long) a.get("expense");
            Long expenseB = (Long) b.get("expense");
            return expenseB.compareTo(expenseA); // 내림차순 정렬
        });

        // 상위 6개 항목 선택
        int limit = Math.min(expenseByTagList.size(), 6);
        List<Map<String, Object>> topExpenseByTagList = expenseByTagList.subList(0, limit);

        // 기타 항목 계산
        long etcExpense = 0;
        for (int i = limit; i < expenseByTagList.size(); i++) {
            etcExpense += (long) expenseByTagList.get(i).get("expense");
        }

        // 전체 금액 계산
        long totalExpense1 = etcExpense;
        for (Map<String, Object> entry : topExpenseByTagList) {
            totalExpense1 += (long) entry.get("expense");
        }

        // 퍼센티지 값 계산하여 업데이트
        DecimalFormat df = new DecimalFormat("0.00");
        for (Map<String, Object> entry : topExpenseByTagList) {
            long expense = (long) entry.get("expense");
            double percentage = (expense / (double) totalExpense1) * 100;
            entry.put("percentage", df.format(percentage));
        }

        // 기타 항목 추가
        Map<String, Object> etcEntry = new HashMap<>();
        etcEntry.put("tag", "etc");
        etcEntry.put("expense", etcExpense);
        double etcPercentage = (etcExpense / (double) totalExpense1) * 100;
        etcEntry.put("percentage", df.format(etcPercentage));
        topExpenseByTagList.add(etcEntry);

        // 결과 반환
        return topExpenseByTagList;
        // 결과 반환

    }

    public List<Long> BoardGetRainBudget(String name, int dateRangeType, String fromDate) {
        String postTableName = "Team5_" + name + "_Budget";

        List<Long> totalBudgetList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            // 최신 데이터를 가장 먼저 반환하도록 ORDER BY 구문 수정
            String selectSql = "SELECT * FROM " + postTableName +
                    " WHERE Date >= ? AND Date <= ?" +
                    " ORDER BY Date ASC";

            try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
                // 조회 종료 날짜 설정
                LocalDate fromDateObj = LocalDate.parse(fromDate).minusDays(1);
                LocalDate toDate = null;

                switch (dateRangeType) {
                    case 1: // 일별
                        toDate = fromDateObj.minusDays(30);
                        break;
                    case 2: // 주별
                        toDate = fromDateObj.minusWeeks(30);
                        break;
                    case 3: // 월별
                        toDate = fromDateObj.minusMonths(30);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid date range type: " + dateRangeType);
                }

                // PreparedStatement에 매개변수 설정
                selectStmt.setDate(1, java.sql.Date.valueOf(toDate));
                selectStmt.setDate(2, java.sql.Date.valueOf(fromDateObj));

                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    Map<LocalDate, Long> resultMap = new LinkedHashMap<>();

                    while (resultSet.next()) {
                        LocalDate useDate = resultSet.getDate("Date").toLocalDate();
                        long totalBudget = resultSet.getLong("Budget");

                        resultMap.put(useDate, totalBudget);
                    }

                    // 일별 데이터 처리
                    if (dateRangeType == 1) {
                        for (int i = 0; i < 29; i++) {
                            LocalDate currentDate = fromDateObj.minusDays(i);
                            totalBudgetList.add(resultMap.getOrDefault(currentDate, null));
                        }

                        for (int i = totalBudgetList.size() - 1; i >= 0; i--) {
                            if (totalBudgetList.get(i) == 0L) {
                                totalBudgetList.remove(i);
                            }
                        }

                        Collections.reverse(totalBudgetList);
                        String selectSql1 = "SELECT * FROM Team5_AccountBook WHERE URL = ?";
                        try (PreparedStatement selectStmt1 = connection.prepareStatement(selectSql1)) {
                            selectStmt1.setString(1, name);

                            try (ResultSet rs = selectStmt1.executeQuery()) {
                                if (rs.next()) {
                                    Long Budget = rs.getLong("Budget");
                                    System.out.print(Budget);
                                    totalBudgetList.add(Budget);

                                }
                            }
                        } catch (SQLException e) {
                            // Exception handling
                        }
                        for (int i = totalBudgetList.size(); i < 30; i++) {
                            totalBudgetList.add(i, 0L);
                        }
                    }
                    // 주별 데이터 처리
                    else if (dateRangeType == 2) {
                        for (int i = 0; i < 29; i++) {
                            LocalDate currentWeekEndDate = fromDateObj.minusWeeks(i);
                            LocalDate currentWeekStartDate = currentWeekEndDate.minusDays(6);

                            Long lastWeekTotalBudget = 0L;

                            for (LocalDate currentDate = currentWeekStartDate; !currentDate
                                    .isAfter(currentWeekEndDate); currentDate = currentDate.plusDays(1)) {
                                Long totalBudget = resultMap.getOrDefault(currentDate, lastWeekTotalBudget);
                                lastWeekTotalBudget = totalBudget;
                            }

                            totalBudgetList.add(lastWeekTotalBudget);
                        }

                        for (int i = totalBudgetList.size() - 1; i >= 0; i--) {
                            if (totalBudgetList.get(i) == 0L) {
                                totalBudgetList.remove(i);
                            }
                        }

                        Collections.reverse(totalBudgetList);

                        for (int i = totalBudgetList.size(); i < 30; i++) {
                            totalBudgetList.add(i, 0L);
                        }

                    }
                    // 월별 데이터 처리
                    else if (dateRangeType == 3) {
                        for (int i = 0; i < 29; i++) {
                            LocalDate currentMonthEndDate = fromDateObj.minusMonths(i).withDayOfMonth(1).plusMonths(1)
                                    .minusDays(1);
                            LocalDate currentMonthStartDate = currentMonthEndDate.withDayOfMonth(1);

                            Long lastMonthTotalBudget = 0L;

                            for (LocalDate currentDate = currentMonthStartDate; !currentDate
                                    .isAfter(currentMonthEndDate); currentDate = currentDate.plusDays(1)) {
                                Long totalBudget = resultMap.getOrDefault(currentDate, lastMonthTotalBudget);
                                lastMonthTotalBudget = totalBudget;
                            }

                            totalBudgetList.add(lastMonthTotalBudget);
                        }

                        for (int i = totalBudgetList.size() - 1; i >= 0; i--) {
                            if (totalBudgetList.get(i) == 0L) {
                                totalBudgetList.remove(i);
                            }
                        }

                        Collections.reverse(totalBudgetList);

                        for (int i = totalBudgetList.size(); i < 30; i++) {
                            totalBudgetList.add(i, 0L);
                        }
                    }

                }
            }
        } catch (SQLException e) {
            // Exception handling
        }

        return totalBudgetList;
    }

    private Map<String, Object> createPostMap(LocalDate useDate, long usedBudget, long totalBudget, boolean type) {
        Map<String, Object> postMap = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        postMap.put("useDate", dateFormat.format(Date.from(useDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        postMap.put("Used_Budget", usedBudget);
        postMap.put("Total_Budget", totalBudget);
        postMap.put("Type", type);
        return postMap;
    }

    public List<Post> getAllPosts(String tableName) {
        List<Post> posts = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String selectSql = "SELECT * FROM " + tableName;
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                ResultSet resultSet = selectStmt.executeQuery();

                while (resultSet.next()) {
                    Post post = new Post();
                    post.setPostId(resultSet.getLong("Num"));
                    post.setPostTag(resultSet.getString("Tag"));
                    post.setPostType(resultSet.getBoolean("Type"));
                    post.setPostDate(new Date(resultSet.getTimestamp("Date").getTime()));
                    post.setPostTitle(resultSet.getString("Title"));
                    post.setPostText(resultSet.getString("Text"));
                    String imageStream = resultSet.getString("Image");
                    ArrayList<String> imagePaths = new ArrayList<>(Arrays.asList(imageStream.split(",")));
                    post.setPostImage(imagePaths);
                    post.setPostTotalBudget(resultSet.getLong("Total_Budget"));
                    post.setPostUsedBudget(resultSet.getLong("Used_Budget"));
                    posts.add(post);
                }
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public boolean AddPost(Post newPost, String table) {
        String tableName = "Team5_" + table + "_Post";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String selectSql = "SELECT * FROM Team5_AccountBook WHERE URL = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, table);

                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        Long Budget = rs.getLong("Budget");
                        if (newPost.getPostType() == true) {
                            Budget = Budget + newPost.getPostUsedBudget();
                        } else {
                            Budget = Budget - newPost.getPostUsedBudget();
                        }
                        newPost.setPostTotalBudget(Budget);

                        // 데이터 추가
                        String insertSql = "INSERT INTO " + tableName
                                + " (Tag, Type, useDate, Title, Text, Image, Total_Budget, Used_Budget) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setString(1, newPost.getPostTag());
                            insertStmt.setBoolean(2, newPost.getPostType());
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDate postDate = null;
                            try {
                                postDate = LocalDate.parse(newPost.getUseDate(), formatter);
                            } catch (DateTimeParseException e) {
                                e.printStackTrace(); // 예외 정보 출력
                            }
                            if (postDate != null) {
                                insertStmt.setDate(3, java.sql.Date.valueOf(postDate));
                            } else {
                                insertStmt.setNull(3, Types.DATE);
                            }

                            insertStmt.setString(4, newPost.getPostTitle());
                            insertStmt.setString(5, newPost.getPostText());
                            if (newPost.getPostImage() == null) {
                                insertStmt.setString(6, "");
                            } else {
                                String joinedString = String.join(",", newPost.getPostImage());
                                insertStmt.setString(6, joinedString);
                            }
                            insertStmt.setLong(7, newPost.getPostTotalBudget());
                            insertStmt.setLong(8, newPost.getPostUsedBudget());
                            insertStmt.executeUpdate();

                            System.out.println("데이터가 추가되었습니다.");

                            String updateSql = "UPDATE Team5_AccountBook SET Budget = ? WHERE URL = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                                updateStmt.setLong(1, Budget);
                                updateStmt.setString(2, table);
                                updateStmt.executeUpdate();
                            }
                            return true;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean AddNotice(Notice newNotice, String table) {
        String tableName = "Team5_" + table + "_Notice";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {

            String insertSql = "INSERT INTO " + tableName
                    + " (Member, Title, Text) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, newNotice.getMember());
                insertStmt.setString(2, newNotice.getTitle());
                insertStmt.setString(3, newNotice.getText());
                insertStmt.executeUpdate();
                System.out.println("데이터가 추가되었습니다.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Notice getNotice(int num, String table) { // 찬영
        String tableName = "Team5_" + table + "_Notice";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            // 데이터 검색
            String selectSql = "SELECT * FROM " + tableName + " WHERE Num = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setInt(1, num);

                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        Notice reNorice = new Notice();
                        reNorice.setNum(rs.getLong("Num"));
                        reNorice.setDate((new Date(rs.getTimestamp("Date").getTime())));
                        reNorice.setTitle(rs.getString("Title"));
                        reNorice.setText(rs.getString("Text"));
                        Member member = mService.FindMemberUseId(rs.getString("Member"));
                        reNorice.setMember(member.getMemberNickName());

                        return reNorice;
                    } else {
                        System.out.println("멤버를 찾을 수 없습니다.");
                    }
                }
            }
        } catch (

        SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Community getCommunity(int num, String table) { // 찬영
        String tableName = "Team5_" + table + "_Community";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            // 데이터 검색
            String selectSql = "SELECT * FROM " + tableName + " WHERE Num = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setInt(1, num);

                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        Community reNorice = new Community();
                        reNorice.setNum(rs.getLong("Num"));
                        reNorice.setDate((new Date(rs.getTimestamp("Date").getTime())));
                        reNorice.setTitle(rs.getString("Title"));
                        reNorice.setText(rs.getString("Text"));
                        Member member = mService.FindMemberUseId(rs.getString("Member"));
                        reNorice.setMember(member.getMemberNickName());

                        return reNorice;
                    } else {
                        System.out.println("멤버를 찾을 수 없습니다.");
                    }
                }
            }
        } catch (

        SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean AddCommunity(Community newCommunity, String table) {
        String tableName = "Team5_" + table + "_Community";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {

            String insertSql = "INSERT INTO " + tableName
                    + " (Member, Title, Text) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, newCommunity.getMember());
                insertStmt.setString(2, newCommunity.getTitle());
                insertStmt.setString(3, newCommunity.getText());
                insertStmt.executeUpdate();
                System.out.println("데이터가 추가되었습니다.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean AddPostComment(Comment newComment, String table) {
        String tableName = "Team5_" + table + "_Comment";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String insertSql = "INSERT INTO " + tableName
                    + " (Member, PostType, Post, Type, Text) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, newComment.getCommentMember());
                insertStmt.setString(2, newComment.getPostType());
                insertStmt.setLong(3, newComment.getCommentPost());
                insertStmt.setLong(4, newComment.getCommentType());
                insertStmt.setString(5, newComment.getCommentText());
                insertStmt.executeUpdate();
                System.out.println("데이터가 추가되었습니다.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePostString(String tableName, int postId, String column, String value) {
        String updateSql = "UPDATE " + tableName + " SET " + column + " = ? WHERE Num = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                updateStmt.setString(1, value);
                updateStmt.setInt(2, postId);
                int affectedRows = updateStmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("게시물이 업데이트되었습니다.");
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePostLong(String tableName, int postId, String column, Long value) {
        String updateSql = "UPDATE " + tableName + " SET " + column + " = ? WHERE Num = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                updateStmt.setLong(1, value);
                updateStmt.setInt(2, postId);
                int affectedRows = updateStmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("게시물이 업데이트되었습니다.");
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<String, Map<String, Long>> CalenderBord(String name, String startday, String endday) {
        String postTableName = "Team5_" + name + "_Post";

        Map<Date, Map<String, Long>> result = new HashMap<>();

        // 모든 날짜를 초기화하고 0으로 설정
        LocalDate startDate = LocalDate.parse(startday);
        LocalDate endDate = LocalDate.parse(endday);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            result.put(Date.valueOf(date), new HashMap<>());
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String selectSql = "SELECT useDate, Type, SUM(Used_Budget) AS TotalAmount FROM " + postTableName +
                    " WHERE useDate >= ? AND useDate <= ?" +
                    " GROUP BY useDate, Type";

            try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
                selectStmt.setDate(1, java.sql.Date.valueOf(startday));
                selectStmt.setDate(2, java.sql.Date.valueOf(endday));

                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    while (resultSet.next()) {
                        Date date = resultSet.getDate("useDate");
                        boolean isExpense = resultSet.getBoolean("Type");
                        long amount = resultSet.getLong("TotalAmount");

                        // 해당 날짜에 대한 Map 가져오기
                        Map<String, Long> dateData = result.get(date);

                        // 수입 또는 지출에 따라 금액 추가
                        if (isExpense) {
                            dateData.put("Expense", amount);
                        } else {
                            dateData.put("Income", amount);
                        }
                    }
                }
            } catch (SQLException e) {
                // Exception handling
            }
        } catch (SQLException e) {
            // Exception handling
        }
        for (Map.Entry<Date, Map<String, Long>> entry : result.entrySet()) {
            Map<String, Long> dateData = entry.getValue();
            dateData.putIfAbsent("Income", 0L);
            dateData.putIfAbsent("Expense", 0L);
        }

        // 날짜를 원하는 형식으로 변환하여 결과 맵 생성
        Map<String, Map<String, Long>> formattedResult = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Map.Entry<Date, Map<String, Long>> entry : result.entrySet()) {
            Date date = entry.getKey();
            String formattedDate = dateFormat.format(date);
            formattedResult.put(formattedDate, entry.getValue());
        }

        return formattedResult;
    }

}