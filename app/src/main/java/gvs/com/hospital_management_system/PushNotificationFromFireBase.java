package gvs.com.hospital_management_system;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushNotificationFromFireBase extends FirebaseMessagingService {
    String title,body;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getNotification() != null) {
             title = remoteMessage.getNotification().getTitle();
             body = remoteMessage.getNotification().getBody();
             displayNotifications();
            Log.d("notification",title+body);
        }
    }
    void displayNotifications() {
        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(this,Emergency.CHANNEL_ID)
                .setSmallIcon(R.drawable.common_full_open_on_phone)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,mBuilder.build());
    }
}
