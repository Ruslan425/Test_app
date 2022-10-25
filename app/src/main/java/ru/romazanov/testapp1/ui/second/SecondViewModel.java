package ru.romazanov.testapp1.ui.second;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.romazanov.testapp1.db.AuthEntity;
import ru.romazanov.testapp1.repository.Repository;

@HiltViewModel
public class SecondViewModel extends ViewModel {

    @Inject
    SecondViewModel(Repository repository) {
        authData = repository.getList();
    }

   private LiveData<List<AuthEntity>> authData;
        LiveData<List<AuthEntity>> getData() {
        return authData;
    }
}