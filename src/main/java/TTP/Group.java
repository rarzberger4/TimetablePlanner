package TTP;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {
    private List<Student> studentsList = new ArrayList<>();
    private String name;

    public void addStudentToList(Student student) {
        studentsList.add(student);
    }

    public List<Student> getStudentsList() {
        return studentsList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        if (!Objects.isNull(name)) {
            return name;
        } else {
            return "1/1";
        }
    }

}
