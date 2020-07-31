package com.shine56.easywrite.util.viewHelper

import android.content.Context
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.shine56.easywrite.R
import com.shine56.easywrite.util.logD
import java.security.MessageDigest

class MyTransform(private val context: Context,
                  private val scaleWidth: Float?,
                  private val radius: Float,
                  private val isDelete: Boolean) : BitmapTransformation() {
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        return cornersCrop(pool, toTransform)
    }

    private fun cornersCrop(pool: BitmapPool, source: Bitmap) : Bitmap {
        var bitmap = source
        //缩放
        "缩放".logD()
        scaleWidth?.let {
            val originalWidth = source.width.toFloat()
            val originalHeight = source.height.toFloat()
            var newWidth = it
            var newHeight = originalHeight * (newWidth / originalWidth)
            //计算宽、高缩放率
            val scanleWidth = newWidth / originalWidth
            val scanleHeight = newHeight / originalHeight
            // 缩放图片动作
            val matrix = Matrix()
            matrix.postScale(scanleWidth, scanleHeight)
            // 创建新的图片Bitmap
            bitmap = Bitmap.createBitmap(source, 0, 0, originalWidth.toInt(), originalHeight.toInt(), matrix, true)
        }

        val result = pool[bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888]
        val canvas = Canvas(result)

        //圆角矩形
        val rectF = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
        val paint = Paint()
        paint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        canvas.drawRoundRect(rectF, radius, radius, paint)

        "画布高度${canvas.height}, 图片高度${bitmap.height}, 矩形高度${rectF.bottom}".logD()

        //删除logo
        if(isDelete){
            //"为真？".logD()
            val deletePaint = Paint()
            deletePaint.isAntiAlias = true
            val rect = Rect(bitmap.width-80, 20, bitmap.width-20, 80)
            val bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.delete_photo);
            canvas.drawBitmap(bitmap, null, rect, deletePaint)
        }

        return result
    }

}