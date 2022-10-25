package ru.romazanov.testapp1.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import ru.romazanov.testapp1.api.Api;
import ru.romazanov.testapp1.api.model.AuthResponse;
import ru.romazanov.testapp1.api.model.Response;
import ru.romazanov.testapp1.api.model.UserResponse;
import ru.romazanov.testapp1.db.AuthEntity;
import ru.romazanov.testapp1.db.MyDataBase;
import ru.romazanov.testapp1.utils.SingleLiveEvent;

public class Repository {

    private final Api api;
    private final UUID uuid = UUID.randomUUID();
    private final MyDataBase dataBase;
    private final DateFormat timeFormat = new SimpleDateFormat("yyyy.MM.dd 'в' HH:mm:ss", Locale.getDefault());

    @Inject
    Repository(Api api, MyDataBase dataBase) {
        this.api = api;
        this.dataBase = dataBase;
    }

    private final SingleLiveEvent<AuthResponse> authResponse = new SingleLiveEvent<>();

    public LiveData<AuthResponse> getAuthResponse() {
        return authResponse;
    }

    public LiveData<ArrayList<UserResponse>> getUserList() {
        MutableLiveData<ArrayList<UserResponse>> liveData = new MutableLiveData<>();
        api.getList(uuid.toString()).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        liveData.postValue(null);
                    }
                    liveData.postValue(response.body().getUsers().getUserList());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public void getAuth(String userId, String password) {
        api.getAuth(uuid.toString(), userId, password).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, retrofit2.Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        authResponse.postValue(null);
                    }
                    insert(response.body().getCode());
                    authResponse.setValue(response.body());
                } else {
                    authResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                authResponse.postValue(null);
            }
        });
    }

    void insert(int code) {
        String timeText = timeFormat.format(new Date());
        MyDataBase.databaseWriteExecutor.execute(() -> {
            dataBase.dao().insert(new AuthEntity(code, timeText));
        });
    }

    public LiveData<List<AuthEntity>> getList() {
        MutableLiveData<List<AuthEntity>> liveData = new MutableLiveData<>();
        dataBase.dao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AuthEntity>>() {
                    @Override
                    public void accept(List<AuthEntity> authEntities) throws Throwable {
                        liveData.postValue(authEntities);
                    }
                });
        return liveData;
    }
}
