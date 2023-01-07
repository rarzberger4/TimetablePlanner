import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class parser {
    public void test() throws IOException {
        FileInputStream file = new FileInputStream(new File("fileLocation"));
        Workbook workbook = new XSSFWorkbook(file);
    }
}
