package ru.diaproject.vkplus.news.model.users;

import ru.diaproject.vkplus.R;

public class DataUserWoman extends DataUser {
    @Override
    public OwnerSex getSex() {
        return OwnerSex.WOMAN;
    }

    @Override
    public Integer getPlaceholderResource() {
        return R.drawable.woman_silhouette;
    }

    @Override
    public Integer getErrorResource() {
        return R.drawable.woman_silhouette;
    }
}
