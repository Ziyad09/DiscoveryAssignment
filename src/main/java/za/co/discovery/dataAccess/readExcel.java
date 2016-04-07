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
import java.util.List;
import java.util.Vector;

public class readExcel {

    //    @Autowired
    private VertexDAO vertexDAO;

    private final int numberOfVertices = 37;
    List<Vertex> vertexList = new Vector<>(numberOfVertices);

    public List<Vertex> readExcel() {

        try {
//            ClassLoader classLoader = this.getClass().getClassLoader();
//            File fileEx = new File(classLoader.getResource("PlanetData.xlsx").getFile());
//            FileInputStream file = new FileInputStream(fileEx);
            FileInputStream file = new FileInputStream(new File("C:\\PlanetData.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet("Planet Names");
            int rowStart = Math.min(1, 1); // 0 based not 1 based rows
            int rowEnd = Math.max(12, sheet.getLastRowNum());
            for (int rowNum = rowStart; rowNum < rowEnd + 1; rowNum++) {
                Row row = sheet.getRow(rowNum);
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    String planetNode = row.getCell(0).getStringCellValue();
                    String planetName = row.getCell(1).getStringCellValue();
//                    System.out.print(planetNode + " " + planetName + "\n\n");
                    Vertex vertex = new Vertex(planetNode);
                    vertex.setPlanetName(planetName);
                    vertexList.add(vertex);
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
        return vertexList;
    }

}
