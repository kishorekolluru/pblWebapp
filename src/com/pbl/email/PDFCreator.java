package com.pbl.email;

import java.io.FileOutputStream;

import org.pbl.business.DonorReceipt;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFCreator {
	public static void createPDF(DonorReceipt donorReceipt) {
		Font blueFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 25,
				Font.BOLD, new CMYKColor(255, 0, 0, 0));

		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("C:/Users/SRIRAM/Desktop/Receipt/Receipt.pdf"));
			document.open();

			Paragraph heading = new Paragraph("Donation Receipt", blueFont);
			heading.setAlignment(Element.ALIGN_CENTER);
			document.add(heading);
			document.add(Chunk.NEWLINE);

			Image img1 = Image
					.getInstance("C:/Users/SRIRAM/Desktop/Receipt/download.png");
			img1.setAlignment(Element.ALIGN_CENTER);
			img1.scaleAbsolute(100f, 100f);
			document.add(img1);

			Paragraph p1 = new Paragraph("Date: ");

			Chunk c1 = new Chunk(donorReceipt.getReceiptDate());
			c1.setUnderline(0.1f, -2f);
			p1.add(c1);

			Chunk space = new Chunk(
					"                                                                                       ");
			p1.add(space);

			Chunk c2 = new Chunk("Receipt No: ");
			p1.add(c2);

			Chunk c3 = new Chunk(donorReceipt.getReceiptNo() + "4597105");
			c3.setUnderline(0.1f, -2f);
			p1.add(c3);
			document.add(p1);
			document.add(Chunk.NEWLINE);

			Paragraph p2 = new Paragraph(
					"Donated By:                                ");

			Chunk p2c1 = new Chunk(donorReceipt.getDonorName());
			p2c1.setUnderline(0.1f, -2f);
			p2.add(p2c1);

			document.add(p2);
			document.add(Chunk.NEWLINE);

			Paragraph p3 = new Paragraph(
					"Donor Address:                           ");

			Chunk p3c1 = new Chunk(donorReceipt.getDonorAddress());
			p3c1.setUnderline(0.1f, -2f);
			p3.add(p3c1);

			document.add(p3);
			document.add(Chunk.NEWLINE);

			Paragraph p4 = new Paragraph(
					"Item Donated:                             ");

			Chunk p4c1 = new Chunk(donorReceipt.getItem());
			p4c1.setUnderline(0.1f, -2f);
			p4.add(p4c1);

			document.add(p4);
			document.add(Chunk.NEWLINE);

			Paragraph p5 = new Paragraph(
					"Donated on:                                ");

			Chunk p5c1 = new Chunk(donorReceipt.getDonatedOn());
			p5c1.setUnderline(0.1f, -2f);
			p5.add(p5c1);

			document.add(p5);
			document.add(Chunk.NEWLINE);

			Paragraph p6 = new Paragraph(
					"Eligible Amonut of Gift for Tax purposes(A-B) :           ");

			Chunk p6c1 = new Chunk(donorReceipt.getTaxGift());
			p6c1.setUnderline(0.1f, -2f);
			p6.add(p6c1);

			document.add(p6);
			document.add(Chunk.NEWLINE);

			Paragraph p7 = new Paragraph(
					"Verified By :                                 ");

			Chunk p7c1 = new Chunk(" PBL CLOSET");
			p7c1.setUnderline(0.1f, -2f);
			p7.add(p7c1);

			document.add(p7);
			document.add(Chunk.NEWLINE);

			Image img3 = Image
					.getInstance("C:/Users/SRIRAM/Desktop/Receipt/approved.jpg");
			img3.setAlignment(Element.ALIGN_CENTER);
			img3.scaleAbsolute(100f, 100f);

			document.add(img3);

			Image img2 = Image
					.getInstance("C:/Users/SRIRAM/Desktop/Receipt/thanks.gif");
			img2.setAlignment(Element.ALIGN_RIGHT);
			img2.scaleAbsolute(100f, 100f);

			document.add(img2);

			document.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
