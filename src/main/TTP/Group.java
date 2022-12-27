package TTP;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private List<Student> studentsList = new ArrayList<>();

    public void addStudentToList(Student student) {
        studentsList.add(student);
    }

    public List<Student> getStudentsList() {
        return studentsList;
    }

}
