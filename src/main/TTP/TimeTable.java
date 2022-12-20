package TTP;


import java.time.LocalDate;
import java.util.Calendar;
import java.time.LocalTime;
import java.util.Locale;

public class TimeTable {
    private LocalDate startDate;
    private LocalDate endDate;
    private TTUnit[] masterTable;

    public TimeTable() {
    }


    public void initTT(LocalDate start, LocalDate end){
        masterTable = new TTUnit[end.compareTo(start)];
        System.out.println(end.compareTo(start));
    }

    public void initLecUnit(){

    }


    public void getNextDay(){

    }

    public void setLectureUnit(LectureUnit lecUnit, int pos, LocalDate date){
    }

    public void checkTT(){

    }

    public void getTimeTable(){

    }

}
