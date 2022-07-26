package effective.band.compose.drawer_modules.timber

import androidx.core.content.FileProvider


/**
 * This class must exist in order to avoid conflicts with consuming applications defining their own
 * FileProviders. See here for more information:
 * https://commonsware.com/blog/2017/06/27/fileprovider-libraries.html
 */
class DebugDrawerTimberFileProvider : FileProvider()