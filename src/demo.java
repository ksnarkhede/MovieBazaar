import java.util.Arrays;

public class demo {
    public static void main(String[] args){
        String abc="9.00 am ,10.00pm";
        String[] str=abc.split(",");
        for(int i=0;i<str.length;i++){
            System.out.println(str[i]);
        }
    }
}
