package gift.exception.itemException;

import gift.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ItemQuantityException extends ApplicationException {
    public ItemQuantityException() {
        super(HttpStatus.BAD_REQUEST,"수량은 1 - 100,000,000 개까지 입니다.");
    }
}
