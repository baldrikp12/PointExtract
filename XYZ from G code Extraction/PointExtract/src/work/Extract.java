package work;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Extract {

    private static String fileName = "";

    private static String filePath = "";

    public static void extract(File theGCodeFile, JTextPane theExtractXYZArea)
            throws FileNotFoundException {

        File gCodeFile = theGCodeFile;

        fileName = theGCodeFile.getName();
        filePath = theGCodeFile.getParent().replace("\\", "\\\\");

        List<String> xyzPoints = new ArrayList<String>();

        boolean isPoint = false;

        String xRegex = "(?i)(?<=x)\\-?\\d*\\.\\d*";
        String xPoint = "0.";

        String yRegex = "(?i)(?<=y)\\-?\\d*\\.\\d*";
        String yPoint = "0.";

        String zRegex = "(?i)(?<=z)\\-?\\d*\\.\\d*";
        String zPoint = "0.";

        Scanner sc = new Scanner(gCodeFile);
        String scLine = "";

        Pattern patt;
        Matcher m;

        while (sc.hasNextLine()) {
            scLine = sc.nextLine();

            // Looks for an X point and assigns it to xPoint if one is found
            patt = Pattern.compile(xRegex);
            m = patt.matcher(scLine);
            if (m.find()) {
                xPoint = m.group(0);
                isPoint = true;
            }

            // Looks for a Y point and assigns it to yPoint if one is found
            patt = Pattern.compile(yRegex);
            m = patt.matcher(scLine);
            if (m.find()) {
                yPoint = m.group(0);
                isPoint = true;
            }

            // Looks for a Z point and assigns it to zPoint if one is found
            patt = Pattern.compile(zRegex);
            m = patt.matcher(scLine);
            if (m.find()) {
                zPoint = m.group(0);
                isPoint = true;
            }

            // If this line in file contains a single coordinate point then add
            // X Y Z points to arraylist.
            if (isPoint) {
                xyzPoints.add(xPoint);
                xyzPoints.add(yPoint);
                xyzPoints.add(zPoint);
            }

            isPoint = false;

        }
        sc.close();

        export(xyzPoints);
    }

    private static void export(List<String> thePointsList) {

        try {
            // Create a Workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            // Create a blank sheet
            XSSFSheet sheet = workbook.createSheet("sheet1");
            // Create row object

            int rowNum = 0;
            int colNum = 0;
            XSSFRow row = sheet.createRow(rowNum++);
            Cell cell = row.createCell(colNum);
            cell.setCellValue(thePointsList.get(0));

            for (int i = 1; i < thePointsList.size(); i++) {
                colNum++;
                if (i % 3 == 0) {
                    row = sheet.createRow(rowNum++);
                    colNum = 0;
                }
                cell = row.createCell(colNum);
                cell.setCellValue(Double.parseDouble(thePointsList.get(i)));

            }

            FileOutputStream out = new FileOutputStream(
                    new File(filePath + "\\" + fileName + ".xlsx"));
            workbook.write(out);
            out.close();
            workbook.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
