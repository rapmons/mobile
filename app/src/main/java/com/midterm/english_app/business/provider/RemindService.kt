//package com.midterm.english_app.business.provider
//
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import androidx.core.app.JobIntentService
//import androidx.work.*
//import com.midterm.english_app.business.component.DaggerServiceComponent
//import com.midterm.english_app.business.component.ServiceComponent
//import com.midterm.english_app.business.module.ApplicationContextModule
//import com.midterm.english_app.business.module.DatabaseModule
//import com.midterm.english_app.business.module.NotificationModule
//import com.midterm.english_app.business.module.RemindModule
//import java.util.concurrent.TimeUnit
//
//class RemindService : JobIntentService() {
//
//    companion object {
//        private val job_ID = 14121412
//        val uniqueWorkName = "english_vocabulary_remind"
//
//        lateinit var serviceComponent: ServiceComponent
//       private lateinit var serviceComponentModule: DaggerServiceComponent.Builder
//        private fun serviceComponentInitialized() = ::serviceComponent.isInitialized
//        private fun serviceComponentModuleInitialized() = ::serviceComponentModule.isInitialized
//
//        fun initDI(context: Context) {
//            if (!serviceComponentModuleInitialized())
//                serviceComponentModule = DaggerServiceComponent.builder()
//                    .applicationContextModule(ApplicationContextModule(context))
//                    .notificationModule(NotificationModule())
//                    .databaseModule(DatabaseModule())
//                    .remindModule(RemindModule())
//
//            if (!serviceComponentInitialized())
//                serviceComponent = serviceComponentModule.build()
//        }
//
//        fun enqueue(context: Context, intent: Intent) {
//            initDI(context)
//            enqueueWork(context, RemindService::class.java, job_ID, intent)
//        }
//    }
//
//    override fun onHandleWork(intent: Intent) {
//        Log.d("onHandleWork", "onHandleWork")
//        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(RemindWorker::class.java)
//            .addTag("english_vocabulary_remind")
//            .setInitialDelay(15, TimeUnit.MINUTES)
//            .build()
//
//        WorkManager.getInstance(applicationContext).enqueueUniqueWork(RemindService.uniqueWorkName, ExistingWorkPolicy.REPLACE, oneTimeWorkRequest)
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        Log.d("onStartCommand", "onStartCommand")
//        return super.onStartCommand(intent, flags, startId)
//    }
//
//    override fun onTaskRemoved(rootIntent: Intent?) {
//        Log.d("onTaskRemoved", "onTaskRemoved")
//        enqueue(applicationContext, Intent())
//        super.onTaskRemoved(rootIntent)
//    }
//
//    override fun onDestroy() {
//        Log.d("onDestroy", "onDestroy")
//        super.onDestroy()
//    }
//}