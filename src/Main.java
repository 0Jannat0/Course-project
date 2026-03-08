import java.util.*;

public class Main {

    static ArrayDeque<String> line = new ArrayDeque<>();
    static HashMap<String, Integer> arrival = new HashMap<>();

    static int currentTime = 0;
    static int servedCount = 0;
    static long totalWait = 0;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Cafeteria Line Manager — Commands:");
        help();

        while (true) {

            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            String[] parts = input.split(" ");
            String command = parts[0].toUpperCase();

            switch (command) {

                case "HELP":
                    help();
                    break;

                case "ARRIVE":
                    arrive(parts);
                    break;

                case "VIP_ARRIVE":
                    vipArrive(parts);
                    break;

                case "SERVE":
                    serve();
                    break;

                case "LEAVE":
                    leave(parts);
                    break;

                case "PEEK":
                    peek();
                    break;

                case "SIZE":
                    size();
                    break;

                case "PRINT":
                    printLine();
                    break;

                case "TICK":
                    tick(parts);
                    break;

                case "STATS":
                    stats();
                    break;

                case "EXIT":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Unknown command. Type HELP.");
            }
        }
    }

    static void help() {
        System.out.println("HELP");
        System.out.println("ARRIVE <name>");
        System.out.println("VIP_ARRIVE <name>");
        System.out.println("SERVE");
        System.out.println("LEAVE <name>");
        System.out.println("PEEK");
        System.out.println("SIZE");
        System.out.println("TICK <minutes>");
        System.out.println("STATS");
        System.out.println("PRINT");
        System.out.println("EXIT");
    }

    static boolean validName(String name) {
        return name != null && !name.isEmpty() && !name.contains(" ");
    }

    static void arrive(String[] parts) {

        if (parts.length != 2 || !validName(parts[1])) {
            System.out.println("Invalid name.");
            return;
        }

        String name = parts[1];

        if (arrival.containsKey(name)) {
            System.out.println("Name already in system");
            return;
        }

        line.addLast(name);
        arrival.put(name, currentTime);

        System.out.println(name + " arrived at time " + currentTime + ". Line size = " + line.size());
    }

    static void vipArrive(String[] parts) {

        if (parts.length != 2 || !validName(parts[1])) {
            System.out.println("Invalid name.");
            return;
        }

        String name = parts[1];

        if (arrival.containsKey(name)) {
            System.out.println("Name already in system");
            return;
        }

        line.addFirst(name);
        arrival.put(name, currentTime);

        System.out.println("VIP " + name + " arrived at time " + currentTime + " (front). Line size = " + line.size());
    }

    static void serve() {

        if (line.isEmpty()) {
            System.out.println("No one to serve.");
            return;
        }

        String name = line.removeFirst();
        int arrivalTime = arrival.get(name);

        int wait = currentTime - arrivalTime;

        servedCount++;
        totalWait += wait;

        arrival.remove(name);

        System.out.println("Served: " + name + " (waited " + wait + " min).");
    }

    static void leave(String[] parts) {

        if (parts.length != 2) {
            System.out.println("Invalid command.");
            return;
        }

        String name = parts[1];

        if (line.removeFirstOccurrence(name)) {

            arrival.remove(name);

            System.out.println(name + " left the line voluntarily. Line size = " + line.size());

        } else {

            System.out.println("Not found");
        }
    }

    static void peek() {

        if (line.isEmpty()) {

            System.out.println("Line is empty.");

        } else {

            System.out.println("Next: " + line.peekFirst());
        }
    }

    static void size() {

        System.out.println("Size: " + line.size());
    }

    static void printLine() {

        System.out.println("Line (front -> back): " + line);
    }

    static void tick(String[] parts) {

        if (parts.length != 2) {
            System.out.println("Invalid command.");
            return;
        }

        try {

            int minutes = Integer.parseInt(parts[1]);

            if (minutes < 0) {
                System.out.println("Invalid time.");
                return;
            }

            currentTime += minutes;

            System.out.println("Time advanced by " + minutes + " minutes. Current time = " + currentTime);

        } catch (NumberFormatException e) {

            System.out.println("Invalid number.");
        }
    }

    static void stats() {

        if (servedCount == 0) {

            System.out.println("Served count = 0, Avg wait = 0.00 min.");

            return;
        }

        double avg = (double) totalWait / servedCount;

        System.out.println(
                "Served count = " + servedCount +
                        ", Avg wait = " + avg + " min.");
    }
}