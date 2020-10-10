package com.novoseltsev.dictionaryapi.controller.v1;

import com.novoseltsev.dictionaryapi.domain.dto.user.SignUpUserDto;
import com.novoseltsev.dictionaryapi.domain.dto.user.UserDto;
import com.novoseltsev.dictionaryapi.domain.entity.User;
import com.novoseltsev.dictionaryapi.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return UserDto.from(userService.findById(id));
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll().stream().map(UserDto::from).collect(Collectors.toList());
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> create(
            @Valid @RequestBody SignUpUserDto signUpUserDto) {
        User createdUser = userService.create(signUpUserDto.toEntity());
        return new ResponseEntity<>(UserDto.from(createdUser), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public UserDto update(
            @Valid @RequestBody UserDto userDto, @PathVariable Long id) {
        userDto.setId(id);
        User updatedUser = userService.update(userDto.toEntity());
        return UserDto.from(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> markAsDeletedById(@PathVariable Long id) {
        userService.markAsDeletedById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/full-deletion/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
