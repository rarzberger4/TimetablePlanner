package TTP;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class DayTest {

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
        Lecturer lecturer = new Lecturer("Albert Einstein");
        LectureUnit lectureUnit = new LectureUnit("Relativitätstheorie",  5, lecturer, startDate, endDate);

        assertFalse(day.setLecUnit(lectureUnit));
    }

    @org.junit.jupiter.api.Test
    void setLecUnit_allGood() {
        Day day = new Day(LocalDate.of(2023,1,16));
        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2023,1,30);
        Lecturer lecturer = new Lecturer("Albert Einstein");
        LectureUnit lectureUnit = new LectureUnit("Relativitätstheorie",  1, lecturer, startDate, endDate);

        assertTrue(day.setLecUnit(lectureUnit));
    }

    // two lectures with sum of unitLength 3
    @org.junit.jupiter.api.Test
    void setLecUnit_wrongSumOfUnitLengths() {
        Day day = new Day(LocalDate.of(2023,1,16));
        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2023,1,30);
        Lecturer lecturer = new Lecturer("Albert Einstein");
        LectureUnit lectureUnit_1 = new LectureUnit("Relativitätstheorie",  1, lecturer, startDate, endDate);
        day.setLecUnit(lectureUnit_1);
        LectureUnit lectureUnit_2 = new LectureUnit("Spezielle Relativitätstheorie",  2, lecturer, startDate, endDate);
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
        LectureUnit lectureUnit = new LectureUnit("Relativitätstheorie",  1, lecturer, startDate, endDate);

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
        LectureUnit lectureUnit = new LectureUnit("Relativitätstheorie",  1, lecturer, startDate, endDate);

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



}