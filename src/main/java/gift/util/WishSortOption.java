package gift.util;

public enum WishSortOption {
    ID("id"),
    PRODUCT_NAME("product.name"),
    PRODUCT_PRICE("product.price");

    private final String value;

    WishSortOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public static boolean isValid(String value) {
        for (ProductSortOption option : ProductSortOption.values()) {
            if (option.getValue().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

}
