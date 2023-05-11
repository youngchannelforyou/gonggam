package App.Gonggam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        memberservice.AddMember(new_member);

        return "정상처리 완료";
    }

    @GetMapping(path = "/login", produces = "application/json", consumes = "application/json")
    public void get1() {
        // 데이터베이스에서 사용자 리스트를 가져온다고 가정

    }
}
