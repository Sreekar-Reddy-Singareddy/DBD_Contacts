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

import java.text.SimpleDateFormat;
import java.util.List;

import singareddy.productionapps.dbd_contacts.models.Date;

public class DatesAdapter extends RecyclerView.Adapter<DatesAdapter.DatesVH> {

    public class DatesVH extends RecyclerView.ViewHolder {
        private TextView type, date;
        private ImageView delete;

        public DatesVH (View view) {
            super(view);
            delete = view.findViewById(R.id.date_item_iv_delete);
            type = view.findViewById(R.id.date_item_tv_type);
            date = view.findViewById(R.id.date_item_tv_date);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dates.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    private Context context;
    private List<Date> dates;
    private LayoutInflater inflater;

    public DatesAdapter (Context c, List<Date> data) {
        context = c;
        dates = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    @NonNull
    @Override
    public DatesVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DatesVH(inflater.inflate(R.layout.dates_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DatesVH datesVH, int i) {
        Date dateObject = dates.get(i);
        datesVH.type.setText(dateObject.getDateType());
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("MM-dd-YYYY");
        datesVH.date.setText(format.format(dateObject.getDate()));
    }
}
