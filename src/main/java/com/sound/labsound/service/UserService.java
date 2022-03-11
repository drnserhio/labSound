package com.sound.labsound.service;

import com.sound.labsound.exception.domain.*;
import com.sound.labsound.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    User registerAccount(User user) throws UserNameExistsException, EmailExistsException, NullOrEmtpyFieldUserException, PasswordNotValidException;
    User updateAccount(String currentUsername, User user) throws  UserNameExistsException, EmailExistsException, UserNotFoundException, NullOrEmtpyFieldUserException;

    boolean removeAccount(String username);

    User getUserByUsername(String username);
    User getUserByEmail(String email);

    boolean resetPassword(String email) throws EmailExistsException;

    boolean changeNewPassword(String username, String oldPassword, String newPassword, String confirmPassword) throws UserNotFoundException, PasswordNotValidException;

    boolean updateImage(String username, MultipartFile imageAvatar) throws UserNotFoundException, IOException;
}
