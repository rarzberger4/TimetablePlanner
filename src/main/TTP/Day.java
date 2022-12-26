package TTP;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day {
    private LocalDate date;
    private int lectureUnitsPerDay = 2;
    // private LectureUnit[][] lectureUnits = new LectureUnit[2][5];
    private  List<LectureUnit> lectureUnits = new ArrayList<>();

    public Day(LocalDate date) {
        this.date = date;
    }

    public boolean setLecUnit(LectureUnit newLectureUnit) {
        if (checkConstraints(newLectureUnit)) {
            lectureUnits.add(newLectureUnit);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkConstraints(LectureUnit newLectureUnit) {
        return newLectureUnit.getUnitLength() <= this.getNumberOfFreeLecUnits(newLectureUnit)
                && date.isAfter(newLectureUnit.getFirstDate().minusDays(1))            // lecture start and end date is considered
                && date.isBefore(newLectureUnit.getLastDate().plusDays(1))
                && !Day.getDayStringNew(date, Locale.GERMAN).equals("Samstag")          // lecture is not on the weekend
                && !Day.getDayStringNew(date, Locale.GERMAN).equals("Sonntag")
                && newLectureUnit.getLecturer().checkAvailability(date);
    }

    private int getNumberOfFreeLecUnits(LectureUnit newLectureUnit) {
        int sum = 0;

        for (LectureUnit lectureUnit : lectureUnits) {
            if (lectureUnit.getLecturer().equals(newLectureUnit.getLecturer())) {
                sum += lectureUnit.getUnitLength();
            } else {
                if (Objects.nonNull(lectureUnit.getGroup())) {
                    for (Student student : newLectureUnit.getGroup().getStudentsList()) {
                        if (lectureUnit.getGroup().getStudentsList().contains(student)) {
                            sum += lectureUnit.getUnitLength();
                            break;
                        }
                    }
                } else {
                    sum += lectureUnit.getUnitLength();
                }
            }
        }
        return lectureUnitsPerDay - sum;
    }

    public void resetLectureUnits() {
        this.lectureUnits.clear();
    }

    public List<LectureUnit> getLectureUnits() {
        return lectureUnits;
    }

    public void print() {
        System.out.println(date +  ": " + getDayStringNew(date, Locale.GERMAN));
        for (int i=0; i < lectureUnits.size(); i++) {
            for (int j=0; j < lectureUnits.get(i).getUnitLength(); j++) {
                System.out.println((i+j+1) + ": " + lectureUnits.get(i).getName() + " " + "(" + lectureUnits.get(i).getLecturer().getName() + ")");
            }
        }
        System.out.println();
    }

    public static List<Day> getBusinessDaysBetween(final LocalDate startDate, final LocalDate endDate, final Optional<List<LocalDate>> holidays) {
        // Validate method arguments
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Invalid method argument(s) to countBusinessDaysBetween (" + startDate + "," + endDate + "," + holidays + ")");
        }
        // Predicate 1: Is a given date is a holiday
        Predicate<LocalDate> isHoliday = date -> holidays.isPresent()
                && holidays.get().contains(date);
        // Predicate 2: Is a given date is a weekday
        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;
        // Iterate over stream of all dates and check each day against any weekday or holiday
        List<LocalDate> businessDays = startDate.datesUntil(endDate)
                .filter(isWeekend.or(isHoliday).negate())
                .collect(Collectors.toList());

        List<Day> days = new ArrayList<>();
        for(LocalDate temp : businessDays){
            Day day = new Day(temp);
            days.add(day);
        }

        return days;
    }

    public static String getDayStringNew(LocalDate date, Locale locale) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getDisplayName(TextStyle.FULL, locale);
    }

    public LocalDate getDate() { return date; }

}


