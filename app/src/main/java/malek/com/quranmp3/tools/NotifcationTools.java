package malek.com.quranmp3.tools;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import malek.com.quranmp3.NotifcationActivity;
import malek.com.quranmp3.R;
import malek.com.quranmp3.SwarActivity;

public class NotifcationTools {
    private RemoteViews notifView;
    private NotificationCompat.Builder builder;
    private NotificationManager mNotificationManager;
    private Context context;

    public NotifcationTools(Context context) {
        this.context = context;
    }

    public void createNotif(String nameSwar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Intent notifSerciveIntent = new Intent(context, NotificationService.class);
            context.startService(notifSerciveIntent);
            Intent intent = new Intent(context, NotifcationActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    10,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent precIntent = PendingIntent.getBroadcast(context, 3, new Intent(SwarActivity.PREC_NOTIF), PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent nextIntent = PendingIntent.getBroadcast(context, 4, new Intent(SwarActivity.NEXT_NOTIF), PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent playtIntent = PendingIntent.getBroadcast(context, 5, new Intent(SwarActivity.PLAY_NOTIF), PendingIntent.FLAG_UPDATE_CURRENT);
            notifView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
            notifView.setTextViewText(R.id.titleSwar, nameSwar);
            notifView.setOnClickPendingIntent(R.id.precNotif, precIntent);
            notifView.setOnClickPendingIntent(R.id.nextNotif, nextIntent);
            notifView.setOnClickPendingIntent(R.id.playNotif, playtIntent);
            builder = new NotificationCompat.Builder(context);
            builder.setContent(notifView);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(false);
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(10, builder.build());

        }

    }

    public void clearNotifcation() {
        mNotificationManager.cancel(10);

    }

    public void updateNotifIcon(int view, int icon) {
        if (notifView != null && builder != null) {
            notifView.setImageViewResource(view, icon);
            mNotificationManager.notify(10, builder.build());

        }
    }
}
