package TTP;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private FileInputStream file;
    private Workbook workbook;
    private Sheet sheet;
    private final String filepath = "test.xlsx";
    private final Map<String, Lecturer> lecturerMap = new HashMap<>();
    private final Map<String, LectureUnit> lecturesMap = new HashMap<>();

    public Parser() throws IOException {
        file = new FileInputStream(filepath);
        workbook = new XSSFWorkbook(file);
    }


    public void parseXLS() {
        sheet = workbook.getSheetAt(1);
        parseLecturer();
        sheet = workbook.getSheetAt(3);
        parseLectures();
    }

    private void parseLecturer() {
        String currentLecturer = null;
        for (Row row : sheet) {
            for (Cell cell : row) {

                    switch (cell.getCellType()) {

                        case STRING:
                            if (!(cell.getRowIndex() == 0)) {       //ignore first row
                                lecturerMap.put(cell.getRichStringCellValue().getString(), new Lecturer(cell.getRichStringCellValue().getString()));
                                currentLecturer = cell.getRichStringCellValue().getString();
                            }
                            break;
                        case NUMERIC:
                            lecturerMap.get(currentLecturer).setNotAvailable(cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    }
            }
        }
    }

    private void parseLectures(){
        for (Row row : sheet) {
            for (Cell cell : row) {
                System.out.println(cell.toString());
            }
            }
        }
}
