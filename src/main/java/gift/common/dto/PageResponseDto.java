package gift.common.dto;

import java.util.List;

public record PageResponseDto<T>(
    List<T> contents,
    int pageNumber,
    int pageSize,
    long totalElements,
    int totalPages
) {

}
