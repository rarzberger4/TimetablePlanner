package TTP;

import java.sql.Date;

public class TTUnit {
    private Date date;
    private LectureUnit[] lecUnit = new LectureUnit[2];


    public TTUnit(Date date) {
        this.date = date;
    }

    public void setLecUnit(LectureUnit unit){
        unit.getLecturer();
        unit.getUnitLength();
        lecUnit[pos] = unit;
    }
}


