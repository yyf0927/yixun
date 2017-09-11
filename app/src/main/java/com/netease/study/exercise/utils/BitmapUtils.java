package com.netease.study.exercise.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BitmapUtils {

	public static Bitmap createReflectionBitmap(Bitmap bitmap, int reflectionHeight) {
		if (reflectionHeight == 0) {
			return bitmap;
		}
		//This will not scale but will flip on the Y axis
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		//Create a Bitmap with the flip matix applied to it.
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		Bitmap reflectionBitmap = Bitmap.createBitmap(bitmap, 0, height - reflectionHeight, width, reflectionHeight, matrix, false);
		//Create a new bitmap with same width but taller to fit reflection
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + reflectionHeight), Config.ARGB_8888);
		//Create a new Canvas with the bitmap that's big enough for the image plus gap plus reflection
		Canvas canvas = new Canvas(bitmapWithReflection);
		//Draw in the original image
		canvas.drawBitmap(bitmap, 0, 0, null);

		/**Draw in the gap
		 Paint deafaultPaint = new Paint();
		 canvas.drawRect(0, height, width, height + 0, deafaultPaint);
		 */

		//Draw in the reflection
		canvas.drawBitmap(reflectionBitmap, 0, height + 0, null);

		/**Create a shader that is a linear gradient that covers the reflection
		 Paint paint = new Paint();
		 LinearGradient shader = new LinearGradient(0, height, 0,
		 bitmapWithReflection.getHeight() + 0, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		 //Set the paint to use this shader (linear gradient)
		 paint.setShader(shader);
		 //Set the Transfer mode to be porter duff and destination in
		 paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		 //Draw a rectangle using the paint with our linear gradient
		 canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + 0, paint);
		 */

		return bitmapWithReflection;
	}

	public static byte[] getBitmapData(Bitmap bitmap, long thresholdBytes) {
		ByteArrayOutputStream out = null;
		byte[] data = new byte[0];
		try {
			int dimension = 2048;
			do {
				bitmap = scaleBitmap(bitmap, dimension, dimension, false);
				out = new ByteArrayOutputStream();
				bitmap.compress(CompressFormat.JPEG, 85, out);
				data = out.toByteArray();
				out.close();
				dimension -= 100;
			} while (data.length > thresholdBytes && dimension > 0);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IoUtils.closeSilently(out);
		}
		return data;
	}

	public static Bitmap scaleBitmap(Bitmap originalBitmap, int reqWidth, int reqHeight, boolean inside) {
		Bitmap bitmap = originalBitmap;
		if (originalBitmap != null && reqWidth > 0 && reqHeight > 0) {
			int width = originalBitmap.getWidth();
			int height = originalBitmap.getHeight();
			if (width > reqWidth || height > reqHeight) {
				final int widthRatio = (int) Math.ceil((float) width / (float) reqWidth);
				final int heightRatio = (int) Math.ceil((float) height / (float) reqHeight);
				double sample = widthRatio < heightRatio ? inside ? heightRatio : widthRatio : inside ? widthRatio : heightRatio;
				int dstWidth = (int) (width / sample);
				int dstHeight = (int) (height / sample);
				if (dstWidth > 0 && dstHeight > 0) {
					try {
						bitmap = Bitmap.createScaledBitmap(originalBitmap, dstWidth, dstHeight, false);
					} catch (IllegalArgumentException e) {//线上异常
						e.printStackTrace();
					}
				}
			}
		}
		return bitmap;
	}

	public static Bitmap scaleBitmapRelyWidth(Bitmap originalBitmap, int reqWidth) {
		Bitmap bitmap = originalBitmap;
		double width = originalBitmap.getWidth();
		int height = originalBitmap.getHeight();
		if (width == 0 || height == 0) {
			return bitmap;
		}
		int dstHeight = (int) Math.ceil(reqWidth / width * height);
		if (dstHeight > 0) {
			bitmap = Bitmap.createScaledBitmap(originalBitmap, reqWidth, dstHeight, false);
		}
		return bitmap;
	}

	public static Bitmap cutBitmapEnd(Bitmap originalBitmap, int reqSize, int orient) {
		int width = originalBitmap.getWidth();
		int height = originalBitmap.getHeight();
		int originSize = orient == 0 ? width : height;
		if (originSize <= reqSize) {
			return originalBitmap;
		}
		return Bitmap.createBitmap(originalBitmap, 0, 0, orient == 0 ? reqSize : width, orient == 0 ? height : reqSize);
	}

	/**
	 * Calculate an inSampleSize for use in a {@link android.graphics.BitmapFactory.Options} object when decoding
	 * bitmaps using the decode* methods from {@link android.graphics.BitmapFactory}. This implementation calculates
	 * the closest inSampleSize that is a power of 2 and will result in the final decoded bitmap
	 * having a width and height equal to or larger than the requested width and height.
	 *
	 * @param options An options object with out* params already populated (run through a decode*
	 *            method with inJustDecodeBounds==true
	 * @param reqWidth The requested width of the resulting bitmap
	 * @param reqHeight The requested height of the resulting bitmap
	 * @return The value to be used for inSampleSize
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}

			// This offers some additional logic in case the image has a strange
			// aspect ratio. For example, a panorama may have a much larger
			// width than height. In these cases the total pixels might still
			// end up being too large to fit comfortably in memory, so we should
			// be more aggressive with sample down the image (=larger inSampleSize).
//            long totalPixels = width * height / inSampleSize;
//
//            // Anything more than 2x the requested pixels we'll sample down further
//            final long totalReqPixelsCap = reqWidth * reqHeight * 2;
//
//            while (totalPixels > totalReqPixelsCap) {
//                inSampleSize *= 2;
//                totalPixels /= 2;
//            }

		}
		return inSampleSize;
	}

	public static int calculateInSampleSizeLarger(BitmapFactory.Options options, int size) {
		final int width = options.outWidth;
		final int height = options.outHeight;
		int inSampleSize = Math.max(width / size, height / size);
		if(inSampleSize <= 1) {
			return 1;
		} else if(inSampleSize <= 8) {
			return Integer.highestOneBit(inSampleSize);
		} else {
			return (inSampleSize / 8) * 8;
		}
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public static Bitmap decodeSampledBitmapFromFileAndRotate(String path, int reqWidth, int reqHeight) {
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return rotateWithExif(path, BitmapFactory.decodeFile(path, options));
	}

	public static Bitmap rotateWithExif(String path, Bitmap bitmap){
		if (bitmap != null) {
			ExifInterface exif;
			try {
				exif = new ExifInterface(path);
				int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
				Matrix matrix = null;
				if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
					matrix = new Matrix();
					matrix.postRotate(180);
				} else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
					matrix = new Matrix();
					matrix.postRotate(270);
				} else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
					matrix = new Matrix();
					matrix.postRotate(90);
				}
				if (matrix != null) {
					bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	public static Pair<Integer, Integer> decodeDimensions(String path) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		return Pair.create(options.outWidth, options.outHeight);
	}
}
