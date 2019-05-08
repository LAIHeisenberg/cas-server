package me.plutoz.cas.server.exception;

import javax.security.auth.login.LoginException;

public class CapchaException extends LoginException {
    private static final long serialVersionUID = 5010179669623488036L;

    public CapchaException() {
        super();
    }

    public CapchaException(String msg) {
        super(msg);
    }
}
