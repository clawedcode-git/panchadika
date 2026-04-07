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
public final class SendSmsUseCase_Factory implements Factory<SendSmsUseCase> {
  private final Provider<SmsRepository> smsRepositoryProvider;

  public SendSmsUseCase_Factory(Provider<SmsRepository> smsRepositoryProvider) {
    this.smsRepositoryProvider = smsRepositoryProvider;
  }

  @Override
  public SendSmsUseCase get() {
    return newInstance(smsRepositoryProvider.get());
  }

  public static SendSmsUseCase_Factory create(Provider<SmsRepository> smsRepositoryProvider) {
    return new SendSmsUseCase_Factory(smsRepositoryProvider);
  }

  public static SendSmsUseCase newInstance(SmsRepository smsRepository) {
    return new SendSmsUseCase(smsRepository);
  }
}
