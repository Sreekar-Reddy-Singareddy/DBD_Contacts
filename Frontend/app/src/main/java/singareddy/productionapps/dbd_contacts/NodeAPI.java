package singareddy.productionapps.dbd_contacts;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import singareddy.productionapps.dbd_contacts.models.Contact;
import singareddy.productionapps.dbd_contacts.models.Name;

public interface NodeAPI {
    @GET("/add")
    public Call<Name> testServer();

    @POST("/add")
    public Call<Object> addContact(@Body Contact contact);
}
