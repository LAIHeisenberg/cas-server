package me.plutoz.cas.server.credential;

import org.apereo.cas.authentication.RememberMeUsernamePasswordCredential;

import javax.validation.constraints.Size;

public class RememberMeUsernamePasswordCaptchaCredential extends RememberMeUsernamePasswordCredential {
    @Size(min = 4, max = 8, message = "require captcha")
    private String capcha;

    public String getCapcha() {
        return capcha;
    }

    public void setCapcha(String capcha) {
        this.capcha = capcha;
    }
}
