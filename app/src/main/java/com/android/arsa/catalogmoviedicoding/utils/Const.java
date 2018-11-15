package com.android.arsa.catalogmoviedicoding.utils;

import com.android.arsa.catalogmoviedicoding.BuildConfig;

public class Const {
    public static final String SORT_NOW_PLAYING = "upcoming";
    public static final String SORT_UPCOMING = "now_playing";

    public static String sortMovie(String movieType) {
        return BuildConfig.BASE_URL + "movie/" + movieType + "?api_key=" + BuildConfig.API_KEY + "&language=" + BuildConfig.EXTRA_LANG;
    }

    public static String searchMovie(String query) {
        return BuildConfig.BASE_URL + "search/movie?api_key=" + BuildConfig.API_KEY + "&language=" + BuildConfig.EXTRA_LANG + "&query=" + query;
    }

    public static String loadMoviePoster(String fileName) {
        return BuildConfig.POSTER_PATH + fileName;
    }

    public static String loadMovieBackdrop(String fileName) {
        return BuildConfig.BACKDROP_PATH + fileName;
    }

}
