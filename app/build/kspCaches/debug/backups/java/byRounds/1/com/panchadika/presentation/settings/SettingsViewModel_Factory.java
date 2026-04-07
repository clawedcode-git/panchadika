package com.panchadika.presentation.settings;

import com.panchadika.domain.usecase.GetCarrierInfoUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<GetCarrierInfoUseCase> getCarrierInfoUseCaseProvider;

  public SettingsViewModel_Factory(Provider<GetCarrierInfoUseCase> getCarrierInfoUseCaseProvider) {
    this.getCarrierInfoUseCaseProvider = getCarrierInfoUseCaseProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(getCarrierInfoUseCaseProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<GetCarrierInfoUseCase> getCarrierInfoUseCaseProvider) {
    return new SettingsViewModel_Factory(getCarrierInfoUseCaseProvider);
  }

  public static SettingsViewModel newInstance(GetCarrierInfoUseCase getCarrierInfoUseCase) {
    return new SettingsViewModel(getCarrierInfoUseCase);
  }
}
