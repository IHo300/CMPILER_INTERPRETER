package Notifications;

public interface NotificationListener {
    public abstract void onNotify(String notificationString, Parameters params);
}
