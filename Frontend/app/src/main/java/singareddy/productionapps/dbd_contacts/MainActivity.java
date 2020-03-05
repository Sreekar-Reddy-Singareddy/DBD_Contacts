package singareddy.productionapps.dbd_contacts;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import singareddy.productionapps.dbd_contacts.models.Address;
import singareddy.productionapps.dbd_contacts.models.Date;
import singareddy.productionapps.dbd_contacts.models.Phone;

public class MainActivity extends AppCompatActivity implements AddressListener{

    private EditText fname, mname, lname;
    private RecyclerView addresses, phones, dates;
    private Button save;
    private ImageView addAddress;

    AddressesAdapter addressesAdapter;
    PhoneAdapter phoneAdapter;
    DatesAdapter datesAdapter;
    List<Address> addressesData = new ArrayList<>();
    List<Phone> phonesData = new ArrayList<>();
    List<Date> datesData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        addAddress.setOnClickListener(this::addAddress);

        LinearLayoutManager addressLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager phoneLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager dateLayoutManager = new LinearLayoutManager(this);
        addressesAdapter = new AddressesAdapter(this, addressesData, this);
        addresses.setLayoutManager(addressLayoutManager);
        addresses.setAdapter(addressesAdapter);

        phoneAdapter = new PhoneAdapter(this, phonesData);
        phones.setAdapter(phoneAdapter);
        phones.setLayoutManager(phoneLayoutManager);

        datesAdapter = new DatesAdapter(this, datesData);
        dates.setAdapter(datesAdapter);
        dates.setLayoutManager(dateLayoutManager);
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
                addressObject.setAddressType((String)type.getTag());
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
