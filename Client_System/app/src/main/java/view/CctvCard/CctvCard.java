package view.CctvCard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import com.bumptech.glide.Glide;
import com.example.midassignment.R;
import org.jetbrains.annotations.NotNull;

public class CctvCard extends CardView {

    private TextView cctvText;
    private ImageView cctvImage;

    public CctvCard(@NonNull @NotNull Context context) {
        super(context);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cctv_card, this, true);
        this.setBackgroundColor(Color.TRANSPARENT);

        cctvText = findViewById(R.id.cctvText);
        cctvImage = findViewById(R.id.cctvImage);
    }

    public void setCctvText(String cctv) {
        cctvText.setText(cctv);
    }

    public void setCctvImage(String url) {
        Glide.with(this).load(url).into(cctvImage);
    }
}
