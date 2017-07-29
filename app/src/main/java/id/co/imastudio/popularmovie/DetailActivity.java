package id.co.imastudio.popularmovie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.sackcentury.shinebuttonlib.ShineButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivDetailPoster;
    private TextView tvRating;
    private TextView tvRelease;
    private TextView tvSinopsisFilm;
    private RecyclerView recyclerTriler;
    private RecyclerView recyclerReview;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private ArrayList<TrailerModel> trailerList;
    private ArrayList<ReviewModel> reviewList;
    private ArrayList<FilmModel> filmList;
    private String dataidFilm;
    private ShineButton btnFavorit;

//    private boolean mIsFavorite = false;
//    FilmModel movies;

    private static final String FILM_FAVORIT = "filmfavorit";
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        filmList = new ArrayList<>();
//        movies = new FilmModel();
//        movies.setFavorite(false);
//        filmList.add(movies);

//        movies = (FilmModel) getIntent().getExtras().getParcelable(FilmModel.TAG_MOVIES);


        dataidFilm = getIntent().getStringExtra("ID_JUDUL");
        String datajudulFilm = getIntent().getStringExtra("DATA_JUDUL");
        String dataposterFilm = getIntent().getStringExtra("DATA_POSTER");
        String datasinopsisFilm = getIntent().getStringExtra("DATA_SINOPSIS");
        String dataratingFilm = getIntent().getStringExtra("DATA_RATING");
        String datareleaseFilm = getIntent().getStringExtra("DATA_RELEASE");

        initView();

//        pref = getSharedPreferences("SETTING", 0);
//        final Boolean favorit = pref.getBoolean(FILM_FAVORIT, false);
//        if (favorit) {
//            btnFavorit.setChecked(true);
//        }

        Log.d("DATA", "" + dataidFilm + datajudulFilm + dataposterFilm + datasinopsisFilm + dataratingFilm + datareleaseFilm);
        getSupportActionBar().setTitle(datajudulFilm);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + dataposterFilm)
                .into(ivDetailPoster);
        tvRating.setText(dataratingFilm);
        tvRelease.setText(datareleaseFilm);
        tvSinopsisFilm.setText(datasinopsisFilm);

        trailerList = new ArrayList<>();
        reviewList = new ArrayList<>();

        getDataOnline();

