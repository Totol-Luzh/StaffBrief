package com.bytewave.staffbrief.di

import com.bytewave.staffbrief.data.db.StaffBriefDataBase
import com.bytewave.staffbrief.domain.repository.StaffBriefRepository
import com.bytewave.staffbrief.domain.repository.StaffBriefRepositoryImpl
import com.bytewave.staffbrief.domain.use_case.AddCategoryUseCase
import com.bytewave.staffbrief.domain.use_case.AddCategoryUseCaseImpl
import com.bytewave.staffbrief.domain.use_case.AddPersonUseCase
import com.bytewave.staffbrief.domain.use_case.AddPersonUseCaseImpl
import com.bytewave.staffbrief.domain.use_case.AddRelativeUseCase
import com.bytewave.staffbrief.domain.use_case.AddRelativeUseCaseImpl
import com.bytewave.staffbrief.domain.use_case.AddSoldierUseCase
import com.bytewave.staffbrief.domain.use_case.AddSoldierUseCaseImpl
import com.bytewave.staffbrief.domain.use_case.DeleteCategoryUseCase
import com.bytewave.staffbrief.domain.use_case.DeleteCategoryUseCaseImpl
import com.bytewave.staffbrief.domain.use_case.GetAllCategoriesCurrentUseCase
import com.bytewave.staffbrief.domain.use_case.GetAllCategoriesCurrentUseCaseImpl
import com.bytewave.staffbrief.domain.use_case.GetAllCategoriesUseCase
import com.bytewave.staffbrief.domain.use_case.GetAllCategoriesUseCaseImpl
import com.bytewave.staffbrief.domain.use_case.GetAllPersonBySoldierUseCase
import com.bytewave.staffbrief.domain.use_case.GetAllSoldierFullInfoUseCaseImpl
import com.bytewave.staffbrief.domain.use_case.GetFullSoldierInfoByPersonUseCase
import com.bytewave.staffbrief.domain.use_case.GetFullSoldierInfoByPersonUseCaseImpl
import com.bytewave.staffbrief.domain.use_case.GetRelativesBySoldierUseCase
import com.bytewave.staffbrief.domain.use_case.GetRelativesBySoldierUseCaseImpl
import com.bytewave.staffbrief.domain.use_case.InsertSoldiersCategoriesUseCase
import com.bytewave.staffbrief.domain.use_case.InsertSoldiersCategoriesUseCaseImpl
import com.bytewave.staffbrief.presentation.BaseViewModel
import com.bytewave.staffbrief.presentation.CategoryManagementViewModel
import com.bytewave.staffbrief.presentation.HomeViewModel
import com.bytewave.staffbrief.presentation.SoldierFormViewModel
import com.bytewave.staffbrief.presentation.SoldierScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val domainModule = module {
    single<StaffBriefRepository> { StaffBriefRepositoryImpl(get()) }
    single { StaffBriefDataBase.getInstance(get()).staffBriefDao }

    factory<AddPersonUseCase> { AddPersonUseCaseImpl(get()) }
    factory<AddSoldierUseCase> { AddSoldierUseCaseImpl(get()) }
    factory<InsertSoldiersCategoriesUseCase> { InsertSoldiersCategoriesUseCaseImpl(get()) }
    factory<AddCategoryUseCase> { AddCategoryUseCaseImpl(get()) }
    factory<DeleteCategoryUseCase> { DeleteCategoryUseCaseImpl(get()) }
    factory<GetAllCategoriesUseCase> { GetAllCategoriesUseCaseImpl(get()) }
    factory<GetAllCategoriesCurrentUseCase> { GetAllCategoriesCurrentUseCaseImpl(get()) }
    factory<GetAllPersonBySoldierUseCase> { GetAllSoldierFullInfoUseCaseImpl(get()) }
    factory<GetFullSoldierInfoByPersonUseCase> { GetFullSoldierInfoByPersonUseCaseImpl(get()) }
    factory<AddRelativeUseCase> { AddRelativeUseCaseImpl(get()) }
    factory<GetRelativesBySoldierUseCase> { GetRelativesBySoldierUseCaseImpl(get()) }

    viewModel { BaseViewModel(get()) }
    viewModel { CategoryManagementViewModel(get(), get(), get()) }
    viewModel { SoldierFormViewModel(get(), get(), get(), get(), get(), get(), get()) }
    viewModel { HomeViewModel( get(), get()) }
    viewModel { SoldierScreenViewModel( get(), get()) }
}