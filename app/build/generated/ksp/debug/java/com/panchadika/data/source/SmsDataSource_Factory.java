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
public final class SmsDataSource_Factory implements Factory<SmsDataSource> {
  private final Provider<Context> contextProvider;

  public SmsDataSource_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SmsDataSource get() {
    return newInstance(contextProvider.get());
  }

  public static SmsDataSource_Factory create(Provider<Context> contextProvider) {
    return new SmsDataSource_Factory(contextProvider);
  }

  public static SmsDataSource newInstance(Context context) {
    return new SmsDataSource(context);
  }
}
