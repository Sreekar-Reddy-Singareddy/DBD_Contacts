package singareddy.productionapps.dbd_contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import singareddy.productionapps.dbd_contacts.models.Phone;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneVH> {

    public class PhoneVH extends RecyclerView.ViewHolder{
        private TextView type, phone;
        private ImageView delete;

        public PhoneVH (View view) {
            super(view);
            delete = view.findViewById(R.id.add_phone_et_areacode);
            phone = view.findViewById(R.id.date_item_tv_date);
            type = view.findViewById(R.id.date_item_tv_type);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    phones.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    private Context context;
    private LayoutInflater inflater;
    private List<Phone> phones;

    public PhoneAdapter(Context c, List<Phone> data) {
        context = c;
        phones = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }

    @NonNull
    @Override
    public PhoneVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PhoneVH(inflater.inflate(R.layout.phone_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneVH phoneVH, int i) {
        Phone phoneObject = phones.get(i);
        phoneVH.phone.setText(phoneObject.getAreaCode().toString()+"-"+phoneObject.getNumber().toString());
        phoneVH.type.setText(phoneObject.getPhoneType());
    }
}

