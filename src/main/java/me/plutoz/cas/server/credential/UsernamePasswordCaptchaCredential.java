package me.plutoz.cas.server.credential;

import org.apereo.cas.authentication.UsernamePasswordCredential;

import javax.validation.constraints.Size;

public class UsernamePasswordCaptchaCredential extends UsernamePasswordCredential {
    @Size(min = 4, max = 8, message = "require captcha")
    private String capcha;

    public String getCapcha() {
        return capcha;
    }

    public void setCapcha(String capcha) {
        this.capcha = capcha;
    }
}
