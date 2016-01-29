package ru.diaproject.vkplus.news.binders;



import android.content.Context;
import android.view.View;
import ru.diaproject.vkplus.core.databinders.DataBindAdapter;
import ru.diaproject.vkplus.core.databinders.DataBinder;
import ru.diaproject.vkplus.news.binders.bindhelpers.PhotoBindHelper;
import ru.diaproject.vkplus.news.model.NewsResponse;
import ru.diaproject.vkplus.news.model.baseitems.IDataMainItem;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.users.IDataOwner;
import ru.diaproject.vkplus.news.viewholders.DataPhotosViewHolder;

public abstract class DataPhotosBinder<T extends DataPhotosViewHolder, V extends IDataMainItem> extends DataBinder<T,V>{
    private static final int MAX_PHOTOS_DISPLAY = 5;

    private static final int TOTAL_PIXELS_OFFSET = 40;
    private static final int TWO_IMAGE_MIDDLE_OFFSET = 8;

    private final float density;
    private PhotoBindHelper photoBindHelper;

    public DataPhotosBinder(Context context, DataBindAdapter dataBindAdapter, NewsResponse items) {
        super(context, dataBindAdapter, items);
        density = context.getResources().getDisplayMetrics().density;
        int photoContainerWidth = (int) (context.getResources().getDisplayMetrics().widthPixels - TOTAL_PIXELS_OFFSET * density);
        photoBindHelper = new PhotoBindHelper(context, photoContainerWidth, TWO_IMAGE_MIDDLE_OFFSET);
    }

    public void setPhotos(IDataMainItem item, T holder){
        holder.photoContainer.setVisibility(View.VISIBLE);
        if (item.getAttachmentPhotos() != null
                && !item.getAttachmentPhotos().getPhotos().isEmpty()) {
            photoBindHelper.setPhotos(item, holder);
        } else
            hidePhotos(holder);
    }

    protected void hidePhotos(T holder) {
        holder.photoContainer.setVisibility(View.GONE);
    }
    public int getMaxPhotosDisplay(){
        return MAX_PHOTOS_DISPLAY;
    }

    public static int getTotalPixelsOffset() {
        return TOTAL_PIXELS_OFFSET;
    }

    public static int getTwoImageMiddleOffset() {
        return TWO_IMAGE_MIDDLE_OFFSET;
    }

    public float getDensity() {
        return density;
    }

}
