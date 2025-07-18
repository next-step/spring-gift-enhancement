package gift.service;

import gift.dto.Pagination;
import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    Page<ProductResponse> getAllProducts(Pagination pagination);

    ProductResponse getProduct(Long id);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);
}
