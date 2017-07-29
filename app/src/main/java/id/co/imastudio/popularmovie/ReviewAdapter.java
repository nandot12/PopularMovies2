package id.co.imastudio.popularmovie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by idn on 7/29/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<ReviewModel> reviewList;
    private Context context;

    //Create constructor
    public ReviewAdapter(List<ReviewModel> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView authorReview;
        TextView contentReview;


        public ViewHolder(View itemView) {
            super(itemView);
            authorReview = (TextView) itemView.findViewById(R.id.tv_author_review);
            contentReview = (TextView) itemView.findViewById(R.id.tv_content_review);
        }
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);
        return new ViewHolder(itemView);
        //item layout
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, final int position) {
        //proses komponen
        holder.authorReview.setText(reviewList.get(position).getAuthorReview());
        holder.contentReview.setText(reviewList.get(position).getContentReview());

        holder.contentReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Judul Film : " + reviewList.get(position).getJudulFilm(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(reviewList.get(position).getUrlReview()));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
        //jumlah list
    }
}
