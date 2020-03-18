package singareddy.productionapps.dbd_contacts;

import android.annotation.SuppressLint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import singareddy.productionapps.dbd_contacts.models.Address;
import singareddy.productionapps.dbd_contacts.models.Contact;
import singareddy.productionapps.dbd_contacts.models.Name;
import singareddy.productionapps.dbd_contacts.models.Date;
import singareddy.productionapps.dbd_contacts.models.Phone;

import static android.view.View.GONE;

public class ContactDetailsActivity extends AppCompatActivity implements AddressListener{

    private static boolean NEW_CONTACT = true;
    private static int ID = -1;

    private EditText fname, mname, lname;
    private RecyclerView addresses, phones, dates;
    private Button save;
    private ImageView addAddress, addPhone, addDate;
    private MenuItem edit, delete;
    private Contact contactBeingDisplayed;

    AddressesAdapter addressesAdapter;
    PhoneAdapter phoneAdapter;
    DatesAdapter datesAdapter;
    List<Address> addressesData = new ArrayList<>();
    List<Phone> phonesData = new ArrayList<>();
    List<Date> datesData = new ArrayList<>();
    Name nameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getExtras().getBoolean("New") == true) {
            // This is new contact
            NEW_CONTACT = true;
            ID = -1;
        }
        else {
            // This is old contact, here to modify
            NEW_CONTACT = false;
            ID = getIntent().getExtras().getInt("Contact_Id");
        }
        dummyData();
        initialiseUI();
    }

    private void initialiseUI() {
        fname = findViewById(R.id.first_name);
        mname = findViewById(R.id.middle_name);
        lname = findViewById(R.id.last_name);
        save = findViewById(R.id.save_bt);
        addresses = findViewById(R.id.addresses_rv);
        phones = findViewById(R.id.phones_rv);
        dates = findViewById(R.id.dates_rv);
        addAddress = findViewById(R.id.add_address_iv);
        addPhone = findViewById(R.id.add_phone_button);
        addDate = findViewById(R.id.add_date_button);
        addAddress.setOnClickListener(this::addAddress);
        addPhone.setOnClickListener(this::addPhone);
        addDate.setOnClickListener(this::addDate);
        save.setOnClickListener(this::saveContact);

        LinearLayoutManager addressLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager phoneLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager dateLayoutManager = new LinearLayoutManager(this);
        addressesAdapter = new AddressesAdapter(this, addressesData, this, NEW_CONTACT);
        addresses.setLayoutManager(addressLayoutManager);
        addresses.setAdapter(addressesAdapter);

        phoneAdapter = new PhoneAdapter(this, phonesData, NEW_CONTACT);
        phones.setAdapter(phoneAdapter);
        phones.setLayoutManager(phoneLayoutManager);

        datesAdapter = new DatesAdapter(this, datesData, NEW_CONTACT);
        dates.setAdapter(datesAdapter);
        dates.setLayoutManager(dateLayoutManager);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void toggleViews() {
        // Toggle views based on the new or old contact
        if (!NEW_CONTACT) {
            save.setVisibility(GONE);
            addAddress.setVisibility(GONE);
            addPhone.setVisibility(GONE);
            addDate.setVisibility(GONE);
            edit.setVisible(true); edit.setEnabled(true);
            fname.setEnabled(false);
            mname.setEnabled(false);
            lname.setEnabled(false);
        }
        else {
            save.setVisibility(View.VISIBLE);
            addAddress.setVisibility(View.VISIBLE);
            addPhone.setVisibility(View.VISIBLE);
            addDate.setVisibility(View.VISIBLE);
            edit.setVisible(false); edit.setEnabled(false);
            fname.setEnabled(true);
            mname.setEnabled(true);
            lname.setEnabled(true);
        }
        addressesAdapter.setNewContact(NEW_CONTACT);
        phoneAdapter.setNewContact(NEW_CONTACT);
        datesAdapter.setNewContact(NEW_CONTACT);
        addressesAdapter.notifyDataSetChanged();
        phoneAdapter.notifyDataSetChanged();
        datesAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_menu_item){
            NEW_CONTACT = !NEW_CONTACT;
            toggleViews();
        }
        else if (item.getItemId() == R.id.delete_menu_item) {
            Retrofit retrofit = RetrofitService.getInstance();
            NodeAPI api = retrofit.create(NodeAPI.class);
            Call<Integer> call = api.deleteContact(ID);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    System.out.println("Deleted: "+response.body());
                    System.out.println("Code: "+response.code());
                    finish();
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    System.out.println("Exception: "+t.getMessage());
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.contact_details_menu, menu);
        edit = menu.findItem(R.id.edit_menu_item);
        delete = menu.findItem(R.id.delete_menu_item);
        toggleViews();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!NEW_CONTACT){
            Retrofit retrofit = RetrofitService.getInstance();
            NodeAPI api = retrofit.create(NodeAPI.class);
            Call<Contact> call = api.getContactWithID(ID);
            call.enqueue(new Callback<Contact>() {
                @Override
                public void onResponse(Call<Contact> call, Response<Contact> response) {
                    contactBeingDisplayed = response.body();
                    Name nameObj = contactBeingDisplayed.getNameData();
                    List<Address> addressObj = contactBeingDisplayed.getAddressData();
                    List<Phone> phoneObj = contactBeingDisplayed.getPhoneData();
                    List<Date> dateObj = contactBeingDisplayed.getDateData();
                    fname.setText(nameObj.getFname());
                    mname.setText(nameObj.getMname());
                    lname.setText(nameObj.getLname());
                    addressesData.clear(); addressesData.addAll(addressObj);
                    phonesData.clear(); phonesData.addAll(phoneObj);
                    datesData.clear(); datesData.addAll(dateObj);
                    addressesAdapter.notifyDataSetChanged();
                    phoneAdapter.notifyDataSetChanged();
                    datesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Contact> call, Throwable t) {
                    System.out.println("Exception: "+t.getMessage());
                }
            });
        }
    }

    private void saveContact(View view) {
        String fName = fname.getText().toString().trim();
        String mName = mname.getText().toString().trim();
        String lName = lname.getText().toString().trim();
        if (fName.length() > 1) fName = fName.substring(0,1).toUpperCase() + fName.substring(1);
        if (mName.length() > 1) mName = mName.substring(0,1).toUpperCase() + mName.substring(1);
        if (lName.length() > 1) lName = lName.substring(0,1).toUpperCase() + lName.substring(1);

        nameData = new Name(fName, mName, lName);
        if (ID >= 0) {
            nameData.set_id(ID);
            contactBeingDisplayed.setNameData(nameData);
            updateContact();
            return;
        }
        Contact contact = new Contact(nameData, addressesData, phonesData, datesData);

        Retrofit retrofit = RetrofitService.getInstance();
        NodeAPI api = retrofit.create(NodeAPI.class);
        Call<Object> call = api.addContact(contact);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                System.out.println("Connection Status: "+response.code());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println("Error: "+t.getMessage());
            }
        });
    }

    private void updateContact() {
        contactBeingDisplayed.setAddressData(addressesData);
        contactBeingDisplayed.setPhoneData(phonesData);
        contactBeingDisplayed.setDateData(datesData);
        Retrofit retrofit = RetrofitService.getInstance();
        NodeAPI api = retrofit.create(NodeAPI.class);
        Call<Integer> call = api.updateContact(contactBeingDisplayed);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                System.out.println("Response: "+response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                System.out.println("Exception: "+t.getMessage());
            }
        });
    }

    private void addPhone(View view) {
        Phone phoneObject = new Phone();
        AlertDialog dialog;
        View dialogView;
        Button done, cancel;
        EditText areacode, phone;
        RadioGroup type;
        LayoutInflater inflater = LayoutInflater.from(this);
        dialogView = inflater.inflate(R.layout.add_phone_view, null);
        done = dialogView.findViewById(R.id.phone_item_bt_done);
        cancel = dialogView.findViewById(R.id.phone_item_bt_cancel);
        areacode = dialogView.findViewById(R.id.add_phone_et_areacode);
        phone = dialogView.findViewById(R.id.add_phone_et_number);
        type = dialogView.findViewById(R.id.add_phone_rg);

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(dialogView);
        dialog = builder.create();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneObject.setAreaCode(Integer.parseInt(areacode.getText().toString()));
                phoneObject.setNumber(Integer.parseInt(phone.getText().toString()));
                phoneObject.setPhoneType((String) dialogView.findViewById(type.getCheckedRadioButtonId()).getTag());
                phonesData.add(phoneObject);
                phoneAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @SuppressLint("NewApi")
    private void addDate(View view) {
        Date dateObject = new Date();
        java.util.Date selectedDate;
        AlertDialog dialog;
        View dialogView;
        Button done, cancel;
        EditText type;
        DatePicker date;
        LayoutInflater inflater = LayoutInflater.from(this);
        dialogView = inflater.inflate(R.layout.add_date_view,null);
        done = dialogView.findViewById(R.id.add_date_bt_done);
        cancel = dialogView.findViewById(R.id.add_date_bt_cancel);
        type = dialogView.findViewById(R.id.add_date_et_type);
        date = dialogView.findViewById(R.id.add_date_et_date);

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(dialogView);
        dialog = builder.create();

        date.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat formatter = new SimpleDateFormat();
                formatter.applyPattern("MM-dd-YYYY");
                try {
                    dateObject.setDate(formatter.parse((monthOfYear+1)+"-"+dayOfMonth+"-"+year));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateObject.setDateType(type.getText().toString());
                datesData.add(dateObject);
                datesAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addAddress(View view) {
        Address addressObject = new Address();
        AlertDialog dialog;
        View dialogView;
        Button done, cancel;
        EditText address, city, state, zipcode;
        RadioGroup type;
        LayoutInflater inflater = LayoutInflater.from(this);
        dialogView = inflater.inflate(R.layout.add_address_view, null);
        address = dialogView.findViewById(R.id.add_address_tv_address);
        city = dialogView.findViewById(R.id.add_address_tv_city);
        state = dialogView.findViewById(R.id.add_address_tv_state);
        zipcode = dialogView.findViewById(R.id.add_address_tv_zipcode);
        type = dialogView.findViewById(R.id.add_address_rg_types);
        done = dialogView.findViewById(R.id.add_address_bt_done);
        cancel = dialogView.findViewById(R.id.add_address_bt_cancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(dialogView);
        dialog = builder.create();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressObject.setAddress(address.getText().toString());
                addressObject.setCity(city.getText().toString());
                addressObject.setState(state.getText().toString());
                addressObject.setZipcode(Integer.parseInt(zipcode.getText().toString()));
                addressObject.setAddressType((String)dialogView.findViewById(type.getCheckedRadioButtonId()).getTag());
                addressesData.add(addressObject);
                addressesAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void dummyData() {
        addressesData.add(new Address("Home", "7815 McCallum Blvd", "Dallas", "Texas", 75252));
        addressesData.add(new Address("Work", "7825 McCallum Blvd", "Arlington", "Texas", 75342));
        addressesData.add(new Address("Home", "78#C McCallum Blvd", "Austin", "Texas", 75765));
        addressesData.add(new Address("Office", "123 McCallum Blvd", "Atlanta", "Georgia", 71253));

        phonesData.add(new Phone("Work", 682, 5529078));
        phonesData.add(new Phone("Office", 682, 5529144));
        phonesData.add(new Phone("Home", 805, 6367767));

        datesData.add(new Date("Birthday", new java.util.Date()));
        datesData.add(new Date("Anniversary", new java.util.Date()));
        datesData.add(new Date("Wedding Day", new java.util.Date()));
        datesData.add(new Date("Joining Day", new java.util.Date()));
    }

    @Override
    public void onAddressItemClicked(Address address) {
        System.out.println("Address Clicked");
    }
}
