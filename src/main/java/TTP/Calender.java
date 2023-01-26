package TTP;


import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Calender {
    //Create an iCal4J Calendar
    Calendar c = new Calendar();

    public Calender() {
        //"EN" is the language.
        c.getProperties().add(new ProdId("-//TimeTable//EN"));
        c.getProperties().add(Version.VERSION_2_0);
        c.getProperties().add(CalScale.GREGORIAN);
    }

    public void addDay(Day day) {
        ArrayList<LectureUnit> lectureUnits = day.getAssignedLectures(0);
        for (LectureUnit lec : lectureUnits) {
            c.getComponents().add(getEvent(day.getDate(), lec, 0));
        }
        lectureUnits = day.getAssignedLectures(1);
        for (LectureUnit lec : lectureUnits) {
            c.getComponents().add(getEvent(day.getDate(), lec, 1));
        }
    }

    private VEvent getEvent(LocalDate dat, LectureUnit lectureUnit, int slot) {
            //Add an event to the calendar
            VEvent vEvent = new VEvent();
            PropertyList<Property> eventProps = vEvent.getProperties();
            java.time.ZonedDateTime now = java.time.ZonedDateTime.now();

            //Add a start datetime and a duration
            TimeZoneRegistry tzReg = TimeZoneRegistryFactory.getInstance().createRegistry();
            TimeZone timezone = tzReg.getTimeZone("Europe/Vienna");
            try {
                String s = dat.toString().replace("-", "");
                if (slot == 0) {
                    eventProps.add(new DtStart(new DateTime(s+"T173000", timezone)));
                } else {
                    eventProps.add(new DtStart(new DateTime(s+"T191500", timezone)));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            eventProps.add(new Duration(java.time.Duration.ofMinutes(90)));

            //Add title and description
            eventProps.add(new Summary(lectureUnit.getString()));

            return vEvent;

        }

    public void export(String s) throws IOException {
        FileOutputStream foot = new FileOutputStream(s);
        CalendarOutputter output = new CalendarOutputter();
        output.output(c, foot);
    }

}
