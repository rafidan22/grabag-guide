package com.project.tourguide.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.tourguide.R;
import com.project.tourguide.adapter.FragmentAdapter;
import com.project.tourguide.databinding.FragmentTamanBinding;
import com.project.tourguide.databinding.FragmentVisitBinding;
import com.project.tourguide.viewmodel.MainViewModel;

public class VisitFragment extends Fragment {

    private FragmentVisitBinding binding;
    private FragmentAdapter adapter;
    @Override
    public void onResume() {
        super.onResume();
        initRecylerView();
        initViewModel();
    }

    private void initRecylerView() {
        binding.rvVisit.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FragmentAdapter();
        binding.rvVisit.setAdapter(adapter);
    }

    private void initViewModel() {
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.setVisitList();
        viewModel.getKontenList().observe(this, camera -> {
            if (camera.size() > 0) {
                binding.noData.setVisibility(View.GONE);
                adapter.setData(camera);
            } else {
                binding.noData.setVisibility(View.VISIBLE);
            }
            binding.progressBar.setVisibility(View.GONE);
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVisitBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}