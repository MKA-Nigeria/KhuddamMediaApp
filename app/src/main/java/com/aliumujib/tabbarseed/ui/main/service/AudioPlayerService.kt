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
        stateService = NotifConstants.STATE_SERVICE.NOT_PLAYING
    }

    override fun onDestroy() {
        stateService = NotifConstants.STATE_SERVICE.NOT_PLAYING
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
            NotifConstants.ACTION.START_ACTION -> {
                Log.d(TAG, "Received user starts foreground intent")
                val playableParcelable: PlayableParcelable? = intent.getParcelableExtra(PLAYABLE)
                if(playableParcelable!=null){
                    startForeground(NotifConstants.NOTIFICATION_ID_FOREGROUND_SERVICE, prepareNotification())
                    playAudio(playableParcelable)
                }
            }
            NotifConstants.ACTION.STOP_ACTION -> {
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

    // its connected, so change the notification text
    private fun playAudio(playableParcelable: PlayableParcelable) {
        // after 10 seconds its connected
        stateService = NotifConstants.STATE_SERVICE.PLAYING
        startForeground(NotifConstants.NOTIFICATION_ID_FOREGROUND_SERVICE, prepareNotification())

        simpleExoPlayer.playWhenReady = true

        mediaSource = providesMediaSource(Uri.parse("https://api.soundcloud.com/tracks/${playableParcelable.id}/stream?client_id=${BuildConfig.SOUNDCLOUD_API_KEY}"))
        simpleExoPlayer.prepare(mediaSource, true, false)
    }


    fun providesMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultHttpDataSourceFactory("MKANMEDIA", BANDWIDTH_METER)
        val extractorsFactory = DefaultExtractorsFactory()
        return ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null)
    }


    private fun prepareNotification(): Notification {
        // handle build version above android oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && mNotificationManager!!.getNotificationChannel(FOREGROUND_CHANNEL_ID) == null) {
            val name = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(FOREGROUND_CHANNEL_ID, name, importance)
            channel.enableVibration(false)
            mNotificationManager!!.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.action = NotifConstants.ACTION.MAIN_ACTION
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
        stopIntent.action = NotifConstants.ACTION.STOP_ACTION
        val pendingStopIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val remoteViews = RemoteViews(packageName, R.layout.notification)
        remoteViews.setOnClickPendingIntent(R.id.btn_stop, pendingStopIntent)

        // if it is connected
        when (stateService) {
            NotifConstants.STATE_SERVICE.NOT_PLAYING -> remoteViews.setTextViewText(R.id.tv_state, "DISCONNECTED")
            NotifConstants.STATE_SERVICE.PLAYING -> remoteViews.setTextViewText(R.id.tv_state, "PLAYING")
        }

        // notification builder
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

        private var stateService = NotifConstants.STATE_SERVICE.NOT_PLAYING


        fun startService(context: Context, playableParcelable: PlayableParcelable) {
            val startIntent = Intent(context, AudioPlayerService::class.java)
            startIntent.action = NotifConstants.ACTION.START_ACTION
            startIntent.putExtra(PLAYABLE, playableParcelable)
            context.startService(startIntent)
        }
    }
}


