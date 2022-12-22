package TTP;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeTableTest {

    @org.junit.jupiter.api.Test
    void resetTimeTable() {
        Lecturer lecturer = new Lecturer("Albert Einstein");
        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2022,9,30);
        List<LocalDate> holidays = new ArrayList<>();
        TimeTable timeTable = new TimeTable(startDate, endDate, holidays);

        LectureUnit lectureUnit_1 = new LectureUnit("Relativitätstheorie",  1, lecturer, startDate, endDate);
        LectureUnit lectureUnit_2 = new LectureUnit("Spezielle Relativitätstheorie",  2, lecturer, startDate, endDate);

        timeTable.addLectureUnitToList(lectureUnit_1);
        timeTable.addLectureUnitToList(lectureUnit_2);

        timeTable.resetTimeTable();

        boolean check = true;
        for (Day day : timeTable.getMasterTable()) {
            if (day.getLectureUnits().size() != 0) {
                check = false;
                break;
            }
        }
        assertTrue(check);
    }

}