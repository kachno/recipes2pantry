package pl.recipes2pantry.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EntityExceptionHandler {
    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handle(EntityAlreadyExistsException exp) {
        return new Error(exp.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handle(EntityNotFoundException exp) {
        return new Error(exp.getMessage());
    }

    @ExceptionHandler(EntityWrongValue.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handle(EntityWrongValue exp) {
        return new Error(exp.getMessage());
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Error {
    private String message;
}
