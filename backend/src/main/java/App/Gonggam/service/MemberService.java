package App.Gonggam.service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.*;
import App.Gonggam.model.Member;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

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

    public boolean LogoutMember(String token) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            if (token != null) {
                String updateSql = "UPDATE Team5_Member SET Token = NULL WHERE Token = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setString(1, token);
                    int rowsAffected = updateStmt.executeUpdate();
                    return rowsAffected > 0; // 토큰 삭제 성공 여부 반환
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // 토큰 삭제 실패
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
                        System.out.println(member_accountList);

                        if (member_accountList != null) {
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

                        String sql = "UPDATE Team5_Member SET Token = ? WHERE id = ?";
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

    public String FindMemberUseToken(String token) {
        String id = null;
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD);
                PreparedStatement statement = conn
                        .prepareStatement("SELECT Id FROM Team5_Member WHERE Token = ?")) {
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getString("Id");
            }
        } catch (SQLException e) {
            System.out.println("id 조회 실패");
            e.printStackTrace();
        }
        return id;
    }

    public Member FindMemberUseId(String Id) {
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
                        System.out.println(member_accountList);

                        if (member_accountList != null) {
                            String[] accountListArray = member_accountList.split("/");
                            for (String account : accountListArray) {
                                if (account.startsWith("(M)")) {
                                    manage_accountBook.add(account.substring(3));
                                } else if (account.startsWith("(P)")) {
                                    parti_accountBook.add(account.substring(3));
                                }
                            }
                        }
                        member = new Member(member_id, member_password, member_nickName, manage_accountBook,
                                parti_accountBook);

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

    public Boolean checkcode(String email, String code) {
        String selectSql = "SELECT * FROM Team5_SignUp WHERE Email = ? AND Code = ?";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, email);
                selectStmt.setString(2, code);
                ResultSet resultSet = selectStmt.executeQuery();

                if (resultSet.next()) {
                    // Matching row found, update Verified_At column
                    int id = resultSet.getInt("Id");
                    String updateSql = "UPDATE Team5_SignUp SET Verified_At = NOW() WHERE Id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, id);
                        int rowsAffected = updateStmt.executeUpdate();
                        return rowsAffected > 0;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                } else {
                    return false; // No matching row found
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean signsql(String email, String code) {
        if (code == null) {
            return false;
        }

        // 이메일 중복 확인
        String checkEmailSql = "SELECT COUNT(*) FROM Team5_SignUp WHERE Email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            try (PreparedStatement checkEmailStmt = conn.prepareStatement(checkEmailSql)) {
                checkEmailStmt.setString(1, email);
                ResultSet resultSet = checkEmailStmt.executeQuery();
                resultSet.next();
                int count = resultSet.getInt(1);

                if (count > 0) {
                    // 이메일이 이미 존재하는 경우 코드 업데이트
                    String updateSql = "UPDATE Team5_SignUp SET Code = ?, Updated_At = NOW() WHERE Email = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, code);
                        updateStmt.setString(2, email);
                        int rowsAffected = updateStmt.executeUpdate();
                        return rowsAffected > 0;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                } else {
                    // 이메일이 존재하지 않는 경우 코드 추가
                    String insertSql = "INSERT INTO Team5_SignUp (Email, Code) VALUES (?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setString(1, email);
                        insertStmt.setString(2, code);
                        int rowsAffected = insertStmt.executeUpdate();
                        return rowsAffected > 0;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String sendEmail(String email) {
        try {
            // JavaMailSenderImpl 객체 생성
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

            // Gmail SMTP 설정
            mailSender.setHost("smtp.office365.com");
            mailSender.setPort(587);
            mailSender.setUsername("OfficialGongGam@outlook.com");
            mailSender.setPassword("gonggam123");

            // Gmail SMTP 속성 설정
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            Random random = new Random();
            StringBuilder verificationCode = new StringBuilder();

            // 6자리의 랜덤 숫자 생성
            for (int i = 0; i < 6; i++) {
                int digit = random.nextInt(10); // 0부터 9까지의 숫자
                verificationCode.append(digit);
            }

            // 이메일 메시지 생성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setFrom("OfficialGongGam@outlook.com");
            helper.setSubject("[공감] 회원가입 완료 안내 메일입니다.");

            // HTML 파일 경로
            ClassLoader classLoader = getClass().getClassLoader();
            File htmlFile = new File(classLoader.getResource("html/login.html").getFile());
            String htmlContent = new String(Files.readAllBytes(htmlFile.toPath()), StandardCharsets.UTF_8);

            // verificationCode 값으로 대체
            String replacedHtmlContent = htmlContent.replace("{{verificationCode}}", verificationCode.toString());

            // CSS 파일 경로
            File cssFile = new File(classLoader.getResource("css/login.css").getFile());
            String cssContent = new String(Files.readAllBytes(cssFile.toPath()), StandardCharsets.UTF_8);
            String cssLink = "<style>" + cssContent + "</style>";

            // Attach the CSS file and set the modified HTML as the email body
            helper.setText(cssLink + replacedHtmlContent, true);

            // 이메일 전송
            mailSender.send(message);

            return verificationCode.toString();
        } catch (Exception e) {
            // 이메일 전송 실패 시 예외 처리
            e.printStackTrace();
            return "이메일 전송 실패";
        }
    }

    public boolean checkVerified(String email) {
        String selectSql = "SELECT * FROM Team5_SignUp WHERE Email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, SQL_PASSWORD)) {
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, email);
                ResultSet resultSet = selectStmt.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getObject("Verified_At") != null;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // public String sendEmail(String Email) {
    // try {
    // // JavaMailSenderImpl 객체 생성
    // JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    // // Gmail SMTP 설정
    // mailSender.setHost("smtp.office365.com");
    // mailSender.setPort(587);
    // mailSender.setUsername("youngchannel4u@outlook.com");
    // mailSender.setPassword("gkdmscksdud22");

    // // Gmail SMTP 속성 설정
    // Properties props = mailSender.getJavaMailProperties();
    // props.put("mail.transport.protocol", "smtp");
    // props.put("mail.smtp.auth", "true");
    // props.put("mail.smtp.starttls.enable", "true");
    // props.put("mail.debug", "true");

    // Random random = new Random();
    // StringBuilder IntverificationCode = new StringBuilder();

    // // 6자리의 랜덤 숫자 생성
    // for (int i = 0; i < 6; i++) {
    // int digit = random.nextInt(10); // 0부터 9까지의 숫자
    // IntverificationCode.append(digit);
    // }

    // String verificationCode = IntverificationCode.toString();

    // // // 이메일 메시지 생성
    // // SimpleMailMessage message = new SimpleMailMessage();
    // // message.setTo(Email);
    // // message.setFrom("youngchannel4u@outlook.com");
    // // message.setSubject("[공감] 회원가입 완료 안내 메일입니다.");
    // // message.setText("이메일 인증번호 입니다. \n" + msg);
    // // 이메일 메시지 생성
    // MimeMessage message = mailSender.createMimeMessage();
    // MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    // helper.setTo("youngchannel4u@gmail.com");
    // helper.setFrom("youngchannel4u@outlook.com");
    // helper.setSubject("[공감] 회원가입 완료 안내 메일입니다.");

    // String htmlContent = "<table width='100%' style='border-collapse:
    // collapse;'>"
    // + "<tr>"
    // + "<td align='center' style='padding: 20px; border: 1px solid #dddddd;'>"
    // + "<h1 style='color: #333333; font-family: Arial;'>마음을 함께하는 공동 가계부 공감</h1>"
    // + "<p style='color: #666666; font-family: Arial;'>안녕하세요!</p>"
    // + "<p style='color: #666666; font-family: Arial;'>회원가입을 위한 인증번호를
    // 안내해드립니다.</p>"
    // + "<p style='color: #666666; font-family: Arial;'>이것은 소개글 입니다.</p>"
    // + "<p style='color: #666666; font-family: Arial;'>이것은 소개글 입니다.</p>"
    // + "<hr style='border: 1px solid #dddddd;'>"
    // + "<p style='color: #ff0000; font-family: Arial; font-size: 24px;
    // font-weight: bold;'>인증번호: <strong>"
    // + verificationCode + "</strong></p>"
    // + "<hr style='border: 1px solid #dddddd;'>"
    // + "<p style='color: #666666; font-family: Arial;'>감사합니다!</p>"
    // + "</td>"
    // + "</tr>"
    // + "</table>";

    // helper.setText(htmlContent, true);

    // // 이메일 전송
    // mailSender.send(message);

    // return verificationCode;
    // } catch (Exception e) {
    // // 이메일 전송 실패 시 예외 처리
    // e.printStackTrace();
    // return "이메일 전송 실패";
    // }
    // }
}