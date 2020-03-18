package singareddy.productionapps.dbd_contacts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static Retrofit retroServer;

    public static Retrofit getInstance() {
        if (retroServer == null) {
            retroServer = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.12:3000")
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
        }
        return retroServer;
    }
}
