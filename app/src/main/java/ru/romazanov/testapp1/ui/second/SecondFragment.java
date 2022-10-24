package ru.romazanov.testapp1.ui.second;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.List;
import dagger.hilt.android.AndroidEntryPoint;
import ru.romazanov.testapp1.databinding.FragmentSecondBinding;
import ru.romazanov.testapp1.db.AuthEntity;

@AndroidEntryPoint
public class SecondFragment extends Fragment {

    private SecondViewModel mViewModel;
    private FragmentSecondBinding binding;
    private Integer authCode;
    private MyAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(SecondViewModel.class);
        authCode = requireArguments().getInt("key");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getContext(), String.valueOf(authCode), Toast.LENGTH_SHORT).show();

        adapter = new MyAdapter(getContext());

        binding.recycler.setAdapter(adapter);

       mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<AuthEntity>>() {
           @Override
           public void onChanged(List<AuthEntity> authEntities) {
               adapter.setStates(authEntities);
           }
       });
    }
}