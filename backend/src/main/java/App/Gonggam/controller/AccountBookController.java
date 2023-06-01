package App.Gonggam.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import App.Gonggam.service.AccountBookService;

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

    @PostMapping(path = "/addpost", produces = "application/json", consumes = "application/json")
    public String AddPost(
            @RequestBody String inputjson) {
        Post new_post = new Post();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputjson);
            new_post.setPostTag(jsonNode.get("Tag").asText());
            new_post.setPostType(jsonNode.get("Type").asBoolean());
            new_post.setPostDate(jsonNode.get("Date").asText());
            new_post.setPostTitle(jsonNode.get("Title").asText());
            new_post.setPostText(jsonNode.get("Text").asText());

            // String image = jsonNode.get("Image").as();
            JsonNode imageNode = jsonNode.get("Image");
            ArrayList<BufferedImage> imageList = new ArrayList<>();

            if (imageNode.isArray()) {
                for (JsonNode image : imageNode) {
                    String imageData = image.asText();
                    byte[] decodedData = Base64.getDecoder().decode(imageData);

                    try (InputStream inputStream = new ByteArrayInputStream(decodedData)) {
                        BufferedImage bufferedImage = ImageIO.read(inputStream);
                        imageList.add(bufferedImage);
                    } catch (IOException e) {
                        // 이미지 변환 중 오류 처리
                    }
                }
            }

            ArrayList<String> filePaths = new ArrayList<>();

            for (int i = 0; i < imageList.size(); i++) {
                BufferedImage image = imageList.get(i);

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    Date currentDate = new Date();
                    String dateTime = dateFormat.format(currentDate);
                    String fileName = "image_" + dateTime + "_" + i + ".png";

                    String filePath = "./save/" + fileName;

                    File file = new File(filePath);
                    String absolutePath = file.getAbsolutePath();

                    File outputFile = new File(filePath);
                    ImageIO.write(image, "png", outputFile);

                    filePaths.add(absolutePath);
                } catch (IOException e) {
                }
            }

            new_post.setPostImage(filePaths);

            new_post.setPostUsedBudget(jsonNode.get("Used_Budget").asLong());

            boolean check = service.AddPost(new_post, jsonNode.get("Table").asText());
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
}
