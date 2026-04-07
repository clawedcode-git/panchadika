package com.panchadika.presentation.newmessage;

import com.panchadika.domain.usecase.SendSmsUseCase;
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
public final class NewMessageViewModel_Factory implements Factory<NewMessageViewModel> {
  private final Provider<SendSmsUseCase> sendSmsUseCaseProvider;

  public NewMessageViewModel_Factory(Provider<SendSmsUseCase> sendSmsUseCaseProvider) {
    this.sendSmsUseCaseProvider = sendSmsUseCaseProvider;
  }

  @Override
  public NewMessageViewModel get() {
    return newInstance(sendSmsUseCaseProvider.get());
  }

  public static NewMessageViewModel_Factory create(
      Provider<SendSmsUseCase> sendSmsUseCaseProvider) {
    return new NewMessageViewModel_Factory(sendSmsUseCaseProvider);
  }

  public static NewMessageViewModel newInstance(SendSmsUseCase sendSmsUseCase) {
    return new NewMessageViewModel(sendSmsUseCase);
  }
}
