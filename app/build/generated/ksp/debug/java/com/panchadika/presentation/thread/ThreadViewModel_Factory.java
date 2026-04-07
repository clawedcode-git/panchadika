package com.panchadika.presentation.thread;

import com.panchadika.domain.usecase.GetMessagesUseCase;
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
public final class ThreadViewModel_Factory implements Factory<ThreadViewModel> {
  private final Provider<GetMessagesUseCase> getMessagesUseCaseProvider;

  private final Provider<SendSmsUseCase> sendSmsUseCaseProvider;

  public ThreadViewModel_Factory(Provider<GetMessagesUseCase> getMessagesUseCaseProvider,
      Provider<SendSmsUseCase> sendSmsUseCaseProvider) {
    this.getMessagesUseCaseProvider = getMessagesUseCaseProvider;
    this.sendSmsUseCaseProvider = sendSmsUseCaseProvider;
  }

  @Override
  public ThreadViewModel get() {
    return newInstance(getMessagesUseCaseProvider.get(), sendSmsUseCaseProvider.get());
  }

  public static ThreadViewModel_Factory create(
      Provider<GetMessagesUseCase> getMessagesUseCaseProvider,
      Provider<SendSmsUseCase> sendSmsUseCaseProvider) {
    return new ThreadViewModel_Factory(getMessagesUseCaseProvider, sendSmsUseCaseProvider);
  }

  public static ThreadViewModel newInstance(GetMessagesUseCase getMessagesUseCase,
      SendSmsUseCase sendSmsUseCase) {
    return new ThreadViewModel(getMessagesUseCase, sendSmsUseCase);
  }
}
