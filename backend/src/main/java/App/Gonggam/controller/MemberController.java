package App.Gonggam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.RequestMethod;

import App.Gonggam.model.Member;
import App.Gonggam.service.MemberService;

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
    public ResponseEntity<String> Login(@RequestBody Member member) {
        String Id = member.getMember_Id();
        String password = member.getMember_password();
        Member checkMember = memberservice.GetMember(Id);
        if (checkMember == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id를 찾을 수 없습니다.");
        } else {
            if (password.equals(checkMember.getMember_password())) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    String json = objectMapper.writeValueAsString(checkMember);
                    return ResponseEntity.ok(json);
                } catch (JsonProcessingException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호 틀림");
            }
        }
    }
}
