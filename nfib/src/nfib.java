import java.util.ArrayList;

public class nfib {

    public static int nValue;
    public static int count;
    public static int fibNum;
    public static ArrayList<java.lang.Integer> fibNums;

    public void set_fibNum(int fibNum) {
        this.fibNum = fibNum;
    }
    public int get_fibNum(){
        return this.fibNum;
    }

    public int recursiveFib() {

        if (this.count == this.nValue) {
            set_fibNum(fibNums.get(nValue-1));
            return get_fibNum();
        }
        else {
            fibNums.add(count + 1, fibNums.get(count - 1) + fibNums.get(count));
            System.out.println(fibNums.get(count + 1));
            count++;

            return recursiveFib();
        }
    }

    nfib(int nValue){

        this.fibNums = new ArrayList<java.lang.Integer>();
        this.nValue = nValue;
        this.count = 1;
        this.fibNums.add(1);
        this.fibNums.add(1);


    }

    public static void main(String[] args) {

        nfib test = new nfib(22);
        test.recursiveFib();

    }


}
