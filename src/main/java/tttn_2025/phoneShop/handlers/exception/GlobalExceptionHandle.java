package tttn_2025.phoneShop.handlers.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import tttn_2025.phoneShop.common.models.response.error.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex) {
        return ResponseEntity.internalServerError().body(
                ErrorResponse.builder().code("ERROR").message(ex.getMessage()).build());
    }
}
