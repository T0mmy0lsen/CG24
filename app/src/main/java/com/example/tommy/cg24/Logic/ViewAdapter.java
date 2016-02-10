package com.example.tommy.cg24.Logic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tommy.cg24.Current;
import com.example.tommy.cg24.Database.Database;
import com.example.tommy.cg24.Models.ModelPlayer;
import com.example.tommy.cg24.Player;
import com.example.tommy.cg24.R;
import com.squareup.picasso.Picasso;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private List<ModelPlayer> items;



    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private ImageView image;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.playerName);
            image = (ImageView) v.findViewById(R.id.bg_list);
        }
    }

    public ViewAdapter(List<ModelPlayer> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public ViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = items.get(position).getId();
                Intent intent = new Intent(v.getContext(), Current.class).putExtra("ID", id);
                v.getContext().startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int id = items.get(position).getId();
                Player.database.deleteUser(id);
                Player.updateList();
                return true;
            }
        });

        Context context = holder.image.getContext();
        holder.name.setText(items.get(position).getSummonname());

        Uri uri = Uri.parse("android.resource://com.example.tommy.cg24/drawable/" + items.get(position).getMostplayed() + "_splash_0");

        Picasso.with(context)
                .load(uri)
                .resize(378 * 2,150 * 2)
                .into(holder.image);
    }
}
