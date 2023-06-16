package App.Gonggam.controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import App.Gonggam.model.AccountBook;
import App.Gonggam.model.Member;
import App.Gonggam.service.AccountBookService;
import App.Gonggam.service.MemberService;
import jakarta.servlet.http.Cookie;

@RestController
@RequestMapping(value = "/Member")
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {
    MemberService memberservice = new MemberService();
    AccountBookService accountBookService = new AccountBookService();

    @PostMapping(path = "/createcode", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> CreateCode(
            @RequestBody String inputjson) {
        String result = "서버에러";
        Boolean SqlResult = false;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputjson);
            String Email = jsonNode.get("Email").asText();

            boolean check = memberservice.checkEmail(Email);

            if (check == false) {
                result = memberservice.sendEmail(Email);
                SqlResult = memberservice.signsql(Email, result);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"Email exist\", \"status\": \"204\"}");
            }
        } catch (Exception e) {
        }
        if (SqlResult != false) {
            return ResponseEntity.ok()
                    .body("{\"message\": \"success\", \"status\": \"200\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail create code\", \"status\": \"500\"}");
        }
    }

    @PostMapping(path = "/checkcode", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> CheckCode(
            @RequestBody String inputjson) {
        boolean result = false;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputjson);
            String Email = jsonNode.get("Email").asText();
            String Code = jsonNode.get("Code").asText();
            result = memberservice.checkcode(Email, Code);
        } catch (Exception e) {
        }

        if (result == true) {
            return ResponseEntity.ok()
                    .body("{\"message\": \"success\", \"status\": \"200\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail check code\", \"status\": \"500\"}");
        }
    }

    @PostMapping(path = "/signupmember", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> SignUpMember(@RequestBody Member new_member) {
        String memberId = new_member.getMemberId();

        // Check if Verified_At is not null for the matching email in Team5_SignUp table
        boolean isVerified = memberservice.checkVerified(memberId);
        if (isVerified) {
            boolean check = memberservice.AddMember(new_member);
            if (check) {
                return ResponseEntity.ok()
                        .body("{\"message\": \"success\", \"status\": \"200\"}");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"fail add member\", \"status\": \"500\"}");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"message\": \"email not verified\", \"status\": \"400\"}");
        }
    }

    @PostMapping(path = "/login", produces = "application/json", consumes = "application/json")
    // public ResponseEntity<String> Login(
    // @RequestParam("Id") String Id,
    // @RequestParam("Password") String password) {
    // Member member = memberservice.GetMember(Id);
    public ResponseEntity<String> Login(@RequestBody String inputjson) {
        String Id;
        String password;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputjson);
            Id = jsonNode.get("Id").asText();
            password = jsonNode.get("Password").asText();

            System.out.println(Id);

            Member checkMember = memberservice.LoginMember(Id);

            if (checkMember == null) {
                System.out.println("id 못 찾음");
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("{\"message\": \"id not found\", \"status\": \"204\"}");
            } else {
                System.out.println(checkMember.getMemberPassword());
                if (password.equals(checkMember.getMemberPassword())) {
                    try {
                        try {
                            HttpHeaders headers = new HttpHeaders();
                            Cookie cookie = new Cookie("memberId", checkMember.getMemberToken());
                            cookie.setMaxAge(3600);
                            headers.add(HttpHeaders.SET_COOKIE,
                                    cookie.getName() + "=" + cookie.getValue()
                                            + "; path=/; secure; httpOnly; SameSite=None");

                            return ResponseEntity.ok().headers(headers)
                                    .body("{\"message\": \"success\", \"status\": \"200\"}");
                        } catch (Exception e) {
                            System.out.println("쿠키에러");
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body("{\"message\": \"cookie error0\", \"status\": \"500\"}");
                        }

                        // return ResponseEntity.ok(json);
                    } catch (Exception e) {
                        System.out.println("{\"message\": \"error1\", \"status\": \"500\"}");

                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("{\"message\": \"error1\", \"status\": \"500\"}");
                    }
                } else {
                    System.out.println("비밀번호 틀림");

                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("{\"message\": \"password error\", \"status\": \"204\"}");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"error\", \"status\": \"500\"}");
    }

    @PostMapping(path = "/getmemberinfo", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> getMemberInfo(@RequestBody String inputjson,
            @CookieValue("memberId") String memberId) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String Id = memberservice.FindMemberUseToken(memberId);
            Member member = memberservice.FindMemberUseId(Id);
            if (member != null) {
                List<String> MaccountBookNames = new ArrayList<>();
                List<String> PaccountBookNames = new ArrayList<>();

                // Retrieve AccountBook names from member.getMemberMAccountBook()
                for (String accountBook : member.getMemberMAccountBook()) {
                    MaccountBookNames.add(accountBookService.getBook(accountBook).getAccountBookName());
                }

                // Retrieve AccountBook names from member.getPemberMAccountBook()
                for (String accountBook : member.getMemberPAccountBook()) {
                    PaccountBookNames.add(accountBookService.getBook(accountBook).getAccountBookName());
                }

                // Create a map to hold the member information and account book names
                Map<String, Object> response = new HashMap<>();
                response.put("member", member);
                response.put("MaccountBookNames", MaccountBookNames);
                response.put("PaccountBookNames", PaccountBookNames);

                String responseJson = objectMapper.writeValueAsString(response);

                // JSON 형식의 응답을 전송
                return ResponseEntity.ok().body(responseJson);
            } else {
                // Member 객체를 찾지 못한 경우에 대한 처리
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"not found Member\", \"status\": \"204\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"error\", \"status\": \"500\"}");
        }
    }

    @GetMapping(path = "/logout", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> Logout(@RequestBody String inputjson,
            @CookieValue("memberId") String Token) {

        try {
            if (Token != null) {
                System.out.println(Token);
                Boolean result = memberservice.LogoutMember(Token);

                if (result == true) {// JSON 형식의 Member 객체를 응답으로 전송
                    return ResponseEntity.ok()
                            .body("{\"message\": \"success\", \"status\": \"200\"}");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\"message\": \"error0\", \"status\": \"500\"}");
                }
            } else {
                // Member 객체를 찾지 못한 경우에 대한 처리
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"No Token\", \"status\": \"204\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"error\", \"status\": \"500\"}");
        }
    }

}
