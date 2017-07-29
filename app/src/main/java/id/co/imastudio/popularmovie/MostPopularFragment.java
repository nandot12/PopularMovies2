package id.co.imastudio.popularmovie;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MostPopularFragment extends Fragment {

    private List<FilmModel> filmList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public MostPopularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentView = inflater.inflate(R.layout.fragment_most_popular, container, false);
        filmList = new ArrayList<>();

        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);

        if (isNetworkConnected()){
            getDataOnline();
        } else {
            Snackbar.make(fragmentView, "No Interner Connection", Snackbar.LENGTH_LONG)
                    .setAction("Try Again", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getDataOnline();
                        }
                    })
                    .show();
        }
        return fragmentView;
    }

    private void getDataOnline() {

        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Loading Data", "Mohon Bersabar", false, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (isNetworkConnected()){
                    return;
                } else {
                    new AlertDialog.Builder(getActivity())
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
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + ApiKey.DATA_KEY + "&language=en-US&page=1";

        JsonObjectRequest ambildata = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //ntar kalo respon ngapain
                loading.hide();
                try {
                    JSONArray arrayresults = response.getJSONArray("results");
                    for (int i = 0; i < arrayresults.length(); i++) {
                        JSONObject json = arrayresults.getJSONObject(i);
                        Log.d("Hasil Json :", "" + json);
                        FilmModel film2 = new FilmModel();
                        film2.setIdFilm(json.getString("id"));
                        film2.setGambarFilm(json.getString("poster_path"));
                        film2.setJudulFilm(json.getString("title"));
                        film2.setPosterFilm(json.getString("backdrop_path"));
                        film2.setSinopsisFilm(json.getString("overview"));
                        film2.setRatingFilm(json.getString("vote_average"));
                        film2.setReleaseFilm(json.getString("release_date"));
                        filmList.add(film2);
                    }
                    adapter = new FilmAdapter(filmList, getActivity());
                    layoutManager = new GridLayoutManager(getActivity(), 2);

                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(layoutManager);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error get json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //kalo error ngapain
                Toast.makeText(getActivity(), "Error no response : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue antrian = Volley.newRequestQueue(getActivity());
        antrian.add(ambildata);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
