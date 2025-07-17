package gift.enums;

import org.springframework.data.domain.Sort;

public enum WishSortKey {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    PRODUCT_ID_ASC(Sort.by(Sort.Direction.ASC, "product.id")),
    PRODUCT_ID_DESC(Sort.by(Sort.Direction.DESC, "product.id")),
    PRODUCT_NAME_ASC(Sort.by(Sort.Direction.ASC, "product.name")),
    PRODUCT_NAME_DESC(Sort.by(Sort.Direction.DESC, "product.name")),
    PRODUCT_PRICE_ASC(Sort.by(Sort.Direction.ASC, "product.price")),
    PRODUCT_PRICE_DESC(Sort.by(Sort.Direction.DESC, "product.price"));

    private final Sort sort;

    WishSortKey(Sort sort) {
        this.sort = sort;
    }

    public Sort getSort() {
        return sort;
    }
}
