package ru.diaproject.vkplus.profiles.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.profiles.viewholders.TestViewHolder;

public class TestAdapter extends RecyclerView.Adapter<TestViewHolder>{
    private Context context;

    public TestAdapter(Context context){
        this.context = context;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.profile_test_item, parent, false);
        TestViewHolder holder = new TestViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        holder.text.setText("sldkjfsd sdldfk sdl fsldkf sfd lsddkf sdlf dsfsldkjfsd sdldfk sdl fsldkf sfd lsddkf sdlf dsfsldkjfsd sdldfk sdl fsldkf sfd lsddkf sdlf dsfsldkjfsd sdldfk sdl fsldkf sfd lsddkf sdlf dsfsldkjfsd sdldfk sdl fsldkf sfd lsddkf sdlf dsfsldkjfsd sdldfk sdl fsldkf sfd lsddkf sdlf dsfsldkjfsd sdldfk sdl fsldkf sfd lsddkf sdlf dsf");
        holder.text.setTextColor(Color.parseColor("#ffffff"));
    }

    @Override
    public int getItemCount() {
        return 1000;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
