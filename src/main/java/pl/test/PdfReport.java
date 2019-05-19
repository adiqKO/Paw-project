package pl.test;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.test.model.Order;
import pl.test.model.Product;

public class PdfReport {

    public static ByteArrayInputStream orderReport(Order order) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(60);
            table.setWidths(new int[]{3, 3, 3});

            Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 15);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Produkt", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Rodzaj", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Cena", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            BaseFont base = BaseFont.createFont(BaseFont.TIMES_ROMAN,BaseFont.CP1250,BaseFont.EMBEDDED);
            Font myFont = new Font(base, 15, Font.NORMAL);
            Font myFontSmall = new Font(base, 13, Font.NORMAL);
            for (Product product : order.getProducts()) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(product.getTitle(),myFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(product.getType(), myFont));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(product.getPrice()+" zł",myFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(5);
                table.addCell(cell);
            }
            Paragraph userName = new Paragraph(order.getUser().getFirstName()+" "+order.getUser().getLastName(),myFontSmall);
            Paragraph userInfo = new Paragraph(order.getUser().getEmail(),myFontSmall);
            Paragraph address = new Paragraph(order.getUser().getUserSpecific().getAddress()+" "+
                    order.getUser().getUserSpecific().getCity(),myFontSmall);
            Paragraph space = new Paragraph(" ",myFontSmall);
            Paragraph price = new Paragraph(order.getPrice()+" zł                                      ",myFont);
            price.setAlignment(Element.ALIGN_RIGHT);

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(userName);
            document.add(address);
            document.add(userInfo);
            document.add(space);
            document.add(table);
            document.add(price);

            document.close();

        } catch (DocumentException ex) {

            Logger.getLogger(PdfReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}