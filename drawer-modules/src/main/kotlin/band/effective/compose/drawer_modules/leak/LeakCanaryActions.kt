package band.effective.compose.drawer_modules.leak

import android.content.Context
import android.content.Context.MODE_PRIVATE
import leakcanary.LeakCanary

object LeakCanaryActions {

    private const val SHARED_PREFS_NAME = "DebugDrawer_LeakCanary"
    private const val KEY_ENABLE_HEAP_DUMPS = "enableHeapDumps"

    fun setup(context: Context) {
        val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
        val enableHeapDumps = sharedPrefs.getBoolean(KEY_ENABLE_HEAP_DUMPS, true)
        LeakCanary.config = LeakCanary.config.copy(dumpHeap = enableHeapDumps)
    }

    fun onToggleDumpHeap(context: Context, checked: Boolean) {
        val sharedPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
        LeakCanary.config = LeakCanary.config.copy(dumpHeap = checked)
        sharedPrefs.edit().putBoolean(KEY_ENABLE_HEAP_DUMPS, checked).apply()
    }
}