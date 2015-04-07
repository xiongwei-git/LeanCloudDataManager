package com.ted.lcmanager.app.network;

/**
 * Created by Ted on 2015/3/27.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ted.lcmanager.app.Constants;
import com.ted.lcmanager.app.model.Person;
import com.ted.lcmanager.app.model.RetrofitResults;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;

public class RetrofitClient {

    public static final String API_URL = "https://leancloud.cn";
    public static final String API_VERSION = "/1.1";

    public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    public interface PersonGetter {
        @GET("/classes/{class}")
        RetrofitResults getResults(
                @Path("class") String className
        );
    }

    public static void main(String... args) {

//        Gson gson = new GsonBuilder()
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL+API_VERSION)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Cache-Control", "public, max-age=" + 60 * 60 * 4);
                        request.addHeader("Content-Type", "application/json");
                        request.addHeader("X-AVOSCloud-Application-Id", Constants.SERVER_ID);
                        request.addHeader("X-AVOSCloud-Application-Key", Constants.SERVER_KEY);
                    }
                })
                .build();

        PersonGetter personGetter = restAdapter.create(PersonGetter.class);

        RetrofitResults results = personGetter.getResults("XiongWeiData");
        for (Person person : results.getResults()) {
            System.out.println(person.getName() + " (" + person.getAge() + ")");
        }
    }
}

