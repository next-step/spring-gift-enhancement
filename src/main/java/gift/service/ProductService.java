package gift.service;

import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import gift.model.Product;
import gift.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품 저장
    public ProductResponse addProduct(ProductRequest request) {
        // 카카오가 포함된 이름은 MD의 승인 필요
        boolean isContainedKakao = request.getName().contains("카카오");
        request.setNeedsMdApproval(isContainedKakao);

        Product product = new Product(request.getName(), request.getPrice(), request.getImageUrl(),
            request.isNeedsMdApproval());
        Product savedProduct = productRepository.save(product);

        return new ProductResponse(savedProduct.getId(), savedProduct.getName(),
            savedProduct.getPrice(), savedProduct.getImageUrl(), savedProduct.getNeedsMdApproval());
    }

    // 상품 전체 조회
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAllByNeedsMdApprovalFalse(pageable);

        return products.map(
            p -> new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getImageUrl(),
                p.getNeedsMdApproval()));
    }

    public Page<ProductResponse> getAllProductsForAdmin(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);

        return products.map(
            p -> new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getImageUrl(),
                p.getNeedsMdApproval()));
    }

    // 상품 단건 조회
    public ProductResponse getProductResponse(Long id) {
        Optional<Product> foundProduct = productRepository.findByIdAndNeedsMdApprovalFalse(id);
        if (foundProduct.isEmpty()) {
            throw new IllegalArgumentException(
                "id: " + id + ". 해당 ID의 상품이 존재하지 않습니다.");
        }

        Product product = foundProduct.get();
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl(), product.getNeedsMdApproval());
    }

    public Product getProduct(Long id) {
        Optional<Product> foundProduct = productRepository.findByIdAndNeedsMdApprovalFalse(id);
        if (foundProduct.isEmpty()) {
            throw new IllegalArgumentException(
                "id: " + id + ". 해당 ID의 상품이 존재하지 않습니다.");
        }

        return foundProduct.get();
    }

    // 상품 수정
    public void updateProduct(Long id, ProductRequest request) {
        // 수정 전에 존재 여부 체크
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isEmpty()) {
            throw new IllegalArgumentException("id: " + id + ". 수정할 상품이 존재하지 않습니다.");
        }
        Product product = foundProduct.get();

        // 카카오가 포함된 이름은 MD의 승인 필요
        boolean isContainedKakao = request.getName().contains("카카오");
        request.setNeedsMdApproval(isContainedKakao);

        product.update(request.getName(), request.getPrice(), request.getImageUrl(),
            request.isNeedsMdApproval());
    }

    // 상품 삭제
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product findById(Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isEmpty()) {
            throw new IllegalArgumentException("id: " + id + ". 해당 ID의 상품이 존재하지 않습니다.");
        }
        return foundProduct.get();
    }
}
