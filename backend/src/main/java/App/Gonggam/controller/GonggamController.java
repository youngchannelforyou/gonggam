package App.Gonggam.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import App.Gonggam.model.AccountBook;
import App.Gonggam.model.Community;
import App.Gonggam.model.Member;
import App.Gonggam.model.Notice;
import App.Gonggam.service.AccountBookService;
import App.Gonggam.service.MemberService;

@RestController
@RequestMapping("/gonggam")
public class GonggamController {
    AccountBookService service = new AccountBookService();
    MemberService mService = new MemberService();

    @GetMapping("/{Accountbook}/homeside")
    public ResponseEntity<String> homeGetRequest(@PathVariable("Accountbook") String Accountbook,
            @CookieValue("memberId") String memberId) {

        String Id = mService.FindMemberUseToken(memberId);
        Member checkmember = mService.FindMemberUseId(Id);

        if (checkmember == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail get cookie\", \"status\": \"204\"}");
        }

        AccountBook book = service.getBook(Accountbook);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
        try {
            // JSON 변환을 위한 ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();
            // book, notice, communities를 JSON 문자열로 변환

            Map<String, Object> accountbook = new HashMap<>();
            accountbook.put("name", book.getAccountBookName());
            accountbook.put("count", book.getMembercount());

            ArrayList<String> PaccountBook = new ArrayList<>();
            ArrayList<String> MaccountBook = new ArrayList<>();

            for (String tempbook : checkmember.getMemberPAccountBook()) {
                String newBook = service.IdToBookName(tempbook);
                PaccountBook.add(newBook);
            }

            for (String tempbook : checkmember.getMemberMAccountBook()) {
                String newBook = service.IdToBookName(tempbook);
                MaccountBook.add(newBook);
            }

            Map<String, Object> member = new HashMap<>();
            member.put("name", checkmember.getMemberNickName());
            member.put("PAccountBook", PaccountBook);
            member.put("MAccountBook", MaccountBook);

            // JSON 변환을 위한 ObjectMapper 생성
            String memberJson = objectMapper.writeValueAsString(member);
            String bookJson = objectMapper.writeValueAsString(accountbook);

            // JSON을 응답 본문에 포함하여 반환
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body("{\"book\": " + bookJson + ", \"user\": " + memberJson + "}");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
    }

    @GetMapping("/{Accountbook}/homeexpense")
    public ResponseEntity<String> homeexpenseGetRequest(@PathVariable("Accountbook") String Accountbook,
            @CookieValue("memberId") String memberId) {

        String Id = mService.FindMemberUseToken(memberId);
        Member checkmember = mService.FindMemberUseId(Id);

        if (checkmember == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail get cookie\", \"status\": \"204\"}");
        }

        AccountBook book = service.getBook(Accountbook);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
        try {
            // JSON 변환을 위한 ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();
            // book, notice, communities를 JSON 문자열로 변환
            List<Map<String, Object>> posts = service.homeGetPost(Accountbook, 0, 10);

            String postsJson = objectMapper.writeValueAsString(posts);

            // JSON을 응답 본문에 포함하여 반환
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(postsJson);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
    }

    @GetMapping("/{Accountbook}/accountexpense")
    public ResponseEntity<String> AccountexpenseGetRequest(@PathVariable("Accountbook") String Accountbook,
            @CookieValue("memberId") String memberId) {

        String Id = mService.FindMemberUseToken(memberId);
        Member checkmember = mService.FindMemberUseId(Id);

        if (checkmember == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail get cookie\", \"status\": \"204\"}");
        }

        AccountBook book = service.getBook(Accountbook);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
        try {
            // JSON 변환을 위한 ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();
            // book, notice, communities를 JSON 문자열로 변환
            Map<String, Object> posts = service.temphomeGetPost(Accountbook);
            // Convert the posts map to JSON

            try {
                // Convert the posts map to JSON string
                String postsJson = objectMapper.writeValueAsString(posts);

                // JSON을 응답 본문에 포함하여 반환
                return ResponseEntity.ok()
                        .header("Content-Type", "application/json")
                        .body(postsJson);
            } catch (JsonProcessingException e) {
                // Handle exception if JSON conversion fails
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"server error\", \"status\": \"500\"}");
    }

