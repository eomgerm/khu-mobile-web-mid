package com.example.midassignment;

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

import java.util.List;

import adapters.CctvAlertCardListAdapter;
import data.response.CctvAlertResponse;
import data.response.CctvResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.CctvService;
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
        CctvService service = RetrofitClient.getRetrofitInstance(getContext()).create(CctvService.class);
        Call<CctvResponse> call = service.getDetail(cctvId);
        call.enqueue(new Callback<CctvResponse>() {
            @Override
            public void onResponse(Call<CctvResponse> call, Response<CctvResponse> response) {
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
            public void onFailure(Call<CctvResponse> call, Throwable t) {
                Log.e("CCTV Detail", "Load Data Failed", t);
                Toast.makeText(binding.getRoot().getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAlertsAndSetList() {
        CctvService cctvService = RetrofitClient.getRetrofitInstance(getContext()).create(CctvService.class);
        Call<List<CctvAlertResponse>> call = cctvService.getAlertsByCctvId(cctvId);
        call.enqueue(new Callback<List<CctvAlertResponse>>() {
            @Override
            public void onResponse(Call<List<CctvAlertResponse>> call, Response<List<CctvAlertResponse>> response) {
                if (response.code() == 200 && response.body() != null) {
                    setRecyclerView(response.body());
                } else {
                    Log.w("CCTV Detail", String.valueOf(response.code()));
                    Toast.makeText(binding.getRoot().getContext(), "Response Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CctvAlertResponse>> call, Throwable t) {
                Log.e("CCTV Detail", "Load Data Failed" + t.getMessage(), t);
                Toast.makeText(binding.getRoot().getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecyclerView(List<CctvAlertResponse> alertList) {
        RecyclerView recyclerView = binding.alertList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CctvAlertCardListAdapter(alertList);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(100));
    }

    private void refresh() {
        getCctvDetailAndSetContents();
        CctvService cctvService = RetrofitClient.getRetrofitInstance(getContext()).create(CctvService.class);
        Call<List<CctvAlertResponse>> call = cctvService.getAlertsByCctvId(cctvId);
        call.enqueue(new Callback<List<CctvAlertResponse>>() {
            @Override
            public void onResponse(Call<List<CctvAlertResponse>> call, Response<List<CctvAlertResponse>> response) {
                if (response.code() == 200 && response.body() != null) {
                    adapter.setItems(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.w("CCTV Detail", String.valueOf(response.code()));
                    Toast.makeText(binding.getRoot().getContext(), "Response Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CctvAlertResponse>> call, Throwable t) {
                Log.e("CCTV Detail", "Load Data Failed" + t.getMessage(), t);
                Toast.makeText(binding.getRoot().getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}