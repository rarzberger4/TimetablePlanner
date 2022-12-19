package TTP;

import java.util.Date;

public class TTUnit {           //each day is represented as TTUnit
    private Date date;
    private LectureUnit[] lecUnit = new LectureUnit[2];


    public TTUnit(Date date, LectureUnit[] lecUnit) {
        this.date = date;
        this.lecUnit = lecUnit;
    }

    public void setLecUnit(Date date, LectureUnit unit){
        this.date = date;
        this.lecUnit = lecUnit;
    }
}


