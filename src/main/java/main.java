import TTP.*;

import java.io.IOException;


public class main {

    public static void main(String[] args) throws IOException {
        String filepath = "test.xlsx";
        Parser parser = new Parser(filepath);
        parser.parseXLS();
        TimeTable tt = parser.fillTT();
        tt.solve();
        // tt.print();
        tt.exportTimeTableICS("timetable.ics");
        tt.exportTimeTable("export.xlsx");

        if (parser.getLectureUnitListLength() == tt.getNumberOfAssignedLectures()) {
            System.out.println("All lectures were successfully assigned!");
        } else {
            System.out.println("Not all lectures were successfully assigned!");
        }

        // ToDo Kommentare einfügen
        // ToDo split von den Studenten in die Gruppen sauber
        // ToDo Excel Export schöner machen
        // ToDo Error message entfernen

    }
}
