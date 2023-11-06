package com.example.midassignment;

import adapters.CctvAlertCardListAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.bumptech.glide.Glide;
import com.example.midassignment.databinding.FragmentCctvDetailBinding;
import data.Cctv;
import data.CctvAlert;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.CctvAlertService;
import service.CctvDetailService;
import service.RetrofitClient;
import view.CctvCard.VerticalSpaceItemDecoration;

public class CctvDetailFragment extends Fragment {

    private FragmentCctvDetailBinding binding;
    private Long cctvId;
    private CctvAlertCardListAdapter adapter;

    public CctvDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        binding = FragmentCctvDetailBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        cctvId = getArguments().getLong("cctvId");

        SwipeRefreshLayout srl = binding.cctvDetailSRL;

        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                srl.setRefreshing(false);
            }
        });

        getCctvDetailAndSetContents();
        getAlertsAndSetList();

        return v;
    }

    private void getCctvDetailAndSetContents() {
        CctvDetailService service = RetrofitClient.getRetrofitInstance().create(CctvDetailService.class);
        Call<Cctv> call = service.getDetail(cctvId);
        call.enqueue(new Callback<Cctv>() {
            @Override
            public void onResponse(Call<Cctv> call, Response<Cctv> response) {
                if (response.code() == 200 && response.body() != null) {
                    binding.cctvName.setText(response.body().getName());
                    Glide.with(binding.getRoot().getContext())
                         .load("https://eomgerm.pythonanywhere.com" + response.body().getCctvUrl())
                         .into(binding.cctvDetailImage);
                } else {
                    Log.w("CCTV Detail", String.valueOf(response.code()));
                    Toast.makeText(binding.getRoot().getContext(), "Response Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cctv> call, Throwable t) {
                Log.e("CCTV Detail", "Load Data Failed", t);
                Toast.makeText(binding.getRoot().getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAlertsAndSetList() {
        CctvAlertService service = RetrofitClient.getRetrofitInstance().create(CctvAlertService.class);
        Call<List<CctvAlert>> call = service.getAlertsByCctvId(cctvId);
        call.enqueue(new Callback<List<CctvAlert>>() {
            @Override
            public void onResponse(Call<List<CctvAlert>> call, Response<List<CctvAlert>> response) {
                if (response.code() == 200 && response.body() != null) {
                    setRecyclerView(response.body());
                } else {
                    Log.w("CCTV Detail", String.valueOf(response.code()));
                    Toast.makeText(binding.getRoot().getContext(), "Response Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CctvAlert>> call, Throwable t) {
                Log.e("CCTV Detail", "Load Data Failed" + t.getMessage(), t);
                Toast.makeText(binding.getRoot().getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecyclerView(List<CctvAlert> alertList) {
        RecyclerView recyclerView = binding.alertList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CctvAlertCardListAdapter(alertList);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(100));
    }

    private void refresh() {
        getCctvDetailAndSetContents();
        CctvAlertService service = RetrofitClient.getRetrofitInstance().create(CctvAlertService.class);
        Call<List<CctvAlert>> call = service.getAlertsByCctvId(cctvId);
        call.enqueue(new Callback<List<CctvAlert>>() {
            @Override
            public void onResponse(Call<List<CctvAlert>> call, Response<List<CctvAlert>> response) {
                if (response.code() == 200 && response.body() != null) {
                    adapter.setItems(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.w("CCTV Detail", String.valueOf(response.code()));
                    Toast.makeText(binding.getRoot().getContext(), "Response Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CctvAlert>> call, Throwable t) {
                Log.e("CCTV Detail", "Load Data Failed" + t.getMessage(), t);
                Toast.makeText(binding.getRoot().getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}