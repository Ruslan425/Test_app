package ru.romazanov.testapp1.di;

import android.content.Context;
import androidx.room.Room;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.romazanov.testapp1.api.Api;
import ru.romazanov.testapp1.api.MyOkhttp;
import ru.romazanov.testapp1.db.MyDataBase;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Singleton
    @Provides
    public OkHttpClient getMyOkhttp() {
        return new MyOkhttp().getOkHttpClient();
    }

    @Singleton
    @Provides
    public Api getRetrofitInstance(OkHttpClient client) {
        String baseUrl = "https://dev.sitec24.ru/UKA_TRADE/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(Api.class);
    }

    @Singleton
    @Provides
    public MyDataBase getDb(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, MyDataBase.class, "my_db").build();
    }
}
