package App.Gonggam.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import App.Gonggam.service.AccountBookService;

import App.Gonggam.model.Member;
import App.Gonggam.model.Post;
import App.Gonggam.model.AccountBook;

@RestController
@RequestMapping("/AccountBook")
public class AccountBookController {
    AccountBookService service = new AccountBookService();

    @PostMapping(path = "/addBook", produces = "application/json", consumes = "application/json")
    public String AddBook(
            @RequestBody AccountBook new_book) {

        boolean check = service.addBook(new_book);
        if (check == true) {
            return "정상처리 완료";
        } else {
            return "실패";
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

    @GetMapping(path = "/getbooks", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> GetPosts() {
        List<AccountBook> books = service.getAllBooks();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(books);
            return ResponseEntity.ok(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }
}
