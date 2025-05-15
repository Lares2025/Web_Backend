package last.lares.domain.control.service;

import last.lares.domain.control.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataService {
    private final DataRepository dataRepository;
}
