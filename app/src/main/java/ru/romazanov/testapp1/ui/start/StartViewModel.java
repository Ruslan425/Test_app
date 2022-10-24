package ru.romazanov.testapp1.ui.start;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import ru.romazanov.testapp1.api.Api;
import ru.romazanov.testapp1.api.model.AuthResponse;
import ru.romazanov.testapp1.api.model.Response;
import ru.romazanov.testapp1.api.model.UserResponse;
import ru.romazanov.testapp1.db.AuthEntity;
import ru.romazanov.testapp1.db.MyDataBase;
import ru.romazanov.testapp1.utils.SingleLiveEvent;

@HiltViewModel
public class StartViewModel extends ViewModel {

    Api api;
    UUID uuid = UUID.randomUUID();
    MyDataBase dataBase;
    DateFormat timeFormat = new SimpleDateFormat("yyyy.MM.dd 'в' HH:mm:ss", Locale.getDefault());

    @Inject
    StartViewModel(Api api, MyDataBase dataBase) {
        this.api = api;
        this.dataBase = dataBase;
        getList();
    }

    private MutableLiveData<ArrayList<UserResponse>> dataList;

    LiveData<ArrayList<UserResponse>> getDataList() {
        if (dataList == null) {
            dataList = new MutableLiveData<ArrayList<UserResponse>>();
        }
        return dataList;
    }

    private MutableLiveData<String> error;

    LiveData<String> error() {
        if (error == null) {
            error = new MutableLiveData<String>();
        }
        return error;
    }

    private SingleLiveEvent<Integer> authResponse;

    LiveData<Integer> getAuth() {
        if (authResponse == null) {
            authResponse = new SingleLiveEvent<>();
        }
        return authResponse;
    }

    public void getList() {
        api.getList(uuid.toString()).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    dataList.postValue(response.body().getUsers().getUserList());
                } else {
                    try {
                        assert response.errorBody() != null;
                        error.postValue(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                error.postValue(t.getMessage());
            }
        });
    }

    public void auth(String userId, String password) {
        api.getAuth(uuid.toString(), userId, password).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, retrofit2.Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    authResponse.postValue(response.body().getCode());
                    insert(response.body().getCode());
                } else {
                    try {
                        error.postValue(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                error.postValue(t.getMessage());
            }
        });
    }

    void insert(int code) {
        String timeText = timeFormat.format(new Date());
        MyDataBase.databaseWriteExecutor.execute(() -> {
            dataBase.dao().insert(new AuthEntity(code, timeText));
        });
    }
}