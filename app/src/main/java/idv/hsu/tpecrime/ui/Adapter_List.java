package idv.hsu.tpecrime.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import idv.hsu.tpecrime.R;
import idv.hsu.tpecrime.data.IResults;
import idv.hsu.tpecrime.data.ResultsTheft;

public class Adapter_List extends BaseAdapter {
    private static final String TAG = Adapter_List.class.getSimpleName();

    private LayoutInflater mInflater;
    private List<IResults> listData;

    public Adapter_List(LayoutInflater inflater, List<IResults> data) {
        mInflater = inflater;
        listData = data;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        private TextView date;
        private TextView time;
        private TextView address;
        private ViewHolder(View view) {
            date = (TextView) view.findViewById(R.id.date);
            time = (TextView) view.findViewById(R.id.time);
            address = (TextView) view.findViewById(R.id.address);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            rowView = mInflater.inflate(R.layout.list_cell, null);
            ViewHolder holder = new ViewHolder(rowView);
            rowView.setTag(holder);
        }
        final ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.date.setText(listData.get(position).getDate());
        holder.time.setText(listData.get(position).getTime());
        holder.address.setText(listData.get(position).getLocation());

        return rowView;
    }
}
