package gift.service.product;

import gift.domain.Product;
import gift.dto.product.ProductRequest;
import gift.dto.product.ProductResponse;
import gift.global.exception.CustomException;
import gift.global.exception.ErrorCode;
import gift.repository.product.ProductJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductJpaRepository productRepository;

    public ProductService(ProductJpaRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(()->CustomException.from(ErrorCode.NOT_EXISTS));

        return ProductResponse.from(product);
    }

    // 페이지네이션: product 목록 조회
    public Page<ProductResponse> getProductPage(Pageable pageable) {
        return productRepository.findAll(pageable)
            .map(ProductResponse::from);
    }

    public Long insert(ProductRequest request) {
        if (request.name().contains("카카오")) {
            throw CustomException.from(ErrorCode.INVALID_KAKAO_NAME);
        }

        return productRepository.save(Product.from(request)).getId();
    }

    @Transactional
    public void update(ProductRequest request) {
        // 이 경우에는 request.id()에 수정하고자 하는 상품id가 담겨서 넘어옵니다.
        productRepository.findById(request.id())
            .orElseThrow(()->CustomException.from(ErrorCode.NOT_EXISTS));

        if (request.name().contains("카카오")) {
            throw CustomException.from(ErrorCode.INVALID_KAKAO_NAME);
        }

        productRepository.save(Product.from(request));
    }

    public void deleteById(Long productId) {
        productRepository.findById(productId)
            .orElseThrow(()->CustomException.from(ErrorCode.NOT_EXISTS));

        productRepository.deleteById(productId);
    }
}
