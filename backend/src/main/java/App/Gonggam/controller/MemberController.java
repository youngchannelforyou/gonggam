package App.Gonggam.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import App.Gonggam.model.Member;
import App.Gonggam.service.MemberService;
import jakarta.servlet.http.Cookie;

@RestController
@RequestMapping(value = "/Member")
public class MemberController {
    MemberService memberservice = new MemberService();

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

            Member checkMember = memberservice.LoginMember(Id);

            if (checkMember == null) {
                System.out.println("id 못 찾음");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id를 찾을 수 없습니다.");
            } else {
                System.out.println(checkMember.getMemberPassword());
                if (password.equals(checkMember.getMemberPassword())) {
                    try {
                        try {
                            String json = objectMapper.writeValueAsString(checkMember);
                            HttpHeaders headers = new HttpHeaders();
                            headers.add("Set-Cookie", "memberId=" + checkMember.getMemberToken());

                            return ResponseEntity.ok().headers(headers).body(json);
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
}
