package CommandPattern;

interface ICommand {
    public void execute();
    public void undo();
}
class Light {
    public void on() {
        System.out.println("Light on");
    }

    public Light() {

    }

    public void off() {
        System.out.println("Light off");
    }
}
class LightCommand implements ICommand {
    Light light  = new Light();
    @Override
    public void execute() {
        this.light.on();
    }

    @Override
    public void undo() {
       this.light.off();
    }
}

class Remote {
    ICommand command;
    boolean isPressed = false;
    public Remote(ICommand cmd) {
        this.command  = cmd;
    }
    public void press() {
       if( !isPressed ) {
           isPressed = true;
           this.command.execute();
       }
       else this.command.undo();
    }
}

public class CommandPattern{
    public static void main(String[] args) {
        ICommand currCommand = new LightCommand();
        Remote remote = new Remote(currCommand);
        remote.press();
        remote.press();
    }
}