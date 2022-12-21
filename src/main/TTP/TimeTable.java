package TTP;

import java.time.LocalDate;
import java.util.List;

public class TimeTable {
    private List<Day> masterTable;

    public TimeTable(LocalDate startDate, LocalDate endDate, List<LocalDate> holidays){
        masterTable = Day.getBusinessDaysBetween(startDate, endDate, java.util.Optional.ofNullable(holidays));
    }

    public void setLectureUnit(LectureUnit lecUnit) throws TimeTableError{
        boolean success = false;

        for (Day day : this.masterTable) {
            if (day.setLecUnit(lecUnit)) {
                success = true;
                break;
            }
        }

        if (!success) {
            throw new TimeTableError("Could not set lecture unit \"" + lecUnit.getName() + "\" to timetable!");
        }
    }

    public void print(){
        for(Day day : masterTable){
            day.print();
        }
    }

}