    @GetMapping("/{Accountbook}/homepeed")
    public ResponseEntity<String> homepeedGetRequest(@PathVariable("Accountbook") String Accountbook,
            @CookieValue("memberId") String memberId) {

        String Id = mService.FindMemberUseToken(memberId);
        Member checkmember = mService.FindMemberUseId(Id);

        if (checkmember == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail get cookie\", \"status\": \"204\"}");
        }

        AccountBook book = service.getBook(Accountbook);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
        try {
            // JSON 변환을 위한 ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();
            // book, notice, communities를 JSON 문자열로 변환

            List<Map<String, Object>> notice = service.homeGetNotice(Accountbook, 0, 3);
            List<Map<String, Object>> communities = service.homeGetCommunity(Accountbook, 0, 7);

            String noticeJson = objectMapper.writeValueAsString(notice);
            String communitiesJson = objectMapper.writeValueAsString(communities);

            // JSON을 응답 본문에 포함하여 반환
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body("{\"notice\": " + noticeJson + ", \"communities\": " + communitiesJson + "}");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
    }

    @GetMapping("/{Accountbook}/notice/{page}")
    public ResponseEntity<String> noticeGetRequest(@PathVariable("Accountbook") String Accountbook,
            @PathVariable("page") int page,
            @CookieValue("memberId") String memberId) {

        String Id = mService.FindMemberUseToken(memberId);
        Member checkmember = mService.FindMemberUseId(Id);

        if (checkmember == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail get cookie\", \"status\": \"204\"}");
        }

        AccountBook book = service.getBook(Accountbook);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
        try {
            // JSON 변환을 위한 ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();
            // book, notice, communities를 JSON 문자열로 변환
            int start = page * 20 - 20;
            int end = page * 20;

            List<Map<String, Object>> notice = service.homeGetNotice(Accountbook, start, end);

            if (notice == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"NO notice\", \"status\": \"500\"}");
            }

            Map<String, Object> accountbook = new HashMap<>();
            accountbook.put("name", book.getAccountBookName());
            accountbook.put("count", book.getMembercount());

            ArrayList<String> PaccountBook = new ArrayList<>();
            ArrayList<String> MaccountBook = new ArrayList<>();

            for (String tempbook : checkmember.getMemberPAccountBook()) {
                String newBook = service.IdToBookName(tempbook);
                PaccountBook.add(newBook);
            }

            for (String tempbook : checkmember.getMemberMAccountBook()) {
                String newBook = service.IdToBookName(tempbook);
                MaccountBook.add(newBook);
            }

            Map<String, Object> member = new HashMap<>();
            member.put("name", checkmember.getMemberNickName());
            member.put("PAccountBook", PaccountBook);
            member.put("MAccountBook", MaccountBook);

            // JSON 변환을 위한 ObjectMapper 생성
            String memberJson = objectMapper.writeValueAsString(member);
            String bookJson = objectMapper.writeValueAsString(accountbook);
            String noticeJson = objectMapper.writeValueAsString(notice);

