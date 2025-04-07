import java.util.*;

public class CPUSchedulingSimulation {

    // First Come First Serve (FCFS)
    public static void FCFS(List<Process> processes) {
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;
        int currentTime = 0;

        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;  // CPU idle time before the process arrives
            }
            p.waitingTime = currentTime - p.arrivalTime;
            p.turnAroundTime = p.waitingTime + p.burstTime;

            totalWaitingTime += p.waitingTime;
            totalTurnAroundTime += p.turnAroundTime;

            currentTime += p.burstTime;
        }

        System.out.println("FCFS Scheduling:");
        System.out.println("Average Waiting Time: " + (totalWaitingTime / (float) processes.size()));
        System.out.println("Average Turnaround Time: " + (totalTurnAroundTime / (float) processes.size()));
        System.out.println();
    }

    // Shortest Job First (SJF) - Non-Preemptive
    public static void SJFNonPreemptive(List<Process> processes) {
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;
        int currentTime = 0;

        // Sort by burst time (Shortest job first)
        processes.sort(Comparator.comparingInt(p -> p.burstTime));

        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;  // CPU idle time before the process arrives
            }
            p.waitingTime = currentTime - p.arrivalTime;
            p.turnAroundTime = p.waitingTime + p.burstTime;

            totalWaitingTime += p.waitingTime;
            totalTurnAroundTime += p.turnAroundTime;

            currentTime += p.burstTime;
        }

        System.out.println("SJF Non-Preemptive Scheduling:");
        System.out.println("Average Waiting Time: " + (totalWaitingTime / (float) processes.size()));
        System.out.println("Average Turnaround Time: " + (totalTurnAroundTime / (float) processes.size()));
        System.out.println();
    }

    // Shortest Job First (SJF) - Preemptive (SRTF)
    public static void SJFPreemptive(List<Process> processes) {
        int n = processes.size();
        int completed = 0;
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;

        // Continue until all processes are completed
        while (completed != n) {
            // Find process with minimum remaining time among those that have arrived
            Process shortest = null;
            int minRemaining = Integer.MAX_VALUE;
            for (Process p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0 && p.remainingTime < minRemaining) {
                    minRemaining = p.remainingTime;
                    shortest = p;
                }
            }

            // If no process is available, advance time
            if (shortest == null) {
                currentTime++;
                continue;
            }

            // Execute the process for 1 time unit
            shortest.remainingTime--;
            currentTime++;

            // If the process is finished, calculate times
            if (shortest.remainingTime == 0) {
                completed++;
                int finishTime = currentTime;
                shortest.turnAroundTime = finishTime - shortest.arrivalTime;
                shortest.waitingTime = shortest.turnAroundTime - shortest.burstTime;
                totalWaitingTime += shortest.waitingTime;
                totalTurnAroundTime += shortest.turnAroundTime;
            }
        }

        float avgWaitingTime = totalWaitingTime / (float) n;
        float avgTurnAroundTime = totalTurnAroundTime / (float) n;
        System.out.println("SJF Preemptive Scheduling (SRTF):");
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnAroundTime);
        System.out.println();
    }

    // Round Robin (RR)
    public static void RoundRobin(List<Process> processes, int quantum) {
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;
        int currentTime = 0;

        Queue<Process> queue = new LinkedList<>();
        List<Process> pendingProcesses = new ArrayList<>(processes);

        while (!pendingProcesses.isEmpty() || !queue.isEmpty()) {
            while (!pendingProcesses.isEmpty() && pendingProcesses.get(0).arrivalTime <= currentTime) {
                queue.add(pendingProcesses.remove(0));
            }

            if (!queue.isEmpty()) {
                Process p = queue.poll();
                if (currentTime < p.arrivalTime) {
                    currentTime = p.arrivalTime;
                }

                if (p.burstTime <= quantum) {
                    currentTime += p.burstTime;
                    p.waitingTime = currentTime - p.arrivalTime - p.burstTime;
                    p.turnAroundTime = p.waitingTime + p.burstTime;
                    totalWaitingTime += p.waitingTime;
                    totalTurnAroundTime += p.turnAroundTime;
                } else {
                    p.burstTime -= quantum;
                    currentTime += quantum;
                    queue.add(p);
                }
            } else {
                currentTime++;
            }
        }

        System.out.println("Round Robin Scheduling (Quantum = " + quantum + "):");
        System.out.println("Average Waiting Time: " + (totalWaitingTime / (float) processes.size()));
        System.out.println("Average Turnaround Time: " + (totalTurnAroundTime / (float) processes.size()));
        System.out.println();
    }
}
