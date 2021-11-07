package com.myapp.composesample.ui.right

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRemotePhotoRepository(impl: RemotePhotoRepositoryImp): RemotePhotoRepository

    @Binds
    @Singleton
    abstract fun bindLocalPhotoRepository(impl: LocalPhotoRepositoryImp): LocalPhotoRepository
}
