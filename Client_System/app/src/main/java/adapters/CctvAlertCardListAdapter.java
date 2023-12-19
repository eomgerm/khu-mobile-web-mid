package adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.midassignment.databinding.CctvAlertCardBinding;

import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.List;

import data.response.CctvAlertResponse;

public class CctvAlertCardListAdapter extends RecyclerView.Adapter<CctvAlertCardListAdapter.ViewHolder> {

    private CctvAlertCardBinding binding;

    private List<CctvAlertResponse> items;

    public CctvAlertCardListAdapter(List<CctvAlertResponse> items) {
        this.items = items;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = CctvAlertCardBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        CctvAlertResponse alert = items.get(position);
        holder.setItem(alert);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<CctvAlertResponse> items) {
        this.items = items;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CctvAlertCardBinding binding;
        private final TextView alertTimeTextView;
        private final ImageView alertCctvImageView;

        public ViewHolder(CctvAlertCardBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
            alertTimeTextView = binding.alertTimeText;
            alertCctvImageView = binding.alertCctvImage;
        }

        public void setItem(CctvAlertResponse item) {
            setAlertTime(item.getAlertTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            setAlertCctvImage(item.getAlertImage());
        }

        public void setAlertTime(String text) {
            this.alertTimeTextView.setText(text);
        }

        public void setAlertCctvImage(String url) {
            Glide.with(binding.getRoot()).load("http://10.0.2.2:8000" + url).into(alertCctvImageView);
        }
    }
}
