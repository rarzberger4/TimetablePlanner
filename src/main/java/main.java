import TTP.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class main {

    public static void main(String[] args) throws IOException {


        Parser parser = new Parser();
        parser.parseXLS();



        /*LocalDate startDate = LocalDate.of(2022, 9, 1);
        LocalDate endDate = LocalDate.of(2022, 9, 30);
        List<LocalDate> holidays = new ArrayList<>();

        TimeTable timeTable = new TimeTable(startDate, endDate, holidays);
        Lecturer Albert = new Lecturer("Albert Einstein");
        Lecturer Kurt = new Lecturer("Kurt G�del");
        Group group1 = new Group();
        Student student1 = new Student("student1");
        group1.addStudentToList(student1);
        Group group2 = new Group();
        Student student2 = new Student("student2");
        group2.addStudentToList(student2);
        Group group3 = new Group();
        group3.addStudentToList(student1);
        group3.addStudentToList(student2);

        // set day on which lecturer is not available
        Albert.setNotAvailable(LocalDate.of(2022, 9, 9));
        Kurt.setNotAvailable(LocalDate.of(2022, 9, 10));
        // set new start date for lecture units
        startDate = LocalDate.of(2022, 9, 7);
        // set new end date for lecture units
        endDate = LocalDate.of(2022, 9, 20);

        LectureUnit lectureUnit_1 = new LectureUnit("Relativit�tstheorie", 1, Albert, startDate, endDate);
        LectureUnit lectureUnit_2 = new LectureUnit("Spezielle Relativit�tstheorie", 2, Albert, group2, startDate, endDate);
        LectureUnit lectureUnit_3 = new LectureUnit("Guenther Relativit�tstheorie", 1, Albert, group3, startDate, endDate);
        LectureUnit lectureUnit_4 = new LectureUnit("Martin Relativit�tstheorie", 2, Kurt, group3, startDate, endDate);
        timeTable.addLectureUnitToList(lectureUnit_1);
        timeTable.addLectureUnitToList(lectureUnit_2);
        timeTable.addLectureUnitToList(lectureUnit_3);
        timeTable.addLectureUnitToList(lectureUnit_4);


        timeTable.solve();
        timeTable.print();*/
    }
}
