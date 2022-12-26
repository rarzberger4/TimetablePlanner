package TTP;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TimeTable {
    private final List<DayNew> masterTable;
    private final List<LectureUnit> lectureUnits = new ArrayList<>();

    public TimeTable(LocalDate startDate, LocalDate endDate, List<LocalDate> holidays){
        masterTable = DayNew.getBusinessDaysBetween(startDate, endDate, java.util.Optional.ofNullable(holidays));
    }

    public List<DayNew> getMasterTable() {
        return this.masterTable;
    }

    public void resetTimeTable() {
        for (DayNew day : this.masterTable) {
            day.resetLectureUnits();
        }
    }

    public boolean setLectureUnit(LectureUnit lectureUnit) {

        for (DayNew day : this.masterTable) {
            if (day.setLecUnit(lectureUnit)) {
                return true;
            }
        }
        return false;
    }

    public void print(){
        for(DayNew day : masterTable){
            if (!day.isEmpty()) {
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

    public DayNew getDay(LocalDate date) {
        for (DayNew day : this.masterTable) {
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