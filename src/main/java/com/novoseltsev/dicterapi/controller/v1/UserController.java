package com.novoseltsev.dicterapi.controller.v1;

import com.novoseltsev.dicterapi.controller.v1.api.UserApi;
import com.novoseltsev.dicterapi.domain.dto.auth.PasswordDto;
import com.novoseltsev.dicterapi.domain.dto.user.AdminUserDto;
import com.novoseltsev.dicterapi.domain.dto.user.SignUpUserDto;
import com.novoseltsev.dicterapi.domain.dto.user.UserDto;
import com.novoseltsev.dicterapi.domain.entity.User;
import com.novoseltsev.dicterapi.domain.status.UserStatus;
import com.novoseltsev.dicterapi.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return UserDto.from(userService.findById(id));
    }

    @Override
    @GetMapping
    public List<AdminUserDto> findAll() {
        return userService.findAll().stream()
            .map(AdminUserDto::from)
            .collect(Collectors.toList());
    }

    @Override
    @PostMapping("/registration")
    public ResponseEntity<UserDto> create(@Valid @RequestBody SignUpUserDto signUpUserDto) {
        User createdUser = userService.create(signUpUserDto.toEntity());
        return new ResponseEntity<>(UserDto.from(createdUser), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public UserDto update(@Valid @RequestBody UserDto userDto) {
        User updatedUser = userService.update(userDto.toEntity());
        return UserDto.from(updatedUser);
    }

    @Override
    @PutMapping("/password/{id}")
    public void updatePassword(@PathVariable Long id, @Valid @RequestBody PasswordDto passwordDto) {
        userService.updatePassword(id, passwordDto.getOldPassword(), passwordDto.getNewPassword());
    }

    @Override
    @PutMapping("/user-status/{id}")
    public void updateUserStatus(@PathVariable Long id, UserStatus status) {
        userService.updateUserStatus(id, status);
    }

    @Override
    @DeleteMapping("/full-deletion/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
