package id.co.imastudio.popularmovie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by idn on 5/13/2017.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

    private List<FilmModel> filmList;
    private Context context;

    //Create constructor
    public FilmAdapter(List<FilmModel> filmList, Context context) {
        this.filmList = filmList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView judulFilm;
        ImageView gambarFilm;

        public ViewHolder(View itemView) {
            super(itemView);
            judulFilm = (TextView) itemView.findViewById(R.id.tvItem);
            gambarFilm = (ImageView) itemView.findViewById(R.id.ivItem);
        }
    }

    @Override
    public FilmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemView);
        //item layout
    }

    @Override
    public void onBindViewHolder(FilmAdapter.ViewHolder holder, final int position) {
        //proses komponen
        holder.judulFilm.setText(filmList.get(position).getJudulFilm());
//        holder.gambarFilm.setImageResource(filmList.get(position).getGambarFilm());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185" + filmList.get(position).getGambarFilm())
                .error(R.mipmap.ic_launcher)
                .into(holder.gambarFilm);

        holder.gambarFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Judul Film : " + filmList.get(position).getJudulFilm(), Toast.LENGTH_SHORT).show();
                Intent pindah = new Intent(context, DetailActivity.class);
                pindah.putExtra("ID_JUDUL", filmList.get(position).getIdFilm());
                pindah.putExtra("DATA_JUDUL", filmList.get(position).getJudulFilm());
                pindah.putExtra("DATA_POSTER", filmList.get(position).getPosterFilm());
                pindah.putExtra("DATA_SINOPSIS", filmList.get(position).getSinopsisFilm());
                pindah.putExtra("DATA_RATING", filmList.get(position).getRatingFilm());
                pindah.putExtra("DATA_RELEASE", filmList.get(position).getReleaseFilm());
                context.startActivity(pindah);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filmList.size();
        //jumlah list
    }


}
