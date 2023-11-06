package adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.midassignment.R;
import data.Cctv;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class CctvCardListAdapter extends RecyclerView.Adapter<CctvCardListAdapter.ViewHolder> {

    private final Context ctx;
    private List<Cctv> items;

    public CctvCardListAdapter(Context ctx, List<Cctv> items) {
        this.ctx = ctx;
        this.items = items;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.cctv_card, parent, false);

        return new ViewHolder(parent.getContext(), itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Cctv cctv = items.get(position);
        holder.setItem(cctv);

        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) ctx;
                Bundle bundle = new Bundle();
                bundle.putLong("cctvId", cctv.getCctvId());
                Navigation.findNavController(view).navigate(R.id.action_cctvListFragment_to_cctvDetailFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Cctv> items) {
        this.items = items;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        private final TextView cctvTextView;
        private final ImageView cctvImageView;

        public ViewHolder(Context context, @NonNull @NotNull View itemView) {
            super(itemView);

            this.context = context;
            cctvTextView = itemView.findViewById(R.id.cctvText);
            cctvImageView = itemView.findViewById(R.id.cctvImage);
        }

        public void setItem(Cctv item) {
            setCctvText(item.getName());
            setCctvImage(item.getCctvUrl());
        }

        private void setCctvText(String text) {
            cctvTextView.setText(text);
        }

        private void setCctvImage(String url) {
            Glide.with(context).load("https://eomgerm.pythonanywhere.com" + url).into(cctvImageView);
        }
    }
}
