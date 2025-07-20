package gift.repository;

import gift.domain.Product;
import gift.dto.common.Page;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    private static final int MIN_PAGE_NUMBER = 1;
    private static final int MIN_PAGE_SIZE = 1;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Product save(Product product) {
        Objects.requireNonNull(product, "상품은 null일 수 없습니다.");
        return productJpaRepository.save(product);
    }

    @Override
    public void update(Long id, Product updatedProduct) {
        Objects.requireNonNull(id, "id는 null일 수 없습니다.");
        Objects.requireNonNull(updatedProduct, "updatedProduct는 null일 수 없습니다.");

        Product existingProduct = productJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품이 존재하지 않아 업데이트할 수 없습니다: " + id));

        existingProduct.changeName(updatedProduct.name());
        existingProduct.changePrice(updatedProduct.price());
        existingProduct.changeImageUrl(updatedProduct.imageUrl());

        productJpaRepository.save(existingProduct);
    }

    @Override
    public void deleteAllByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        productJpaRepository.deleteAllById(ids);
    }

    @Override
    public void deleteById(Long id) {
        Objects.requireNonNull(id, "id는 null일 수 없습니다.");
        productJpaRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }

    @Override
    public Page<Product> findAllByPage(int pageNumber, int pageSize) {
        validatePageParams(pageNumber, pageSize);

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        org.springframework.data.domain.Page<Product> springPage = productJpaRepository.findAll(pageable);

        return new Page<>(
                springPage.getContent(),
                pageNumber,
                pageSize,
                (int) springPage.getTotalElements()
        );
    }

    @Override
    public Optional<Product> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return productJpaRepository.findById(id);
    }

    private void validatePageParams(int pageNumber, int pageSize) {
        if (pageNumber < MIN_PAGE_NUMBER) {
            throw new IllegalArgumentException("페이지 번호는 " + MIN_PAGE_NUMBER + " 이상이어야 합니다.");
        }
        if (pageSize < MIN_PAGE_SIZE) {
            throw new IllegalArgumentException("페이지 크기는 " + MIN_PAGE_SIZE + " 이상이어야 합니다.");
        }
    }
}
