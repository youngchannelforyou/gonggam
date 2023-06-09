package App.Gonggam.service;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import App.Gonggam.model.AccountBook;
import App.Gonggam.model.Community;
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
            String insertSql = "INSERT INTO Team5_AccountBook (Name, Public, Manager, IconImage, Membercount) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, newBook.getAccountBookName());
                insertStmt.setBoolean(2, newBook.getAccountBookPublic());
                insertStmt.setString(3, newBook.getAccountBookMainManager());
                insertStmt.setString(4, newBook.getAccountBookLogo());
                insertStmt.setLong(5, newBook.getMembercount());
                insertStmt.executeUpdate();

                System.out.println("데이터가 추가되었습니다.");

                ResultSet generatedKeys = insertStmt.getGeneratedKeys();

                String generatedId = String.valueOf(generatedKeys.getInt(1));

                String Comment_tableName = "Team5_" + generatedId + "_Comment";
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

    public String addMember(String Manager, String Member, int TableName) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String selectSql = "SELECT * FROM Team5_AccountBook WHERE URL = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setInt(1, TableName);

                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        if (rs.getString("Manager").equals(mService.FindMemberUseToken(Manager))) {
                            String Memberid = mService.FindMemberUseToken(Member);
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
                                updateStmt.setInt(2, TableName);
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

    public AccountBook getBook(int name) {
        AccountBook book = null;
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String sql = "SELECT * FROM Team5_AccountBook WHERE URL = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, name);

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

    public List<Notice> getNotice(String name, int startIndex, int count) {
        String Notice_tableName = "Team5_" + name + "_Notice";

        List<Notice> noticeList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String selectSql = "SELECT * FROM " + Notice_tableName + " ORDER BY Date DESC LIMIT ?, ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
                selectStmt.setInt(1, startIndex);
                selectStmt.setInt(2, count);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    while (resultSet.next()) {
                        Notice notice = new Notice();
                        notice.setNum(resultSet.getLong("Num"));
                        notice.setMember(resultSet.getString("Member"));
                        notice.setDate(new Date(resultSet.getTimestamp("Date").getTime()));
                        notice.setTitle(resultSet.getString("Title"));
                        notice.setText(resultSet.getString("Text"));
                        noticeList.add(notice);
                    }
                }
            }
        } catch (SQLException e) {
            // Exception handling
        }

        return noticeList;
    }

    public List<Community> getCommunity(String name, int startIndex, int count) {
        String Notice_tableName = "Team5_" + name + "_Notice";

        List<Community> communityList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String selectSql = "SELECT * FROM " + Notice_tableName + " ORDER BY Date DESC LIMIT ?, ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
                selectStmt.setInt(1, startIndex);
                selectStmt.setInt(2, count);
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    while (resultSet.next()) {
                        Community community = new Community();
                        community.setNum(resultSet.getLong("Num"));
                        community.setMember(resultSet.getString("Member"));
                        community.setDate(new Date(resultSet.getTimestamp("Date").getTime()));
                        community.setTitle(resultSet.getString("Title"));
                        community.setText(resultSet.getString("Text"));
                        communityList.add(community);
                    }
                }
            }
        } catch (SQLException e) {
            // Exception handling
        }

        return communityList;
    }

    public List<Map<String, String>> FindBook(int newBook) {
        // SQL 쿼리
        String sql = "SELECT Name FROM Team5_AccountBook WHERE URL LIKE ?";

        List<Map<String, String>> bookList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 쿼리 매개변수 설정
            stmt.setString(1, "%" + newBook + "%");

            // 쿼리 실행
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Name");
                String url = rs.getString("URL");

                Map<String, String> bookMap = new HashMap<>();
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

    public List<Map<String, Object>> getPosts(String name, int dateRangeType, Date fromDate) {
        String Post_tableName = "Team5_" + name + "_Post";

        List<Map<String, Object>> postList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String dateColumnName;
            switch (dateRangeType) {
                case 1: // 일별
                    dateColumnName = "DATE(useDate)";
                    break;
                case 2: // 주별
                    dateColumnName = "WEEK(useDate)";
                    break;
                case 3: // 월별
                    dateColumnName = "MONTH(useDate)";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid date range type: " + dateRangeType);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fromDateStr = sdf.format(fromDate);

            String selectSql = "SELECT useDate, Used_Budget FROM " + Post_tableName +
                    " WHERE useDate >= '" + fromDateStr + "' AND " +
                    " useDate <= DATE_ADD('" + fromDateStr + "', " +
                    " INTERVAL 30 " + getDateRangeUnit(dateRangeType) + ")" +
                    " GROUP BY " + dateColumnName + ", Used_Budget" +
                    " ORDER BY useDate DESC";

            try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
                try (ResultSet resultSet = selectStmt.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> postMap = new HashMap<>();
                        postMap.put("useDate", resultSet.getTimestamp("useDate"));
                        postMap.put("Used_Budget", resultSet.getLong("Used_Budget"));
                        postList.add(postMap);
                    }
                }
            }
        } catch (SQLException e) {
            // Exception handling
        }

        return postList;
    }

    private String getDateRangeUnit(int dateRangeType) {
        switch (dateRangeType) {
            case 1: // 일별
                return "DAY";
            case 2: // 주별
                return "WEEK";
            case 3: // 월별
                return "MONTH";
            default:
                throw new IllegalArgumentException("Invalid date range type: " + dateRangeType);
        }
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
            // 데이터 추가
            String insertSql = "INSERT INTO " + tableName
                    + " (Tag, Type, Date, Title, Text, Image, Total_Budget, Used_Budget) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, newPost.getPostTag());
                insertStmt.setBoolean(2, newPost.getPostType());
                insertStmt.setDate(3, newPost.getPostDate());
                insertStmt.setString(4, newPost.getPostTitle());
                insertStmt.setString(5, newPost.getPostText());
                String joinedString = String.join(",", newPost.getPostImage());
                insertStmt.setString(6, joinedString);
                Long usedBudget = 0L;
                if (newPost.getPostTotalBudget() != null) {
                    usedBudget = newPost.getPostTotalBudget().longValue();
                }
                insertStmt.setLong(7, usedBudget);
                insertStmt.setLong(8, newPost.getPostUsedBudget());
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
}