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
    }

}
