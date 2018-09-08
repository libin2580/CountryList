package app.pie.district.cyberpark.com.countrylist.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import app.pie.district.cyberpark.com.countrylist.model.Countrydb_model;
import app.pie.district.cyberpark.com.countrylist.MainActivity;
import app.pie.district.cyberpark.com.countrylist.R;

/**
 * Created by libin on 22/12/2017.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>  {
    List<Countrydb_model> country_list;
    List<Countrydb_model> country_list1;
    Context mcontext;



    public DataAdapter(ArrayList<Countrydb_model> cl, MainActivity context) {
        mcontext = context;
        this.country_list = cl;

        this.country_list1 = cl;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_row_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, final  int i) {
        viewHolder.country_name.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "Mark Simonson - Proxima Nova Alt Regular-webfont.ttf"));

        viewHolder.country_name.setText(country_list.get(i).getName());
       // Picasso.with(mcontext).load(contactList.get(i).getAlpha2_code()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(viewHolder.imageView);
    }
    @Override
    public int getItemCount() {
        return country_list.size();
    }

    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    country_list = country_list1;
                } else {

                    ArrayList<Countrydb_model> filteredList = new ArrayList<>();
                    HashSet<Countrydb_model> hashSet = new HashSet<Countrydb_model>();
                    hashSet.addAll(filteredList);
                    filteredList.clear();
                    filteredList.addAll(hashSet);
                    for (Countrydb_model model : country_list1) {

                        if (model.getName().toLowerCase().contains(charString.toLowerCase())||model.getName().toLowerCase().contains(charString.toLowerCase()) ) {

                            filteredList.add(model);
                        }
                    }

                    country_list = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = country_list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                country_list = (ArrayList<Countrydb_model>) filterResults.values;
                HashSet<Countrydb_model> hashSet = new HashSet<Countrydb_model>();
                hashSet.addAll(country_list);
                country_list.clear();
                country_list.addAll(hashSet);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         RelativeLayout rootView;
        ImageView imageView;
        TextView country_name;
        TextView textViewEmail;
        public ViewHolder(View rootView) {
            super(rootView);
            country_name = (TextView) rootView.findViewById(R.id.country_name);

        }

    }

}