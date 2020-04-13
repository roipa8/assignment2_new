import java.util.Stack;

public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int fd, int bk, Stack myStack) {
        // TODO: implement your code here
        int count=0;
        for(int i=0;i<arr.length;i=i+1){
            myStack.push(i);
            count=count+1;
            if(arr[(int)myStack.peek()]==x){
                return i;
            }
            if(count==fd){
                i=i-bk;
                count=0;
            }
        }
        return -1;
    }
    public static void main(String[]args){
//        int []A={17,62,19,10,1,78,20,20,20,10};
        int []B={1,1,2,14,15,16,23,99,100,100,100,132,193,196,197};
        Stack st=new Stack();
//        System.out.println(backtrackingSearch(A,10,3,2,st));
        System.out.println(consistentBinSearch(B,14,st));
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
        // TODO: implement your code here
        int low=0;
        int high=arr.length-1;
        int isCos;
        while (low<=high) {
            isCos=isConsistent(arr);
            myStack.push(low);
            myStack.push(high);
            if(isCos==1){
                high=(int)myStack.pop();
                low=(int)myStack.pop();
            }
            if(isCos==2){
                myStack.pop();
                myStack.pop();
                high=(int)myStack.pop();
                low=(int)myStack.pop();
            }
            int mid=(low+high)/2;
            if(arr[mid]==x){
                return mid;
            }
            else{
                if(arr[mid]>x){
                    high=mid-1;
                }
                else {
                    low=mid+1;
                }
            }
        }
        return -1;
    }

    private static int isConsistent(int[] arr) {
        double res = Math.random() * 100 - 75;

        if (res > 0) {
            return (int)Math.round(res / 10);
        } else {
            return 0;
        }
    }
}
