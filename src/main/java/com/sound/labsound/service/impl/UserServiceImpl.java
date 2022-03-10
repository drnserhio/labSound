package com.sound.labsound.service.impl;

import com.sound.labsound.exception.domain.*;
import com.sound.labsound.model.Role;
import com.sound.labsound.model.User;
import com.sound.labsound.model.principal.CustomUserPrincipal;
import com.sound.labsound.repos.UserRepository;
import com.sound.labsound.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.EMPTY;


@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username with username + " + username + " didn't find.");
        }
        return new CustomUserPrincipal(user);
    }

    @Override
    public User registerAccount(User user)
            throws PasswordIsLowException, UserNameExistsException, EmailExistsException, NullOrEmtpyFieldUserException {
        validationUsernameEmailAndPass(user.getUsername(), user.getEmail(), user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER.name());
        user.setEnable(true);
        User save = userRepository.save(user);
        return save;
    }

    @Override
    public User updateAccount(String currentUsername, User user)
            throws PasswordIsLowException, UserNameExistsException, EmailExistsException, UserNotFoundException, NullOrEmtpyFieldUserException {
        validationUsernameEmailAndPass(user.getUsername(), user.getEmail(), EMPTY);
        User userByUsername = getUserByUsername(currentUsername);
        if (user == null) {
            throw new UserNotFoundException("User didn't find with username: " + currentUsername);
        }
        userByUsername.setUsername(user.getUsername());
        userByUsername.setEmail(user.getEmail());
        User save = userRepository.save(userByUsername);
        return save;
    }

    @Override
    public boolean removeAccount(String username) {
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private void validationUsernameEmailAndPass(String username, String email, String password)
            throws UserNameExistsException, EmailExistsException, PasswordIsLowException, NullOrEmtpyFieldUserException {
        validEmptyField(username, email);
        User byUsername = getUserByUsername(username);
        User byEmail = getUserByEmail(email);

        //TODO: validation regex email, and username badly word
        if (byUsername != null) {
            throw new UserNameExistsException("Username already exists.");
        }
        if (byEmail != null) {
            throw new EmailExistsException("Email already exists.");
        }
        if (StringUtils.isNotEmpty(password) &&
                StringUtils.isNotEmpty(username) &&
                    StringUtils.isNotEmpty(email)) {
            if (password.length() < 8) {
                throw new PasswordIsLowException("Password is low.Please put in more than 8 character.");
            }
        }
    }

    private void validEmptyField(String username, String email)
            throws NullOrEmtpyFieldUserException {
        if (StringUtils.isEmpty(username)) {
            throw new NullOrEmtpyFieldUserException("You entry empty field username.");
        }
        if (StringUtils.isEmpty(email)) {
            throw new NullOrEmtpyFieldUserException("You entry empty field email.");
        }
    }


    @Override
    public boolean resetPassword(String email)
            throws EmailExistsException {
        User usr = getUserByEmail(email);
        if (usr == null) {
            throw new EmailExistsException("Email doesn't register.");
        }
        String randPass = UUID.randomUUID().toString().substring(0, 6);
        usr.setPassword(passwordEncoder.encode(randPass));
        //TODO: send to email new pass
        return true;
    }
}
