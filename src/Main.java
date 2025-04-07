import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random rand = new Random();
        int numProcesses = 100;
        List<Process> processes = new ArrayList<>();

        // Generate random processes (arrival times between 0 and 10, burst times between 5 and 15)
        for (int i = 0; i < numProcesses; i++) {
            int arrivalTime = rand.nextInt(10);
            int burstTime = rand.nextInt(11) + 5;
            processes.add(new Process(i + 1, burstTime, arrivalTime));
        }

        // Print processes for reference
        System.out.println("Processes (ID, Burst Time, Arrival Time):");
        for (Process p : processes) {
            System.out.println("P" + p.number + " - Burst Time: " + p.burstTime + " Arrival Time: " + p.arrivalTime);
        }
        System.out.println();

        // Run all algorithms
        CPUSchedulingSimulation.FCFS(new ArrayList<>(processes)); // Pass a copy of the list to preserve original data
        CPUSchedulingSimulation.SJFNonPreemptive(new ArrayList<>(processes));
        CPUSchedulingSimulation.SJFPreemptive(new ArrayList<>(processes));
        CPUSchedulingSimulation.RoundRobin(new ArrayList<>(processes), 15);  // Example quantum value
    }
}