            // JSON을 응답 본문에 포함하여 반환
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body("{\"book\": " + bookJson + ", \"user\": " + memberJson + ", \"notice\": " + noticeJson + "}");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
    }

    @GetMapping("/{Accountbook}/community/{page}")
    public ResponseEntity<String> communityGetRequest(@PathVariable("Accountbook") String Accountbook,
            @PathVariable("page") int page,
            @CookieValue("memberId") String memberId) {

        String Id = mService.FindMemberUseToken(memberId);
        Member checkmember = mService.FindMemberUseId(Id);

        if (checkmember == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail get cookie\", \"status\": \"204\"}");
        }

        AccountBook book = service.getBook(Accountbook);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
        try {
            // JSON 변환을 위한 ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();
            // book, notice, communities를 JSON 문자열로 변환
            int start = page * 20 - 20;
            int end = page * 20;

            List<Map<String, Object>> communities = service.homeGetCommunity(Accountbook, start, end);

            if (communities == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"NO community\", \"status\": \"500\"}");
            }

            Map<String, Object> accountbook = new HashMap<>();
            accountbook.put("name", book.getAccountBookName());
            accountbook.put("count", book.getMembercount());

            ArrayList<String> PaccountBook = new ArrayList<>();
            ArrayList<String> MaccountBook = new ArrayList<>();

            for (String tempbook : checkmember.getMemberPAccountBook()) {
                String newBook = service.IdToBookName(tempbook);
                PaccountBook.add(newBook);
            }

            for (String tempbook : checkmember.getMemberMAccountBook()) {
                String newBook = service.IdToBookName(tempbook);
                MaccountBook.add(newBook);
            }

            Map<String, Object> member = new HashMap<>();
            member.put("name", checkmember.getMemberNickName());
            member.put("PAccountBook", PaccountBook);
            member.put("MAccountBook", MaccountBook);

            // JSON 변환을 위한 ObjectMapper 생성
            String memberJson = objectMapper.writeValueAsString(member);
            String bookJson = objectMapper.writeValueAsString(accountbook);
            String communitiesJson = objectMapper.writeValueAsString(communities);

            // JSON을 응답 본문에 포함하여 반환
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body("{\"book\": " + bookJson + ", \"user\": " + memberJson + ", \"community\": " + communitiesJson
                            + "}");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
    }

    @GetMapping("/{Accountbook}/communitypeed/{post}")
    public ResponseEntity<String> CommunityPostGetRequest(@PathVariable("Accountbook") String Accountbook,
            @PathVariable("post") int page,
            @CookieValue("memberId") String memberId) {

        String Id = mService.FindMemberUseToken(memberId);
        Member checkmember = mService.FindMemberUseId(Id);

        if (checkmember == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail get cookie\", \"status\": \"204\"}");
        }

        AccountBook book = service.getBook(Accountbook);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
        try {
            // JSON 변환을 위한 ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();

            Community communities = service.getCommunity(page, Accountbook); // 찬영

            if (communities == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"NO community\", \"status\": \"500\"}");
            }

            Map<String, Object> accountbook = new HashMap<>();
            accountbook.put("name", book.getAccountBookName());
            accountbook.put("count", book.getMembercount());

            ArrayList<String> PaccountBook = new ArrayList<>();
            ArrayList<String> MaccountBook = new ArrayList<>();

            for (String tempbook : checkmember.getMemberPAccountBook()) {
                String newBook = service.IdToBookName(tempbook);
                PaccountBook.add(newBook);
            }

            for (String tempbook : checkmember.getMemberMAccountBook()) {
                String newBook = service.IdToBookName(tempbook);
                MaccountBook.add(newBook);
            }

            Map<String, Object> member = new HashMap<>();
            member.put("name", checkmember.getMemberNickName());
            member.put("PAccountBook", PaccountBook);
            member.put("MAccountBook", MaccountBook);

            // JSON 변환을 위한 ObjectMapper 생성
            String memberJson = objectMapper.writeValueAsString(member);
            String bookJson = objectMapper.writeValueAsString(accountbook);
            String communitiesJson = objectMapper.writeValueAsString(communities);

            // JSON을 응답 본문에 포함하여 반환
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body("{\"book\": " + bookJson + ", \"user\": " + memberJson + ", \"community\": " + communitiesJson
                            + "}");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
    }

    @GetMapping("/{Accountbook}/noticepeed/{post}")
    public ResponseEntity<String> NoticePostGetRequest(@PathVariable("Accountbook") String Accountbook,
            @PathVariable("post") int page,
            @CookieValue("memberId") String memberId) {

        String Id = mService.FindMemberUseToken(memberId);
        Member checkmember = mService.FindMemberUseId(Id);

        if (checkmember == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail get cookie\", \"status\": \"204\"}");
        }

        AccountBook book = service.getBook(Accountbook);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
        try {
            // JSON 변환을 위한 ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();

            Notice communities = service.getNotice(page, Accountbook); // 찬영

            if (communities == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"NO community\", \"status\": \"500\"}");
            }

            Map<String, Object> accountbook = new HashMap<>();
            accountbook.put("name", book.getAccountBookName());
            accountbook.put("count", book.getMembercount());

            ArrayList<String> PaccountBook = new ArrayList<>();
            ArrayList<String> MaccountBook = new ArrayList<>();

            for (String tempbook : checkmember.getMemberPAccountBook()) {
                String newBook = service.IdToBookName(tempbook);
                PaccountBook.add(newBook);
            }

            for (String tempbook : checkmember.getMemberMAccountBook()) {
                String newBook = service.IdToBookName(tempbook);
                MaccountBook.add(newBook);
            }

            Map<String, Object> member = new HashMap<>();
            member.put("name", checkmember.getMemberNickName());
            member.put("PAccountBook", PaccountBook);
            member.put("MAccountBook", MaccountBook);

            // JSON 변환을 위한 ObjectMapper 생성
            String memberJson = objectMapper.writeValueAsString(member);
            String bookJson = objectMapper.writeValueAsString(accountbook);
            String communitiesJson = objectMapper.writeValueAsString(communities);

            // JSON을 응답 본문에 포함하여 반환
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body("{\"book\": " + bookJson + ", \"user\": " + memberJson + ", \"notice\": " + communitiesJson
                            + "}");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
    }

    @GetMapping("/{Accountbook}/postpeed/{date}")
    public ResponseEntity<String> CalenderPostGetRequest(@PathVariable("Accountbook") String Accountbook,
            @PathVariable("date") String date,
            @CookieValue("memberId") String memberId) {

        String Id = mService.FindMemberUseToken(memberId);
        Member checkmember = mService.FindMemberUseId(Id);

        if (checkmember == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail get cookie\", \"status\": \"204\"}");
        }

        AccountBook book = service.getBook(Accountbook);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
        try {
            // JSON 변환을 위한 ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();

            List<Map<String, Object>> communities = service.getCalenderPost(Accountbook, date); // 찬영

            if (communities == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"NO community\", \"status\": \"500\"}");
            }

            Map<String, Object> accountbook = new HashMap<>();
            accountbook.put("name", book.getAccountBookName());
            accountbook.put("count", book.getMembercount());

            ArrayList<String> PaccountBook = new ArrayList<>();
            ArrayList<String> MaccountBook = new ArrayList<>();

            for (String tempbook : checkmember.getMemberPAccountBook()) {
                String newBook = service.IdToBookName(tempbook);
                PaccountBook.add(newBook);
            }

            for (String tempbook : checkmember.getMemberMAccountBook()) {
                String newBook = service.IdToBookName(tempbook);
                MaccountBook.add(newBook);
            }

            Map<String, Object> member = new HashMap<>();
            member.put("name", checkmember.getMemberNickName());
            member.put("PAccountBook", PaccountBook);
            member.put("MAccountBook", MaccountBook);

            // JSON 변환을 위한 ObjectMapper 생성
            String memberJson = objectMapper.writeValueAsString(member);
            String bookJson = objectMapper.writeValueAsString(accountbook);
            String communitiesJson = objectMapper.writeValueAsString(communities);

            // JSON을 응답 본문에 포함하여 반환
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body("{\"book\": " + bookJson + ", \"user\": " + memberJson + ", \"notice\": " + communitiesJson
                            + "}");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }
    }
}
