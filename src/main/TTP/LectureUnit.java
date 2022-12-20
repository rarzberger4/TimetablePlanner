package TTP;

public class LectureUnit {
    private String name;
    private int unitLength;
    private Lecturer lecturer;

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitLength(int unitLength) {
        this.unitLength = unitLength;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public int getUnitLength() {
        return unitLength;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }


    public String getName() {
        return name;
    }


}
