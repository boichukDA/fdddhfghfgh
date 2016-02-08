package ru.diaproject.vkplus.model.users;


import ru.diaproject.vkplus.R;

public class DataUserMan extends DataUser {

    @Override
    public OwnerSex getSex() {
        return OwnerSex.MAN;
    }

    @Override
    public Integer getPlaceholderResource() {
        return R.drawable.man_siluette;
    }

    @Override
    public Integer getErrorResource() {
        return R.drawable.man_siluette;
    }

}
