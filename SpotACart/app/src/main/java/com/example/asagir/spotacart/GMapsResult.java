package com.example.asagir.spotacart;

import java.util.ArrayList;

/**
 * Created by adi on 3/24/16.
 */
public class GMapsResult {

    private ArrayList<SearchItem> results;

    public GMapsResult(ArrayList<SearchItem> results) {
        this.results = results;
    }

    public ArrayList<SearchItem> getResults() {
        return results;
    }

    public void setResults(ArrayList<SearchItem> results) {
        this.results = results;
    }

}

