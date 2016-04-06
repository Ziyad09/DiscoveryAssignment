package za.co.discovery.dataAccess;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import za.co.discovery.models.Edge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Ziyad.Jappie on 2016/04/06.
 */
public class readExcel2 {
    private EdgeDAO edgeDAO;

    public readExcel2() {
        try {

            FileInputStream file = new FileInputStream(new File("C:\\PlanetData.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet("Routes");
            Iterator<Row> rowIterator = sheet.iterator();
            int rowStart = Math.min(1, 1); // 0 based not 1 based rows
            int rowEnd = Math.max(12, sheet.getLastRowNum());
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row r = sheet.getRow(rowNum);
                Iterator<Cell> cellIterator = r.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    Double routeId = r.getCell(0).getNumericCellValue();
                    String planetSource = r.getCell(1).getStringCellValue();
                    String planetDestination = r.getCell(2).getStringCellValue();
                    Double planetDistance = r.getCell(3).getNumericCellValue();
                    int routeId2 = Integer.valueOf(routeId.intValue());
                    Edge edge = new Edge(routeId2, planetSource, planetDestination, planetDistance);
                    edgeDAO.save(edge);
//                    System.out.print(routeId + " " + planetSource + "\n\n");
                    break;
//                    switch(cell.getCellType()) {
//                        case Cell.CELL_TYPE_BOOLEAN:
//                            System.out.print(cell.getBooleanCellValue() + "\t\t");
//                            break;
//                        case Cell.CELL_TYPE_NUMERIC:
//                            System.out.print(cell.getNumericCellValue() + "\t\t");
//                            break;
//                        case Cell.CELL_TYPE_STRING:
//                            System.out.print(cell.getStringCellValue() + "\t\t");
//                            break;
//                    }
                }
                System.out.println("");
            }
            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
