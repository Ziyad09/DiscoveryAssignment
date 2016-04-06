package za.co.discovery.dataAccess;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import za.co.discovery.models.Vertex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class readExcel {
    private VertexDAO vertexDAO;

    public readExcel() {
        try {

            FileInputStream file = new FileInputStream(new File("C:\\PlanetData.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet("Planet Names");
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
                    String planetNode = row.getCell(0).getStringCellValue();
                    String planetName = row.getCell(1).getStringCellValue();
//                    System.out.print(planetNode + " " + planetName + "\n\n");
                    Vertex vertex = new Vertex(planetNode);
                    vertex.setPlanetName(planetName);
                    vertexDAO.save(vertex);
                    break;
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
