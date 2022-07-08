package effective.band.compose.drawer_modules.timber

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_STREAM
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun LogsAlertDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Scaffold(topBar = {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(text = "Logs", style = typography.h5)
            }
        }, bottomBar = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = onDismiss) {
                    Text(text = "Dismiss")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = { share(context = context) }) {
                    Text(text = "Share")
                }
            }
        }, modifier = Modifier.fillMaxHeight(.8f)) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                LazyColumn(content = {
                    items(LumberYard.getEntries()) { entry ->
                        LogItem(entry)
                    }
                })
            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun LogItem(entry: Entry) {
    val strokeWidth = 3.dp
    Column(
        modifier = Modifier
            .background(Color(0xffeeeeee))
            .startBorder(Border(strokeWidth, entry.displayColor()))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(color = entry.displayColor())
            ) {
                Text(
                    text = entry.displayLevel(),
                    color = Color.White,
                    modifier = Modifier.padding(8.dp),
                    fontSize = TextUnit(10f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            entry.tag?.let {
                Text(
                    text = it, fontSize = TextUnit(10f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1, color = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(text = entry.date, fontSize = TextUnit(10f, TextUnitType.Sp), color = Color.Black)
        }
        Text(
            text = entry.message,
            modifier = Modifier.padding(strokeWidth + 8.dp),
            fontSize = TextUnit(12f, TextUnitType.Sp),
            color = Color.Black
        )
    }
}

data class Border(val strokeWidth: Dp, val color: Color)

@Stable
fun Modifier.startBorder(
    start: Border? = null,
    top: Border? = null,
    bottom: Border? = null,
) =
    drawBehind {
        start?.let {
            drawStartBorder(it, shareTop = top != null, shareBottom = bottom != null)
        }
    }

private fun DrawScope.drawStartBorder(
    border: Border,
    shareTop: Boolean = true,
    shareBottom: Boolean = true
) {
    val strokeWidthPx = border.strokeWidth.toPx()
    if (strokeWidthPx == 0f) return
    drawPath(
        Path().apply {
            moveTo(0f, 0f)
            lineTo(strokeWidthPx, if (shareTop) strokeWidthPx else 0f)
            val height = size.height
            lineTo(strokeWidthPx, if (shareBottom) height - strokeWidthPx else height)
            lineTo(0f, height)
            close()
        },
        color = border.color
    )
}

private fun share(context: Context) {
    LumberYard.save { file: File? ->
        // We couldn't produce a logs file for some reason. Give up.
        if (file == null) return@save

        val uri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.drawer_modules.timber_module.fileprovider",
            file
        )

        val sendIntent = Intent(ACTION_SEND)
        sendIntent.type = "text/plain"
        sendIntent.putExtra(EXTRA_STREAM, uri)
        sendIntent.addFlags(FLAG_GRANT_READ_URI_PERMISSION)

        val handlers: List<ResolveInfo> =
            context.packageManager.queryIntentActivities(sendIntent, 0)
        // Give up if our send intent cannot be handled.
        if (handlers.isEmpty()) return@save

        context.startActivity(Intent.createChooser(sendIntent, null))
    }
}
