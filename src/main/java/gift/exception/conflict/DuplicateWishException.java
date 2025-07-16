package gift.exception.conflict;

public class DuplicateWishException extends DataConflictException {

    public DuplicateWishException(String productName) {
        super("이미 위시리스트에 추가된 상품입니다: " + productName);
    }
}
