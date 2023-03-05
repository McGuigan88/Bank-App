package com.bank.service;

import com.bank.dto.UserDto;
import com.bank.entities.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
