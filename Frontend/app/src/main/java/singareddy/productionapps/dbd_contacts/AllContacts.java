package singareddy.productionapps.dbd_contacts;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import singareddy.productionapps.dbd_contacts.models.Contact;

public class AllContacts extends AppCompatActivity implements ContactItemListener {

    private RecyclerView allContacts;
    private SearchView searchView;
    private FloatingActionButton addContact;
    private AllContactsAdapter adapter;
    private List<Contact> contactsData;
    private String searchCriteria = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contacts);
        contactsData = new ArrayList<>();
        initialiseData();
        initialiseUI();
    }

    @Override
    protected void onResume() {
        getContactsContaining(searchCriteria);
        super.onResume();
    }

    private void initialiseData() {

    }

    private void initialiseUI() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new AllContactsAdapter(this, contactsData, this);
        searchView = findViewById(R.id.all_contacts_sv);
        addContact = findViewById(R.id.all_contacts_fab);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllContacts.this, ContactDetailsActivity.class);
                intent.putExtra("New",true);
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCriteria = query;
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Use this string to make new http calls and get the contacts
                searchCriteria = newText;
                getContactsContaining(newText);
                return false;
            }
        });
        allContacts = findViewById(R.id.all_contacts_rv);
        allContacts.setAdapter(adapter);
        allContacts.setLayoutManager(manager);
    }

    private void getContactsContaining(String searchCriteria) {
        Retrofit retrofit = RetrofitService.getInstance();
        NodeAPI api = retrofit.create(NodeAPI.class);
        Call<List<Contact>> contactsFromDB = api.getAllContacts(searchCriteria);
        contactsFromDB.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                contactsData.clear();
                contactsData.addAll(response.body());
                adapter.notifyDataSetChanged();
                System.out.println("Number of Contacts: "+response.body().size());
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                System.out.println("Exception *: "+t.getMessage());
            }
        });
    }

    @Override
    public void onContactItemClicked(Contact contact) {
        int id = contact.getNameData().get_id();
        Intent intent = new Intent(this, ContactDetailsActivity.class);
        intent.putExtra("Contact_Id", id);
        intent.putExtra("New", false);
        startActivityForResult(intent, 123);
    }
}
