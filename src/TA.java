import java.util.concurrent.Semaphore;

public class TA implements Runnable {
    // Data field
    private final Semaphore sem;
    private int numStudentsWaiting;

    // Constructor
    public TA() {
        this.sem = new Semaphore(1);
        this.numStudentsWaiting = 0;
    }

    // Getter and setter
    /** Get the semaphore from TA */
    public Semaphore getSem() { return this.sem; }

    /** Get the number of students waiting in the hallway */
    public int getNumStudentsWaiting() { return this.numStudentsWaiting; }

    /** Set the number of students waiting in the hallway */
    public synchronized void setNumStudentsWaiting(int num) { this.numStudentsWaiting = num; }

    // Methods
    /** TA sleeps */
    public void sleep() { System.out.println("TA is sleeping."); }

    /** TA wakes up */
    public void wakeup() { System.out.println("TA is awake now."); }

    /** Increment the number of students waiting in the hallway */
    public synchronized void incNumOfStudentsWaiting() { this.numStudentsWaiting++; }

    /** Decrement the number of students waiting in the hallway */
    public synchronized void decNumOfStudentsWaiting() { this.numStudentsWaiting--; }

    /** Execute the thread */
    @Override
    public void run() {
        sleep();
    }
}
