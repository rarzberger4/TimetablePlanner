package TTP;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TimeTableTest {
    private final Lecturer Albert = new Lecturer("Albert Einstein");
    private final Lecturer Kurt = new Lecturer("Kurt Gödel");
    private final Lecturer Donald = new Lecturer("Donald Duck");
    private final LocalDate startDate = LocalDate.of(2022,9,1);
    private final LocalDate endDate = LocalDate.of(2022,9,30);
    private final List<LocalDate> holidays = new ArrayList<>();
    private final TimeTable timeTable = new TimeTable(startDate, endDate, holidays);
    private final Group group1 = new Group();
    private final Group group2 = new Group();
    private final Group group3 = new Group();

    @BeforeAll
    void beforeAll() {
        timeTable.addLectureUnitToList(new LectureUnit("Relativitätstheorie",  1, Albert, startDate, endDate, false));
        timeTable.addLectureUnitToList(new LectureUnit("Spezielle Relativitätstheorie",  2, Albert, startDate, endDate, false));
        timeTable.addLectureUnitToList(new LectureUnit("Logik VO",  2, Kurt, startDate, endDate, false));
        timeTable.addLectureUnitToList(new LectureUnit("Logik UE",  1, Kurt, startDate, endDate, false));

        Student student1 = new Student("student1");
        group1.addStudentToList(student1);
        group1.setName("1/2");
        Student student2 = new Student("student2");
        group2.addStudentToList(student2);
        group2.setName("2/2");
        group3.addStudentToList(student1);
        group3.addStudentToList(student2);
        group3.setName("1/1");

        timeTable.solve();
        timeTable.print();
    }

    @org.junit.jupiter.api.Test
    void resetTimeTable() {
        timeTable.resetTimeTable();

        boolean check = true;
        for (Day day : timeTable.getMasterTable()) {
            if (day.isNotEmpty()) {
                check = false;
                break;
            }
        }
        assertTrue(check);
    }


    @org.junit.jupiter.api.Test
    void parallelDifferentGroupsWithOverlappingStudents() {
        LectureUnit lectureUnit1 = new LectureUnit("Comics ILV",  2, Donald, group1, startDate, endDate, false);
        LectureUnit lectureUnit2 = new LectureUnit("Essen UE",  2, Kurt, group3, startDate, endDate, false);
        timeTable.addLectureUnitToList(lectureUnit1);
        timeTable.addLectureUnitToList(lectureUnit2);
        timeTable.solve();
        // timeTable.print();
        Day day1 = timeTable.getDay(LocalDate.of(2022,9,6));
        Day day2 = timeTable.getDay(LocalDate.of(2022,9,7));

        assertTrue(day1.size() == 2 && day2.size() == 2);
        timeTable.removeLectureUnitToList(lectureUnit1);
        timeTable.removeLectureUnitToList(lectureUnit2);
    }

    @org.junit.jupiter.api.Test
    void lecturerNotAvailable() {
        Kurt.setNotAvailable(LocalDate.of(2022,9,1));
        timeTable.solve();
        Day day = timeTable.getDay(LocalDate.of(2022,9,1));

        boolean check = true;
        for(LectureUnit[] lectureUnits : day.getLectureUnits()){
            for (LectureUnit lectureUnit : lectureUnits) {
                if (Objects.nonNull(lectureUnit)) {
                    if (lectureUnit.getLecturer().equals(Kurt)) {
                        check = false;
                        break;
                    }
                }
            }
        }
        assertTrue(check);
        Kurt.resetNotAvailable();
    }

}