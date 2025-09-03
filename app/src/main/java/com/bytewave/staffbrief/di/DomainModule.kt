package com.bytewave.staffbrief.di

import com.bytewave.staffbrief.data.db.StaffBriefDataBase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository
import com.bytewave.staffbrief.domain.repository.StaffBriefRepositoryImpl
import com.bytewave.staffbrief.domain.use_case.AddCategoryUseCase
import com.bytewave.staffbrief.domain.use_case.AddCategoryUseCaseImpl
import com.bytewave.staffbrief.domain.use_case.AddPersonUseCase
import com.bytewave.staffbrief.domain.use_case.AddPersonUseCaseImpl
import com.bytewave.staffbrief.domain.use_case.AddSoldierUseCase
import com.bytewave.staffbrief.domain.use_case.AddSoldierUseCaseImpl
import com.bytewave.staffbrief.domain.use_case.DeleteCategoryUseCase
import com.bytewave.staffbrief.domain.use_case.DeleteCategoryUseCaseImpl
import com.bytewave.staffbrief.domain.use_case.GetAllCategoriesUseCase
import com.bytewave.staffbrief.domain.use_case.GetAllCategoriesUseCaseImpl
import com.bytewave.staffbrief.presentation.CategoryManagementViewModel
import com.bytewave.staffbrief.presentation.CreateSoldierViewModel

val domainModule = module {
    single<StaffBriefRepository> { StaffBriefRepositoryImpl(get()) }
    single { StaffBriefDataBase.getInstance(get()).staffBriefDao }

    factory<AddPersonUseCase> { AddPersonUseCaseImpl(get()) }
    factory<AddSoldierUseCase> { AddSoldierUseCaseImpl(get()) }
    factory<AddCategoryUseCase> { AddCategoryUseCaseImpl(get()) }
    factory<DeleteCategoryUseCase> { DeleteCategoryUseCaseImpl(get()) }
    factory<GetAllCategoriesUseCase> { GetAllCategoriesUseCaseImpl(get()) }

    viewModel { CategoryManagementViewModel(get(), get(), get()) }
    viewModel { CreateSoldierViewModel(get(), get()) }
}