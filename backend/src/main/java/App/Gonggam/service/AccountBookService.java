package App.Gonggam.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import App.Gonggam.model.AccountBook;
import App.Gonggam.model.Post;
import javassist.bytecode.stackmap.BasicBlock.Catch;;

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
                insertStmt.setString(1, newBook.getAccountBook_Name());
                insertStmt.setString(2, newBook.getAccountBook_public());
                insertStmt.setString(3, newBook.getAccountBook_main_manager());
                insertStmt.executeUpdate();

                System.out.println("데이터가 추가되었습니다.");

                String tableName = "Team5_" + newBook.getAccountBook_Name();

                newBook.setAccountBook_Db(tableName);

                try (Connection connection = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
                    String sql = "CREATE TABLE " + tableName + " (" +
                            "Num INT AUTO_INCREMENT PRIMARY KEY, " +
                            "Type VARCHAR(255) NOT NULL, " +
                            "Date DATE NOT NULL, " +
                            "Title VARCHAR(255) NOT NULL, " +
                            "Text TEXT, " +
                            "Comment TEXT, " +
                            "Image MEDIUMBLOB, " +
                            "Used_Budget BIGINT" +
                            ")";

                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sql);
                    System.out.println("테이블이 성공적으로 생성되었습니다.");
                } catch (SQLException e) {
                    System.out.println("테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<AccountBook> getAllBooks() {
        List<AccountBook> books = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            String selectSql = "SELECT * FROM Team5_AccountBook";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                ResultSet resultSet = selectStmt.executeQuery();

                while (resultSet.next()) {
                    AccountBook book = new AccountBook();
                    book.setAccountBook_Name(resultSet.getString("Name"));
                    book.setAccountBook_public(resultSet.getString("Public"));
                    book.setAccountBook_main_manager(resultSet.getString("Manager"));
                    book.setAccountBook_Budget(resultSet.getLong("Budget"));
                    book.setAccountBook_Member(resultSet.getString("Member"));

                    books.add(book);
                }

                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

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
                    post.setPost_type(resultSet.getString("Type"));
                    post.setPost_date(resultSet.getString("Date"));
                    post.setPost_title(resultSet.getString("Title"));
                    post.setPost_text(resultSet.getString("Text"));
                    post.setPost_comment(resultSet.getString("Comment"));
                    // 이미지 데이터 추출
                    InputStream imageStream = resultSet.getBinaryStream("Image");
                    BufferedImage image = ImageIO.read(imageStream);
                    post.getPost_image_list().add(image);
                    post.setPost_used_budget(resultSet.getLong("Used_Budget"));

                    posts.add(post);
                }

                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public boolean AddPost(Post newPost, String book) {
        String tableName = "Team5_" + book;

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            // 데이터 추가
            String insertSql = "INSERT INTO " + tableName
                    + " (Type, Date, Title, Text, Comment, Image, Used_Budget) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, newPost.getPost_type());
                insertStmt.setString(2, newPost.getPost_date());
                insertStmt.setString(3, newPost.getPost_title());
                insertStmt.setString(4, newPost.getPost_text());
                insertStmt.setString(5, newPost.getPost_comment());

                // 이미지 데이터를 Base64로 인코딩하여 바이트 배열로 변환
                ArrayList<BufferedImage> post_image_list = newPost.getPost_image_list();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                for (BufferedImage image : post_image_list) {
                    try {
                        ImageIO.write(image, "png", baos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                baos.flush();
                byte[] imageBytes = baos.toByteArray();
                baos.close();

                // 바이트 배열을 InputStream으로 변환
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);

                insertStmt.setBinaryStream(6, inputStream, imageBytes.length);
                insertStmt.setLong(7, newPost.getPost_used_budget());
                insertStmt.executeUpdate();

                System.out.println("데이터가 추가되었습니다.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

    // 수정 필요

    public boolean updateImage(String tableName, int postId, String column, Long value) {
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