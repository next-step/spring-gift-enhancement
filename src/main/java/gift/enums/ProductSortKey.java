package gift.enums;

import org.springframework.data.domain.Sort;

public enum ProductSortKey {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    NAME_ASC(Sort.by(Sort.Direction.ASC, "name")),
    NAME_DESC(Sort.by(Sort.Direction.DESC, "name")),
    PRICE_ASC(Sort.by(Sort.Direction.ASC, "price")),
    PRICE_DESC(Sort.by(Sort.Direction.DESC, "price"));

    private final Sort sort;

    ProductSortKey(Sort sort) {
        this.sort = sort;
    }

    public Sort getSort() {
        return sort;
    }
}
