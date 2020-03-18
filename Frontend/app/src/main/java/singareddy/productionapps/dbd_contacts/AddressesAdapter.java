package singareddy.productionapps.dbd_contacts;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import singareddy.productionapps.dbd_contacts.models.Address;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.Address_VH> {

    public class Address_VH extends RecyclerView.ViewHolder{
        private TextView address, city, state, type;
        private ImageView delete;

        public Address_VH(View view) {
            super(view);
            address = view.findViewById(R.id.address_item_tv_address);
            city = view.findViewById(R.id.address_item_tv_city);
            state = view.findViewById(R.id.address_item_tv_state);
            type = view.findViewById(R.id.address_item_tv_type);
            delete = view.findViewById(R.id.address_item_iv_delete);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAddressItemClicked(addresses.get(getAdapterPosition()));
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addresses.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    private Context context;
    private LayoutInflater inflater;
    private List<Address> addresses;
    private AddressListener listener;
    private boolean newContact;

    public AddressesAdapter(Context context, List<Address> addresses, AddressListener listener, boolean newContact){
        this.context = context;
        this.addresses = addresses;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.newContact = newContact;
    }

    public boolean isNewContact() {
        return newContact;
    }

    public void setNewContact(boolean newContact) {
        this.newContact = newContact;
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    @NonNull
    @Override
    public Address_VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Address_VH holder = new Address_VH(inflater.inflate(R.layout.address_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Address_VH address_vh, int i) {
        Address address = addresses.get(i);
        if (!newContact) address_vh.delete.setVisibility(View.GONE);
        else address_vh.delete.setVisibility(View.VISIBLE);
        address_vh.address.setText(address.getAddress());
        address_vh.city.setText(address.getCity());
        if(address.getZipcode() == 0) address_vh.state.setText(address.getState());
        else address_vh.state.setText(address.getState()+" - "+address.getZipcode());
        address_vh.type.setText(address.getAddressType());
    }
}
