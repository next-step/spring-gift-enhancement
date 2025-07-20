package gift.exception;

import gift.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("INVALID_CREDENTIALS", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponseDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("RESOURCE_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponseDto);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("EMAIL_ALREADY_EXISTS", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDto);
    }

    @ExceptionHandler(DuplicateWishException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateWishException(DuplicateWishException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("DUPLICATE_WISHLIST", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDto);
    }

    @ExceptionHandler(InvalidSortOptionException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidSortOptionException(InvalidSortOptionException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("INVALID_SORT_OPTION", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponseDto);
    }
}
