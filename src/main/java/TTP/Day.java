package TTP;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day {
    private final LocalDate date;
    private final LectureUnit[][] lectureUnits = new LectureUnit[2][6];
    private boolean isOnline;

    public Day(LocalDate date) {
        this.date = date;
    }

    private boolean checkLecturerRow(int i, Lecturer lecturer) {
        for (int j = 0; j < lectureUnits[0].length; j++) {
            if (Objects.nonNull(lectureUnits[i][j])) {
                if (lectureUnits[i][j].getLecturer().equals(lecturer)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkStudentsRow(int i, Group group) {
        for (int j = 0; j < lectureUnits[0].length; j++) {
            if (Objects.nonNull(lectureUnits[i][j])) {
                if (lectureUnits[i][j].getGroup().getName().equals("1/1") || group.getName().equals("1/1")) {
                    return false;
                } else {
                    for (Student student : lectureUnits[i][j].getGroup().getStudentsList()) {
                        if (group.getStudentsList().contains(student)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean setLecUnit(LectureUnit newLectureUnit) {
        int[] check = new int[lectureUnits.length];
        Arrays.fill(check, 0);

        if (checkDayConstraints(newLectureUnit)) {
            for (int i = 0; i < lectureUnits.length; i++) {
                if (checkLecturerRow(i, newLectureUnit.getLecturer())
                        && checkStudentsRow(i, newLectureUnit.getGroup())) {
                    check[i] = 1;
                }
            }
        }

        int[] arr = findConsecutiveOnes(check);
        if (arr[0] >= newLectureUnit.getUnitLength()) {
            int c = findNextEmptyColumn();
            for (int i = 0; i < newLectureUnit.getUnitLength(); i++) {
                if(c < 0){return false;}
                lectureUnits[arr[1]+i][c] = newLectureUnit; //exception if c == -1
            }
            return true;
        } else {
            return false;
        }
    }

    private int findNextEmptyColumn() {
        int sum = 0;
        for (int j = 0; j < lectureUnits[0].length; j++) {
            for (LectureUnit[] lectureUnit : lectureUnits) {
                if (Objects.isNull(lectureUnit[j])) {
                    sum += 1;
                }
            }
            if (sum == lectureUnits.length) {
                return j;
            } else {
                sum = 0;
            }
        }
        return -1;
    }

    public static int[] findConsecutiveOnes(int[] arr) {
        int key = 1;
        int count = 0;
        int temp = 0;
        int[] retVal = {0, 0};
        int i;

        for (int k = 0; k < arr.length; k++) {
            i = arr[k];
            if (i == key) {
                count++;
                if (retVal[0] < count) {
                    retVal[0] = count;
                    retVal[1] = temp;
                }
            } else {
                count = 0;
                temp = k+1;
            }
        }
        return retVal;
    }

    private boolean checkDayConstraints(LectureUnit newLectureUnit) {                   //checks if lecturer is available in regard to defined constraints
        return date.isAfter(newLectureUnit.getFirstDate().minusDays(1))
                && date.isBefore(newLectureUnit.getLastDate().plusDays(1))
                && !Day.getDayStringNew(date, Locale.GERMAN).equals("Samstag")
                && !Day.getDayStringNew(date, Locale.GERMAN).equals("Sonntag")
                && newLectureUnit.getLecturer().checkAvailability(date)
                && checkLectureType(newLectureUnit.isOnline());
    }

    private boolean checkLectureType(boolean isOnline) {
        if (!isNotEmpty()) {
            this.isOnline = isOnline;
            return true;
        } else {
            return this.isOnline == isOnline;
        }
    }

    public void resetLectureUnits() {
        Arrays.stream(lectureUnits).forEach(x -> Arrays.fill(x, null));
    }

    public LectureUnit[][] getLectureUnits() {
        return lectureUnits;
    }

    public void print() {
        System.out.println(date +  ": " + getDayStringNew(date, Locale.GERMAN));
        for (int i = 0; i < lectureUnits.length; i++) {
            System.out.print(i+1 + ". Slot: ");
            for (int j = 0; j < lectureUnits[0].length; j++) {
                if (Objects.nonNull(lectureUnits[i][j])) {
                    System.out.print(lectureUnits[i][j].getName() + " | ");
                }
            }
            System.out.println();
        }
        System.out.println("-------------");
    }

    public String getString(int slot, String option) {
        LectureUnit[] lectures = getAssignedLectures(slot);
        StringBuilder str = new StringBuilder();

        for (LectureUnit lecture : lectures) {
            if (Objects.nonNull(lecture)) {
                if (option.equals("Name")) {
                    str.append(lecture.getName()).append(" * ");
                }
                if (option.equals("Group")) {
                    str.append(lecture.getGroup().getName()).append(" * ");
                }
                if (option.equals("Mode")) {
                    str.append(lecture.getMode()).append(" * ");
                }
                if (option.equals("Lecturer")) {
                    str.append(lecture.getLecturer().getName()).append(" * ");
                }
            }
        }

        return str.toString();
    }

    private LectureUnit getLectureUnit(int col) {
        for (LectureUnit[] lectureUnit : lectureUnits) {
            if (Objects.nonNull(lectureUnit[col])) {
                return lectureUnit[col];
            }
        }
        return null;
    }

    public static List<Day> getBusinessDaysBetween(final LocalDate startDate, final LocalDate endDate, final Optional<List<LocalDate>> holidays) {
        // Validate method arguments
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Invalid method argument(s) to countBusinessDaysBetween (" + startDate + "," + endDate + "," + holidays + ")");
        }
        // Predicate 1: If a given date is a holiday
        Predicate<LocalDate> isHoliday = date -> holidays.isPresent()
                && holidays.get().contains(date);
        // Predicate 2: If a given date is a weekday
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

    public boolean isNotEmpty() {
        int sum = 0;
        for (int j = 0; j < lectureUnits[0].length; j++) {
            for (LectureUnit[] lectureUnit : lectureUnits) {
                if (Objects.nonNull(lectureUnit[j])) {
                    sum += 1;
                }
            }
        }

        return !(sum == 0);
    }

    public int size() {
        int sum = 0;
        for (int j = 0; j < lectureUnits[0].length; j++) {
            for (LectureUnit[] lectureUnit : lectureUnits) {
                if (Objects.nonNull(lectureUnit[j])) {
                    sum += 1;
                }
            }
        }
        return sum;
    }

    public int getNumberOfAssignedLectures() {
        int sum = 0;

        for (int col = 0; col < lectureUnits[0].length; col++) {
            for (LectureUnit[] lectureUnit : lectureUnits) {
                if (Objects.nonNull(lectureUnit[col])) {
                    sum += 1;
                    break;
                }
            }
        }
        return sum;
    }

    public LectureUnit[] getAssignedLectures(int slot) {
        LectureUnit[] lectures = new LectureUnit[6];

        for (int col = 0; col < lectureUnits[slot].length; col++) {
            if (Objects.nonNull(lectureUnits[slot][col])) {
                lectures[col] = lectureUnits[slot][col];
            }
        }
        return lectures;
    }

}


