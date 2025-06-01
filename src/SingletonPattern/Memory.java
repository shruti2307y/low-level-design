package SingletonPattern;

public class Memory {
    private Memory() {};
    private static final Memory memoryInstance = new Memory();
    public static Memory getMemoryInstance() {
        return memoryInstance;
    }

    public void showCapacity() {
        System.out.println("Capacity: 10GB");
    }

}
