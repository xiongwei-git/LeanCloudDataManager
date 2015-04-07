package com.ted.lcmanager.app.model;

import com.ted.lcmanager.app.model.Person;

import java.util.List;

/**
 * Created by Ted on 2015/3/29.
 */
public class RetrofitResults {
    private List<Person> results;

    public List<Person> getResults() {
        return results;
    }

    public void setResults(List<Person> results) {
        this.results = results;
    }
}
