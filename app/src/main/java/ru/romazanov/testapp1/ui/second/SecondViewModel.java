package ru.romazanov.testapp1.ui.second;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import ru.romazanov.testapp1.db.AuthEntity;
import ru.romazanov.testapp1.db.MyDataBase;

@HiltViewModel
public class SecondViewModel extends ViewModel {

    MyDataBase dataBase;
    @Inject
    SecondViewModel(MyDataBase dataBase) {
        this.dataBase = dataBase;
        getList();
    }

    MutableLiveData<List<AuthEntity>> authData;
    LiveData<List<AuthEntity>> getData() {
        if (authData == null) {
            authData = new MutableLiveData<>();
        }
        return authData;
    }

    void getList() {
        dataBase.dao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AuthEntity>>() {
                    @Override
                    public void accept(List<AuthEntity> authEntities) throws Throwable {
                        authData.postValue(authEntities);
                    }
                });
    }

}