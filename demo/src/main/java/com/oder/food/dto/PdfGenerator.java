package com.oder.food.dto;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.oder.food.model.Oder;
import com.oder.food.model.OderItem;

import java.io.FileOutputStream;
import java.io.IOException;

public class PdfGenerator {

    public void createPdf(Oder oder,String filePath) throws DocumentException, IOException{
        Document document= new Document();
        PdfWriter.getInstance(document,new FileOutputStream(filePath));
        document.open();
        document.add(new Paragraph("Date Create" + oder.getCreateAt()));
        document.add(new Paragraph("Customer" + oder.getCustomer().getFullName()));
        document.add(new Paragraph("Total mount" + oder.getTotalAmount()));
        document.add(new Paragraph("Items:" +oder.getItems().toString()));
        document.add(new Paragraph("Delivery Address" + oder.getDeliveryAddress()));
        document.add(new Paragraph("Delivery Menu Items:"));

        for(OderItem item: oder.getItems()){
            document.add(new Paragraph("Item Name:" + item.getFood().getName()));
            document.add(new Paragraph("Quantity:" + item.getQuantity()));
            document.add(new Paragraph("Price:" + item.getFood().getPrice()));
            document.add(new Paragraph("Ingridients:"));
            document.add(new Paragraph("Ingridient Name" + item.getIngredients()));
        }
        document.close();


    }
}
