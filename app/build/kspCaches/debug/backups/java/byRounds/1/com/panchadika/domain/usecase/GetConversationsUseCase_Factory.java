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
public final class GetConversationsUseCase_Factory implements Factory<GetConversationsUseCase> {
  private final Provider<SmsRepository> smsRepositoryProvider;

  public GetConversationsUseCase_Factory(Provider<SmsRepository> smsRepositoryProvider) {
    this.smsRepositoryProvider = smsRepositoryProvider;
  }

  @Override
  public GetConversationsUseCase get() {
    return newInstance(smsRepositoryProvider.get());
  }

  public static GetConversationsUseCase_Factory create(
      Provider<SmsRepository> smsRepositoryProvider) {
    return new GetConversationsUseCase_Factory(smsRepositoryProvider);
  }

  public static GetConversationsUseCase newInstance(SmsRepository smsRepository) {
    return new GetConversationsUseCase(smsRepository);
  }
}
