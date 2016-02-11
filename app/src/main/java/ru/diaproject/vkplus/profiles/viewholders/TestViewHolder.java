package ru.diaproject.vkplus.profiles.viewholders;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.diaproject.vkplus.R;

public class TestViewHolder extends RecyclerView.ViewHolder{
    public TextView text;
    public TestViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.profile_test_text);
    }
}
