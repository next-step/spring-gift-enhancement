package gift.config;

import gift.exception.DuplicateWishException;
import gift.exception.InsertFailedException;
import gift.exception.UpdateFailedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlerUnexpected(Exception ex) {
        System.out.println(ex);
        return new ResponseEntity<>("INTERNAL_ERROR - 서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handlerException(NoSuchElementException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsertFailedException.class)
    public ResponseEntity<String> handlerException(InsertFailedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UpdateFailedException.class)
    public ResponseEntity<String> handlerException(UpdateFailedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateWishException.class)
    public ResponseEntity<String> handlerException(DuplicateWishException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handlerException(EntityNotFoundException ex)  {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handlerException(ValidationException ex) {
        return new ResponseEntity<>("요청 형식이 잘못 되었습니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handlerException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(ex.getMethod() + " method는 지원되지 않습니다.", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handlerException(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>("유효하지 않은 정렬 기준입니다.", HttpStatus.BAD_REQUEST);
    }
}
