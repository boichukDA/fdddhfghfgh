
package ru.diaproject.vkplus.news;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.news.model.baseitems.FilterType;
import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;
import ru.diaproject.vkplus.news.model.baseitems.NewsPostItem;
import ru.diaproject.vkplus.news.model.groups.Group;
import ru.diaproject.vkplus.news.model.users.User;
import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;
import ru.diaproject.vkplus.vkcore.user.VKNewsUserConfiguration;

public class NewsVariantContainer {
    public static NewsVariantContainer getInstance(Context context, VKNewsUserConfiguration userConfiguration){
        if (container == null) {
            container = new NewsVariantContainer(context, userConfiguration);
            container.init();
        }

        return container;
    }

    private static NewsVariantContainer container;

    private List<NewsVariant> variants;
    private List<NewsVariant> currentVariants;

    private Context context;
    private VKNewsUserConfiguration config;
    private NewsVariantContainer(Context context, VKNewsUserConfiguration userConfiguration){
        variants = new ArrayList<>();
        currentVariants = new ArrayList<>();
        this.context = context;
        config = userConfiguration;

        String[] resourcesStrings = context.getResources().getStringArray(R.array.news_varians);
        variants.add(new NewsVariant(1,ContextCompat.getDrawable(context, R.drawable.news_all_white),
                resourcesStrings[0], true, "post,photo_tag,wall_photo,photo,note", VKQuerySubMethod.DEFAULT, null ));

        variants.add(new NewsVariant(2, ContextCompat.getDrawable(context, R.drawable.news_recom_white),
                resourcesStrings[1], true,"", VKQuerySubMethod.RECOMENDED, null ));

        variants.add(new NewsVariant(3, ContextCompat.getDrawable(context, R.drawable.news_photo_white),
                resourcesStrings[2], true, "post,photo,photo_tag,wall_photo", VKQuerySubMethod.DEFAULT, new IDataFilter() {
            @Override
            public boolean apply(NewsEntityBase item, HashMap<Integer, User> profiles, HashMap<Integer, Group> groups) {
                if (item.getType().equals(FilterType.PHOTO)
                        || item.getType().equals(FilterType.PHOTO_TAG)
                        || item.getType().equals(FilterType.WALL_PHOTO))
                    return true;

                if (item.getType().equals(FilterType.POST))
                    if (((NewsPostItem)item).containsPhoto())
                        return true;

                return false;
            }
        }));

        variants.add(new NewsVariant(4, ContextCompat.getDrawable(context, R.drawable.news_video_white),
                resourcesStrings[3], true, "post", VKQuerySubMethod.DEFAULT, new IDataFilter() {
            @Override
            public boolean apply(NewsEntityBase item, HashMap<Integer, User> profiles, HashMap<Integer, Group> groups) {
                if (item.getType().equals(FilterType.POST))
                    if (((NewsPostItem)item).containsVideo())
                        return true;

                return false;
            }
        }));

        variants.add(new NewsVariant(5, ContextCompat.getDrawable(context, R.drawable.news_group_white),
                resourcesStrings[4], true,"friend", VKQuerySubMethod.DEFAULT, null ));

        variants.add(new NewsVariant(6, ContextCompat.getDrawable(context, R.drawable.news_search_white),
                resourcesStrings[5], true, "post", VKQuerySubMethod.DEFAULT, new IDataFilter() {
            @Override
            public boolean apply(NewsEntityBase item, HashMap<Integer, User> profiles, HashMap<Integer, Group> groups) {
                if (item.getType().equals(FilterType.POST))
                    if (((NewsPostItem)item).containsAudio())
                        return true;

                return false;
            }
        }));
/*

        variants.add(new NewsVariant(7, ContextCompat.getDrawable(context, R.drawable.news_note_white),
                resourcesStrings[6], true,"", VKQuerySubMethod.DEFAULT, null ));
*/

        variants.add(new NewsVariant(8, ContextCompat.getDrawable(context, R.drawable.news_likes_white),
                resourcesStrings[7], true,"post", VKQuerySubMethod.DEFAULT, null ));
    }

    private void init() {
        variants.get(0).setIsSet(true);
        for (int index = 0; index < variants.size();index++) {
            variants.get(index).setIsSet(config.isSet(index));
            if (config.isSet(index))
                currentVariants.add(variants.get(index));
        }
    }

    public int size() {
        return currentVariants.size();
    }

    public NewsVariant get(int position) {
        return currentVariants.get(position);
    }
}
