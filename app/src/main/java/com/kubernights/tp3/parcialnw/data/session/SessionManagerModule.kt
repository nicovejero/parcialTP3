package com.kubernights.tp3.parcialnw.data.session

import com.kubernights.tp3.parcialnw.domain.SessionManagerInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionManagerModule {
    @Singleton
    @Provides
    fun provideSessionManager(): SessionManagerInterface {
        return SessionManager()
    }
}
