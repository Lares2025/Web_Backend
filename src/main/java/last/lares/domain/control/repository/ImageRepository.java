package last.lares.domain.control.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ImageRepository {
    private final List<byte[]> imageCache = new ArrayList<>();

    public void save(byte[] image) {
        imageCache.add(image);
        if (imageCache.size() > 10) imageCache.remove(0);
    }

    public byte[] getLastest() {
        if (imageCache.isEmpty()) return null;
        return imageCache.get(imageCache.size() - 1);
    }
}
