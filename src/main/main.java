import TTP.LectureUnit;
import TTP.Lecturer;
import TTP.TimeTableError;
import TTP.TimeTable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class main {
    public static void main(String[] args) {
        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2022,9,30);
        List<LocalDate> holidays = new ArrayList<>();

        TimeTable timeTable = new TimeTable(startDate, endDate, holidays);
        Lecturer lecturer = new Lecturer("Albert Einstein");

        // set day on which lecturer is not available
        lecturer.setNotAvailable(LocalDate.of(2022,9,8));
        // set new start date for lecture units
        startDate = LocalDate.of(2022,9,7);
        // set new end date for lecture units
        endDate = LocalDate.of(2022,9,20);

        LectureUnit lectureUnit_1 = new LectureUnit("Relativitätstheorie",  1, lecturer, startDate, endDate);
        try {
            timeTable.setLectureUnit(lectureUnit_1);
        } catch (TimeTableError e) {
            e.printStackTrace();
        }

        LectureUnit lectureUnit_2 = new LectureUnit("Spezielle Relativitätstheorie",  2, lecturer, startDate, endDate);
        try {
            timeTable.setLectureUnit(lectureUnit_2);
        } catch (TimeTableError e) {
            e.printStackTrace();
        }

        LectureUnit lectureUnit_3 = new LectureUnit("Gacki Relativitätstheorie",  1, lecturer, startDate, endDate);
        try {
            timeTable.setLectureUnit(lectureUnit_3);
        } catch (TimeTableError e) {
            e.printStackTrace();
        }

        LectureUnit lectureUnit_4 = new LectureUnit("Lulu Relativitätstheorie",  1, lecturer, startDate, endDate);
        try {
            timeTable.setLectureUnit(lectureUnit_4);
        } catch (TimeTableError e) {
            e.printStackTrace();
        }

        timeTable.print();
    }

}
