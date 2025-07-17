package last.lares.domain.control.service;

import jakarta.transaction.Transactional;
import last.lares.domain.control.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public String saveImage(byte[] image) {
        imageRepository.save(image);
        return "이미지가 저장되었습니다.";
    }

    public byte[] getLastestImage() throws FileNotFoundException {
        byte[] image = imageRepository.getLastest();
        if (image == null) throw new FileNotFoundException("저장된 이미지가 존재하지 않습니다.");
        return image;
    }
}
