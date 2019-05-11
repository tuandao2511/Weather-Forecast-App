package com.example.weatherforecastapp.utilities

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton


class AppExecutors (
    val diskIO : DiskIOThreadExecutor = DiskIOThreadExecutor(),
    val mainThread: MainThreadExecutor = MainThreadExecutor()
)