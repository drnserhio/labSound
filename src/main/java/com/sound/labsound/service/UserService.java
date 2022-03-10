package com.sound.labsound.service;

import com.sound.labsound.exception.domain.*;
import com.sound.labsound.model.User;

public interface UserService {

    User registerAccount(User user) throws PasswordIsLowException, UserNameExistsException, EmailExistsException, NullOrEmtpyFieldUserException;
    User updateAccount(String currentUsername, User user) throws PasswordIsLowException, UserNameExistsException, EmailExistsException, UserNotFoundException, NullOrEmtpyFieldUserException;

    boolean removeAccount(String username);

    User getUserByUsername(String username);
    User getUserByEmail(String email);

    boolean resetPassword(String email) throws EmailExistsException;
}
