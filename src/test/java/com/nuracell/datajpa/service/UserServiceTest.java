package com.nuracell.datajpa.service;

import com.nuracell.datajpa.entity.User;
import com.nuracell.datajpa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

//@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock // emulating (mocking) UserRepository
    private UserRepository userRepository;
    private UserService userService;

    public UserServiceTest(){
        // to search for mocks
//        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.openMocks(this);// Use @ExtendedWith annotation on this class instead
        userService = new UserService(userRepository);
    }

    @Test
    void checkUserByUsername_Should_Return_True() throws Exception {
        given(userRepository.findUserByUsername("Catherine")).willReturn(
                User.builder().username("Catherine").build()
        );

        assertThat(userService.checkUserPresence(User.builder().username("Catherine").build())).isTrue();

        // verify - verifies if the method was executed or not
        verify(userRepository).findUserByUsername("Catherine");
    }

    @Test
    void checkUserByUsername_Should_Return_False() throws Exception {
        given(userRepository.findUserByUsername("Catherine")).willReturn(null);

        assertThat(userService.checkUserPresence(User.builder().username("Catherine").build())).isFalse();

        verify(userRepository).findUserByUsername("Catherine");
    }

    @Test
    void checkUserByUsername_Should_Return_Null() throws Exception {
        given(userRepository.findUserByUsername("Catherine")).willReturn(null);

        assertThat(userService.getUserByUsername("Catherine")).isNull();

        verify(userRepository).findUserByUsername("Catherine");
    }

    @Test/*(expected = Exception.class)*/
    void checkUserByUsername_Should_Throw_Exception() throws Exception {
        String s = anyString();
        System.out.println(s);

        given(userRepository.findUserByUsername(s)).willThrow(Exception.class);
//        verify(userRepository).findUserByUsername(s);
    }

    @Test
    public void testCaptor() throws Exception {
        given(userRepository.findUserByUsername(anyString())).willReturn(
                User.builder().username("Elise").build()
        );

        boolean b = userService.checkUserPresence(User.builder().username("Elise").build());

        // captor used for capture any() arguments
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(userRepository).findUserByUsername(captor.capture());
        String argument = captor.getValue();

        assertThat(argument).isEqualTo("Elise");
    }
}