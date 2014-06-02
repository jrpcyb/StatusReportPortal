package com.report.manage.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class CreateDocumentFromScratch {
 
    public static void main(String[] args) {
        XWPFDocument document = new XWPFDocument();
 
        XWPFParagraph paragraphOne = document.createParagraph();
        paragraphOne.setAlignment(ParagraphAlignment.CENTER);
        paragraphOne.setBorderBottom(Borders.SINGLE);
        paragraphOne.setBorderTop(Borders.SINGLE);
        paragraphOne.setBorderRight(Borders.SINGLE);
        paragraphOne.setBorderLeft(Borders.SINGLE);
        paragraphOne.setBorderBetween(Borders.SINGLE);
 
        XWPFRun paragraphOneRunOne = paragraphOne.createRun();
        paragraphOneRunOne.setBold(true);
        paragraphOneRunOne.setItalic(true);
        paragraphOneRunOne.setText("Hello world! This is paragraph one!");
        paragraphOneRunOne.addBreak();
 
        XWPFRun paragraphOneRunTwo = paragraphOne.createRun();
        paragraphOneRunTwo.setText("Run two!");
        paragraphOneRunTwo.setTextPosition(100);
 
        XWPFRun paragraphOneRunThree = paragraphOne.createRun();
        paragraphOneRunThree.setStrike(true);
        paragraphOneRunThree.setFontSize(20);
        paragraphOneRunThree.setSubscript(VerticalAlign.SUBSCRIPT);
        paragraphOneRunThree.setText(" More text in paragraph one...");
 
        XWPFParagraph paragraphTwo = document.createParagraph();
        paragraphTwo.setAlignment(ParagraphAlignment.DISTRIBUTE);
        paragraphTwo.setIndentationRight(200);
        XWPFRun paragraphTwoRunOne = paragraphTwo.createRun();
        paragraphTwoRunOne.setText("And this is paragraph two.");
 
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream("C:\\Users\\jyotipatt\\testWS\\reports\\abc.doc");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
 
        try {
            document.write(outStream);
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}