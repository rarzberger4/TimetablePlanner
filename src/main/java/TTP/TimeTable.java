package TTP;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Random;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TimeTable {        //
    private final List<Day> masterTable;
    private final List<LectureUnit> lectureUnits = new ArrayList<>();
    private final ArrayList<Integer> hashCodes = new ArrayList<>();
    private final Random rand = new Random();

    public TimeTable(LocalDate startDate, LocalDate endDate, List<LocalDate> holidays){
        masterTable = Day.getBusinessDaysBetween(startDate, endDate, java.util.Optional.ofNullable(holidays));
    }

    public List<Day> getMasterTable() {
        return this.masterTable;
    }

    public List<LectureUnit> getLectureUnits() { return this.lectureUnits; }

    public void resetTimeTable() {
        for (Day day : this.masterTable) {
            day.resetLectureUnits();
        }
    }

    public boolean setLectureUnit(LectureUnit lectureUnit) {

        for (Day day : this.masterTable) {
            if (day.setLecUnit(lectureUnit)) {
                return true;
            }
        }
        return false;
    }

    public void print(){
        for(Day day : masterTable){
            if (day.isNotEmpty()) {
                day.print();
            }
        }
    }


    public void solve() {
        resetTimeTable();
        Collections.shuffle(lectureUnits);
        hashCodes.add(lectureUnits.hashCode());
        int i = 0;
        int k = 0;
        while (i<1000000) {
            try {
                for (LectureUnit lectureUnit : this.lectureUnits) {
                    if (!setLectureUnit(lectureUnit)) {
                        lectureUnits.remove(lectureUnit);
                        lectureUnits.add(0, lectureUnit);

                        while (hashCodes.contains(lectureUnits.hashCode()) && k < 10) {
                            lectureUnits.remove(lectureUnit);
                            lectureUnits.add(rand.nextInt(lectureUnits.size()), lectureUnit);
                            System.out.println(lectureUnit.getName() + " runs in cycles");
                            k++;
                        }
                        this.resetTimeTable();
                        throw new TimeTableError("Could not set lecture:" + lectureUnit.getName()+"\n");
                }   }
                System.out.println("Number of failed executions: " + i);
                break;
            } catch (TimeTableError e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    public void addLectureUnitToList(LectureUnit lectureUnit) {
        lectureUnits.add(lectureUnit);
    }

    public Day getDay(LocalDate date) {
        for (Day day : this.masterTable) {
            if (date.equals(day.getDate())) {
                return day;
            }
        }
        return null;
    }

    public void removeLectureUnitToList(LectureUnit lectureUnit) {
        lectureUnits.remove(lectureUnit);
    }

    public int getNumberOfAssignedLectures() {
        int sum = 0;
        for (Day day : masterTable) {
            sum += day.getNumberOfAssignedLectures();
        }
        return sum;
    }

    public void exportTimeTable(String filepath) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(filepath);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");

        Object[][] bookData = new Object[15][masterTable.size()];

        Object[] daysArr = new Object[masterTable.size()];
        Object[] daysNamesArr = new Object[masterTable.size()];
        Object[] lectureArr1stSlotName = new Object[masterTable.size()];
        Object[] lectureArr1stSlotLecturer = new Object[masterTable.size()];
        Object[] lectureArr1stSlotLectureTyp = new Object[masterTable.size()];
        Object[] lectureArr1stSlotMode = new Object[masterTable.size()];
        Object[] lectureArr1stSlotGroups = new Object[masterTable.size()];
        Object[] lectureArr2ndSlotName = new Object[masterTable.size()];
        Object[] lectureArr2ndSlotLecturer = new Object[masterTable.size()];
        Object[] lectureArr2ndSlotMode = new Object[masterTable.size()];
        Object[] lectureArr2ndSlotGroups = new Object[masterTable.size()];
        Object[] lectureArr2ndSlotLectureTyp = new Object[masterTable.size()];
        for (int i= 0; i < masterTable.size(); i++) {
            Day day = masterTable.get(i);
            LocalDate date = day.getDate();
            daysArr[i] = date.format(formatter);
            daysNamesArr[i] = Day.getDayStringNew(date, Locale.GERMAN);
            lectureArr1stSlotName[i] = day.getString(0, "Name");
            lectureArr1stSlotLecturer[i] = day.getString(0, "Lecturer");
            lectureArr1stSlotMode[i] = day.getString(0, "Mode");
            lectureArr1stSlotGroups[i] = day.getString(0, "Group");
            lectureArr1stSlotLectureTyp[i] = day.getString(0, "Type");
            lectureArr2ndSlotName[i] = day.getString(1, "Name");
            lectureArr2ndSlotLecturer[i] = day.getString(1, "Lecturer");
            lectureArr2ndSlotMode[i] = day.getString(1, "Mode");
            lectureArr2ndSlotGroups[i] = day.getString(1, "Group");
            lectureArr2ndSlotLectureTyp[i] = day.getString(1, "Type");
        }

        System.arraycopy(daysNamesArr, 0, bookData[0], 0, daysNamesArr.length);
        System.arraycopy(daysArr, 0, bookData[1], 0, daysArr.length);
        System.arraycopy(lectureArr1stSlotName, 0, bookData[3], 0, lectureArr1stSlotName.length);
        System.arraycopy(lectureArr1stSlotLecturer, 0, bookData[4], 0, lectureArr1stSlotLecturer.length);
        System.arraycopy(lectureArr1stSlotLectureTyp, 0, bookData[5], 0, lectureArr1stSlotLectureTyp.length);
        System.arraycopy(lectureArr1stSlotMode, 0, bookData[6], 0, lectureArr1stSlotMode.length);
        System.arraycopy(lectureArr1stSlotGroups, 0, bookData[7], 0, lectureArr1stSlotGroups.length);
        System.arraycopy(lectureArr2ndSlotName, 0, bookData[9], 0, lectureArr2ndSlotName.length);
        System.arraycopy(lectureArr2ndSlotLecturer, 0, bookData[10], 0, lectureArr2ndSlotLecturer.length);
        System.arraycopy(lectureArr2ndSlotLectureTyp, 0, bookData[11], 0, lectureArr2ndSlotLectureTyp.length);
        System.arraycopy(lectureArr2ndSlotMode, 0, bookData[12], 0, lectureArr2ndSlotMode.length);
        System.arraycopy(lectureArr2ndSlotGroups, 0, bookData[13], 0, lectureArr2ndSlotGroups.length);


        int rowCount = 0;

        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;

            for (Object field : aBook) {
                Cell cell = row.createCell(columnCount++);

                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }

        }

        try (FileOutputStream outputStream = new FileOutputStream(filepath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}