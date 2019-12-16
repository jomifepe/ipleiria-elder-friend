package com.example.tsngapp.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tsngapp.R;
import com.example.tsngapp.api.AuthManager;
import com.example.tsngapp.api.SMARTAAL;
import com.example.tsngapp.ui.adapter.BedStateRecyclerAdapter;
import com.example.tsngapp.ui.adapter.DoorStateRecyclerAdapter;
import com.example.tsngapp.ui.adapter.decorator.SimpleHorizontalDividerItemDecoration;

public class BedStateFragment extends BaseNestedFragment {
    public static final int NUM_OF_VALUES = 10;

    private RecyclerView rvStateList;
    private BedStateRecyclerAdapter listAdapter;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreateViewPostActions(@NonNull LayoutInflater inflater,
                                           @Nullable ViewGroup container,
                                           @Nullable Bundle savedInstanceState) {
        rootView.findViewById(R.id.btn_go_back)
                .setOnClickListener(v -> parentListener.onBackToMenuPressed());

        bindViews();
        setupRecyclerView();
        loadListData();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_bed_state;
    }

    private void loadListData() {
        refreshLayout.setRefreshing(true);
        new SMARTAAL.BedLastValues(AuthManager.getInstance().getElder().getId(), NUM_OF_VALUES,
            AuthManager.getInstance().getUser().getAcessToken(), r -> {
                listAdapter.setList(r);
                refreshLayout.setRefreshing(false);
            },
            e -> {
                Toast.makeText(rootView.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        ).execute();
    }

    private void setupRecyclerView() {
        listAdapter = new BedStateRecyclerAdapter();
        rvStateList.setAdapter(listAdapter);
        rvStateList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        final SimpleHorizontalDividerItemDecoration dividerItemDecoration =
                new SimpleHorizontalDividerItemDecoration(rvStateList.getContext());
        rvStateList.addItemDecoration(dividerItemDecoration);
    }

    private void bindViews() {
        refreshLayout = rootView.findViewById(R.id.srl_fragment_state);
        refreshLayout.setOnRefreshListener(this::loadListData);
        rvStateList = rootView.findViewById(R.id.rv_bed_state);
    }
}

