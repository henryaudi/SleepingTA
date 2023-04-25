import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* Student Class */
public class Student implements Runnable {
    // Data field
    private final String tId;
    private final Lock chairLock;
    private TA ta;

    // Constructor
    public Student(String tId) {
        this.tId = tId;
        this.chairLock = new ReentrantLock();
        this.ta = new TA();
    }

    // Getters and setters
    /** Return the thread ID of the student object */
    public String gettId() { return this.tId; }

    /** Return the TA object associated with the student */
    public TA getTa() { return this.ta; }

    /** Set the TA object associated with the student */
    public void setTa(TA ta) { this.ta = ta; }

    // Methods
    /** Student is programming */
    public void programming() {
        System.out.println("Student" + gettId() + " is programming.");
        try {
            Thread.sleep((int) (Math.random() * 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** Student sits on chair
        @return {true} if the student sits on chair successfully, otherwise return {false}
    */
    public boolean sitOnChair() {
        boolean isSitting = false;
        chairLock.lock();
        try {
            if (ta.getNumStudentsWaiting() < Main.NUM_CHAIRS) {
                // Enough seats in the hallway.
                ta.incNumOfStudentsWaiting();
                System.out.println("Student " + this.gettId() + " sits in the hallway.");
                isSitting = true;
            } else {
                programming();
                sitOnChair();
            }
        } finally {
            chairLock.unlock();
        }
        return isSitting;
    }

    /** Seek help from TA */
    public void seekHelp() {
        Semaphore sem = this.ta.getSem();
        try {
            // Student sits down
            this.sitOnChair();
            sem.acquire();

            // Successfully enter critical section.
            ta.wakeup();
            ta.decNumOfStudentsWaiting();
            System.out.println("TA is helping student " + this.gettId() + ".");
            Thread.sleep(1000);
            System.out.println("TA finished helping student " + this.gettId() + '.');
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            sem.release();
        }
    }

    /** Execute the student object as a thread */
    @Override
    public void run() {
        this.programming();
        this.seekHelp();
    }
}
