package App.Gonggam.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import App.Gonggam.service.AccountBookService;
import App.Gonggam.service.MemberService;
import App.Gonggam.model.Post;
import App.Gonggam.model.AccountBook;
import App.Gonggam.model.Community;
import App.Gonggam.model.Notice;

@RestController
@RequestMapping("/AccountBook")
public class AccountBookController {
    AccountBookService service = new AccountBookService();
    MemberService mService = new MemberService();

    @PostMapping(path = "/addBook", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> AddBook(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("Name") String Name,
            @RequestParam("Public") Boolean Public,
            @CookieValue("memberId") String memberId) {
        AccountBook new_book = new AccountBook();
        String imgpath;

        if (file == null) {
            imgpath = "./dataset/img/AccountBookImg/AccountBook.png";

        } else {
            try {
                // 이미지 폴더 경로 설정
                String uploadDir = "./dataset/img/AccountBookImg";

                Path absolutePath = Paths.get(uploadDir).toAbsolutePath();

                File directory = new File(absolutePath.toString());
                if (!directory.exists()) {
                    directory.mkdirs(); // 폴더가 없으면 생성
                }

                Date currentTime = new Date();
                imgpath = absolutePath + "/" + currentTime + Name;
                File dest = new File(imgpath);

                // 파일 저장
                file.transferTo(dest);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"fail save image\", \"status\": \"500\"}");
            }
        }
        ///////////////////////////////////////////////

        try {
            new_book.setAccountBookName(Name);
            new_book.setAccountBookPublic(Public);
            new_book.setAccountBookMainManager(mService.FindMemberUseToken(memberId)); // Manager토큰
            new_book.setAccountBook_Budget(0);
            new_book.setMembercount(1);
            new_book.setAccountBookLogo(imgpath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (new_book.getAccountBookMainManager() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail found Manager token\", \"status\": \"204\"}");
        }

        boolean check = service.addBook(new_book);

        if (check == true) {
            return ResponseEntity.ok()
                    .body("{\"message\": \"success\", \"status\": \"200\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail check code\", \"status\": \"500\"}");
        }
    }

    @GetMapping("/gonggam/{url}")
    public ResponseEntity<String> handleGetRequest(@PathVariable("url") String url,
            @CookieValue("memberId") String memberId) {

        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail get cookie\", \"status\": \"204\"}");
        }

        String[] pathSegments = url.split("/");
        int accountbook = Integer.parseInt(pathSegments[0]); // ex> "1"
        String requestType = pathSegments[1]; // ex> "home"
        AccountBook book = service.getBook(accountbook);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"server error\", \"status\": \"500\"}");
        }

        if (requestType.equals("home")) {
            List<Notice> notice = service.getNotice(requestType, 1, 3);
            List<Community> communities = service.getCommunity(requestType, 1, 7);
        } else if (requestType.equals("budget")) {

        } else if (requestType.equals("asset")) {

        } else if (requestType.equals("accountbook")) {

        } else if (requestType.equals("notice")) {

        } else if (requestType.equals("community")) {

        } else if (requestType.equals("settings")) {

        } else {

        }
        return ResponseEntity.ok("Success");
    }

    @PostMapping(path = "/getBook", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> GetBook(
            @RequestBody String inputjson) {
        int book = 1;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputjson);
            book = jsonNode.get("book").asInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(book);

        AccountBook booklist = service.getBook(book);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(booklist);
            System.out.println("json");
            return ResponseEntity.ok(json);
        } catch (JsonProcessingException e) {
            System.out.println("서버 오류");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail check code\", \"status\": \"500\"}");
        }
    }

    @PostMapping(path = "/findBook", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> FindBook(
            @RequestBody String inputjson) {
        int book = 0;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputjson);
            book = jsonNode.get("book").asInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(book);

        List<Map<String, String>> booklist = service.FindBook(book);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(booklist);
            System.out.println("json");
            return ResponseEntity.ok(json);
        } catch (JsonProcessingException e) {
            System.out.println("서버 오류");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"fail check code\", \"status\": \"500\"}");
        }
    }

    @PostMapping(path = "/getposts", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> GetPosts(
            String tableName) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(tableName);
            tableName = jsonNode.get("tableName").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Post> posts = service.getAllPosts(tableName);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(posts);
            return ResponseEntity.ok(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }

    // // 전송된 데이터 사용
    // System.out.println("Tag: " + );
    // System.out.println("Type: " + );
    // System.out.println("Date: " + );
    // System.out.println("Title: " + title);
    // System.out.println("Text: " + text);
    // System.out.println("File: " + file.getOriginalFilename());

    // // 처리 로직 추가
    // // 파일 저장

    // file.transferTo(dest);

    // // 처리 로직 추가

    // return "success";
    // }

    // }

    @PostMapping(path = "/addpost", produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> AddPost(@RequestParam(value = "file", required = false) MultipartFile[] file,
            @RequestParam("Budget") Long Used_Budget,
            @RequestParam(value = "Text", required = false) String text,
            @RequestParam("Title") String title,
            @RequestParam("Date") java.sql.Date date,
            @RequestParam("Type") String type,
            @RequestParam("Table") String table,
            @RequestParam(value = "Tag", required = false) String tag) {
        Post new_post = new Post();
        try {
            // 테스트
            new_post.setPostTag(tag);
            new_post.setPostType(Boolean.parseBoolean(type));
            new_post.setPostDate(date);
            new_post.setPostTitle(title);
            new_post.setPostText(text);
            String uploadDir = "";
            Path absolutePath = null;
            // String image = jsonNode.get("Image").as();
            try {
                // 이미지 폴더 경로 설정
                uploadDir = "./dataset/img";

                absolutePath = Paths.get(uploadDir).toAbsolutePath();

                File directory = new File(absolutePath.toString());
                if (!directory.exists()) {
                    directory.mkdirs(); // 폴더가 없으면 생성
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"fail check code\", \"status\": \"500\"}");
            }
            ArrayList<String> filePaths = new ArrayList<>();

            if (file != null && file.length > 0) {
                for (MultipartFile img : file) {
                    String fileName = img.getOriginalFilename();
                    String filePath = absolutePath + "/" + fileName;
                    File dest = new File(filePath);

                    filePaths.add(filePath);
                    // 파일 저장
                    img.transferTo(dest);
                }
            }

            new_post.setPostImage(filePaths);

            new_post.setPostUsedBudget(Used_Budget);

            boolean check = service.AddPost(new_post, table);
            if (check == true) {
                return ResponseEntity.ok()
                        .body("{\"message\": \"success\", \"status\": \"200\"}");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"server error2\", \"status\": \"500\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"server error3\", \"status\": \"500\"}");
    }

    @PostMapping(path = "/addmember", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> AddMember(
            @RequestBody String inputjson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputjson);
            String Manager = jsonNode.get("Manager").asText();
            String Member = jsonNode.get("Member").asText();
            int TableName = jsonNode.get("TableName").asInt();

            String result = service.addMember(Manager, Member, TableName);
            try {
                String json = objectMapper.writeValueAsString(result);
                return ResponseEntity.ok(json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
    }
}
