package com.panchadika.domain.usecase;

import com.panchadika.domain.repository.SmsRepository;
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
public final class GetMessagesUseCase_Factory implements Factory<GetMessagesUseCase> {
  private final Provider<SmsRepository> smsRepositoryProvider;

  public GetMessagesUseCase_Factory(Provider<SmsRepository> smsRepositoryProvider) {
    this.smsRepositoryProvider = smsRepositoryProvider;
  }

  @Override
  public GetMessagesUseCase get() {
    return newInstance(smsRepositoryProvider.get());
  }

  public static GetMessagesUseCase_Factory create(Provider<SmsRepository> smsRepositoryProvider) {
    return new GetMessagesUseCase_Factory(smsRepositoryProvider);
  }

  public static GetMessagesUseCase newInstance(SmsRepository smsRepository) {
    return new GetMessagesUseCase(smsRepository);
  }
}
