package cnblogs.jcli.calendar.field;

public enum WeekDayEnum {

    MO(1),
    TU(2),
    WE(3),
    TH(4),
    FR(5),
    SA(6),
    SU(7);
    
    private int index;
    
    private WeekDayEnum(int index){
        this.index = index;
    }
    
    public int getIndex(){
        return index;
    }
}
