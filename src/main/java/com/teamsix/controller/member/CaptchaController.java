package com.teamsix.controller.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

@Controller
@RequestMapping("/captcha")
public class CaptchaController {
	
	 private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

	    @Resource(name = "captchaProducer")
	    private Producer captchaProducer;

	    @GetMapping("/captchaImage")
	    public ModelAndView getKaptchaImage(HttpServletResponse response,HttpSession session) throws Exception {
	        ServletOutputStream out = response.getOutputStream();
	        try {
	            response.setDateHeader("Expires", 0);
	            // Set standard HTTP/1.1 no-cache headers.
	            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	            // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
	            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	            // Set standard HTTP/1.0 no-cache header.
	            response.setHeader("Pragma", "no-cache");
	            // return a jpeg
	            response.setContentType("image/jpeg");
	            // create the text for the image
	            String capText = captchaProducer.createText();
	            //将验证码存到session
	            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
	            logger.info(capText);
	            // 創建驗證碼圖片
	            BufferedImage bi = captchaProducer.createImage(capText);
	            // 響應
	            out = response.getOutputStream();
	            // 寫入數據
	            ImageIO.write(bi, "jpg", out);

	            out.flush();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (out != null) {
	                    out.close();
	                }
	            }
	            catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return null;
	    }
	    
	    @GetMapping("/checkcaptchanumber")
	    @ResponseBody
	    public Boolean checkCaptchaNumber(HttpSession session,@RequestParam("v-code") String vCode) {
//	    	HttpSession session = request.getSession();
	    	String kaptchaSessionKey = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
	    	if(vCode.equals(kaptchaSessionKey)) {
	    		return true;
	    	}
	    	return false;
	    }

}
