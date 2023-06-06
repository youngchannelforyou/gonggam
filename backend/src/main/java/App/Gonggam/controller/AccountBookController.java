package App.Gonggam.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import App.Gonggam.service.AccountBookService;
import App.Gonggam.service.MemberService;
import App.Gonggam.model.Post;
import App.Gonggam.model.AccountBook;

@RestController
@RequestMapping("/AccountBook")
public class AccountBookController {
    AccountBookService service = new AccountBookService();
    MemberService mService = new MemberService();

    @PostMapping(path = "/addBook", produces = "application/json", consumes = "application/json")
    public String AddBook(
            @RequestBody String inputjson) {
        AccountBook new_book = new AccountBook();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputjson);
            new_book.setAccountBookName(jsonNode.get("Name").asText());
            new_book.setAccountBookPublic(jsonNode.get("Public").asBoolean());
            new_book.setAccountBookMainManager(mService.FindMemberUseToken(jsonNode.get("Manager").asText())); // Manager토큰
            new_book.setAccountBook_Budget(jsonNode.get("Budget").asLong());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (new_book.getAccountBookMainManager() == null) {
            return "토큰실패";
        }
        boolean check = service.addBook(new_book);
        if (check == true) {
            return "정상처리 완료";
        } else {
            return "실패";
        }
    }

    @PostMapping(path = "/getBook", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> GetBook(
            @RequestBody String inputjson) {
        String book = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputjson);
            book = jsonNode.get("book").asText();
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

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }

    @PostMapping(path = "/findBook", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> FindBook(
            @RequestBody String inputjson) {
        String book = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputjson);
            book = jsonNode.get("book").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(book);

        ArrayList<String> booklist = new ArrayList<String>();
        booklist = service.FindBook(book);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(booklist);
            System.out.println("json");
            return ResponseEntity.ok(json);
        } catch (JsonProcessingException e) {
            System.out.println("서버 오류");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
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
    public String AddPost(@RequestParam(value = "file", required = false) MultipartFile[] file,
            @RequestParam("Budget") Long Used_Budget,
            @RequestParam(value = "Text", required = false) String text,
            @RequestParam("Title") String title,
            @RequestParam("Date") String date,
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
                return "error";
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
                return "정상처리 완료";
            } else {
                return "실패";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "실패";
    }

    @PostMapping(path = "/addmember", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> AddMember(
            @RequestBody String inputjson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputjson);
            String Manager = jsonNode.get("Manager").asText();
            String Member = jsonNode.get("Member").asText();
            String TableName = jsonNode.get("TableName").asText();

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
