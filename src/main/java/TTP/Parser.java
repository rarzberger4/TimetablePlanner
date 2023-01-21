package TTP;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private final Workbook workbook;
    private Sheet sheet;
    private final Map<String, Lecturer> lecturerMap = new HashMap<>();
    private final ArrayList<LectureUnit> lectureUnitList = new ArrayList<>();
    private final ArrayList<String> students = new ArrayList<>();
    private LocalDate semesterStartDate;
    private LocalDate semesterEndDate;
    List<LocalDate> holidays = new ArrayList<>();

    public Parser(String filepath) throws IOException {
        FileInputStream file = new FileInputStream(filepath);
        workbook = new XSSFWorkbook(file);
    }

    public int getLectureUnitListLength() {
        return lectureUnitList.size();
    }

    public void parseXLS() {
        parseLecturer();
        parseStudents();
        parseGeneral();

        for(int x = 3; x < workbook.getNumberOfSheets(); x++){      // Lectures start at 3
            parseLectures(x);
        }

    }

    private void parseGeneral() {
        sheet = workbook.getSheet("General");

        CellReference cellReference = new CellReference("A2");
        Row row = sheet.getRow(cellReference.getRow());
        Cell cell = row.getCell(cellReference.getCol());
        semesterStartDate = cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        cellReference = new CellReference("B2");
        row = sheet.getRow(cellReference.getRow());
        cell = row.getCell(cellReference.getCol());
        semesterEndDate = cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        List<Double> values = new ArrayList<Double>();
        for(Row r : sheet) {
            Cell c = r.getCell(2);
            if(c != null) {
                if(c.getCellType() == CellType.NUMERIC) {
                    holidays.add(c.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
            }
        }

    }

    public TimeTable fillTT(){

        TimeTable timeTable = new TimeTable(semesterStartDate,semesterEndDate,holidays);
        for (LectureUnit lec:lectureUnitList) {
            timeTable.addLectureUnitToList(lec);
        }
        return timeTable;
    }

    private void parseStudents( ) {
        sheet = workbook.getSheet("Students");

        for (Row row : sheet) {
            for (Cell cell : row) {
                if(cell.getCellType() == CellType.STRING){
                    students.add(cell.getRichStringCellValue().getString());
                }
            }
        }
    }

    private void parseLecturer() {
        sheet = workbook.getSheet("Lecturers");

        String currentLecturer = null;
        for (Row row : sheet) {
            for (Cell cell : row) {

                    switch (cell.getCellType()) {

                        case STRING:
                            if (!(cell.getRowIndex() == 0)) {       // ignore first row
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
        ArrayList<String> oneRow;

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

        ArrayList<Group> groups = new ArrayList<>();

        // create groups and distribute students to the groups

        for(int i = 1; i < data.size(); i ++){      // first row is ignored, data.size() == rows in the xlsx
            String lectureName = data.get(i).get(0);
            String lectureType = data.get(i).get(1);
            String quantity = data.get(i).get(2);
            String units = data.get(i).get(3);
            String numberOfGroups = data.get(i).get(4);
            String lectureMode = data.get(i).get(5);
            String lecturer = data.get(i).get(6);
            String startDate = data.get(i).get(7);
            String endDate = data.get(i).get(8);

            boolean isOnline = lectureMode.equals("online");

            int studentNum = students.size()/(int)Double.parseDouble(numberOfGroups);       // number of students per group      TODO: equally split number into n-group parts

            for(int g = 1; g <= (int)Double.parseDouble(numberOfGroups); g++) {     //
                // System.out.println((int) Double.parseDouble(numberOfGroups);

                Group group = new Group();
                group.setName(g + "/" + (int) Double.parseDouble(numberOfGroups));

                for (int x = 0; x < studentNum && ((x + studentNum * (g - 1) < students.size())); x++) {        // add equal number of students to the groups
                    group.addStudentToList(new Student(students.get(x + studentNum * (g - 1))));
                }

                groups.add(group);

                String[] parts = lecturer.split(", ");        // in case of multiple lecturers/groups

                for (int l = 0; l < (int)Double.parseDouble(quantity); l++) {     // add quantity of lectures to lectureUnitList
                    lectureUnitList.add(new LectureUnit(
                            lectureName,
                            (int) Double.parseDouble(units),
                            lecturerMap.get(parts[g-1]),
                            group,
                            LocalDate.parse((startDate)),
                            LocalDate.parse((endDate)), isOnline, lectureType));
                }
            }
        }

    }
}
