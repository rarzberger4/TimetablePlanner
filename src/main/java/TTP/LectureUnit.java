package TTP;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LectureUnit {
    private final String name;
    private final int unitLength;
    private final Lecturer lecturer;
    private final String type;
    private final LocalDate firstDate;
    private final LocalDate lastDate;
    private Group group = new Group();
    private final boolean isOnline;

    public LectureUnit(String name, int unitLength, Lecturer lecturer, Group group, LocalDate firstDate, LocalDate lastDate, boolean isOnline, String type) {
        this.name = name;
        this.unitLength = unitLength;
        this.lecturer = lecturer;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.group = group;
        this.isOnline = isOnline;
        this.type = type;
    }

    public LectureUnit(String name, int unitLength, Lecturer lecturer, LocalDate firstDate, LocalDate lastDate, boolean isOnline, String type) {
        this.name = name;
        this.unitLength = unitLength;
        this.lecturer = lecturer;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.isOnline = isOnline;
        this.type = type;
    }

    public LocalDate getFirstDate() { return firstDate; }

    public LocalDate getLastDate() { return lastDate; }

    public int getUnitLength() { return unitLength; }

    public Lecturer getLecturer() { return lecturer; }

    public String getName() { return name; }

    public Group getGroup() { return group; }

    public boolean isOnline() { return isOnline; }

    public String getType() { return type; }

    public String getMode() {
        if (isOnline) {
            return "online";
        } else {
            return "on-site";
        }
    }

    public String getString() { return name + " | " + group.getName() + " | " + this.getMode() + " | " + lecturer.getName() + " | " + type; }

}
