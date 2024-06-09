package org.example.export;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PdfExport implements IExporter{

    public HashMap<String, HashMap<String, BigDecimal>> detailedData;
    public HashMap<String, BigDecimal>  simpleData;
    public PdfExport(HashMap<String, BigDecimal> simpleData, HashMap<String, HashMap<String, BigDecimal>> detailedData) {
        this.simpleData = simpleData;
        this.detailedData = detailedData;
    }


    public void export(String filename) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            // Load the TrueType font
            File fontFile = new File("src/resources/fonts/DejaVuSans.ttf");
            PDType0Font font = PDType0Font.load(doc, fontFile);

            try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
                contents.setFont(font, 12);

                contents.beginText();
                contents.newLineAtOffset(100, 700);
                contents.showText("Report");

                // Move to a new line
                contents.newLineAtOffset(-20, -20);  // Reset x offset and move down

                for (Map.Entry<String, BigDecimal> set : simpleData.entrySet()) {
                    String text = set.getKey() + " - " + set.getValue() + " h";
                    contents.showText(text);
                    contents.newLineAtOffset(0, -15);  // Move down for the next line
                }

                contents.endText();
            }

            doc.save(filename);
        }
    }

    public void exportDetailed(String filename) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            File fontFile = new File("src/resources/fonts/DejaVuSans.ttf");
            PDType0Font font = PDType0Font.load(doc, fontFile);

            try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
                contents.setFont(font, 10);

                contents.beginText();
                contents.newLineAtOffset(100, 700);
                contents.showText("Report");

                contents.newLineAtOffset(-20, -20);

                for (Map.Entry<String, HashMap<String, BigDecimal>> set : detailedData.entrySet()) {
                    BigDecimal totalHours = set.getValue().values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                    contents.showText(set.getKey() + " = " + totalHours + " h");
                    contents.newLineAtOffset(20, -15);
                    for (Map.Entry<String, BigDecimal> details : set.getValue().entrySet()) {
                        contents.showText("     " + details.getKey() + " - " + details.getValue() + " h");
                        contents.newLineAtOffset(0, -25);
                    }
                    contents.newLineAtOffset(-20, -25);
                }

                contents.endText();
            }

            doc.save(filename);
        }
    }
}
