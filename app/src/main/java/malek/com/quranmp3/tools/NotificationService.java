package malek.com.quranmp3.tools;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotificationService extends IntentService {

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("onTaskRemoved", "true");
        new NotifcationTools(this).clearNotifcation();

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("onTaskRemoved", "false");

    }
}
