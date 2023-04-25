public class SleepingTA {
    // Data field
    private final Student[] students;
    private final TA ta;
    private Thread[] threads;

    // Constructor
    public SleepingTA(int numOfStudents) {
        this.ta = new TA();
        this.students = new Student[numOfStudents];
        this.threads = new Thread[numOfStudents];

        for (int i = 0; i < numOfStudents; i++) {
            Student student = new Student(Integer.toString(i));
            student.setTa(ta);
            students[i] = student;
        }

        // Load TA object to a thread and execute it.
        Thread taThread = new Thread(ta);
        taThread.start();

        // Load student objects to a thread and execute them.
        for (int i = 0; i < students.length; i++) {
            threads[i] = new Thread(students[i]);
            threads[i].start();
        }

        // Terminate student threads.
        for (Thread t : threads) {
            try {
                t.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
