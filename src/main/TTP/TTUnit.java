package TTP;

import java.time.LocalDate;
import java.util.Date;

public class TTUnit {           //each day is represented as TTUnit
    private LocalDate date;
    private LectureUnit[] lecUnits;


    public TTUnit() {
    }

    public void setLecUnit(LocalDate date, LectureUnit unit) {
        if(unit.getUnitLength() == 1){
            this.date = date;
            this.lecUnits[0] = unit;        //TODO find a way to set the unit the right way
        }

    }

    public LectureUnit[] getLecUnits() {
        return lecUnits;
    }
}


