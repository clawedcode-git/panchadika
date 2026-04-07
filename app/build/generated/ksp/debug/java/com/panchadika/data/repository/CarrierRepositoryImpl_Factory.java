package com.panchadika.data.repository;

import com.panchadika.data.source.CarrierDataSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class CarrierRepositoryImpl_Factory implements Factory<CarrierRepositoryImpl> {
  private final Provider<CarrierDataSource> carrierDataSourceProvider;

  public CarrierRepositoryImpl_Factory(Provider<CarrierDataSource> carrierDataSourceProvider) {
    this.carrierDataSourceProvider = carrierDataSourceProvider;
  }

  @Override
  public CarrierRepositoryImpl get() {
    return newInstance(carrierDataSourceProvider.get());
  }

  public static CarrierRepositoryImpl_Factory create(
      Provider<CarrierDataSource> carrierDataSourceProvider) {
    return new CarrierRepositoryImpl_Factory(carrierDataSourceProvider);
  }

  public static CarrierRepositoryImpl newInstance(CarrierDataSource carrierDataSource) {
    return new CarrierRepositoryImpl(carrierDataSource);
  }
}
