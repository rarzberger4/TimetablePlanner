package TTP;


import java.time.LocalDate;

public class TimeTable {
    private LocalDate startDate;
    private LocalDate endDate;
    private TTUnit[] masterTable;

    public TimeTable() {
    }


    public void initTT(LocalDate start, LocalDate end){
        masterTable = new TTUnit[end.compareTo(start)];

        for(int i = 0; i < masterTable.length; i++){
            masterTable[i] = new TTUnit();
        }

        System.out.println(end.compareTo(start));
    }

    public void initLecUnit(){

    }


    public void getNextDay(){

    }

    public void setLectureUnit(LectureUnit lecUnit, int pos,LocalDate date){      /// TODO: 20.12.2022 pos should be calculated from the date
        masterTable[pos].setLecUnit(date, lecUnit);
    }

    public void checkTT(){

    }

    public void getTimeTable(){

    }

    public void printTT(){

        for(int i = 0; i < masterTable.length; i++){
            for(int n = 0; n < 2; n++){
                LectureUnit lecUnit = masterTable[i].getLecUnits()[n];
                System.out.println("lectureUnitName: "+lecUnit.getName());
                System.out.println("lectureUnitLecturerName: "+lecUnit.getLecturer().getName());
            }
        }
    }

}
