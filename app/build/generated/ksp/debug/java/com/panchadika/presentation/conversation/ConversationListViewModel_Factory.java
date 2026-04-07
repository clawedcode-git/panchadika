package com.panchadika.presentation.conversation;

import com.panchadika.domain.usecase.GetConversationsUseCase;
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
public final class ConversationListViewModel_Factory implements Factory<ConversationListViewModel> {
  private final Provider<GetConversationsUseCase> getConversationsUseCaseProvider;

  public ConversationListViewModel_Factory(
      Provider<GetConversationsUseCase> getConversationsUseCaseProvider) {
    this.getConversationsUseCaseProvider = getConversationsUseCaseProvider;
  }

  @Override
  public ConversationListViewModel get() {
    return newInstance(getConversationsUseCaseProvider.get());
  }

  public static ConversationListViewModel_Factory create(
      Provider<GetConversationsUseCase> getConversationsUseCaseProvider) {
    return new ConversationListViewModel_Factory(getConversationsUseCaseProvider);
  }

  public static ConversationListViewModel newInstance(
      GetConversationsUseCase getConversationsUseCase) {
    return new ConversationListViewModel(getConversationsUseCase);
  }
}
