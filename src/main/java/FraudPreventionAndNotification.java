import java.util.*;

/**
 * Created by timad on 3/22/2019.
 */
public class FraudPreventionAndNotification {


    public static PriorityQueue<Integer> lowerPriorityQueue = new PriorityQueue<>(Collections.reverseOrder());
    public static PriorityQueue<Integer> higherPriorityQueue = new PriorityQueue<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number:: ");
        int number = sc.nextInt();
        System.out.println("Enter the day:: ");
        int day = sc.nextInt();

        List<Integer> list = new ArrayList<>();
        long notificationCount = 0;

        for(int i=0; i<number; i++) {
            System.out.println("Enter value:: ");
            int x = sc.nextInt();
            list.add(x);
            int first = -1;

            if(i < day){
                first = -1;
            } else if(i >= day) {
                first = list.remove(0);
            }

            double median = calculateMedian(x,first,i,day);

            if(i < day) {
                continue;
            } else if(i >= day) {
                if(x >= 2 *median)
                    notificationCount++;
            }
        }

        System.out.println("Notification Count ==> " + notificationCount);
    }

    public static void addNumber(int number, PriorityQueue<Integer> lower, PriorityQueue<Integer> upper)
    {
        if(lower.size() == 0 || number<lower.peek()) {
            lower.add(number);
        } else {
            upper.add(number);
        }
    }


    public static void calculateBalance(PriorityQueue<Integer> lowers, PriorityQueue<Integer> highers) {

        PriorityQueue<Integer> biggerHeap = lowers.size() > highers.size() ? lowers:highers;
        PriorityQueue<Integer> smallerHeap = lowers.size() > highers.size() ? highers:lowers;

        if(biggerHeap.size() - smallerHeap.size() >= 2) {
            smallerHeap.add(biggerHeap.poll());
        }
    }

    public static double calculateMedian(PriorityQueue<Integer> lowers, PriorityQueue<Integer> highers) {
        PriorityQueue<Integer> biggerHeap = lowers.size() > highers.size() ? lowers:highers;
        PriorityQueue<Integer> smallerHeap = lowers.size() > highers.size() ? highers:lowers;

        if(biggerHeap.size() == smallerHeap.size()) {
            return  ((double) biggerHeap.peek()+smallerHeap.peek())/2;
        } else {
            return biggerHeap.peek();
        }
    }

    public static double calculateMedian(int number, int first, int position, int win) {
        if (position < win) {
            addNumber(number, lowerPriorityQueue, higherPriorityQueue);
            calculateBalance(lowerPriorityQueue, higherPriorityQueue);
            return 0.0;
        } else if (position >= win) {
            double median = calculateMedian(lowerPriorityQueue, higherPriorityQueue);
            if (lowerPriorityQueue.peek() < first) {
                higherPriorityQueue.remove(new Integer(first));
            } else {
                lowerPriorityQueue.remove(new Integer(first));
            }

            addNumber(number, lowerPriorityQueue, higherPriorityQueue);
            calculateBalance(lowerPriorityQueue, higherPriorityQueue);
            return median;
        }
        return 0.0;
    }
}
