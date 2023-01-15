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

import static java.lang.Integer.parseInt;

public class Parser {
    private FileInputStream file;
    private Workbook workbook;
    private Sheet sheet;
    private final String filepath = "test.xlsx";
    private final Map<String, Lecturer> lecturerMap = new HashMap<>();
    private ArrayList<LectureUnit> lectureUnitList = new ArrayList<>();
    private ArrayList<String> students = new ArrayList<>();

    public Parser() throws IOException {
        file = new FileInputStream(filepath);
        workbook = new XSSFWorkbook(file);
    }


    public void parseXLS() {                //order is important
        parseLecturer(1);
        parseStudents(2);
        parseLectures(3);
    }

    public void fillTT(){

    }

    private void parseStudents(int sheetNr) {
        sheet = workbook.getSheetAt(sheetNr);

        for (Row row : sheet) {
            for (Cell cell : row) {
                if(cell.getCellType() == CellType.STRING){
                    students.add(cell.getRichStringCellValue().getString());
                }
            }
        }
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

        ArrayList<Group> groups = new ArrayList<>();

        //create groups and distribute students to the groups

        for(int i = 1; i < data.size()-1; i ++){      //first row is ignored
            int studentNum = students.size()/(int)Double.parseDouble(data.get(i).get(4));       //number of students per group      TODO: equally split number into n-group parts

            for(int g = 1; g <= (int)Double.parseDouble(data.get(i).get(4)); g++){
                System.out.println((int)Double.parseDouble(data.get(i).get(4)));

                Group group = new Group();

                for(int x = 0; x < studentNum && ((x + studentNum * (g - 1) < students.size())); x++){
                    group.addStudentToList(new Student(students.get(x + studentNum * (g - 1))));
                }

                groups.add(group);
            }
        }

        //fill groups



        lectureUnitList.add(new LectureUnit(data.get(1).get(0), (int)Double.parseDouble(data.get(1).get(3)), lecturerMap.get(data.get(1).get(6)), groups.get(0), LocalDate.parse((data.get(1).get(7))), LocalDate.parse((data.get(1).get(8)))));


    }
}
