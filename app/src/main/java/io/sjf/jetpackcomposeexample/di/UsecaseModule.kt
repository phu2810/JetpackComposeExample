package io.sjf.jetpackcomposeexample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.sjf.jetpackcomposeexample.ui.example.GetUsersUseCase
import io.sjf.jetpackcomposeexample.ui.example.UserRepository

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetUsersUseCase(userRepository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(userRepository)
    }
}