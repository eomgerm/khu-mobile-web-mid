package com.example.midassignment;

import adapters.CctvCardListAdapter;
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
import com.example.midassignment.databinding.FragmentCctvListBinding;
import data.Cctv;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.CctvService;
import service.RetrofitClient;
import view.CctvCard.VerticalSpaceItemDecoration;

public class CctvListFragment extends Fragment {

    private FragmentCctvListBinding binding;
    private CctvCardListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        binding = FragmentCctvListBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        getCctvsAndSetList();

        SwipeRefreshLayout srl = binding.cctvListSRL;
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                srl.setRefreshing(false);
            }
        });

        return v;
    }

    private void getCctvsAndSetList() {
        CctvService service = RetrofitClient.getRetrofitInstance().create(CctvService.class);
        Call<List<Cctv>> call = service.getCctvs();
        call.enqueue(new Callback<List<Cctv>>() {
            @Override
            public void onResponse(Call<List<Cctv>> call, Response<List<Cctv>> response) {
                if (response.code() == 200 && response.body() != null) {
                    setRecyclerView(response.body());
                } else {
                    Toast.makeText(binding.getRoot().getContext(), "Response Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cctv>> call, Throwable t) {
                Log.e("CCTV Detail", "Load Data Failed" + t.getMessage(), t);
                Toast.makeText(binding.getRoot().getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecyclerView(List<Cctv> cctvs) {
        RecyclerView recyclerView = binding.cctvCardList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CctvCardListAdapter(this.getContext(), cctvs);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(100));
    }

    private void refresh() {
        CctvService service = RetrofitClient.getRetrofitInstance().create(CctvService.class);
        Call<List<Cctv>> call = service.getCctvs();
        call.enqueue(new Callback<List<Cctv>>() {
            @Override
            public void onResponse(Call<List<Cctv>> call, Response<List<Cctv>> response) {
                if (response.code() == 200 && response.body() != null) {
                    adapter.setItems(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(binding.getRoot().getContext(), "Response Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cctv>> call, Throwable t) {
                Log.e("CCTV Detail", "Load Data Failed" + t.getMessage(), t);
                Toast.makeText(binding.getRoot().getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}