import java.util.*;

public class Movie {
    public String Movie_title;
     int timing_id=1;
    public Map<Integer,Timing> showTimings=new HashMap<>();
    public Movie(String Movie_title){
        this.Movie_title=Movie_title;
    }
    public boolean houseFullMovie;
    Scanner sc=new Scanner(System.in);
    public boolean checkHouseFull(){
        int check=1;
        for(int i=1;i<=showTimings.size();i++){
            check*=showTimings.get(i).seatMatrix.allBook()==true?1:0;
//            System.out.println(check);
        }

        return check==1?true:false;
    }
    public void addShowTimimgs(String Time){
        try{

            Timing timeshow=new Timing();

            timeshow.addTimingDetails(Time);

            System.out.println("------------------------------------------------------");

            System.out.print("Add Ticket Prize for "+Time+ " Show: ₹");
            int eventPrize=sc.nextInt();

            System.out.println("------------------------------------------------------");

            timeshow.addTicketprice(eventPrize);

            showTimings.put(timing_id,timeshow);

            ++timing_id;

        }catch (InputMismatchException e) {

            System.out.println("Invalid input format. Please start again.");

            sc.nextLine();

        } catch (Exception e) {

            System.out.println("An error occurred: " + e.getMessage());

        }

    }
    public void displayShowTimings(){
        for(int i=1;i<=showTimings.size();i++){
            System.out.println("     "+(i)+". Time: "+showTimings.get(i).timeString);
            System.out.println("     "+" "+"  price: "+showTimings.get(i).eventprice);
        }

    }
}
