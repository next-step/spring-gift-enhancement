package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
@DisplayName("JPA 통합 테스트")
class JpaIntegrationTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private WishJpaRepository wishJpaRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("회원 CRUD 테스트")
    void memberCrudTest() {
        // Create
        Member member = Member.of("test@example.com", "password123");
        Member savedMember = memberJpaRepository.save(member);

        assertAll(
                () -> assertThat(savedMember.id()).isNotNull(),
                () -> assertThat(savedMember.email()).isEqualTo("test@example.com"),
                () -> assertThat(savedMember.password()).isEqualTo("password123")
        );

        // Read
        Member foundMember = memberJpaRepository.findById(savedMember.id()).orElseThrow();
        assertThat(foundMember.email()).isEqualTo("test@example.com");

        // Update
        foundMember.changeEmail("updated@example.com");
        memberJpaRepository.save(foundMember);

        Member updatedMember = memberJpaRepository.findById(savedMember.id()).orElseThrow();
        assertThat(updatedMember.email()).isEqualTo("updated@example.com");

        // Delete
        memberJpaRepository.delete(updatedMember);
        assertThat(memberJpaRepository.findById(savedMember.id())).isEmpty();
    }

    @Test
    @DisplayName("상품 CRUD 및 페이징 테스트")
    void productCrudAndPagingTest() {
        // Create multiple products
        Product product1 = Product.of("상품1", 10000, "http://example.com/1.jpg");
        Product product2 = Product.of("상품2", 20000, "http://example.com/2.jpg");
        Product product3 = Product.of("상품3", 30000, "http://example.com/3.jpg");

        productJpaRepository.saveAll(List.of(product1, product2, product3));

        // Test pagination
        var page = productJpaRepository.findAll(PageRequest.of(0, 2));
        assertAll(
                () -> assertThat(page.getContent()).hasSize(2),
                () -> assertThat(page.getTotalElements()).isEqualTo(3),
                () -> assertThat(page.getTotalPages()).isEqualTo(2)
        );

        // Update
        Product savedProduct = productJpaRepository.findAll().get(0);
        savedProduct.changeName("업데이트된 상품");
        savedProduct.changePrice(99999);
        productJpaRepository.save(savedProduct);

        Product updatedProduct = productJpaRepository.findById(savedProduct.id()).orElseThrow();
        assertAll(
                () -> assertThat(updatedProduct.name()).isEqualTo("업데이트된 상품"),
                () -> assertThat(updatedProduct.price()).isEqualTo(99999)
        );
    }

    @Test
    @DisplayName("위시 CRUD 및 연관관계 테스트")
    void wishCrudAndRelationshipTest() {
        // Setup
        Member member = Member.of("wish@example.com", "password");
        Member savedMember = memberJpaRepository.save(member);

        Product product1 = Product.of("위시상품1", 10000, "http://example.com/wish1.jpg");
        Product product2 = Product.of("위시상품2", 20000, "http://example.com/wish2.jpg");
        Product savedProduct1 = productJpaRepository.save(product1);
        Product savedProduct2 = productJpaRepository.save(product2);

        // Create wishes
        Wish wish1 = Wish.of(savedMember, savedProduct1);
        Wish wish2 = Wish.of(savedMember, savedProduct2);
        wishJpaRepository.saveAll(List.of(wish1, wish2));

        // Test relationship queries
        List<Wish> memberWishes = wishJpaRepository.findByMemberIdWithProduct(savedMember.id());
        assertThat(memberWishes).hasSize(2);

        // Test entity relationship navigation
        Wish foundWish = memberWishes.get(0);
        assertAll(
                () -> assertThat(foundWish.getMember().email()).isEqualTo("wish@example.com"),
                () -> assertThat(foundWish.getProduct().name()).startsWith("위시상품")
        );

        // Test unique constraint
        var existingWish = wishJpaRepository.findByMemberAndProduct(savedMember, savedProduct1);
        assertThat(existingWish).isPresent();

        // Test delete by member and id
        int deletedCount = wishJpaRepository.deleteByIdAndMemberId(wish1.getId(), savedMember.id());
        assertThat(deletedCount).isEqualTo(1);

        List<Wish> remainingWishes = wishJpaRepository.findByMemberIdWithProduct(savedMember.id());
        assertThat(remainingWishes).hasSize(1);
    }

    @Test
    @DisplayName("이메일 유니크 제약조건 테스트")
    void emailUniqueConstraintTest() {
        // Given
        Member member1 = Member.of("duplicate@example.com", "password1");
        memberJpaRepository.save(member1);

        // When & Then
        Member member2 = Member.of("duplicate@example.com", "password2");
        assertThatThrownBy(() -> {
            memberJpaRepository.save(member2);
            entityManager.flush(); // 실제 DB에 쓰기를 강제하여 제약조건 확인
        }).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("연관관계 지연로딩 테스트")
    void lazyLoadingTest() {
        // Setup
        Member member = Member.of("lazy@example.com", "password");
        Member savedMember = memberJpaRepository.save(member);

        Product product = Product.of("지연로딩상품", 10000, "http://example.com/lazy.jpg");
        Product savedProduct = productJpaRepository.save(product);

        Wish wish = Wish.of(savedMember, savedProduct);
        Wish savedWish = wishJpaRepository.save(wish);

        // Clear persistence context
        entityManager.clear();

        // Load wish without product
        Wish foundWish = wishJpaRepository.findById(savedWish.getId()).orElseThrow();
        
        // Product should be loaded lazily
        assertThat(foundWish.getProduct().name()).isEqualTo("지연로딩상품");
    }

    @Test
    @DisplayName("Batch 삭제 테스트")
    void batchDeleteTest() {
        // Setup multiple products
        List<Product> products = List.of(
                Product.of("삭제상품1", 1000, "http://example.com/delete1.jpg"),
                Product.of("삭제상품2", 2000, "http://example.com/delete2.jpg"),
                Product.of("삭제상품3", 3000, "http://example.com/delete3.jpg")
        );
        
        List<Product> savedProducts = productJpaRepository.saveAll(products);
        List<Long> productIds = savedProducts.stream().map(Product::id).toList();

        // Batch delete
        productJpaRepository.deleteAllById(productIds);

        // Verify deletion
        List<Product> remainingProducts = productJpaRepository.findAllById(productIds);
        assertThat(remainingProducts).isEmpty();
    }
}
