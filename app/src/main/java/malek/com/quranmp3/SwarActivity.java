package malek.com.quranmp3;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import malek.com.quranmp3.models.Attachment;
import malek.com.quranmp3.models.SawrContainer;
import malek.com.quranmp3.tools.NotifcationTools;
import malek.com.quranmp3.tools.RetrofitSingleton;

public class SwarActivity extends ListViewActivity implements AudioManager.OnAudioFocusChangeListener, MediaPlayer.OnPreparedListener, DialogInterface.OnDismissListener, SeekBar.OnSeekBarChangeListener, View.OnClickListener, MediaPlayer.OnErrorListener, SearchView.OnQueryTextListener {

    public final static int REQUEST_CODE_SWAR = 10;
    public final static String PREC_NOTIF = "prec";
    public final static String NEXT_NOTIF = "next";
    public final static String PLAY_NOTIF = "play";
    public static final String TAG = SwarActivity.class.getSimpleName();
    private int positionSelected;
    private Dialog audioDialog;
    private TextView titreAudio;
    private TextView timerAudio;
    private SeekBar seekBarAudio;
    private MediaPlayer mediaPlayer;
    private ImageView playBtn;
    private CountDownTimer countDownTimerPlayer;
    private BroadcastReceiver notifcationReciver = null;
    private boolean prepared;
    private NotifcationTools notifcationTools = null;
    private AudioManager mAudioManager;
    private List<Attachment> swars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

    }


    public void setUpNotifactionBrodcastReciver() {
        if (notifcationReciver == null) {
            notifcationReciver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    switch (intent.getAction()) {
                        case PREC_NOTIF:
                            precAudio();
                            break;
                        case NEXT_NOTIF:
                            nextAudio();
                            break;
                        case PLAY_NOTIF:
                            playStopAudio();
                            break;
                    }
                }
            };
            IntentFilter filter = new IntentFilter();
            filter.addAction(PREC_NOTIF);
            filter.addAction(NEXT_NOTIF);
            filter.addAction(PLAY_NOTIF);
            registerReceiver(notifcationReciver, filter);
        }
    }

    public void removeBrodcastReciver() {
        if (notifcationReciver != null) {
            unregisterReceiver(notifcationReciver);
            notifcationReciver = null;
        }
        if (notifcationTools != null) {
            notifcationTools.clearNotifcation();
        }
    }

    @Override
    public void finish() {
        super.finish();
        removeBrodcastReciver();
    }

    @Override
    public void setData() {
        getId();
        if (id != null) {
            RetrofitSingleton.getInstance().getApiService().getSawrContainer(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Function<SawrContainer, List<Attachment>>() {
                        @Override
                        public List<Attachment> apply(@NonNull SawrContainer sawrContainer) throws Exception {
                            return sawrContainer.getAttachments();
                        }
                    }).flatMap(new Function<List<Attachment>, ObservableSource<Attachment>>() {
                @Override
                public ObservableSource<Attachment> apply(@NonNull List<Attachment> attachments) throws Exception {
                    return Observable.fromIterable(attachments);
                }
            }).safeSubscribe(new Observer<Attachment>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Attachment attachment) {
                    Log.e(TAG, attachment.getTitle());
                    quraAdapter.addItem(attachment);
                    swars.add(attachment);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, e.toString());
                }

                @Override
                public void onComplete() {

                }
            });
        }

    }

    @Override
    public void onClickItem(int position) {
        listViewQuray.setEnabled(false);
        positionSelected = position;
        audioDialog = new Dialog(this);
        audioDialog.setContentView(R.layout.dialog_player);
        audioDialog.getWindow().setGravity(Gravity.BOTTOM);
        audioDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        //titre de l'audio
        titreAudio = (TextView) audioDialog.findViewById(R.id.titreAudio);
        timerAudio = (TextView) audioDialog.findViewById(R.id.timerAudio);
        titreAudio.setText(quraAdapter.getApiModels().get(position).getTitle());
        // seekBar de l'audio
        seekBarAudio = (SeekBar) audioDialog.findViewById(R.id.seekBarAudio);
        audioDialog.setCancelable(false);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            Attachment attachment = (Attachment) quraAdapter.getApiModels().get(position);
            Log.e("attachement", attachment.toString());
            mediaPlayer.setDataSource(attachment.getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //progress Dialog pour telecharger l'audio

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        audioDialog.setOnDismissListener(this);
        seekBarAudio.setOnSeekBarChangeListener(this);
        // controle du lecteur
        playBtn = (ImageView) audioDialog.findViewById(R.id.play);
        playBtn.setOnClickListener(this);
        audioDialog.findViewById(R.id.precedent).setOnClickListener(this);
        audioDialog.findViewById(R.id.suivant).setOnClickListener(this);
        audioDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    dialogInterface.dismiss();
                }
                return false;
            }
        });


    }

    public void formatageTimer(long postion) {
        long hour = TimeUnit.MILLISECONDS.toHours(postion);
        long min = TimeUnit.MILLISECONDS.toMinutes(postion - hour * 60 * 60 * 1000);
        long sec = TimeUnit.MILLISECONDS.toSeconds(postion - hour * 60 * 60 * 1000 - min * 60 * 1000);
        timerAudio.setText(String.format(Locale.FRANCE, "%02d:%02d:%02d", hour, min, sec));
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        prepared = true;
        if (notifcationTools == null) {
            notifcationTools = new NotifcationTools(getApplicationContext());
        }
        notifcationTools.createNotif(titreAudio.getText().toString());
        setUpNotifactionBrodcastReciver();
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (runing) {
            audioDialog.show();
        }

        setUpDialog(mediaPlayer.getDuration());
        mediaPlayer.start();
        seekBarAudio.setMax(mediaPlayer.getDuration());

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (progressDialog != null && prepared) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            if (!audioDialog.isShowing()) {
                audioDialog.show();
            }
        }

    }

    private void setUpDialog(long duree) {
        Log.d("setUp", "setUp");
        //        seekBarAudio.setOnSeekBarChangeListener(this);
        countDownTimerPlayer = new CountDownTimer(duree, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                seekBarAudio.setProgress(mediaPlayer.getCurrentPosition());
            }

            @Override
            public void onFinish() {
                seekBarAudio.setProgress(mediaPlayer.getCurrentPosition());
                positionSelected = positionSelected + 1;
                passageAudio();
            }
        }.start();
    }

    public void passageAudio() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        countDownTimerPlayer.cancel();
        seekBarAudio.setProgress(0);
        formatageTimer(0);
        if (positionSelected >= quraAdapter.getApiModels().size())
            positionSelected = 0;
        if (positionSelected < 0)
            positionSelected = quraAdapter.getApiModels().size() - 1;
        if (quraAdapter.getApiModels().get(positionSelected) != null) {
            titreAudio.setText(quraAdapter.getApiModels().get(positionSelected).getTitle());
            mediaPlayer.reset();
            try {

                mediaPlayer.setDataSource(this, Uri.parse(((Attachment) quraAdapter.getApiModels().get(positionSelected)).getUrl()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            prepared = false;
            mediaPlayer.prepareAsync();

        }

    }

    public void realseMediaPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            countDownTimerPlayer.cancel();
            mediaPlayer.stop();
            mediaPlayer.release();
            prepared = false;
            listViewQuray.setEnabled(true);
        }
        removeBrodcastReciver();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        realseMediaPlayer();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        formatageTimer(mediaPlayer.getCurrentPosition());
        if (fromUser) {
            mediaPlayer.seekTo(progress);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                playStopAudio();
                break;
            case R.id.suivant:
                nextAudio();
                break;
            case R.id.precedent:
                precAudio();
                break;
        }
    }

    public void stopAudio() {
        playBtn.setImageResource(R.drawable.play);
        mediaPlayer.pause();
        countDownTimerPlayer.cancel();
        notifcationTools.updateNotifIcon(R.id.playNotif, R.drawable.ic_play_arrow_black_24dp);
    }

    public void playStopAudio() {
        if (mediaPlayer.isPlaying()) {
            stopAudio();
        } else {
            playBtn.setImageResource(R.drawable.pause);
            mediaPlayer.start();
            setUpDialog(mediaPlayer.getDuration());
            notifcationTools.updateNotifIcon(R.id.playNotif, R.drawable.ic_pause_black_24dp);


        }
    }

    public void precAudio() {
        positionSelected = positionSelected - 1;
        passageAudio();
    }

    public void nextAudio() {
        positionSelected = positionSelected + 1;
        passageAudio();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) searchMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (runing && progressDialog.isShowing()) {
            progressDialog.dismiss();
            listViewQuray.setEnabled(true);
        }

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {

        if (newText.length() > 0) {
            Observable.fromIterable(swars)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Predicate<Attachment>() {
                        @Override
                        public boolean test(@NonNull Attachment apiModel) throws Exception {
                            String testString = apiModel.getTitle().replace("سورة ", "").substring(0, newText.length());
                            if (testString.contains("أ")) {
                                testString = testString.replace("أ", "ا");
                            }
                            return testString.equals(newText);
                        }
                    }).safeSubscribe(new Observer<Attachment>() {
                @Override
                public void onSubscribe(Disposable d) {
                    quraAdapter.clearAdapter();
                }

                @Override
                public void onNext(Attachment apiModel) {
                    quraAdapter.addItem(apiModel);

                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onComplete() {
                }
            });


        } else {
            quraAdapter.clearAdapter();
            Observable.fromIterable(swars).safeSubscribe(new Observer<Attachment>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Attachment attachment) {
                    quraAdapter.addItem(attachment);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, e.toString());
                }

                @Override
                public void onComplete() {

                }
            });
        }
        return false;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        stopAudio();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAudioManager.abandonAudioFocus(this);
    }
}
