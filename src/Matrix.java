import java.util.Arrays;

class Matrix {
    private int[][] data;

    public Matrix() {
        this.data = new int[5][5];
    }
    public char seat_row='A';
    public int seat_col=1;


    public void showMatrix() {
        System.out.print("    ");
        Arrays.stream(data).forEach((n)->{System.out.print(" "+seat_col++);});
        System.out.println();

        for (int i = 0; i < data.length; i++) {
            System.out.print(seat_row+"--> ");
            seat_row++;
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println("");
        }
        seat_col=1;
        seat_row='A';
    }
    public void setxy(int x, int y) {
        this.data[x][y] = 1;
    }
    public boolean isset(int x, int y) {
        if(x < 0 || x >= 5  || y < 0 || y >= 5 || this.data[x][y] == 1 ) return true;
        return false;
    }
    public boolean allBook(){
        long zeroCount = Arrays.stream(data)
                .flatMapToInt(Arrays::stream)
                .filter(x -> x == 0)
                .count();
        return zeroCount==0 ? true: false;
    }
}