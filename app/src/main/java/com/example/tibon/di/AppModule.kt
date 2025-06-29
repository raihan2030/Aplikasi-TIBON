package com.example.tibon.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tibon.data.local.AppDao
import com.example.tibon.data.local.AppDatabase
import com.example.tibon.data.local.ExpenseCategory
import com.example.tibon.data.local.IncomeCategory
import com.example.tibon.data.remote.ApiService
import com.example.tibon.data.repository.SettingsRepositoryImpl
import com.example.tibon.data.repository.TibonRepositoryImpl
import com.example.tibon.domain.repository.SettingsRepository
import com.example.tibon.domain.repository.TibonRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindTibonRepository(impl: TibonRepositoryImpl): TibonRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    companion object {

        @Provides
        @Singleton
        fun provideApplicationScope() = CoroutineScope(SupervisorJob())

        class AppDatabaseCallback(
            private val scope: CoroutineScope,
            private val appDaoProvider: Provider<AppDao>
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                scope.launch {
                    populateDefaultCategories(appDaoProvider.get())
                }
            }

            suspend fun populateDefaultCategories(dao: AppDao) {
                // Kategori Pemasukan Default
                dao.insertIncomeCategory(IncomeCategory(name = "Pendapatan"))
                dao.insertIncomeCategory(IncomeCategory(name = "Gaji"))
                dao.insertIncomeCategory(IncomeCategory(name = "Bonus"))
                dao.insertIncomeCategory(IncomeCategory(name = "Hadiah"))
                dao.insertIncomeCategory(IncomeCategory(name = "Lainnya"))

                // Kategori Pengeluaran Default
                dao.insertExpenseCategory(ExpenseCategory(name = "Makanan"))
                dao.insertExpenseCategory(ExpenseCategory(name = "Transportasi"))
                dao.insertExpenseCategory(ExpenseCategory(name = "Belanja"))
                dao.insertExpenseCategory(ExpenseCategory(name = "Hiburan"))
                dao.insertExpenseCategory(ExpenseCategory(name = "Tagihan"))
                dao.insertExpenseCategory(ExpenseCategory(name = "Lainnya"))
            }
        }

        @Provides
        @Singleton
        fun provideDatabase(
            app: Application,
            callback: AppDatabaseCallback
        ): AppDatabase {
            return Room.databaseBuilder(
                app,
                AppDatabase::class.java,
                "tibon_database"
            )
                .fallbackToDestructiveMigration(false)
                .addCallback(callback)
                .build()
        }

        @Provides
        @Singleton
        fun provideDatabaseCallback(
            scope: CoroutineScope,
            appDaoProvider: Provider<AppDao>
        ): AppDatabaseCallback {
            return AppDatabaseCallback(scope, appDaoProvider)
        }


        @Provides
        @Singleton
        fun provideDao(db: AppDatabase) = db.appDao()

        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://v6.exchangerate-api.com/")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }
}