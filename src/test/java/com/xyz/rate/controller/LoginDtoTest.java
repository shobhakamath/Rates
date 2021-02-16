package com.xyz.rate.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.xyz.rate.dto.LoginDto;
import org.junit.jupiter.api.Test;

public class LoginDtoTest {

    @Test
    public void testAll() {
        LoginDto dto = new LoginDto("user", "pwd");
        assertThat(dto.getUsername(), is("user"));
        assertThat(dto.getPassword(), is("pwd"));
    }
}