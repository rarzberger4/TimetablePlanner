package TTP;

import java.time.LocalDate;
import java.util.Date;

public class TTUnit {           //each day is represented as TTUnit
    private LocalDate date;
    private LectureUnit[] lecUnits;


    public TTUnit() {
    }

    public void setLecUnit(LocalDate date, LectureUnit[] unit) {
        this.date = date;
        this.lecUnits = unit;
    }

    public LectureUnit[] getLecUnits() {
        return lecUnits;
    }
}


