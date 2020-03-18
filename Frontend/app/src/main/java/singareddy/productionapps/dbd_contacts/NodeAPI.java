package singareddy.productionapps.dbd_contacts;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import singareddy.productionapps.dbd_contacts.models.Contact;
import singareddy.productionapps.dbd_contacts.models.Name;

public interface NodeAPI {
    @GET("")
    public Call<Name> testServer();

    @POST("/add")
    public Call<Object> addContact(@Body Contact contact);

    @GET("/")
    public Call<List<Contact>> getAllContacts(@Query("search_criteria") String searcQuery);

    @GET("/")
    public Call<Contact> getContactWithID(@Query("contact_id") int id);

    @DELETE("/")
    public Call<Integer> deleteContact(@Query("contact_id") int id);

    @POST("/update")
    public Call<Integer> updateContact(@Body Contact contactBeingDisplayed);
}
