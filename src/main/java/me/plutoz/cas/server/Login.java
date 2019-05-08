package me.plutoz.cas.server;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import me.plutoz.boot.tool.rpc.HttpClient;
import me.plutoz.cas.server.credential.UsernamePasswordCaptchaCredential;
import me.plutoz.cas.server.dto.AuthUserInfo;
import me.plutoz.cas.server.dto.AuthUserInfoResponse;
import me.plutoz.cas.server.exception.CapchaException;
import org.apache.http.entity.ContentType;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.Map;

public class Login extends AbstractPreAndPostProcessingAuthenticationHandler {
    public Login(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {
        UsernamePasswordCaptchaCredential myCredential = (UsernamePasswordCaptchaCredential) credential;

        String capcha = myCredential.getCapcha();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String right = attributes.getRequest().getSession().getAttribute("capcha").toString();

        if (!right.equals(capcha)) {
            throw new CapchaException("验证码错误");
        }

        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setUserId(myCredential.getUsername());
        authUserInfo.setPassword(myCredential.getPassword());
        try {
            String authUrl = "https://localhost:27501/xmanagement/pms/pms-api/auth";
            System.out.println("用户认证[req]:" + JSON.toJSONString(authUserInfo));
            String post = HttpClient.post(authUrl, JSON.toJSONString(authUserInfo));
            System.out.println("用户认证[res]:" + post);
            AuthUserInfoResponse response = JSON.parseObject(post, AuthUserInfoResponse.class);
            if (!response.isAuth()) {
                throw new FailedLoginException(response.getMsg());
            }

            Map<String, Object> userAttributes = Maps.newHashMap();
            userAttributes.put("phoneNo", response.getPhoneNo());
            userAttributes.put("userName", response.getUserName());
            userAttributes.put("data", JSON.toJSONString(response.getData()));
            userAttributes.put("func", JSON.toJSONString(response.getFunc()));

            return createHandlerResult(credential, this.principalFactory.createPrincipal(myCredential.getUsername(), userAttributes));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailedLoginException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Credential credential) {
        return credential instanceof UsernamePasswordCaptchaCredential;
    }
}
