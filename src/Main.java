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
//   public void movieSelection(){
//       Scanner sc = new Scanner(System.in);
//       System.out.println("Seat Reservation------->");
//       display();
//       boolean action_status = true;
//       System.out.println("------------------------------------------------");
//       System.out.print("Select Movie using Index: ");
//       int movie_choice=sc.nextInt();
//
//       if (movieList.get(movie_choice).checkHouseFull()) {
//           System.out.println("Movie HouseFull");
//           action_status = false;
//
//       }
//       movieList.get(movie_choice).displayShowTimings();
//       System.out.println("------------------------------------------------");
//   }

   public void adminFuncion(){
       int ch;
       Scanner sc = new Scanner(System.in);
       boolean isLogin=false;
       do{
           System.out.println(" -- Enter Admin Password -- ");
           String adminPassword="adminpass";
           String inputPassword=sc.next();
           if(inputPassword.equals(adminPassword)){
               isLogin=true;
           }
           else{
               System.out.println("Password is incorrect!! Re-enter Password");
           }
       }while(!isLogin);
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
    public static void main(String[] args) {
        Main main = new Main();
        Scanner sc = new Scanner(System.in);
        int accesschoice;
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
                    accesschoice = sc.nextInt();
                } while (accesschoice != 1 && accesschoice != 2 );
                switch (accesschoice) {
                    case 1:
                        main.adminFuncion();
                        break;
                    case 2:
                        System.out.println("Seat Reservation------->");
                        main.display();
                        boolean action_status = true;
                        System.out.println("------------------------------------------------");
                        System.out.print("Select Movie using Index: ");
                        int movie_choice=sc.nextInt();

                        if (movieList.get(movie_choice).checkHouseFull()) {
                            System.out.println("Movie HouseFull");
                            action_status = false;
                            break;
                        }
                        movieList.get(movie_choice).displayShowTimings();
                        System.out.println("------------------------------------------------");
                        System.out.print("Choose the movie time using index: ");
                        int timingchoice = sc.nextInt();

                        movieList.get(movie_choice).showTimings.get(timingchoice).checkHouseFull();

                        movieList.get(movie_choice).showTimings.get(timingchoice).seatMatrix.showMatrix();
                        if (movieList.get(movie_choice).showTimings.get(timingchoice).seatMatrix.allBook()) {
                            System.out.println("Oops HouseFull!!\nPlease Choose Another Timings");
                            action_status = false;
                            break;
                        }

                        while (action_status) {
                            System.out.println("------------------------------------------------");
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
//                            action_status=false;
                                }
                            } while (!seatvalid);

                            for (int i = 0; i < seats.length; i++) {
                                int x = seats[i].charAt(0) - 'A';
                                String s1 = seats[i].substring(1);
                                int y = Integer.parseInt(s1) - 1;
                                if (movieList.get(movie_choice).showTimings.get(timingchoice).seatMatrix.isset(x, y)) {
                                    action_status = false;
                                    System.out.println("------------------------------------------------------");
                                    System.out.println("Seat " + seats[i] + " is not available");
                                    System.out.println("------------------------------------------------------");

                                    break;
                                }
                            }

                            if (action_status) {
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
                                        movieList.get(movie_choice).showTimings.get(timingchoice).seatMatrix.setxy(x, y);
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
                        }
                        break;
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input format. Please enter a valid choice (1 or 2).");
                sc.nextLine(); // Clear scanner buffer
                accesschoice = 0;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                accesschoice = 0; // Reset accessChoice to re-enter loop
            } catch (Exception e) {
                System.out.println(" You have chose unavailable index. Please Start again");
                accesschoice = 0;
            }
        } while(accesschoice<3);
}
}