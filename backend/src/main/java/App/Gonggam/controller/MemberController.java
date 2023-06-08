package App.Gonggam.controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.SqlResult;

import App.Gonggam.model.Member;
import App.Gonggam.service.MemberService;
import jakarta.servlet.http.Cookie;

@RestController
@RequestMapping(value = "/Member")
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {
    MemberService memberservice = new MemberService();

    @PostMapping(path = "/createcode", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> CreateCode(
            @RequestBody String inputjson) {
        String result = "서버에러";
        Boolean SqlResult = false;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputjson);
            String Email = jsonNode.get("Email").asText();
            result = memberservice.sendEmail(Email);
            SqlResult = memberservice.signsql(Email, result);
        } catch (Exception e) {
        }
        if (SqlResult != null) {
            return ResponseEntity.ok().body("코드 생성 완료");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 : 코드 생성 실패");
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
            return ResponseEntity.ok().body("정상");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 : 실패 없음");
        }
    }

    // http://localhost:8080/Member/signupmember?name=유찬영&nick_name=UUU&Age=17&Email=youngchannl4u@gmail.com&password=12341234
    @PostMapping(path = "/signupmember", produces = "application/json", consumes = "application/json")
    public String SignUpMember(
            @RequestBody Member new_member) {

        boolean check = memberservice.AddMember(new_member);
        if (check == true) {
            return "정상처리 완료";
        } else {
            return "실패";
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
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("id를 찾을 수 없습니다.");
            } else {
                System.out.println(checkMember.getMemberPassword());
                if (password.equals(checkMember.getMemberPassword())) {
                    try {
                        try {
                            HttpHeaders headers = new HttpHeaders();
                            Cookie cookie = new Cookie("memberId", checkMember.getMemberToken());
                            cookie.setMaxAge(3600); // 쿠키의 유효 기간을 초 단위로 설정 (예: 1시간)
                            cookie.setPath("/"); // 쿠키의 전송 경로를 루트 경로로 설정

                            // cookie.setDomain(".example.com"); // 쿠키의 도메인을 설정 (예: .example.com, 서브도메인 포함)
                            // cookie.setSecure(true); // 쿠키를 보안 연결(HTTPS)에서만 전송하도록 설정
                            // cookie.setHttpOnly(true); // 클라이언트 스크립트에서 쿠키에 접근하지 못하도록 설정
                            // headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
                            headers.add("Access-Control-Allow-Credentials", "true");
                            headers.add(HttpHeaders.SET_COOKIE,
                                    cookie.getName() + "=" + cookie.getValue());

                            return ResponseEntity.ok().headers(headers).body("{\"result\": \"success\"}");
                        } catch (Exception e) {
                            System.out.println("쿠키에러");
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("쿠키에러");
                        }

                        // return ResponseEntity.ok(json);
                    } catch (Exception e) {
                        System.out.println("서버 오류");

                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
                    }
                } else {
                    System.out.println("비밀번호 틀림");

                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호 틀림");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
    }

    public ResponseEntity<String> getMemberInfo(@RequestBody String inputjson,
            @CookieValue("memberId") String memberId) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String Id = memberservice.FindMemberUseToken(memberId);
            Member member = memberservice.FindMemberUseId(Id);
            if (member != null) {
                String memberJson = objectMapper.writeValueAsString(member);

                // JSON 형식의 Member 객체를 응답으로 전송
                return ResponseEntity.ok().body(memberJson);
            } else {
                // Member 객체를 찾지 못한 경우에 대한 처리
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 멤버를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }

}
