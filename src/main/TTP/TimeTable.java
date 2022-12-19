package TTP;

import java.util.Calendar;
import java.time;

public class TimeTable {
    private Date startDate;
    private Date endDate;
    private TTUnit[] masterTable;

    public TimeTable() {
    }


    public void initTT(Date start, Date end){


        masterTable = new TTUnit[];
    }

    public void initLecUnit(){

    }


    public void getNextDay(){

    }

    public void setLectureUnit(LectureUnit lecUnit, int pos, Date date){
            masterTable[pos].setLecUnit(date, );
    }

    public void checkTT(){

    }

    public void getTimeTable(){

    }

}
