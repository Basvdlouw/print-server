package printserver.server;

public class PrintServer {
    public static void print(String filename) {
        System.out.printf("Printing file: %s ...%n", filename);
    }

    public static void queue() {
        System.out.println("Showing the queue of print jobs ...");
    }

    public static void topQueue(int job) {
        System.out.printf("Putting job: %s at the top of the queue ...%n", job);
    }

    public static void start() {
        System.out.println("Starting the print server...");
    }

    public static void stop() {
        System.out.println("Stopping the print server...");
    }

    public static void reset() {
        System.out.println("Stopping the print server, erasing the print queue, and restarting the print server ...");
    }

    public static void status() {
        System.out.println("Showing the status of the printer ...");
    }

    public static void readConfig(String parameter)  {
        System.out.printf("Showing the value of the parameter: %s ...%n", parameter);
    }

    public static void setConfig(String parameter, String value)  {
        System.out.printf("Setting the value of the parameter: %s to %s ...%n", parameter, value);
    }
}
