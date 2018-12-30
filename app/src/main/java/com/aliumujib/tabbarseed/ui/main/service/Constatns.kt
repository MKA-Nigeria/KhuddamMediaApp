package com.aliumujib.tabbarseed.ui.main.service

public object PlaybackConstants {
    const  val NOTIFICATION_ID_FOREGROUND_SERVICE = 8466503

    object ACTION {
        const val MAIN_ACTION = "test.action.main"
        const val START_ACTION = "test.action.start"
        const val STOP_ACTION = "test.action.stop"
        const val CONTINUE_ACTION = "test.action.continue"
        const val PAUSE_ACTION = "test.action.pause"
    }

    object STATE_SERVICE {
        const val PLAYING = 10
        const val NOT_PLAYING = 1
    }

}