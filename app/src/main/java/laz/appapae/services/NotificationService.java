package laz.appapae.services;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import laz.appapae.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {
    public NotificationService() {
        FirebaseMessaging.getInstance().subscribeToTopic("new-partner");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(false)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setSmallIcon(R.drawable.logo_apae)
                .setContentText(remoteMessage.getData().get("message"));

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}