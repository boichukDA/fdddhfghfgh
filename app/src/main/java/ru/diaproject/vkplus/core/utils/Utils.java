package ru.diaproject.vkplus.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    private static final String PLUGIN_CACHE_FOLDER = md5("VKPlus");
    private static String cachePath = "";

    public static String getCachePath(Context context) {
        if(TextUtils.isEmpty(cachePath))
            cachePath = context.getExternalCacheDir() + File.separator + PLUGIN_CACHE_FOLDER + File.separator;

        return cachePath;
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        boolean isOnline = false;

        if (ni != null && ni.isConnectedOrConnecting()){
            isOnline = true;
        }

        return isOnline;
    }

    public static String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException nSAEx) {
        }

        return "";
    }
    public static boolean saveBitmapToFile(Context context, String url, Bitmap bitmap){
        try {
            File file = new File(getCachePath(context) + md5(url));

            if (!file.exists()) {
                new File(getCachePath(context)).mkdirs();
                file.createNewFile();
            }

            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            return true;
        }
        catch(Throwable e){
            Log.e("Error caching icon", e.toString(), e);
            return false;
        }
    }

    public static Bitmap tryLoadingBitmapFromFile(Context context, String url){
        File file = new File(getCachePath(context) + md5(url));

        if (!file.exists())
           return null;

        Bitmap bitmap = Utils.processBitmap(
                file.getAbsolutePath(),
                Bitmap.Config.RGB_565);

        return bitmap;

    }


    public static Bitmap processBitmap(String fileName, Bitmap.Config config) {
        Bitmap bitmap = null;

        try {File tempFile = new File(fileName);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(tempFile));

            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(fileInputStream, null, opts);
            fileInputStream.close();
            fileInputStream = new BufferedInputStream(new FileInputStream(tempFile));

            opts = new BitmapFactory.Options();
            opts.inPreferredConfig = config;

            try {
                System.gc();
                bitmap = BitmapFactory.decodeStream(fileInputStream, null, opts);

                if (bitmap != null)
                    return bitmap;
            } catch (Exception ex) {
            }

            fileInputStream.close();
            fileInputStream = new BufferedInputStream(new FileInputStream(tempFile));

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                System.gc();
                bitmap = BitmapFactory.decodeStream(fileInputStream, null, opts);

                if (bitmap != null)
                    return bitmap;
            } catch (Exception ex) {
            }

            fileInputStream.close();
            fileInputStream = new BufferedInputStream(new FileInputStream(tempFile));

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                System.gc();
                bitmap = BitmapFactory.decodeStream(fileInputStream, null, opts);
            } catch (Exception ex) {
            }

            fileInputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return bitmap;
    }

    public static String downloadFile(Context context, String url, String md5) {
        final int BYTE_ARRAY_SIZE = 8024;
        final int CONNECTION_TIMEOUT = 30000;
        final int READ_TIMEOUT = 30000;

        try {
            for(int i = 0; i < 3; i++) {
                URL fileUrl = new URL(URLDecoder.decode(url, "UTF-8"));
                HttpURLConnection connection = (HttpURLConnection)fileUrl.openConnection();
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.connect();

                int status = connection.getResponseCode();

                if(status >= HttpStatus.SC_BAD_REQUEST) {
                    connection.disconnect();

                    continue;
                }

                BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
                File file = new File(getCachePath(context) + md5);

                if(!file.exists()) {
                    new File(getCachePath(context)).mkdirs();
                    file.createNewFile();
                }

                FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                int byteCount;
                byte[] buffer = new byte[BYTE_ARRAY_SIZE];

                while ((byteCount = bufferedInputStream.read(buffer, 0, BYTE_ARRAY_SIZE)) != -1)
                    fileOutputStream.write(buffer, 0, byteCount);

                bufferedInputStream.close();
                fileOutputStream.flush();
                fileOutputStream.close();

                return file.getAbsolutePath();
            }
        } catch (Exception e) {
            e.printStackTrace();

            return "";
        }

        return "";
    }

    public static String readStringDataFromFile(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    public static String downloadFileAsString(String url) {
        final int CONNECTION_TIMEOUT = 30000;
        final int READ_TIMEOUT = 30000;

        try {
            for(int i = 0; i < 3; i++) {
                URL fileUrl = new URL(URLDecoder.decode(url, "UTF-8"));
                HttpURLConnection connection = (HttpURLConnection)fileUrl.openConnection();
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.connect();

                int status = connection.getResponseCode();

                if(status >= HttpStatus.SC_BAD_REQUEST) {
                    connection.disconnect();
                    continue;
                }

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null)
                    stringBuilder.append(line);

                bufferedReader.close();

                return stringBuilder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();

            return "";
        }

        return "";
    }
}
