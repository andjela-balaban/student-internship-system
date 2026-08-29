package org.unibl.etf.internship.intern_hub.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.unibl.etf.internship.intern_hub.model.dto.CvResponseDTO;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Service
public class PdfService {

	@Autowired
    private TemplateEngine templateEngine;

    public PdfService() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);
        this.templateEngine = engine;
    }

    public byte[] generateCvPdf(CvResponseDTO cvDto, byte[] profileImageBytes) throws Exception {
        Context context = new Context();
        context.setVariable("cv", cvDto);

        // Pripremanje slike za ugradjivanje u html sablon
        if (profileImageBytes != null && profileImageBytes.length > 0) {
            String base64Image = Base64.getEncoder().encodeToString(profileImageBytes);
            
            String fullImageSrc = "data:image/jpeg;base64," + base64Image;
            context.setVariable("cvImageSrc", fullImageSrc);
        }

        // Generisanje HTML-a iz sablona
        String htmlContent = templateEngine.process("europass-cv", context);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();

            // Ucitavanje eksternog fonta (Arial) radi UTF-8 podrske
            try (InputStream fontRegular = new ClassPathResource("fonts/arial.ttf").getInputStream();
                 InputStream fontBold = new ClassPathResource("fonts/arialbd.ttf").getInputStream()) {

                builder.useFont(() -> fontRegular, "Arial", 400, PdfRendererBuilder.FontStyle.NORMAL, true);
                builder.useFont(() -> fontBold, "Arial", 700, PdfRendererBuilder.FontStyle.NORMAL, true);
            }

            builder.withHtmlContent(htmlContent, null);
            builder.toStream(os);
            builder.run();

            return os.toByteArray();
        }
    }
}
