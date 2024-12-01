package com.arziman_off.learnservices

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService


class MyJobIntentService : JobIntentService() {


    override fun onCreate() {
        super.onCreate()
        log("onCreate()")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy()")
    }

    override fun onHandleWork(intent: Intent) {
        log("onHandleIntent()")

        val page = intent.getIntExtra(PAGE, 0)

        for (i in 0..5) {
            Thread.sleep(1000)
            log("Timer $i page: $page")
        }
    }

    private fun log(msg: String) {
        Log.d("SERVICE_TAG", "MyJobIntentService $msg")
    }

    companion object {
        private const val PAGE = "page"
        private const val JOB_ID = 1

        private fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyJobIntentService::class.java).apply {
                putExtra(PAGE, page)
            }
        }

        fun enqueue(context: Context, page: Int) {
            enqueueWork(
                context,
                MyJobIntentService::class.java,
                JOB_ID,
                newIntent(context, page)
            )
        }
    }
}
