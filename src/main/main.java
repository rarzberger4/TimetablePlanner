import TTP.LectureUnit;
import TTP.Lecturer;
import TTP.TTUnit;
import TTP.TimeTable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;


public class main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        TimeTable tt = new TimeTable();    //TTUnit[numberOfDays]

        LocalDate startDate = LocalDate.of(2022,9,1);
        LocalDate endDate = LocalDate.of(2022,9,30);

        tt.initTT(startDate,endDate);

        LocalDate lectureDate = LocalDate.of(2022,9,1);
        Lecturer lecturer = new Lecturer();
        lecturer.setName("TestLecturer");
        LectureUnit[] units2 = new LectureUnit[2];

        for (LectureUnit x: units2) {
            x = new LectureUnit();
        }

        LectureUnit lu = new LectureUnit();
        lu.setLecturer(lecturer);
        lu.setName("Test69");
        lu.setUnitLength(1);

        units2[0] = lu;
        units2[1] = lu;


        tt.setLectureUnit(lu, 0, lectureDate);

        tt.printTT();
    }

}
