package id.co.imastudio.popularmovie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by idn on 7/29/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private List<TrailerModel> trailerList;
    private Context context;

    //Create constructor
    public TrailerAdapter(List<TrailerModel> trailerList, Context context) {
        this.trailerList = trailerList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gambarFilm;

        public ViewHolder(View itemView) {
            super(itemView);
            gambarFilm = (ImageView) itemView.findViewById(R.id.iv_item_trailer);
        }
    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_list_item, parent, false);
        return new ViewHolder(itemView);
        //item layout
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder, final int position) {
        //proses komponen

        Glide.with(context)
                .load("http://img.youtube.com/vi/"+trailerList.get(position).getKeyTrailer()+"/hqdefault.jpg")
                .error(R.mipmap.ic_launcher)
                .into(holder.gambarFilm);

        holder.gambarFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Judul Film : " + trailerList.get(position).getJudulFilm(), Toast.LENGTH_SHORT).show();
                String url = "https://www.youtube.com/watch?v=".concat(trailerList.get(position).getKeyTrailer());
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailerList.size();
        //jumlah list
    }

}
