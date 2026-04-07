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
public final class ContactDataSource_Factory implements Factory<ContactDataSource> {
  private final Provider<Context> contextProvider;

  public ContactDataSource_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ContactDataSource get() {
    return newInstance(contextProvider.get());
  }

  public static ContactDataSource_Factory create(Provider<Context> contextProvider) {
    return new ContactDataSource_Factory(contextProvider);
  }

  public static ContactDataSource newInstance(Context context) {
    return new ContactDataSource(context);
  }
}
