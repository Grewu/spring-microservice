package handler;

import org.example.exception.AccountNotFoundException;
import org.example.exception.ExceptionMessage;
import org.example.handler.GlobalHandlerAdvice;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

public class GlobalHandlerAdviceTest {

    private final GlobalHandlerAdvice globalHandlerAdvice = new GlobalHandlerAdvice();

    @Test
    void handleNewsNotFoundException() {
        AccountNotFoundException newsNotFoundException = new AccountNotFoundException(1L);
        ResponseEntity<ExceptionMessage> handle =
                globalHandlerAdvice.handle(newsNotFoundException);

        assertThat(handle)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.NOT_FOUND)
                .extracting("body")
                .hasFieldOrPropertyWithValue("status", 404)
                .hasFieldOrPropertyWithValue("message", "Account with id 1 not found.");
    }
}