//        btnFavorit.setChecked(movies.isFavorite());
        btnFavorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (pref.getBoolean(FILM_FAVORIT, true)) { // Already added is removed
////                    MoviePersistenceUtils.removeFromFavorites(DetailActivity.this, movies.getIdFilm());
////                    getContentResolver().delete(FilmContract.FilmEntry.CONTENT_URI.buildUpon().appendPath(movies.getIdFilm()).build(), null, null);
//
////                    SharedPreferences.Editor editor = pref.edit();
////                    editor.putBoolean(FILM_FAVORIT, false);
////                    editor.commit();
//                    btnFavorit.setChecked(false);
//
//                    Set<String> defaultids = new HashSet<String>();
//                    Set<String> idvals=pref.getStringSet("ids",defaultids);
//                    if(idvals.contains(dataidFilm)){
//                        idvals.remove(dataidFilm);
//                    }
//                    Set<String> newvals=new HashSet<String>();
//                    Iterator itarator = idvals.iterator();
//                    while(itarator.hasNext()) {
//                        String vals = (String) itarator.next();
//                        newvals.add(vals);
//                    }
//                    SharedPreferences.Editor editor=pref.edit();
//                    editor.putStringSet("ïds",newvals);
//                    editor.commit();

//                    Toast.makeText(DetailActivity.this, "Removed from Favorite", Toast.LENGTH_SHORT).show();
//                    movies.setFavorite(false);
//
//
//
//                } else {
////                    MoviePersistenceUtils.addToFavorites(DetailActivity.this, movies.getIdFilm());
////                    ContentValues values = FilmDbHelper.getMovieContentValues(movies);
////                    getContentResolver().insert(FilmContract.FilmEntry.CONTENT_URI, values);
//
//                    Set<String> idset=new HashSet<String>();
//                    idset.add(dataidFilm);
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putBoolean(FILM_FAVORIT, true);
//                    editor.putStringSet("id",idset);
//                    editor.commit();
//                    btnFavorit.setChecked(true);
//
//                    Toast.makeText(DetailActivity.this, "Added to Favorite", Toast.LENGTH_SHORT).show();
////                    movies.setFavorite(true);
//
//                }
////                mIsFavorite = true;
////                btnFavorit.setChecked(movies.isFavorite());
            }
        });

    }

    private void getDataOnline() {

        final ProgressDialog loading = ProgressDialog.show(DetailActivity.this, "Loading Data", "Mohon Bersabar", false, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (isNetworkConnected()) {
                    return;
                } else {
                    new AlertDialog.Builder(DetailActivity.this)
                            .setTitle("No Internet Connection")
                            .setMessage("It looks like your internet connection is off. Please turn it " +
                                    "on and try again")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert).show();
                }
            }
        });
        String urlTrailer = "https://api.themoviedb.org/3/movie/" + dataidFilm + "/videos?api_key=" + ApiKey.DATA_KEY + "&language=en-US";

        JsonObjectRequest ambildataTrailer = new JsonObjectRequest(Request.Method.GET, urlTrailer, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //ntar kalo respon ngapain
                loading.hide();
                try {
                    JSONArray arrayresults = response.getJSONArray("results");
                    for (int i = 0; i < arrayresults.length(); i++) {
                        JSONObject json = arrayresults.getJSONObject(i);
                        Log.d("Hasil Json :", "" + json);
                        TrailerModel datatrailer = new TrailerModel();
                        datatrailer.setKeyTrailer(json.getString("key"));
                        trailerList.add(datatrailer);
                    }
                    trailerAdapter = new TrailerAdapter(trailerList, DetailActivity.this);
                    LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false);

                    recyclerTriler.setAdapter(trailerAdapter);
                    recyclerTriler.setLayoutManager(trailerLayoutManager);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DetailActivity.this, "Error get json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //kalo error ngapain
                Toast.makeText(DetailActivity.this, "Error no response : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        String urlReview = "https://api.themoviedb.org/3/movie/" + dataidFilm + "/reviews?api_key=" + ApiKey.DATA_KEY + "&language=en-US";
        JsonObjectRequest ambildataReview = new JsonObjectRequest(Request.Method.GET, urlReview, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //ntar kalo respon ngapain
                loading.hide();
                try {
                    JSONArray arrayresults = response.getJSONArray("results");
                    for (int i = 0; i < arrayresults.length(); i++) {
                        JSONObject json = arrayresults.getJSONObject(i);
                        Log.d("Hasil Json :", "" + json);
                        ReviewModel datareview = new ReviewModel();
                        datareview.setAuthorReview(json.getString("author"));
                        datareview.setContentReview(json.getString("content"));
                        datareview.setUrlReview(json.getString("url"));
                        reviewList.add(datareview);
                    }
                    reviewAdapter = new ReviewAdapter(reviewList, DetailActivity.this);
                    LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(DetailActivity.this);

                    recyclerReview.setAdapter(reviewAdapter);
                    recyclerReview.setLayoutManager(reviewLayoutManager);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DetailActivity.this, "Error get json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //kalo error ngapain
                Toast.makeText(DetailActivity.this, "Error no response : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue antrian = Volley.newRequestQueue(DetailActivity.this);
        antrian.add(ambildataTrailer);
        antrian.add(ambildataReview);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) DetailActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void initView() {
        ivDetailPoster = (ImageView) findViewById(R.id.ivDetailPoster);
        tvRating = (TextView) findViewById(R.id.tvRating);
        tvRelease = (TextView) findViewById(R.id.tvRelease);
        tvSinopsisFilm = (TextView) findViewById(R.id.tvSinopsisFilm);

        recyclerTriler = (RecyclerView) findViewById(R.id.recyclerTriler);
        recyclerReview = (RecyclerView) findViewById(R.id.recyclerReview);

        btnFavorit = (ShineButton) findViewById(R.id.po_image2);
    }

//    private void removeFromFavorite() {
//        if (MoviePersistenceUtils.removeMovieFromFavorites(mFilm, DetailActivity.this)) {
//            Toast.makeText(DetailActivity.this, "Successfully remove movie from favorites", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(DetailActivity.this, "Failed to remove movie from favorites", Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void addToFavorite() {
//        if (mFilm.getPosterFilm() == null) {
//            mFilm.setPosterFilm(ImageUtils.bytesFromImageView(ivDetailPoster));
//        }
//
////        if (MoviePersistenceUtils.addMovieToFavorites(mFilm, DetailActivity.this)) {
////            Toast.makeText(DetailActivity.this, "Successfully added to favorite", Toast.LENGTH_SHORT).show();
////        } else {
////            Toast.makeText(DetailActivity.this, "Failed to favorite", Toast.LENGTH_SHORT).show();
////        }
//    }
}
