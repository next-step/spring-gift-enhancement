package gift.common.mapper;

import gift.dto.product.ProductDefaultResponse;
import gift.dto.user.UserAdminResponse;
import gift.dto.user.UserDefaultResponse;
import gift.dto.wishlist.WishedProductResponse;
import gift.entity.*;

import java.util.stream.Collectors;

public class EntityToDtoMapper {

    private EntityToDtoMapper() {
        // 인스턴스 생성 방지
    }



    public static ProductDefaultResponse toDto(Product product) {
        return new ProductDefaultResponse(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }

    public static WishedProductResponse toDto(WishedProduct wishedProduct) {
        Product product = wishedProduct.getProduct();
        Long subtotal = product.getPrice() * wishedProduct.getQuantity();

        return new WishedProductResponse(
                wishedProduct.getId(),
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                wishedProduct.getQuantity(),
                subtotal,
                wishedProduct.getCreatedAt(),
                wishedProduct.getUpdatedAt()
        );
    }

    public static UserDefaultResponse toDto(User user) {
        return new UserDefaultResponse(
            user.getId(),
            user.getEmail()
        );
    }

    public static UserAdminResponse toAdminDto(User user) {
        return new UserAdminResponse(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            user.getRoles().stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.toList()),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}
