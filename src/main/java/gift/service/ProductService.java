package gift.service;

import gift.dto.RequestDto;
import gift.dto.ResponseDto;
import org.springframework.data.domain.Page;

public interface ProductService {

    // 1. 상품 등록
    public ResponseDto create(RequestDto dto);

    // 2-1. 전체 상품 조회
    public Page<ResponseDto> findAll(int page, int size);

    // 2-2. 특정 상품 조회
    public ResponseDto findById(Long id);

    // 3. 상품 수정
    public ResponseDto update(Long id, RequestDto dto);

    // 4. 상품 삭제
    public void delete(Long id);

}
