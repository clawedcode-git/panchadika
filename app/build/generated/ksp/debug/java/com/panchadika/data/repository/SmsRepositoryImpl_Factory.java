package com.panchadika.data.repository;

import com.panchadika.data.source.ContactDataSource;
import com.panchadika.data.source.SmsDataSource;
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
public final class SmsRepositoryImpl_Factory implements Factory<SmsRepositoryImpl> {
  private final Provider<SmsDataSource> smsDataSourceProvider;

  private final Provider<ContactDataSource> contactDataSourceProvider;

  public SmsRepositoryImpl_Factory(Provider<SmsDataSource> smsDataSourceProvider,
      Provider<ContactDataSource> contactDataSourceProvider) {
    this.smsDataSourceProvider = smsDataSourceProvider;
    this.contactDataSourceProvider = contactDataSourceProvider;
  }

  @Override
  public SmsRepositoryImpl get() {
    return newInstance(smsDataSourceProvider.get(), contactDataSourceProvider.get());
  }

  public static SmsRepositoryImpl_Factory create(Provider<SmsDataSource> smsDataSourceProvider,
      Provider<ContactDataSource> contactDataSourceProvider) {
    return new SmsRepositoryImpl_Factory(smsDataSourceProvider, contactDataSourceProvider);
  }

  public static SmsRepositoryImpl newInstance(SmsDataSource smsDataSource,
      ContactDataSource contactDataSource) {
    return new SmsRepositoryImpl(smsDataSource, contactDataSource);
  }
}
