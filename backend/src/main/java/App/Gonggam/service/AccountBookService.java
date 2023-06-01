package App.Gonggam.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import App.Gonggam.model.AccountBook;
import App.Gonggam.model.Post;

public class AccountBookService {
    String URL = "jdbc:mysql://selab.hknu.ac.kr:51714/2023_pbl3";
    String USERNAME = "pbl3_team5";
    String SQL_PASSWORD = "12345678";

    // DB에 멤버 추가

    public boolean addBook(AccountBook newBook) {

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            // 데이터 추가
            String insertSql = "INSERT INTO Team5_AccountBook (Name, Public, Manager) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, newBook.getAccountBookName());
                insertStmt.setBoolean(2, newBook.getAccountBookPublic());
                insertStmt.setString(3, newBook.getAccountBookMainManager());
                insertStmt.executeUpdate();

                System.out.println("데이터가 추가되었습니다.");

                String Comment_tableName = "Team5_" + newBook.getAccountBookName() + "_Comment";
                String Post_tableName = "Team5_" + newBook.getAccountBookName() + "_Post";

                try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
                    String sql = "CREATE TABLE " + Comment_tableName + " (" +
                            "Num BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                            "Member VARCHAR(255) NOT NULL, " +
                            "Post BIGINT NOT NULL, " +
                            "Date VARCHAR(255) NOT NULL, " +
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
                            "Date VARCHAR(255) NOT NULL, " +
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

    // public List<AccountBook> getAllBooks() {
    // List<AccountBook> books = new ArrayList<>();

    // try (Connection conn = DriverManager.getConnection(URL, USERNAME,
    // SQL_PASSWORD)) {
    // String selectSql = "SELECT * FROM Team5_AccountBook";
    // try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
    // ResultSet resultSet = selectStmt.executeQuery();

    // while (resultSet.next()) {
    // AccountBook book = new AccountBook();
    // book.setAccountBookName(resultSet.getString("Name"));
    // book.setAccountBookPublic(resultSet.getString("Public"));
    // book.setAccountBookMainManager(resultSet.getString("Manager"));
    // book.setAccountBook_Budget(resultSet.getLong("Budget"));
    // book.setAccountBook_Member(resultSet.getString("Member"));

    // books.add(book);
    // }

    // resultSet.close();
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }

    // return books;
    // }

    public ArrayList<String> FindBook(String newBook) {
        ArrayList<String> booklist = new ArrayList<String>();

        // SQL 쿼리
        String sql = "SELECT Name FROM Team5_AccountBook WHERE Name LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 쿼리 매개변수 설정
            stmt.setString(1, "%" + newBook + "%");

            // 쿼리 실행
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Name");
                booklist.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(booklist);
        return booklist;
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
                    post.setPostDate(resultSet.getString("Date"));
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
                    + " (Num, Tag, Type, Date, Title, Text, Image, Total_Budget, Used_Budget) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(2, newPost.getPostTag());
                insertStmt.setBoolean(3, newPost.getPostType());
                insertStmt.setString(4, newPost.getPostDate());
                insertStmt.setString(5, newPost.getPostTitle());
                insertStmt.setString(6, newPost.getPostText());
                String joinedString = String.join(",", newPost.getPostImage());
                insertStmt.setString(7, joinedString);
                insertStmt.setLong(8, newPost.getPostTotalBudget());
                insertStmt.setLong(9, newPost.getPostUsedBudget());
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