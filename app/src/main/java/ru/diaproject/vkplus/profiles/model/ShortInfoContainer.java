package ru.diaproject.vkplus.profiles.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.utils.DateUtils;
import ru.diaproject.vkplus.core.utils.VkStringUtils;
import ru.diaproject.vkplus.model.users.extusers.City;
import ru.diaproject.vkplus.model.users.extusers.DataUserExt;
import ru.diaproject.vkplus.model.users.extusers.occupations.Occupation;
import ru.diaproject.vkplus.model.users.extusers.occupations.OccupationType;
import ru.diaproject.vkplus.profiles.model.items.InfoItem;
import ru.diaproject.vkplus.profiles.model.items.KeyValueItem;

/**
 * *Enum ProfileItemType determines view holder type
 * Item for binders
 */
public class ShortInfoContainer {
    private List<InfoItem> items;
    public ShortInfoContainer(DataUserExt dataUserExt, Context context) {
        items = new ArrayList<>();

        String bDate = dataUserExt.getBdate();
        if (bDate!= null && !"".equals(bDate))
            items.add(createBDateItem(bDate, context));

        City city = dataUserExt.getCity();
        if (city != null)
            items.add(createCityItem(city, context));

        Occupation occupation = dataUserExt.getOccupation();
        if (occupation!= null)
            items.add(createOccupationItem(occupation, dataUserExt, context));
    }

    private InfoItem createOccupationItem(Occupation occupation, DataUserExt dataUserExt, Context context) {
        InfoItem<KeyValueItem> infoItem = new InfoItem<>(ProfileItemType.KEY_VALUE);
        String key;
        String value;
        if (occupation.getType().equals(OccupationType.SCHOOL)
                || occupation.getType().equals(OccupationType.UNIVERSITY))
            key = context.getString(R.string.profile_school);
        else
            key = context.getString(R.string.profile_work);

        value = occupation.getName();
        KeyValueItem item = new KeyValueItem(key, value);
        item.setSpanKey(VkStringUtils.toSpannable(key));
        item.setSpanValue(VkStringUtils.toSpannable(value));
        infoItem.setItem(item);
        return infoItem;
    }

    private InfoItem createBDateItem(String bDate, Context context) {
        InfoItem<KeyValueItem> infoItem = new InfoItem<>(ProfileItemType.KEY_VALUE);
        String key = context.getString(R.string.profile_bdate);
        String value = DateUtils.parseBDateString(bDate);

        KeyValueItem item = new KeyValueItem(key, value);
        item.setSpanKey(VkStringUtils.toSpannable(key));
        item.setSpanValue(VkStringUtils.toSpannable(value));
        infoItem.setItem(item);
        return infoItem;
    }

    private InfoItem createCityItem(City city, Context context) {
        InfoItem<KeyValueItem> infoItem = new InfoItem<>(ProfileItemType.KEY_VALUE);
        String key = context.getString(R.string.profile_city);
        String value = city.getTitle();

        KeyValueItem item = new KeyValueItem(key, value);
        item.setSpanKey(VkStringUtils.toSpannable(key));
        item.setSpanValue(VkStringUtils.toSpannable(value));
        infoItem.setItem(item);
        return infoItem;
    }
}
