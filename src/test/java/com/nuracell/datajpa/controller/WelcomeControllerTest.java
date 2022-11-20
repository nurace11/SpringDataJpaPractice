package com.nuracell.datajpa.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class WelcomeControllerTest {

    WelcomeController underTest;
    @Mock
    WelcomeService serviceMock;

    @BeforeEach
    void setUp() {
//        serviceMock = mock(WelcomeService.class); // Instead of using @ExtendWith(MockitoExtension) and @Mock annotations
        underTest = new WelcomeController(serviceMock);
    }

    @Test
    void canGetWelcome() {
        String message = "Garf" +
                "ield";
        given(serviceMock.getWelcomeMessage(message)).willReturn(
                "Welcome Garfield!"
        );

        assertThat(underTest.welcome(message)).isEqualTo("Welcome Garfield!");

        verify(serviceMock).getWelcomeMessage(message);
    }

    @Test
    void canGetWelcomeBlankMessage() {
        String message = "";
        given(serviceMock.getWelcomeMessage(message)).willReturn(
                "Welcome !"
        );

        assertThat(underTest.welcome(message)).isEqualTo("Welcome !");

        verify(serviceMock).getWelcomeMessage(message);
    }
}