package testNGandPDFReport;


import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import com.lowagie.text.pdf.PdfWriter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.lowagie.text.Document;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;


public class ReportPDFListener implements ITestListener {

    /**
     * Document
     */
    private Document document=null;

    /**
     * PDF tables
     */
    PdfPTable successTable=null;
    PdfPTable failTable=null;

    /**
     * throwableHashMap
     */
    private HashMap<Integer,Throwable> throwableHashMap=null;

    private int nbExceptions=0;

    public ReportPDFListener() {
        this.document = new Document();
        this.throwableHashMap = new HashMap<Integer,Throwable>();
    }

    private static void log(Object o) {
        System.out.println("PDFListener"+o);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log("onTestSuccess("+result+")");
        if(successTable==null){

            this.successTable=new PdfPTable(new float[]{.3f,.3f,.1f,.3f});
            Paragraph p=new Paragraph("PASSED TEST",new Font(Font.TIMES_ROMAN,Font.DEFAULTSIZE,
                    Font.BOLD));
            p.setAlignment(Element.ALIGN_CENTER);
            PdfPCell cell=new PdfPCell(p);
            cell.setColspan(4);
            cell.setBackgroundColor(Color.GREEN);
            this.successTable.addCell(cell);

            cell=new PdfPCell(new Paragraph("Class"));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            this.successTable.addCell(cell);

            cell=new PdfPCell(new Paragraph("Method"));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            this.successTable.addCell(cell);

            cell=new PdfPCell(new Paragraph("Time(ms)"));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            this.successTable.addCell(cell);

            cell=new PdfPCell(new Paragraph("Exception"));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            this.successTable.addCell(cell);
        }

        //Get Class Name of Result
        PdfPCell cell=new PdfPCell(new Paragraph(result.getTestClass().toString()));
        this.successTable.addCell(cell);
        //Get Method Name of Result
        cell=new PdfPCell(new Paragraph(result.getMethod().getMethodName().toString()));
        this.successTable.addCell(cell);
        //Get Time
        cell=new PdfPCell(new Paragraph(""+(result.getEndMillis()-result.getStartMillis())));
        this.successTable.addCell(cell);

        // Get Exception and Convert to String
        Throwable throwable=result.getThrowable();
        if(throwable !=null){
            this.throwableHashMap.put(throwable.hashCode(),throwable);
            this.nbExceptions++;
            Paragraph except=new Paragraph(
                    new Chunk(throwable.toString(),
                            new Font(Font.TIMES_ROMAN,Font.DEFAULTSIZE,Font.UNDERLINE))
                            .setLocalGoto(""+throwable.hashCode()));
            cell=new PdfPCell(except);
            this.successTable.addCell(cell);
        }else {
            this.successTable.addCell(new PdfPCell(new Paragraph("")));
        }
    }



    @Override
    public void onTestFailure(ITestResult result) {
        log("onTestFailure("+result+")");
        // screen shot generate
        String file="C:\\Users\\xin.gu\\SpringPractices\\testNGandPDF\\screenshot"+(new Random().nextInt())+".png";
        try{
            BaseClass.takeSnapShot(BaseClass.getDriver(),file);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(failTable==null){
            //
            this.failTable=new PdfPTable(new float[]{.3f,.3f,.1f,.3f});
            // Setting absolute Width or "widthPercentage" Setting Percent of Base Container
            this.failTable.setTotalWidth(20f);

            Paragraph p=new Paragraph("FAILED TEST",new Font(Font.TIMES_ROMAN,Font.DEFAULTSIZE,
                    Font.BOLD));
            p.setAlignment(Element.ALIGN_CENTER);
            PdfPCell cell=new PdfPCell(p);
            cell.setColspan(4);
            cell.setBackgroundColor(Color.RED);
            this.failTable.addCell(cell);

            cell=new PdfPCell(new Paragraph("Class"));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            this.failTable.addCell(cell);

            cell=new PdfPCell(new Paragraph("Method"));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            this.failTable.addCell(cell);

            cell=new PdfPCell(new Paragraph("Time(ms)"));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            this.failTable.addCell(cell);

            cell=new PdfPCell(new Paragraph("Exception"));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            this.failTable.addCell(cell);
        }

        PdfPCell cell=new PdfPCell(new Paragraph(result.getTestClass().toString()));
        this.failTable.addCell(cell);
        cell=new PdfPCell(new Paragraph(result.getMethod().getMethodName().toString()));
        this.failTable.addCell(cell);
        cell=new PdfPCell(new Paragraph(""+(result.getEndMillis()-result.getStartMillis())));
        this.failTable.addCell(cell);

        Throwable throwable=result.getThrowable();
        if(throwable !=null){
            this.throwableHashMap.put(throwable.hashCode(),throwable);
            this.nbExceptions++;

            Chunk imdb=new Chunk("[SCREEN SHOT]",
                    new Font(Font.TIMES_ROMAN,Font.DEFAULTSIZE,Font.UNDERLINE));
            imdb.setAction(new PdfAction("file:///"+file));

            Paragraph except=new Paragraph(throwable.toString());
            except.add(imdb);

            cell=new PdfPCell(except);
            this.failTable.addCell(cell);
        }else {
            this.failTable.addCell(new PdfPCell(new Paragraph("")));
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log("onTestSkipped("+result+")");
    }

    @Override
    public void onStart(ITestContext context) {
        log("onStart("+context+")");
        // Define PDF File Name
        try {
            PdfWriter.getInstance(this.document,new FileOutputStream(context.getName()+".pdf"));
        }catch (Exception e){
            e.printStackTrace();
        }
        this.document.open();
        // Define Paragraph and Characters
        Paragraph p=new Paragraph(context.getName()+"TESTING RESULTS",
                //FontFactory.getFont(FontFactory.HELVETICA,20,Font.BOLD,new Color(0,0,255))
                new Font(Font.HELVETICA,20,Font.BOLD,Color.BLUE)
        );
        try{
            this.document.add(p);
            this.document.add(new Paragraph(new Date().toString()));
        }catch (DocumentException e1){
            e1.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        log("onFinish(" + context + ")");

        try {
            if (this.failTable != null) {
                log("Added fail table");
                //
                this.failTable.setSpacingBefore(15f);
                this.document.add(this.failTable);
                this.failTable.setSpacingAfter(15f);
            }
            if (this.successTable != null) {
                log("Added success table");
                this.successTable.setSpacingBefore(15f);
                this.document.add(this.successTable);
                this.successTable.setSpacingAfter(15f);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // Exception Contents
        Paragraph p = new Paragraph("EXCEPTIONS SUMMARY"
                , FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
        try {
            document.add(p);
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }

        Set<Integer> keys = this.throwableHashMap.keySet();

        assert keys.size() == this.nbExceptions;

        for (Integer key : keys) {
            Throwable throwable = this.throwableHashMap.get(key);
            Chunk chunk = new Chunk(throwable.toString(),
                    FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new Color(255, 0, 0)));
            chunk.setLocalDestination("" + key);
            Paragraph throwTitlePara = new Paragraph(chunk);
            try {
                this.document.add(throwTitlePara);
            } catch (DocumentException e2) {
                e2.printStackTrace();
            }
            StackTraceElement[] elems = throwable.getStackTrace();
            String exception = "";
            for (StackTraceElement ste : elems) {
                Paragraph throwParagraph = new Paragraph(ste.toString());
                try {
                    this.document.add(throwParagraph);
                } catch (DocumentException e3) {
                    e3.printStackTrace();
                }
            }
        }
        this.document.close();
     }
   }


