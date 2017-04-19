package mdweb.com.quranmp3.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mdweb.com.quranmp3.R;
import mdweb.com.quranmp3.models.Qura;
import mdweb.com.quranmp3.tools.RecylerViewClickItem;

/**
 * Created by lenovo on 14/05/2016.
 */
public class QuraAdapter extends RecyclerView.Adapter<QuraAdapter.ViewHolder> {
    private Context context;
    private int resource;

    public void setQuras(ArrayList<Qura> quras) {
        this.quras = quras;
    }

    public ArrayList<Qura> getQuras() {
        return quras;
    }

    private ArrayList<Qura> quras;

    public void setRecylerViewClickItem(RecylerViewClickItem recylerViewClickItem) {
        this.recylerViewClickItem = recylerViewClickItem;
    }

    private RecylerViewClickItem recylerViewClickItem;


    public QuraAdapter(Context context, int resource, ArrayList<Qura> quras) {
        this.context = context;
        this.resource = resource;
        this.quras = quras;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.titre.setText(quras.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recylerViewClickItem != null)
                    recylerViewClickItem.onClickItem(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return quras.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titre;

        public ViewHolder(View itemView) {
            super(itemView);
            titre = (TextView) itemView.findViewById(R.id.titre);

        }
    }
}
