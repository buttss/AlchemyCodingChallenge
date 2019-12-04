package com.butts.sean.alchemycodingchallenge.di

import com.butts.sean.alchemycodingchallenge.Urls
import com.butts.sean.alchemycodingchallenge.data.*
import com.butts.sean.alchemycodingchallenge.viewmodel.ItemListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

object Constants {
    const val STORY_IDS_FILENAME = "story_ids"
    const val REMOTE_STORY_IDS_SERVICE = "remote_story_ids_service"
    const val LOCAL_STORY_IDS_SERVICE = "local_story_ids_service"
    const val LOCAL_STORY_SERVICE = "local_story_service"
    const val REMOTE_STORY_SERVICE = "remote_story_service"
}

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(viewModelModule,
            repositoryModule,
            serviceModule,
            networkModule,
            cacheModule)
    )
}

val viewModelModule: Module = module {
    viewModel { ItemListViewModel(get()) }
}

val repositoryModule: Module = module {
    single {
        AppDatabase.getDatabase(androidApplication()).storyDao()
    }
}

val serviceModule: Module = module {
    single<ItemIdsService> { ItemIdsServiceImpl(Urls.TOP_STORIES, get()) }
    single<ItemService> { ItemServiceImpl(get()) }
    single<ItemDataSource>(named(Constants.LOCAL_STORY_SERVICE)) { LocalItemDataSource(get()) }
    single<ItemDataSource>(named(Constants.REMOTE_STORY_SERVICE)) { RemoteItemDataSource(get(), get()) }
    single<ItemRepository> { ItemRepositoryImpl(get(named(Constants.REMOTE_STORY_SERVICE)),
                                                    get(named(Constants.LOCAL_STORY_SERVICE))) }
}

val networkModule: Module = module {
    single<Retrofit> {
        val baseUrl = "https://hacker-news.firebaseio.com"
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}

val cacheModule: Module = module {
    single(named(Constants.STORY_IDS_FILENAME)) {
        val appContext = androidApplication()
        File(appContext.filesDir, Constants.STORY_IDS_FILENAME)
    }
}