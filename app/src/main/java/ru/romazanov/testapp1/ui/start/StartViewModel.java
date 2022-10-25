package ru.romazanov.testapp1.ui.start;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.romazanov.testapp1.api.model.AuthResponse;
import ru.romazanov.testapp1.api.model.UserResponse;
import ru.romazanov.testapp1.repository.Repository;

@HiltViewModel
public class StartViewModel extends ViewModel {

    @Inject
    StartViewModel(Repository repository) {
        this.repository = repository;
        authResponse = repository.getAuthResponse();
    }

    private final Repository repository;

    private LiveData<ArrayList<UserResponse>> dataList = new MutableLiveData<>();
        LiveData<ArrayList<UserResponse>> getDataList() {
            return dataList;
    }

    private final LiveData<AuthResponse> authResponse;
        LiveData<AuthResponse> getAuthResponse() {
        return authResponse;
    }

    public void getUserList() {
        dataList = repository.getUserList();
    }

    public void getAuthCode(String userId, String password) {
        repository.getAuth(userId,password);
    }
}