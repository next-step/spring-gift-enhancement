package gift.service.product;

import gift.domain.Product;
import gift.dto.product.ProductRequest;
import gift.dto.product.ProductResponse;
import gift.global.exception.CustomException;
import gift.global.exception.ErrorCode;
import gift.repository.product.ProductJpaRepository;
import java.util.List;
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

    public List<ProductResponse> getProductList() {
        return productRepository.findAll().stream()
            .map(ProductResponse::from)
            .toList();
    }

}
