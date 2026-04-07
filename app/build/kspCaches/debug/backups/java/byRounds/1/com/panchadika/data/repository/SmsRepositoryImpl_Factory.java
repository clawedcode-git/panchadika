package com.panchadika.data.repository;

import com.panchadika.data.local.dao.ConversationDao;
import com.panchadika.data.local.dao.MessageDao;
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

  private final Provider<ConversationDao> conversationDaoProvider;

  private final Provider<MessageDao> messageDaoProvider;

  private final Provider<ContactDataSource> contactDataSourceProvider;

  public SmsRepositoryImpl_Factory(Provider<SmsDataSource> smsDataSourceProvider,
      Provider<ConversationDao> conversationDaoProvider, Provider<MessageDao> messageDaoProvider,
      Provider<ContactDataSource> contactDataSourceProvider) {
    this.smsDataSourceProvider = smsDataSourceProvider;
    this.conversationDaoProvider = conversationDaoProvider;
    this.messageDaoProvider = messageDaoProvider;
    this.contactDataSourceProvider = contactDataSourceProvider;
  }

  @Override
  public SmsRepositoryImpl get() {
    return newInstance(smsDataSourceProvider.get(), conversationDaoProvider.get(), messageDaoProvider.get(), contactDataSourceProvider.get());
  }

  public static SmsRepositoryImpl_Factory create(Provider<SmsDataSource> smsDataSourceProvider,
      Provider<ConversationDao> conversationDaoProvider, Provider<MessageDao> messageDaoProvider,
      Provider<ContactDataSource> contactDataSourceProvider) {
    return new SmsRepositoryImpl_Factory(smsDataSourceProvider, conversationDaoProvider, messageDaoProvider, contactDataSourceProvider);
  }

  public static SmsRepositoryImpl newInstance(SmsDataSource smsDataSource,
      ConversationDao conversationDao, MessageDao messageDao, ContactDataSource contactDataSource) {
    return new SmsRepositoryImpl(smsDataSource, conversationDao, messageDao, contactDataSource);
  }
}
