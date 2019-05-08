package me.plutoz.cas.server.controller;

import me.plutoz.boot.tool.validatecode.ValidateCodeGenerate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("validateImage")
public class ValidateImageController {

    @GetMapping("refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        int codeCount = 4, width = 62, height = 20, interLine = 10;
        String randomCode = ValidateCodeGenerate.generateTextCode(ValidateCodeGenerate.TYPE_LOWER_ONLY, codeCount, null);
        BufferedImage bufferedImage = ValidateCodeGenerate.generateImageCode(randomCode, width, height, interLine);

        // 将四位数字的验证码保存到Session中。
        HttpSession session = request.getSession();
        session.setAttribute("capcha", randomCode.toString());

        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        try {
            ServletOutputStream sos = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
