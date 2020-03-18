package singareddy.productionapps.dbd_contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import singareddy.productionapps.dbd_contacts.models.Contact;
import singareddy.productionapps.dbd_contacts.models.Name;

public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsAdapter.AllContactsVH> {

    public class AllContactsVH extends RecyclerView.ViewHolder {
        private TextView name, abbr;
        public AllContactsVH (View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onContactItemClicked(contacts.get(getAdapterPosition()));
                }
            });
            name = view.findViewById(R.id.contact_item_tv_name);
            abbr = view.findViewById(R.id.contact_item_tv_abbr);
        }
    }

    private Context context;
    private LayoutInflater inflater;
    private List<Contact> contacts;
    private ContactItemListener listener;

    public AllContactsAdapter(Context context, List<Contact> contacts, ContactItemListener listener) {
        this.context = context;
        this.contacts = contacts;
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @NonNull
    @Override
    public AllContactsVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AllContactsVH(inflater.inflate(R.layout.contact_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllContactsVH allContactsVH, int i) {
        Name name = contacts.get(i).getNameData();
        String fname = name.getFname(), lname = name.getLname();
        allContactsVH.name.setText(fname+" "+name.getMname()+" "+lname);
        allContactsVH.abbr.setText(fname.substring(0,1)+" "+lname.substring(0,1));
    }
}
