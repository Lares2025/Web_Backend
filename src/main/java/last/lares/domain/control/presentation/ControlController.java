package last.lares.domain.control.presentation;

import last.lares.domain.control.presentation.dto.DataListDto;
import last.lares.domain.control.service.DataService;
import last.lares.domain.control.service.ImageService;
import last.lares.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/control")
@RequiredArgsConstructor
public class ControlController {
    private final DataService dataService;
    private final ImageService imageService;

    @GetMapping("/")
    public ResponseEntity<?> allData() {
        try {
            DataListDto response = dataService.allData();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("내부 서버 에러가 발생하였습니다.");
        }
    }

    @DeleteMapping("/{controlId}")
    public ResponseEntity<?> deleteData(@PathVariable int controlId) {
        try {
            CommonResponseDto response = dataService.deleteData(controlId);

            return ResponseEntity.ok().body(response);
        } catch (UsernameNotFoundException e) {
            CommonResponseDto response = CommonResponseDto.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.internalServerError().body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("내부 서버 에러가 발생하였습니다.");
        }
    }

    @PostMapping("/img")
    public ResponseEntity<?> saveImage(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(imageService.saveImage(file.getBytes()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("이미지 저장 실패: " + e.getMessage());
        }
    }

    @GetMapping("/img")
    public ResponseEntity<?> lastestImage() {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageService.getLastestImage());
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body("이미지 불러오기 실패: " + e.getMessage());
        }
    }
}
