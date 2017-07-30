package id.co.imastudio.popularmovie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by idn on 5/13/2017.
 */

public class FilmModel implements Parcelable{

    public static final String TAG_MOVIES = "movies";

    private int idFilm;
    private String judulFilm;
    private String gambarFilm;
    private String posterFilm;
    private String sinopsisFilm;
    private String ratingFilm;
    private String releaseFilm;

    private boolean favorite = false;

    protected FilmModel(Parcel in) {
        idFilm = in.readInt();
        judulFilm = in.readString();
        gambarFilm = in.readString();
        posterFilm = in.readString();
        sinopsisFilm = in.readString();
        ratingFilm = in.readString();
        releaseFilm = in.readString();
        favorite = in.readByte() != 0;
    }

    public static final Creator<FilmModel> CREATOR = new Creator<FilmModel>() {
        @Override
        public FilmModel createFromParcel(Parcel in) {
            return new FilmModel(in);
        }

        @Override
        public FilmModel[] newArray(int size) {
            return new FilmModel[size];
        }
    };

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getPosterFilm() {
        return posterFilm;
    }

    public void setPosterFilm(String posterFilm) {
        this.posterFilm = posterFilm;
    }

    public String getSinopsisFilm() {
        return sinopsisFilm;
    }

    public void setSinopsisFilm(String sinopsisFilm) {
        this.sinopsisFilm = sinopsisFilm;
    }

    public String getRatingFilm() {
        return ratingFilm;
    }

    public void setRatingFilm(String ratingFilm) {
        this.ratingFilm = ratingFilm;
    }

    public String getReleaseFilm() {
        return releaseFilm;
    }

    public void setReleaseFilm(String releaseFilm) {
        this.releaseFilm = releaseFilm;
    }

    public FilmModel() {
    }

    //bikin Setter and Getter
    //Alt +Insert > Getter and setter


    public String getJudulFilm() {
        return judulFilm;
    }

    public void setJudulFilm(String judulFilm) {
        this.judulFilm = judulFilm;
    }

    public String getGambarFilm() {
        return gambarFilm;
    }

    public void setGambarFilm(String gambarFilm) {
        this.gambarFilm = gambarFilm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idFilm);
        dest.writeString(gambarFilm);
        dest.writeString(sinopsisFilm);
        dest.writeString(releaseFilm);
        dest.writeString(judulFilm);
        dest.writeString(posterFilm);
        dest.writeString(ratingFilm);
        dest.writeByte((byte) (favorite ? 1 : 0));
    }
}
