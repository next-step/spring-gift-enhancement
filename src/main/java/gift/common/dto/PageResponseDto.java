package gift.common.dto;

import java.util.List;
import org.springframework.data.domain.Page;

public record PageResponseDto<T>(
    List<T> contents,
    int pageNumber,
    int pageSize,
    long totalElements,
    int totalPages
) {

    public static <T> PageResponseDto<T> from(Page<T> page) {
        return new PageResponseDto<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages()
        );
    }
}
