package gift.exception.itemException;

import gift.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ItemQuantityException extends RuntimeException {

    public ItemQuantityException() {
        super("수량은 1 - 10,000,000개 까지입니다.");
    }
}
