package com.aliumujib.tabbarseed.ui.main.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews

import com.aliumujib.tabbarseed.R

import androidx.core.app.NotificationCompat
import com.aliumujib.tabbarseed.BuildConfig
import com.aliumujib.tabbarseed.data.model.PlayableParcelable
import com.aliumujib.tabbarseed.ui.main.MainActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class AudioPlayerService : Service() {

    private var mNotificationManager: NotificationManager? = null

    var BANDWIDTH_METER: DefaultBandwidthMeter = DefaultBandwidthMeter()

    @Inject
    lateinit var simpleExoPlayer: ExoPlayer

    lateinit var mediaSource: MediaSource


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        stateService = PlaybackConstants.STATE_SERVICE.NOT_PLAYING
    }

    override fun onDestroy() {
        stateService = PlaybackConstants.STATE_SERVICE.NOT_PLAYING
        simpleExoPlayer.stop(true)
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            stopForeground(true)
            stopSelf()
            return Service.START_NOT_STICKY
        }

        // if user starts the service
        when (intent.action) {
            PlaybackConstants.ACTION.START_ACTION -> {
                Log.d(TAG, "Received user starts foreground intent")
                val playableParcelable: PlayableParcelable? = intent.getParcelableExtra(PLAYABLE)
                if (playableParcelable != null) {
                    //startForeground(PlaybackConstants.NOTIFICATION_ID_FOREGROUND_SERVICE, prepareNotification(playableParcelable))
                    playAudio(playableParcelable)
                }
            }
            PlaybackConstants.ACTION.PAUSE_ACTION -> {
                Log.d(TAG, "Received user stopped foreground intent")
                simpleExoPlayer.playWhenReady = false
            }
            PlaybackConstants.ACTION.CONTINUE_ACTION -> {
                Log.d(TAG, "Received user stopped foreground intent")
                simpleExoPlayer.playWhenReady = true
            }
            PlaybackConstants.ACTION.STOP_ACTION -> {
                Log.d(TAG, "Received user stopped foreground intent")
                stopForeground(true)
                stopSelf()
            }
            else -> {
                stopForeground(true)
                stopSelf()
            }
        }

        return Service.START_NOT_STICKY
    }

    // its connected, so change the item_notification text
    private fun playAudio(playableParcelable: PlayableParcelable) {
        // after 10 seconds its connected
        stateService = PlaybackConstants.STATE_SERVICE.PLAYING
        startForeground(PlaybackConstants.NOTIFICATION_ID_FOREGROUND_SERVICE, prepareNotification(playableParcelable))

        simpleExoPlayer.playWhenReady = true

        mediaSource = providesMediaSource(Uri.parse("https://api.soundcloud.com/tracks/${playableParcelable.id}/stream?client_id=${BuildConfig.SOUNDCLOUD_API_KEY}"))
        simpleExoPlayer.prepare(mediaSource, true, false)
    }


    fun providesMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultHttpDataSourceFactory("MKANMEDIA", BANDWIDTH_METER)
        val extractorsFactory = DefaultExtractorsFactory()
        return ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null)
    }


    private fun prepareNotification(playableParcelable: PlayableParcelable?): Notification {
        // handle build version above android oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && mNotificationManager!!.getNotificationChannel(FOREGROUND_CHANNEL_ID) == null) {
            val name = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(FOREGROUND_CHANNEL_ID, name, importance)
            channel.enableVibration(false)
            mNotificationManager!!.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.action = PlaybackConstants.ACTION.MAIN_ACTION
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        // if min sdk goes below honeycomb
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else {
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }*/

        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        // make a stop intent
        val stopIntent = Intent(this, AudioPlayerService::class.java)
        stopIntent.action = PlaybackConstants.ACTION.STOP_ACTION
        val remoteViews = RemoteViews(packageName, R.layout.item_notification)

        //val pendingStopIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //remoteViews.setOnClickPendingIntent(R.id.btn_stop, pendingStopIntent)

        remoteViews.setTextViewText(R.id.textView6, playableParcelable?.name)
        remoteViews.setTextViewText(R.id.description, playableParcelable?.description)

//        // if it is connected
//        when (stateService) {
//            PlaybackConstants.STATE_SERVICE.NOT_PLAYING -> remoteViews.setTextViewText(R.id.tv_state, "DISCONNECTED")
//            PlaybackConstants.STATE_SERVICE.PLAYING -> remoteViews.setTextViewText(R.id.tv_state, "PLAYING")
//        }

        // item_notification builder
        val notificationBuilder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder = NotificationCompat.Builder(this, FOREGROUND_CHANNEL_ID)
        } else {
            notificationBuilder = NotificationCompat.Builder(this)
        }
        notificationBuilder
                .setContent(remoteViews)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setOnlyAlertOnce(true)
                .setOngoing(true)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        }

        return notificationBuilder.build()
    }

    companion object {

        private val TAG = AudioPlayerService::class.java.simpleName
        private val FOREGROUND_CHANNEL_ID = "foreground_channel_id"

        private val PLAYABLE = "PLAYABLE"

        private var stateService = PlaybackConstants.STATE_SERVICE.NOT_PLAYING


        fun startService(context: Context, playableParcelable: PlayableParcelable) {
            val startIntent = Intent(context, AudioPlayerService::class.java)
            startIntent.action = PlaybackConstants.ACTION.START_ACTION
            startIntent.putExtra(PLAYABLE, playableParcelable)
            context.startService(startIntent)
        }

        fun pausePlayback(context: Context) {
            val intent = Intent(context, AudioPlayerService::class.java)
            intent.action = PlaybackConstants.ACTION.PAUSE_ACTION
            context.startService(intent)
        }

        fun continuePlayback(context: Context) {
            val intent = Intent(context, AudioPlayerService::class.java)
            intent.action = PlaybackConstants.ACTION.CONTINUE_ACTION
            context.startService(intent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, AudioPlayerService::class.java)
            stopIntent.action = PlaybackConstants.ACTION.STOP_ACTION
            context.startService(stopIntent)
        }
    }
}


