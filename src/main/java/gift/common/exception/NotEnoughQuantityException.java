package gift.common.exception;

public class NotEnoughQuantityException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "옵션 수량이 부족합니다.";

    public NotEnoughQuantityException() {
        super(DEFAULT_MESSAGE);
    }
}
