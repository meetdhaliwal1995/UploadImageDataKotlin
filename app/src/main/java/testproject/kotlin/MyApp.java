package testproject.kotlin;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApp extends Application {

    private static Retrofit retrofit;
    private static Gson gson;
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static Gson getGson() {
        return gson;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://dev1.xicom.us/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gson = new GsonBuilder().setLenient().create();

    }
}