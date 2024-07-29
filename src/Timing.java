import java.util.Scanner;

public class Timing {
    public boolean housefullEvent;
    public String timeString;
//    public String dateString;
    public Matrix seatMatrix;
    public int eventprice;

    public boolean checkHouseFull(){
        housefullEvent=seatMatrix.allBook();
        if(housefullEvent){
            System.out.println("Event Housefull");
        }
        return housefullEvent;
    }
    public Timing(){
        this.seatMatrix=new Matrix();
    }
    public void addTimingDetails(String timeString){

//        this.dateString=dateString;
        this.timeString=timeString;
        System.out.println("Time Details added successfully");
        System.out.println("------------------------------------------------------");

    }
    public void addTicketprice(int eventprice){

        this.eventprice=eventprice;
    }
}
