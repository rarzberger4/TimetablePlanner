package TTP;

import org.apache.poi.ss.usermodel.*;
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
    private List<LectureUnit> lectureUnitList = new ArrayList<>();

    public Parser() throws IOException {
        file = new FileInputStream(filepath);
        workbook = new XSSFWorkbook(file);
    }


    public void parseXLS() {
        parseLecturer(1);
        parseLectures(3);
    }

    private void parseLecturer(int sheetNr) {
        sheet = workbook.getSheetAt(sheetNr);


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

    private void parseLectures(int sheetNr) {
        sheet = workbook.getSheetAt(sheetNr);


        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<String> oneRow = null;

        for (Row row : sheet) {
            oneRow = new ArrayList<>();
            for (Cell cell : row) {
                if(cell.getCellType() == CellType.STRING){
                    oneRow.add(cell.getRichStringCellValue().getString());
                } else if (cell.getCellType() == CellType.NUMERIC) {
                    if(cell.getColumnIndex() < 6){
                        oneRow.add(String.valueOf(cell.getNumericCellValue()));
                    }else{
                        oneRow.add(cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
                    }
                }
            }
            data.add(oneRow);
        }

        for (ArrayList row:data) {
            System.out.println(row.toString());
        }

        Group group = new Group();


        lectureUnitList.add(new LectureUnit(data.get(1).get(0), 1, lecturerMap.get(data.get(1).get(6)), group, LocalDate.parse((data.get(1).get(7))), LocalDate.parse((data.get(1).get(8)))));


    }
}
