package icetea.com.hdvietplayer.player;

import android.net.Uri;

/**
 * Created by icetea on 5/4/2015.
 */
public class PlayerData {
    private String movieName;
    private boolean haveSub;
    private boolean isFavorite;
    private boolean haveEpisode;
    private int numberEpisode;
    private int movieId;
    private Uri contentUri;
    private Uri subUri;

    public PlayerData(String movieName, boolean haveSub, boolean isFavorite, boolean haveEpisode,
                      int numberEpisode, int movieId, Uri contentUri) {
        this.movieName = movieName;
        this.haveSub = haveSub;
        this.isFavorite = isFavorite;
        this.haveEpisode = haveEpisode;
        this.numberEpisode = numberEpisode;
        this.movieId = movieId;
        this.contentUri = contentUri;
    }

    public String getMovieName() {
        return movieName;
    }

    public boolean isHaveSub() {
        return haveSub;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public boolean isHaveEpisode() {
        return haveEpisode;
    }

    public int getNumberEpisode() {
        return numberEpisode;
    }

    public int getMovieId() {
        return movieId;
    }

    public Uri getContentUri() {
        return contentUri;
    }
    public void setContentUri(Uri uri){this.contentUri=uri;}
    public Uri getSubUri() {
        return subUri;
    }
}
