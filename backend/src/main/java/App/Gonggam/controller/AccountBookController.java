package App.Gonggam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import App.Gonggam.service.MemberService;

import App.Gonggam.model.Member;
import App.Gonggam.model.AccountBook;

@RestController
@RequestMapping("/AccountBook")
public class AccountBookController {

    @PostMapping(path = "/addBook", produces = "application/json", consumes = "application/json")
    public String SignUpMember(
            @RequestBody AccountBook new_member) {

        return "정상처리 완료";

    }
}
