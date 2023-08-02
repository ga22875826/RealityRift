//package com.teamsix.controller.member;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//import org.xhtmlrenderer.pdf.ITextFontResolver;
//import org.xhtmlrenderer.pdf.ITextRenderer;
//
//import com.lowagie.text.pdf.BaseFont;
//import com.teamsix.service.MemberService;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.OutputStream;
//
//@Controller
//@RequestMapping("/pdf")
//public class PdfController {
//	
//    @Autowired
//    private TemplateEngine templateEngine;
//    
//    @Autowired
//    private MemberService ms;
//
//    @GetMapping("/export")
//    public void exportPdf(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            Context context = new Context();
//            context.setVariable("members", ms.findAll());
//            String html = templateEngine.process("/member/MemberPdf", context);
//            
//
//            // 創建PDF文件
//            ITextRenderer renderer = new ITextRenderer();
//            
//            // 設置字體
//            ITextFontResolver fontResolver = renderer.getFontResolver();
//            fontResolver.addFont("/static/fonts/NotoSansCJKtc-Bold.otf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//            renderer.setDocumentFromString(html);
//            renderer.layout();
//
//            // 設置響應
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Disposition", "attachment; filename=your-file.pdf");
//            response.setCharacterEncoding("UTF-8");
//
//            // 將PDF寫入響應輸出
//            OutputStream outputStream = response.getOutputStream();
//            renderer.createPDF(outputStream);
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//	
//}
