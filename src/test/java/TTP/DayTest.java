package TTP;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DayTest {
    private final Lecturer Albert = new Lecturer("Albert Einstein");
    private final Lecturer Kurt = new Lecturer("Kurt Gödel");
    private final Lecturer Donald = new Lecturer("Donald Duck");
    private final Group group1 = new Group();
    private final Group group2 = new Group();
    private final Group group3 = new Group();

    @BeforeAll
    void beforeAll() {
        Student student1 = new Student("student1");
        group1.addStudentToList(student1);
        group1.setName("1/2");
        Student student2 = new Student("student2");
        group2.addStudentToList(student2);
        group2.setName("2/2");
        group3.addStudentToList(student1);
        group3.addStudentToList(student2);
        group3.setName("1/1");
    }

    // count business dates between two dates
    @org.junit.jupiter.api.Test
    void countBusinessDaysBetween() {
        List<LocalDate> holidays = new ArrayList<>();
        holidays.add(LocalDate.of(2022,12,23));
        LocalDate startDate = LocalDate.of(2022,12,19);
        LocalDate endDate = LocalDate.of(2022,12,31);
        int expected = 9;

        List<Day> businessDays = Day.getBusinessDaysBetween(startDate, endDate, java.util.Optional.of(holidays));
        int actual = businessDays.size();

        // System.out.println(businessDays);
        assertEquals(expected, actual);
    }

    // unitLength 5
    @org.junit.jupiter.api.Test
    void setLecUnit_wrongUnitLength() {
        Day day = new Day(LocalDate.of(2023,1,16));
        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2023,1,30);
        LectureUnit lectureUnit = new LectureUnit("Relativitätstheorie",  5, Albert, startDate, endDate, false);

        assertFalse(day.setLecUnit(lectureUnit));
    }

    @org.junit.jupiter.api.Test
    void setLecUnit_allGood() {
        Day day = new Day(LocalDate.of(2023,1,16));
        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2023,1,30);
        LectureUnit lectureUnit = new LectureUnit("Relativitätstheorie",  1, Albert, startDate, endDate, false);

        assertTrue(day.setLecUnit(lectureUnit));
    }

    // two lectures with sum of unitLength 3
    @org.junit.jupiter.api.Test
    void setLecUnit_wrongSumOfUnitLengths() {
        Day day = new Day(LocalDate.of(2023,1,16));
        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2023,1,30);
        Lecturer lecturer = new Lecturer("Albert Einstein");
        LectureUnit lectureUnit_1 = new LectureUnit("Relativitätstheorie",  1, lecturer, startDate, endDate, false);
        day.setLecUnit(lectureUnit_1);
        LectureUnit lectureUnit_2 = new LectureUnit("Spezielle Relativitätstheorie",  2, lecturer, startDate, endDate, false);
        day.setLecUnit(lectureUnit_2);

        assertFalse(day.setLecUnit(lectureUnit_2));
    }

    // day is set to weekend
    @org.junit.jupiter.api.Test
    void setLecUnit_weekend() {
        Day day = new Day(LocalDate.of(2023,1,15));
        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2023,1,30);
        Lecturer lecturer = new Lecturer("Albert Einstein");
        LectureUnit lectureUnit = new LectureUnit("Relativitätstheorie",  1, lecturer, startDate, endDate, false);

        assertFalse(day.setLecUnit(lectureUnit));
    }

    // lecturer is not available
    @org.junit.jupiter.api.Test
    void setLecUnit_lecturerNotAvailable() {
        Day day = new Day(LocalDate.of(2023,1,25));
        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2023,1,30);
        Lecturer lecturer = new Lecturer("Albert Einstein");
        lecturer.setNotAvailable(day.getDate());
        LectureUnit lectureUnit = new LectureUnit("Relativitätstheorie",  1, lecturer, startDate, endDate, false);

        assertFalse(day.setLecUnit(lectureUnit));
    }

    @org.junit.jupiter.api.Test
    void findConsecutiveOnes_1() {
        int[] arr = {0,1,1,1,0,0,0,1,1,1,1,0,1,0,0,0,0};

        int[] actual = Day.findConsecutiveOnes(arr);
        assertEquals(4, actual[0]);
    }

    @org.junit.jupiter.api.Test
    void findConsecutiveOnes_2() {
        int[] arr = {0,1,1,1,0,0,0,1,1,1,1,0,1,0,0,0,0};

        int[] actual = Day.findConsecutiveOnes(arr);
        assertEquals(7, actual[1]);
    }

    @org.junit.jupiter.api.Test
    void findConsecutiveOnes_3() {
        int[] arr = {0,0,0,0,1,1,1,1};

        int[] actual = Day.findConsecutiveOnes(arr);
        assertEquals(4, actual[1]);
    }

    @org.junit.jupiter.api.Test
    void checkLectureType1() {
        Day day = new Day(LocalDate.of(2023,1,16));
        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2023,1,30);
        Lecturer lecturer = new Lecturer("Albert Einstein");
        LectureUnit lectureUnit_1 = new LectureUnit("Relativitätstheorie",  1, lecturer, startDate, endDate, false);
        day.setLecUnit(lectureUnit_1);
        LectureUnit lectureUnit_2 = new LectureUnit("Spezielle Relativitätstheorie",  1, lecturer, startDate, endDate, true);
        day.setLecUnit(lectureUnit_2);

        assertEquals(1, day.getNumberOfAssignedLectures());
    }

    @org.junit.jupiter.api.Test
    void checkLectureType2() {
        Day day = new Day(LocalDate.of(2023,1,16));
        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2023,1,30);
        Lecturer lecturer = new Lecturer("Albert Einstein");
        LectureUnit lectureUnit_1 = new LectureUnit("Relativitätstheorie",  1, lecturer, startDate, endDate, true);
        day.setLecUnit(lectureUnit_1);
        LectureUnit lectureUnit_2 = new LectureUnit("Spezielle Relativitätstheorie",  1, lecturer, startDate, endDate, true);
        day.setLecUnit(lectureUnit_2);

        assertEquals(2, day.getNumberOfAssignedLectures());
    }

    @org.junit.jupiter.api.Test
    void parallelDifferentGroupsSameLecturer() {
        Day day = new Day(LocalDate.of(2022,9,15));
        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2022,9,30);

        LectureUnit lectureUnit1 = new LectureUnit("Comics ILV",  2, Donald, group1, startDate, endDate, true);
        LectureUnit lectureUnit2 = new LectureUnit("Zeichnen UE",  1, Donald, group2, startDate, endDate, true);
        day.setLecUnit(lectureUnit1);
        day.setLecUnit(lectureUnit2);

        assertEquals(1, day.getNumberOfAssignedLectures());
    }

    @org.junit.jupiter.api.Test
    void parallelDifferentGroups() {
        Day day = new Day(LocalDate.of(2022,9,15));
        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2022,9,30);

        LectureUnit lectureUnit1 = new LectureUnit("Comics ILV",  2, Donald, group1, startDate, endDate, false);
        LectureUnit lectureUnit2 = new LectureUnit("Essen UE",  2, Kurt, group2, startDate, endDate, false);
        day.setLecUnit(lectureUnit1);
        day.setLecUnit(lectureUnit2);

        assertEquals(2, day.getNumberOfAssignedLectures());
    }

    @org.junit.jupiter.api.Test
    void superTest() {
        Day day = new Day(LocalDate.of(2022,9,15));
        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2022,9,30);

        LectureUnit lectureUnit1 = new LectureUnit("Comics ILV",  1, Donald, group1, startDate, endDate, false);
        LectureUnit lectureUnit2 = new LectureUnit("Essen UE",  1, Kurt, group2, startDate, endDate, false);
        LectureUnit lectureUnit3 = new LectureUnit("Daisy",  1, Donald, group3, startDate, endDate, false);

        day.setLecUnit(lectureUnit1);
        day.setLecUnit(lectureUnit2);
        day.setLecUnit(lectureUnit3);

        assertEquals(3, day.getNumberOfAssignedLectures());
    }


}