package TTP;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day {
    private LocalDate date;
    final int lecturesPerDay = 2;
    private List<LectureUnit> lectureUnits = new ArrayList<>();


    public Day(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean setLecUnit(LectureUnit lectureUnit) {
        if(checkConstraints(lectureUnit)){
            lectureUnits.add(lectureUnit);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkConstraints(LectureUnit lectureUnit) {
        return lectureUnit.getUnitLength() <= this.getNumberOfFreeLecUnits()        // enough available space
                && lectureUnit.getLecturer().checkAvailability(this.date)           // lecturer is available
                && date.isAfter(lectureUnit.getFirstDate().minusDays(1))            // lecture start and end date is considered
                && date.isBefore(lectureUnit.getLastDate().plusDays(1))
                && !getDayStringNew(date, Locale.GERMAN).equals("Samstag")          // lecture is not on the weekend
                && !getDayStringNew(date, Locale.GERMAN).equals("Sonntag");
    }

    public int getNumberOfFreeLecUnits() {
        int sum = 0;

        for (LectureUnit lectureUnit : lectureUnits) {
            sum += lectureUnit.getUnitLength();
        }

        return lecturesPerDay - sum; }

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

}


