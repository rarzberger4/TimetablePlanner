package TTP;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TimeTable {
    private final List<Day> masterTable;
    private final List<LectureUnit> lectureUnits = new ArrayList<>();

    public TimeTable(LocalDate startDate, LocalDate endDate, List<LocalDate> holidays){
        masterTable = Day.getBusinessDaysBetween(startDate, endDate, java.util.Optional.ofNullable(holidays));
    }

    public List<Day> getMasterTable() {
        return this.masterTable;
    }

    public void resetTimeTable() {
        for (Day day : this.masterTable) {
            day.resetLectureUnits();
        }
    }

    public boolean setLectureUnit(LectureUnit lectureUnit) {

        for (Day day : this.masterTable) {
            if (day.getDate().isAfter(lectureUnit.getFirstDate().minusDays(1))            // lecture start and end date is considered
                    && day.getDate().isBefore(lectureUnit.getLastDate().plusDays(1))
                    && !Day.getDayStringNew(day.getDate(), Locale.GERMAN).equals("Samstag")          // lecture is not on the weekend
                    && !Day.getDayStringNew(day.getDate(), Locale.GERMAN).equals("Sonntag")
                    && lectureUnit.getLecturer().checkAvailability(day.getDate())) {
                if (day.setLecUnit(lectureUnit)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void print(){
        for(Day day : masterTable){
            if (!day.getLectureUnits().isEmpty()) {
                day.print();
            }
        }
    }

    public void solve() {
        resetTimeTable();
        int i = 0;
        while (i<10) {
            try {
                for (LectureUnit lectureUnit : this.lectureUnits) {
                    if (!setLectureUnit(lectureUnit)) {
                        lectureUnits.remove(lectureUnit);
                        lectureUnits.add(0, lectureUnit);
                        this.resetTimeTable();
                        throw new TimeTableError("Could not set lecture: \"" + lectureUnit.getName() + "\"");
                    }
                }
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
}