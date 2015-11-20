package ru.diaproject.vkplus.core.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Patterns;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devspark.robototextview.widget.RobotoExpandableTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.vkcore.VK;

public class RobotoImageExpandableTextView extends RobotoExpandableTextView {
    private static final int ICON_FIRST_INT_VAR1 = 0xD83D;
    private static final int ICON_FIRST_INT_VAR2 = 0xD83C;
    private static final int ICON_INTERVAL = 8000;

    private static final String TWITTER_HASH_TAG = "#(?:\\[[^\\]]+\\]|\\S+)";

    private static int TWITTER_COLOR = Color.parseColor("#4099FF");
    private static int URL_COLOR = Color.parseColor("#0645AD");

    private static final Spannable.Factory spannableFactory = Spannable.Factory.getInstance();
    public RobotoImageExpandableTextView(Context context) {
        super(context);
    }

    public RobotoImageExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private SpannableStringBuilder addImages( SpannableStringBuilder spannable) {
        int startIndex = 0;
        int endIndex = 0;
        int resourceId = 0;
        List<Integer> toConvert;
        ImageSpan newSpan;

       for (int index = 0; index< spannable.length(); index++){
           toConvert = new ArrayList<>();
           int code = spannable.charAt(index);

           if (code == ICON_FIRST_INT_VAR1 || code == ICON_FIRST_INT_VAR2){
               startIndex = index;
               endIndex = index+1;
               toConvert.add(code);
               toConvert.add((int) spannable.charAt(endIndex));
               index++;
               endIndex++;
               if (resourceId == 0 && !notSetSingle(toConvert, spannable, startIndex, endIndex)){
                   if (index+2<=spannable.length()-1)
                   if (spannable.charAt(endIndex) == ICON_FIRST_INT_VAR1
                           || spannable.charAt(endIndex) == ICON_FIRST_INT_VAR2){
                      toConvert.add((int) spannable.charAt(index+1));
                       toConvert.add((int) spannable.charAt(index+2));
                       endIndex+=2;
                       index++;
                   }
               }
           }
           else if (code > ICON_INTERVAL) {
               startIndex = index;
               endIndex=index+1;
               toConvert.add(code);
           }

           if (toConvert.size()!= 0) {
               resourceId = getResourceId(toConvert);
               if (resourceId != 0) {
                   newSpan = new ImageSpan(getContext(), resourceId);
                   spannable.setSpan(newSpan, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
               }else if (toConvert.size()>2){
                   String url = VK.SINGLETON.getIconUrl()+getResourceString(toConvert);
                   try {
                         Bitmap icon = Glide.with(getContext()).load(url).asBitmap().into(-1,-1).get();
                         newSpan = new ImageSpan(getContext(), icon);
                         spannable.setSpan(newSpan, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                   } catch (Throwable e) {
                   }
               }
           }
       }
        return spannable;

    }

    private boolean notSetSingle(List<Integer> toConvert, SpannableStringBuilder spannable, int startIndex, int endIndex) {
        String firstImageUrl = VK.SINGLETON.getIconUrl() + getResourceString(toConvert.get(0), toConvert.get(1));
    try {
        Bitmap icon =  Glide.with(getContext()).load(firstImageUrl).asBitmap().into(-1, -1).get();
        ImageSpan newSpan = new ImageSpan(getContext(), icon);
        spannable.setSpan(newSpan, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return true;
    }
    catch(Throwable thE) {
              Log.e("Glide load bitmap error", "exception",thE);
        return false;
    }
}

    private SpannableStringBuilder addHashTags(SpannableStringBuilder spannableStringBuilder){
        Pattern pattern = Pattern.compile(TWITTER_HASH_TAG,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(spannableStringBuilder);

        while(matcher.find())
            spannableStringBuilder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(TWITTER_COLOR);
                    ds.setUnderlineText(false);
                }
            }, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableStringBuilder;
    }

    private SpannableStringBuilder addPhotoUpdateTextColor(SpannableStringBuilder spannableStringBuilder){
        Pattern patternMan = Pattern.compile(getResources().getString(R.string.news_photo_update_man),Pattern.DOTALL);
        Pattern patternWoman = Pattern.compile(getResources().getString(R.string.news_photo_update_woman),Pattern.DOTALL);

        Matcher matcherMan = patternMan.matcher(spannableStringBuilder);
        Matcher matcherWoman = patternWoman.matcher(spannableStringBuilder);

        while(matcherMan.find() )
            spannableStringBuilder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(ContextCompat.getColor(getContext(), R.color.m_teal));
                    ds.setUnderlineText(false);
                }
            }, matcherMan.start(), matcherMan.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        while(matcherWoman.find() )
            spannableStringBuilder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(ContextCompat.getColor(getContext(), R.color.m_teal));
                    ds.setUnderlineText(false);
                }
            }, matcherWoman.start(), matcherWoman.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableStringBuilder;
    }

    private SpannableStringBuilder addUrls(SpannableStringBuilder spannableStringBuilder){
        String patternS = Patterns.WEB_URL.pattern();
        Pattern pattern = Pattern.compile(patternS, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(spannableStringBuilder);

        while(matcher.find())
            spannableStringBuilder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(URL_COLOR);
                    ds.setUnderlineText(false);
                }
            }, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableStringBuilder;
    }
    private String getResourceString(List<Integer> toConvert) {
        try {
            String resourceIcon ="";
            for (int fcode : toConvert) {
                resourceIcon += Integer.toHexString(fcode).toLowerCase();
            }
            return resourceIcon.toUpperCase()+"_2x.png";
        }
        catch(Throwable e){
            Log.e("getResourceId error", "exception", e);
            return "";
        }
    }
    private String getResourceString(Integer ... codes){
        try {
            String resourceIcon ="";
            for (int fcode : codes) {
                resourceIcon += Integer.toHexString(fcode).toLowerCase();
            }
            return resourceIcon.toUpperCase()+"_2x.png";
        }
        catch(Throwable e){
            Log.e("getResourceId error", "exception", e);
            return "";
        }
    }
    private int getResourceId(List<Integer> code) {
        try {
            String resourceIcon = "i";
            for (int fcode : code) {
                resourceIcon += Integer.toHexString(fcode).toLowerCase();
            }

            int resourceId = getContext().getResources().getIdentifier(resourceIcon, "drawable", getContext().getPackageName());
            return resourceId;
        }
        catch(Throwable e){
            Log.e("getResourceId error", "exception", e);
            return 0;
        }
    }

    private  Spannable addImagesInText( CharSequence text) {
        Spannable spannable = spannableFactory.newSpannable(text);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannable);
        spannableStringBuilder = addImages(spannableStringBuilder);
        spannableStringBuilder = addUrls(spannableStringBuilder);
        spannableStringBuilder = addHashTags(spannableStringBuilder);
        spannableStringBuilder = addPhotoUpdateTextColor(spannableStringBuilder);
        return spannableStringBuilder;
    }

    @Override
    public void setText(@Nullable final CharSequence text) {
        VKMainExecutor.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                final Spannable s = addImagesInText(text);
                ((Activity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RobotoImageExpandableTextView.super.setText(s, TextView.BufferType.SPANNABLE);
                    }
                });

            }
        });

    }

    @Override
    public void setText(@Nullable final CharSequence text, @NonNull final SparseBooleanArray collapsedStatus, final int position) {
        VKMainExecutor.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                final Spannable imageText = addImagesInText( text);
                ((Activity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RobotoImageExpandableTextView.super.setText(imageText, collapsedStatus, position);
                    }
                });

            }
        });
    }
}
