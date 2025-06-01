package NotificationSystem;

import java.util.ArrayList;
import java.util.List;

interface INotification {
    String getContent();
}

class SimpleNotification implements INotification {
    private final String content;
    public SimpleNotification(String text) {
    this.content = text;
    }
    @Override
    public String getContent() {
        return this.content;
    }
}

abstract class INotificationDecorator implements INotification {
    INotification iNotification;
    public INotificationDecorator(INotification n) {
        this.iNotification = n;
    }
}

class TimeDecorator extends INotificationDecorator {

    public TimeDecorator(INotification n) {
        super(n);
    }

    @Override
    public String getContent() {
        return "01-06-2025 " + iNotification.getContent();
    }
}

interface IObserver {
    void update();
}
interface INotifier {
    void addSubscriber(IObserver observer);
    void removeSubscriber(IObserver observer);
    void notifySubscribers();

}

class NotificationNotifier implements INotifier {
    List<IObserver> subscribers = new ArrayList<IObserver>();
    INotification currNotification;
    @Override
    public void addSubscriber(IObserver observer) {
        this.subscribers.add(observer);
    }

    @Override
    public void removeSubscriber(IObserver observer) {
        this.subscribers.remove(observer);
    }

    @Override
    public void notifySubscribers() {
            for( IObserver sub:  subscribers) {
                sub.update();
            }
    }

    public void setNotification(INotification notification) {
        this.currNotification = notification;
        notifySubscribers();
    }
    public INotification getNotification() {
        return this.currNotification;
    }
}

class Logger implements IObserver {
    private final NotificationNotifier notificationNotifier;

    public Logger(NotificationNotifier notifier){
        this.notificationNotifier = notifier;
    }
    @Override
    public void update() {
        System.out.println("Logging current notification: " + this.notificationNotifier.getNotification().getContent());
    }
}

interface INotificationStrategy {
    public void sendNotification(String content);

}

class EmailNotificationStrategy implements INotificationStrategy {
   private final String email;
    public EmailNotificationStrategy(String e) {
        this.email = e;
    }
    @Override
    public void sendNotification(String content) {
        System.out.println("Sending email notification with content: "+content+ " from "+ this.email);
    }
}

class NotificationEngine implements IObserver {
    List<INotificationStrategy> strategies = new ArrayList<INotificationStrategy>();
    NotificationNotifier notificationNotifier;
    public NotificationEngine(NotificationNotifier notifier) {
        this.notificationNotifier = notifier;
    }
    @Override
    public void update() {
        for(INotificationStrategy s: strategies) {
            s.sendNotification(this.notificationNotifier.getNotification().getContent());
        }
    }

    public void addNotificationStrategy(INotificationStrategy strategy) {
        this.strategies.add(strategy);

    }
}

class NotificationService {
    NotificationNotifier notificationNotifier;
    private static NotificationService instance;

    private NotificationService() {
        notificationNotifier = new NotificationNotifier();
    }
    public static NotificationService getInstance(){
        if(instance == null) {
            instance = new NotificationService();
        }
        return instance;

    }
    public NotificationNotifier getNotificationNotifier() {
        return notificationNotifier;
    }

    public void sendNotification(INotification notification) {
        notificationNotifier.setNotification(notification);
    }

}

public class NotificationSystem {
    public static void main(String[] args) {
        NotificationService notificationService = NotificationService.getInstance();
        NotificationNotifier notifier = notificationService.getNotificationNotifier();
        Logger logger  = new Logger(notifier);
        NotificationEngine notificationEngine = new NotificationEngine(notifier);

        notifier.addSubscriber(logger);
        notifier.addSubscriber(notificationEngine);

        EmailNotificationStrategy emailNotificationStrategy = new EmailNotificationStrategy("shrutiyadav");

        notificationEngine.addNotificationStrategy(emailNotificationStrategy);

        INotification notification = new SimpleNotification("notification system working");
        notification = new TimeDecorator(notification);

        notificationService.sendNotification(notification);

    }
}



