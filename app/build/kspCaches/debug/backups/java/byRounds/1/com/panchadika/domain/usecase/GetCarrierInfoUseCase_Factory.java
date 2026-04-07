package com.panchadika.domain.usecase;

import com.panchadika.domain.repository.CarrierRepository;
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
public final class GetCarrierInfoUseCase_Factory implements Factory<GetCarrierInfoUseCase> {
  private final Provider<CarrierRepository> carrierRepositoryProvider;

  public GetCarrierInfoUseCase_Factory(Provider<CarrierRepository> carrierRepositoryProvider) {
    this.carrierRepositoryProvider = carrierRepositoryProvider;
  }

  @Override
  public GetCarrierInfoUseCase get() {
    return newInstance(carrierRepositoryProvider.get());
  }

  public static GetCarrierInfoUseCase_Factory create(
      Provider<CarrierRepository> carrierRepositoryProvider) {
    return new GetCarrierInfoUseCase_Factory(carrierRepositoryProvider);
  }

  public static GetCarrierInfoUseCase newInstance(CarrierRepository carrierRepository) {
    return new GetCarrierInfoUseCase(carrierRepository);
  }
}
