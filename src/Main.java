import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TimeValidator {
    private static final String timePattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    private static final String seatPattern="^[A-E]([1-5]|5)";
    public static boolean isValidTime(String time) {
        Pattern pattern = Pattern.compile(timePattern);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }
    public static boolean isValidSeat(String seat) {
        Pattern pattern = Pattern.compile(seatPattern);
        Matcher matcher = pattern.matcher(seat);
        return matcher.matches();
    }
}

public class Main {
    public static HashMap<Integer,Movie>movieList=new HashMap<>();
    static int movie_id=1;
    public void addMovie(Movie movie){
        movieList.put(movie_id,movie);
        ++movie_id;
    }
    public void display(){
        int numMovies=movieList.size();
        System.out.println("Available Movies");

        for(int i=1;i<=numMovies;i++){
            System.out.println((i)+ ". " +movieList.get(i).Movie_title);
            System.out.println("     "+"Show Timings");
            movieList.get(i).displayShowTimings();
        }
    }
    public void paymentConfirmation(int movieChoice,int timingChoice,String seats[]){
        Scanner sc=new Scanner(System.in);
        int numseats = seats.length;
        System.out.println("------------------------------------------------------");
        System.out.println("Amount to be paid");
        System.out.println("₹ " + movieList.get(movieChoice).showTimings.get(timingChoice).eventprice * numseats);
        System.out.println("------------------------------------------------------");
        System.out.println("Confirm Payment: 1-->Yes 2-->No");
        int payChoice = sc.nextInt();
        System.out.println("------------------------------------------------------");
        if (payChoice == 1) {
            Arrays.stream(seats).forEach((seat) -> {
                int x = seat.charAt(0) - 'A';
                String s1 = seat.substring(1);
                int y = Integer.parseInt(s1) - 1;
                movieList.get(movieChoice).showTimings.get(timingChoice).seatMatrix.setxy(x, y);
            });
            movieList.get(movieChoice).showTimings.get(timingChoice).seatMatrix.showMatrix();
            System.out.println("------------------------------------------------------");
            System.out.println("Ticket Booked Successfully");
            System.out.println("------------------------------------------------------");
        } else {
            System.out.println("Payment Cancelled by user");
        }
    }
    public void adminFuncion(){
        int ch;
        Scanner sc = new Scanner(System.in);
        do {
            try {
                System.out.println("-------------------------Menu-------------------------");

                System.out.println("1.Add Movie\n2.Display List\n3.Exit");
                System.out.println("------------------------------------------------------");

                System.out.print("Enter your choice: ");
                ch = sc.nextInt();
                System.out.println("------------------------------------------------------");

                switch (ch) {
                    case 1:
                        System.out.println("------------------------------------------------------");
                        System.out.print("Enter Movie Name: ");
                        String movieTitle = sc.next();
                        System.out.println("------------------------------------------------------");
                        Movie movie = new Movie(movieTitle);
                        System.out.println("Movie added Successfully");
                        System.out.println("------------------------------------------------------");
                        System.out.println("Now You have to add Movie Show Timings:");
                        System.out.println("eg. 9:00,12:00,3:00,6:00 (comma seperated)");
                        boolean isValid = true;
                        String[] showTimings;
                        sc.nextLine();
                        do {
                            isValid = true;

                            String showTimingString = sc.nextLine();
                            showTimings = showTimingString.split(",");
                            for (String time : showTimings) {
                                isValid = isValid && TimeValidator.isValidTime(time);
                            }
                            if (!isValid) {
                                System.out.println("Re-enter the time again (Follow format)");
                            } else {
                                break;
                            }

                        }
                        while (!isValid);
                        System.out.println(isValid);
                        int numTimings = showTimings.length;
                        Arrays.stream(showTimings).forEach((n) -> movie.addShowTimimgs(n));
                        addMovie(movie);
                        break;
                    case 2:
                        System.out.println("------------------------------------------------------");
                        display();
                        System.out.println("------------------------------------------------------");

                        break;
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input format\n1.Add Movie\n2.Display List\n3+.Exit");
                sc.nextLine();
                ch = 0;
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                ch = 0;
            }
        } while (ch < 3);
    }
    boolean action_status = true;
    public boolean movieSelection(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Seat Reservation------->");
        display();
        int ch;
        boolean action_status = true;
        System.out.println("Select option 1 for NEXT and 2 for BACK");
        System.out.println("1.Select movie.\n2.Exit");
        do {
            System.out.println("Enter access choice (1 or 2):");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter 1 or 2:");
                sc.next(); // Clear invalid input
            }
            ch = sc.nextInt();
        } while (ch != 1 && ch != 2 );
        if(ch==1){
            System.out.println("------------------------------------------------");
//            System.out.print("Select Movie using Index: \n ");
            display();
            System.out.print("Select Movie using Index: \n ");
            int movie_choice=sc.nextInt();
            if (movieList.get(movie_choice).checkHouseFull()) {
                System.out.println("Movie HouseFull");
                action_status = false;
            }
//            movieList.get(movie_choice).displayShowTimings();
            System.out.println("------------------------------------------------");
            int multiplier=action_status?1:-1;
            return timingSelection(movie_choice, action_status);
        }else{
            return false;
        }
    }
    public boolean timingSelection(int movie_choice,boolean action_status){
        Scanner sc=new Scanner(System.in);
        movieList.get(movie_choice).displayShowTimings();
        System.out.println("1.Do you want to continue?.\n2.Back");
        int ch;
        do {
            System.out.println("Enter access choice (1 or 2):");
      //      movieList.get(movie_choice).displayShowTimings();
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter 1 or 2:");
                sc.next(); // Clear invalid input
            }
            ch = sc.nextInt();
        } while (ch != 1 && ch != 2 );
        if(ch==1){
            System.out.print("Choose the movie time using index: ");
            int timingchoice = sc.nextInt();
            movieList.get(movie_choice).showTimings.get(timingchoice).checkHouseFull();
            movieList.get(movie_choice).showTimings.get(timingchoice).seatMatrix.showMatrix();
            if (movieList.get(movie_choice).showTimings.get(timingchoice).seatMatrix.allBook()) {
                System.out.println("Oops HouseFull!!\n Please Choose Another Timings");
                action_status = false;

            }
            int multiplier=action_status?1:-1;
            return seatSelection(movie_choice, timingchoice, action_status);
        }else{
            return movieSelection();
        }
    }
    public boolean seatSelection(int movie_choice,int timingchoice,boolean action_status){
        Scanner sc = new Scanner(System.in);
//        System.out.println("------------------------------------------------");
        System.out.println("1.Do you want to continue?.\n2.Back");
        int ch;
        do {
            System.out.println("Enter access choice (1 or 2):");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter 1 or 2:");
                sc.next(); // Clear invalid input
            }
            ch = sc.nextInt();
        } while (ch != 1 && ch != 2 );
        if(ch==1) {
            boolean seatvalid = true;
            String[] seats;

            do {
                seatvalid = true;
                System.out.println("Please enter seat by comma seperated (A1,A2,A3)");
                String seat_string = sc.next();
                seats = seat_string.split(",");

                for (String seat : seats) {
                    seatvalid = seatvalid && TimeValidator.isValidSeat(seat);
                }
                System.out.println(seatvalid);

                if (!seatvalid) {
                    System.out.println("Re-enter the seat again (Follow format)");
// action_status=false;
                }
            } while (!seatvalid);
            return seatAvailiabilty(seats, movie_choice, timingchoice, action_status);
        }else{
                return timingSelection(movie_choice,action_status);
        }
    }
    public boolean seatAvailiabilty(String[] seats,int movie_choice,int timingchoice,boolean action_status) {
        for (int i = 0; i < seats.length; i++) {
            int x = seats[i].charAt(0) - 'A';
            String s1 = seats[i].substring(1);

            int y = Integer.parseInt(s1) - 1;

            if (movieList.get(movie_choice).showTimings.get(timingchoice).seatMatrix.isset(x, y)){
                action_status = false;
                System.out.println("------------------------------------------------------");
                System.out.println("Seat "+ seats[i]+" is not available");
                System.out.println("------------------------------------------------------");
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        Main main = new Main();
        Scanner sc = new Scanner(System.in);
        int accessChoice;
        do {
            try {
                System.out.println("PVR Ticket Booking : ");
                System.out.println("------------------------------------------------");
                System.out.println("Type 1 -> 1.Login as Administrator\nType 2 -> 2.Movie Ticket Selection");
                do {
                    System.out.println("Enter access choice (1 or 2):");
                    while (!sc.hasNextInt()) {
                        System.out.println("Invalid input. Enter 1 or 2:");
                        sc.next(); // Clear invalid input
                    }
                    accessChoice = sc.nextInt();
                } while (accessChoice != 1 && accessChoice != 2 );
                switch (accessChoice) {
                    case 1:
                        main.adminFuncion();
                        break;
                    case 2:
                        boolean movieStatus =main.movieSelection();



                        /* while (movieStatus) {

                            if (movieStatus) {
                                int numseats = seats.length;
                                System.out.println("------------------------------------------------------");
                                System.out.println("Amount to be paid");
                                System.out.println("₹ " + movieList.get(movie_choice).showTimings.get(timingchoice).eventprice * numseats);
                                System.out.println("------------------------------------------------------");
                                System.out.println("Confirm Payment: 1-->Yes 2-->No");
                                int paychoice = sc.nextInt();
                                System.out.println("------------------------------------------------------");
                                if (paychoice == 1) {
                                    Arrays.stream(seats).forEach((seat) -> {
                                        int x = seat.charAt(0) - 'A';
                                        String s1 = seat.substring(1);
                                        int y = Integer.parseInt(s1) - 1;
                                        movieList.get(movie_choice).showTimings.get(timingchoice).seatMatrix.setXY(x, y);
                                    });
                                    movieList.get(movie_choice).showTimings.get(timingchoice).seatMatrix.showMatrix();
                                    System.out.println("------------------------------------------------------");
                                    System.out.println("Ticket Booked Successfully");
                                    System.out.println("------------------------------------------------------");
                                } else {
                                    System.out.println("Payment Cancelled by user");
                                    break;
                                }
                            }
                            action_status = false;
                        }*/
                        break;
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input format. Please enter a valid choice (1 or 2).");
                sc.nextLine(); // Clear scanner buffer
                accessChoice = 0;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                accessChoice = 0; // Reset accessChoice to re-enter loop
            } catch (Exception e) {
                System.out.println(" You have chose unavailable index. Please Start again");
                accessChoice = 0;
            }
        } while(accessChoice<3);
    }
}