package com.JournalOne.JournalOne.service;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.security.core.userdetails.User;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().username("user123").password("password123").build()),
                 Arguments.of(User.builder().username("userOne").password("passwordOne").build())
        );
    }
}
