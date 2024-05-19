package org.example.exception;

public class AccountNotFoundException extends AbstractExceptionMessageException  {

    public AccountNotFoundException(Long id) {
        super(String.format("Account with id %s not found.", id));
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
