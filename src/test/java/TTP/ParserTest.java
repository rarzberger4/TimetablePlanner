package TTP;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @org.junit.jupiter.api.Test
    void checkIfAllLecturesWereAssigned() throws IOException {

        String filepath = "test.xlsx";
        Parser parser = new Parser(filepath);
        parser.parseXLS();
        TimeTable tt = parser.fillTT();
        tt.solve();
        assertEquals(tt.getLectureUnits().size(), tt.getNumberOfAssignedLectures());
    }

}