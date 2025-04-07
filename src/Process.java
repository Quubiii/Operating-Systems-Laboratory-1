class Process {
    int number;           // Process number
    int burstTime;        // Burst time (CPU time required)
    int arrivalTime;      // Arrival time
    int waitingTime = 0;  // Waiting time
    int turnAroundTime;   // Turnaround time
    int remainingTime;    // Remaining time for preemptive scheduling

    public Process(int number, int burstTime, int arrivalTime) {
        this.number = number;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.remainingTime = burstTime; // Initialize remaining time
    }
}
