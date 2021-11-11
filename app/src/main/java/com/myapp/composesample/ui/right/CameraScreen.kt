package com.myapp.composesample.ui.right

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.myapp.composesample.R

/**
 * カメラ撮影画面
 *
 * @param onImageCaptured 画像キャプチャ
 * @param onError エラー処理
 */
@Composable
fun CameraView(onImageCaptured: (Uri) -> Unit, onError: (ImageCaptureException) -> Unit) {

    val context = LocalContext.current
    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder().build()
    }

    CameraPreviewView(
        imageCapture
    ) { imageCapture.takePicture(context, onImageCaptured, onError) }
}

/**
 * カメラプレビュー
 *
 * @param imageCapture
 * @param cameraUIAction
 */
@SuppressLint("RestrictedApi")
@Composable
private fun CameraPreviewView(
    imageCapture: ImageCapture,
    cameraUIAction: () -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    val previewView = remember { PreviewView(context) }
    LaunchedEffect(CameraSelector.LENS_FACING_BACK) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize()) {

        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom
        ) {
            CameraBottomBar(cameraUIAction)
        }

    }
}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}


/**
 * カメラBottomBarのコンテンツ
 *
 * @param cameraUIAction クリックアクション
 */
@Composable
private fun CameraBottomBar(cameraUIAction: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CameraBottomItem(
            Icons.Sharp.Add,
            modifier= Modifier
                .size(64.dp)
                .padding(1.dp)
                .border(1.dp, Color.White, CircleShape),
            onClick = { cameraUIAction() }
        )
    }
}

/**
 * アイコンボタン
 *
 * @param imageVector
 * @param modifier
 * @param onClick
 */
@Composable
private fun CameraBottomItem(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector,
            contentDescription = null,
            modifier = modifier,
            tint = Color.White
        )
    }
}


/**
 * ImageCaptureユースケースの拡張関数
 */
private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
private const val PHOTO_EXTENSION = ".jpg"


/**
 * 写真保存機能
 *
 * @param context
 * @param onImageCaptured
 * @param onError
 */
private fun ImageCapture.takePicture(
    context: Context,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    // 画像ファイル生成
//    val outputDirectory = context.getOutputDirectory()
    val outputDirectory =  context.filesDir
    val filename = SimpleDateFormat(FILENAME, Locale.US).format(System.currentTimeMillis()) + PHOTO_EXTENSION
    val photoFile = File(outputDirectory, filename)
    val outputFileOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    // 画像ファイル保存
    this.takePicture(
        outputFileOptions,
        Executors.newSingleThreadExecutor(),
        object : ImageCapture.OnImageSavedCallback {
            // 成功
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
//                // If the folder selected is an external media directory, this is
//                // unnecessary but otherwise other apps will not be able to access our
//                // images unless we scan them using [MediaScannerConnection]
//                val mimeType = MimeTypeMap.getSingleton()
//                    .getMimeTypeFromExtension(savedUri.toFile().extension)
//                MediaScannerConnection.scanFile(
//                    context,
//                    arrayOf(savedUri.toFile().absolutePath),
//                    arrayOf(mimeType)
//                ) { _, uri ->
//                    Log.d("画像保存成功", "uri = " + uri)
//                }
                Log.d("画像保存成功", "saveUri = " + savedUri)
                onImageCaptured(savedUri)
            }
            // 失敗
            override fun onError(exception: ImageCaptureException) {
                Log.d("画像保存失敗", "exception = " + exception)
                onError(exception)
            }
        })
}

/**
 * TODO : おそらくここで内部、外部を選択
 *
 * @return
 */
private fun Context.getOutputDirectory(): File {
    val mediaDir = this.externalMediaDirs.firstOrNull()?.let {
        File(it, this.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else this.filesDir
}