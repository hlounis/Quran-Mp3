package malek.com.quranmp3.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import malek.com.quranmp3.R;
import malek.com.quranmp3.databinding.ItemListBinding;
import malek.com.quranmp3.models.ApiModel;
import malek.com.quranmp3.tools.RecylerViewClickItem;

/**
 * Created by lenovo on 14/05/2016.
 */
public class QuraAdapter extends RecyclerView.Adapter<QuraAdapter.ViewHolder> {

    private List<ApiModel> apiModels = new ArrayList<>();
    private RecylerViewClickItem recylerViewClickItem;

    public QuraAdapter(RecylerViewClickItem recylerViewClickItem) {
        this.recylerViewClickItem = recylerViewClickItem;
    }

    public List<ApiModel> getApiModels() {
        return apiModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListBinding listItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_list, parent, false);
        return new ViewHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.listBinding.setItem(apiModels.get(position));
        holder.listBinding.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recylerViewClickItem != null)
                    recylerViewClickItem.onClickItem(holder.getAdapterPosition());
            }
        });

    }

    public void addItem(ApiModel apiModel) {
        apiModels.add(apiModel);
        notifyItemInserted(apiModels.size() - 1);
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        apiModels.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return apiModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemListBinding listBinding;

        public ViewHolder(ItemListBinding listBinding) {
            super(listBinding.getRoot());
            this.listBinding = listBinding;

        }
    }
}
