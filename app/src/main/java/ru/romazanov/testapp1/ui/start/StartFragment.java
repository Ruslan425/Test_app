package ru.romazanov.testapp1.ui.start;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;
import dagger.hilt.android.AndroidEntryPoint;
import ru.romazanov.testapp1.R;
import ru.romazanov.testapp1.api.model.AuthResponse;
import ru.romazanov.testapp1.api.model.UserResponse;
import ru.romazanov.testapp1.databinding.FragmentStartBinding;

@AndroidEntryPoint
public class StartFragment extends Fragment {

    private StartViewModel mViewModel;
    private FragmentStartBinding binding;
    private NavController navController;
    private Integer spinnerPosition;
    private final String emptyAnswer = "Пустой ответ!";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentStartBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(StartViewModel.class);
        mViewModel.getUserList();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerPosition = 0;
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserResponse id = Objects.requireNonNull(mViewModel.getDataList().getValue()).get(spinnerPosition);
                mViewModel.getAuthCode(id.getUid(), String.valueOf(binding.password.getText()));
            }
        });

        mViewModel.getDataList().observe(getViewLifecycleOwner(), new Observer<ArrayList<UserResponse>>() {
            @Override
            public void onChanged(ArrayList<UserResponse> userResponses) {
                if (userResponses == null) {
                    Toast.makeText(getContext(), emptyAnswer, Toast.LENGTH_SHORT).show();
                } else {
                    binding.spinner.setAdapter(new ArrayAdapter<UserResponse>(getContext(),
                            android.R.layout.simple_list_item_1, userResponses));
                }
            }
        });

        mViewModel.getAuthResponse().observe(getViewLifecycleOwner(), new Observer<AuthResponse>() {
            @Override
            public void onChanged(AuthResponse integer) {
                if (integer == null) {
                    Toast.makeText(getContext(), emptyAnswer, Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("key", integer.getCode());
                    navController.navigate(R.id.action_startFragment_to_secondFragment, bundle);
                }
            }
        });
    }
}