package TTP;

import java.time.LocalDate;

public class LectureUnit {
    private String name;
    private int unitLength;
    private Lecturer lecturer;
    private LocalDate firstDate;
    private LocalDate lastDate;


    public LectureUnit(String name, int unitLength, Lecturer lecturer, LocalDate firstDate, LocalDate lastDate) {
        this.name = name;
        this.unitLength = unitLength;
        this.lecturer = lecturer;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
    }

    public LocalDate getFirstDate() { return firstDate; }

    public LocalDate getLastDate() { return lastDate; }

    public int getUnitLength() { return unitLength; }

    public Lecturer getLecturer() { return lecturer; }

    public String getName() { return name; }

}
