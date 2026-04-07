package com.panchadika.data.source;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class CarrierDataSource_Factory implements Factory<CarrierDataSource> {
  private final Provider<Context> contextProvider;

  public CarrierDataSource_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public CarrierDataSource get() {
    return newInstance(contextProvider.get());
  }

  public static CarrierDataSource_Factory create(Provider<Context> contextProvider) {
    return new CarrierDataSource_Factory(contextProvider);
  }

  public static CarrierDataSource newInstance(Context context) {
    return new CarrierDataSource(context);
  }
}
