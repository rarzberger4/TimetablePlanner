package TTP;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TimeTable {
    private final List<Day> masterTable;
    private List<LectureUnit> lectureUnits = new ArrayList<>();

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

    public boolean setLectureUnit(LectureUnit lecUnit) {

        for (Day day : this.masterTable) {
            if (day.setLecUnit(lecUnit)) {
                return true;
            }
        }
        return false;
    }

    public void print(){
        for(Day day : masterTable){
            day.print();
        }
    }

    public void solve() throws TimeTableError {
        for (LectureUnit lectureUnit : this.lectureUnits) {
            if (!setLectureUnit(lectureUnit)) {
                lectureUnits.remove(lectureUnit);
                lectureUnits.add(0, lectureUnit);
                this.resetTimeTable();
                throw new TimeTableError("Could not set lecture: \"" + lectureUnit.getName() + "\"");
            }
        }
    }

    public void addLectureUnitToList(LectureUnit lectureUnit) {
        lectureUnits.add(lectureUnit);
    }

}