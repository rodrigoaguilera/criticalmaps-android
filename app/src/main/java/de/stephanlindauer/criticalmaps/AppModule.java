package de.stephanlindauer.criticalmaps;

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.stephanlindauer.criticalmaps.handler.ServerResponseProcessor;
import de.stephanlindauer.criticalmaps.model.ChatModel;
import de.stephanlindauer.criticalmaps.model.OtherUsersLocationModel;
import de.stephanlindauer.criticalmaps.model.OwnLocationModel;
import de.stephanlindauer.criticalmaps.model.TwitterModel;
import de.stephanlindauer.criticalmaps.model.UserModel;
import de.stephanlindauer.criticalmaps.provider.EventBusProvider;
import de.stephanlindauer.criticalmaps.service.LocationUpdatesService;

@Module
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public  Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOKHttpClient() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(15, TimeUnit.SECONDS);
        return okHttpClient;
    }

    @Provides
    @Singleton
    public Picasso providePicasso(Application app, OkHttpClient client) {
        return new Picasso.Builder(app)
                .downloader(new OkHttpDownloader(client))
                .build();
    }

    @Provides
    @Singleton
    public ChatModel provideChatModel() {
        return new ChatModel();
    }

    @Provides
    @Singleton
    public UserModel provideUserModel() {
        return new UserModel();
    }


    @Provides
    @Singleton
    public TwitterModel provideTwitterModel() {
        return new TwitterModel();
    }

    @Provides
    @Singleton
    public OwnLocationModel provideOwnLocationModel() {
        return new OwnLocationModel();
    }

    @Provides
    @Singleton
    public ServerResponseProcessor serverResponseProcessor(OtherUsersLocationModel otherUsersLocationModel, EventBusProvider eventService, ChatModel chatModel) {
        return new ServerResponseProcessor(otherUsersLocationModel, eventService, chatModel);
    }

    @Provides
    @Singleton
    public OtherUsersLocationModel provideOtherUsersLocationModel() {
        return new OtherUsersLocationModel();
    }

    @Provides
    @Singleton
    public EventBusProvider provideEventBusProvider() {
        return new EventBusProvider();
    }

    @Provides
    @Singleton
    public LocationUpdatesService provideLocationUpdatesService(OwnLocationModel ownLocationModel, EventBusProvider eventBusProvider) {
        return new LocationUpdatesService(ownLocationModel, eventBusProvider);
    }
}