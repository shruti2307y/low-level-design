package SingletonPattern;

public class SingletonPatternDemo {
    public static void main(String[] args) {
        // Memory memoryTest = new Memory(); // Compile time Error - java: Memory() has private access in SingletonPattern.Memory
        Memory memory = Memory.getMemoryInstance();
        memory.showCapacity();
        Memory memory2 = Memory.getMemoryInstance();
        memory2.showCapacity();
    }
}
