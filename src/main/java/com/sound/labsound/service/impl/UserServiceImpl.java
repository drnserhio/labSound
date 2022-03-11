package com.sound.labsound.service.impl;

import com.sound.labsound.exception.domain.*;
import com.sound.labsound.model.Role;
import com.sound.labsound.model.User;
import com.sound.labsound.model.principal.CustomUserPrincipal;
import com.sound.labsound.repos.UserRepository;
import com.sound.labsound.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.isNotBlank;


@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public static final String USER_IMAGE_PATH = "/usr/image/";
    public static final String FORWARD_SLASH = "/";
    public static final String DOT = "."; // filename.jpg
    public static final String JPG_EXSTENSION = "jpg";

    public static final String USER_FOLDER = System.getProperty("user.home") + "/labSound/usr/";

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
            throws UserNameExistsException, EmailExistsException, NullOrEmtpyFieldUserException, PasswordNotValidException {
        registrationValidationUsernameEmailAndPass(user.getUsername(), user.getEmail(), user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER.name());
        user.setEnable(true);
        User save = userRepository.save(user);
        return save;
    }

    @Override
    public User updateAccount(String currentUsername, User user)
            throws UserNameExistsException, EmailExistsException, UserNotFoundException, NullOrEmtpyFieldUserException {
        updateValidationNewUsernameAndEmail(currentUsername, user.getUsername(), user.getEmail());
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

    private void registrationValidationUsernameEmailAndPass(String username, String email, String password)
            throws UserNameExistsException, EmailExistsException, NullOrEmtpyFieldUserException, PasswordNotValidException {
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
            validationPassword(password);
        }
    }

    private void validationPassword(String password)
            throws PasswordNotValidException {
        if (password.length() < 8) {
            throw new PasswordNotValidException("Password is low.Please put in more than 8 character.");
        }
    }

    private void updateValidationNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
            throws UserNotFoundException, EmailExistsException, UserNameExistsException {
        User userByUsername = getUserByUsername(newUsername);
        User userByEmail = getByEmail(newEmail);
        if (isNotBlank(currentUsername)) {
            User currentUser = getUserByUsername(currentUsername);
            if (currentUser == null) {
                throw new UserNotFoundException("User not found : " + currentUsername);
            }
            if (userByUsername != null && !currentUser.getId().equals(userByUsername.getId())) {
                throw new UserNameExistsException("Username already exists.");
            }
            if (userByEmail != null && !currentUser.getId().equals(userByEmail.getId())) {
                throw new EmailExistsException("Email already exists.");
            }
        } else {
            if (userByUsername != null) {
                throw new UserNameExistsException("Username already exists.");
            }
            if (userByEmail != null) {
                throw new EmailExistsException("Email already exists.");
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

    @Override
    public boolean changeNewPassword(String username, String oldPassword, String newPassword, String confirmPassword)
            throws UserNotFoundException, PasswordNotValidException {
        if (!isExistByUsername(username)) {
            throw new UserNotFoundException("User not found :" + username);
        }
        return validationNewPassword(username, oldPassword, newPassword, confirmPassword);
    }

    private boolean validationNewPassword(String username, String oldPassword, String newPassword, String confirmPassword)
            throws PasswordNotValidException {
        validEmptyPassword(oldPassword, newPassword, confirmPassword);
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordNotValidException("New password and confirm don't same.Please put in correct.");
        }
       return validMathesOldAndNewPassword(username, oldPassword, newPassword);
    }

    private boolean validMathesOldAndNewPassword(String username, String oldPassword, String newPassword)
            throws PasswordNotValidException {
            User usr = getByUsername(username);
            if (!passwordEncoder.matches(oldPassword, usr.getPassword())) {
                throw new PasswordNotValidException("You put incorrect old password.");
            }
            usr.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(usr);
            return true;
        }

        private void validEmptyPassword (String oldPassword, String newPassword, String confirmPassword)
            throws PasswordNotValidException {
            if (StringUtils.isEmpty(oldPassword) ||
                    StringUtils.isEmpty(newPassword) ||
                    StringUtils.isEmpty(confirmPassword)) {
                throw new PasswordNotValidException("You entry empty password.Please put in correct.");
            }
        }

        @Override
        public boolean updateImage (String username, MultipartFile imageAvatar)
            throws UserNotFoundException {
            boolean isUpdate = false;
            if (!isExistByUsername(username)) {
                throw new UserNotFoundException("User with username: " + username + " didn't find");
            }
            try {
                User usr = getByUsername(username);
                saveImage(usr, imageAvatar);
                userRepository.save(usr);
                isUpdate = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return isUpdate;
        }

        private void saveImage (User user, MultipartFile imageFile) throws IOException {
            if (imageFile != null) {
                Path workFolder = Paths.get(USER_FOLDER + user.getUsername())
                        .toAbsolutePath().normalize();
                if (!Files.exists(workFolder)) {
                    Files.createDirectories(workFolder);
                }
                Files.deleteIfExists(Paths.get(workFolder + user.getUsername() + DOT + JPG_EXSTENSION));
                Files.copy(imageFile.getInputStream(), workFolder.resolve(user.getUsername() + DOT + JPG_EXSTENSION), REPLACE_EXISTING);
                user.setImageAvatar(setImage(user.getUsername()));
                log.info("FILE_SAVED_IN_FILE_SYSTEM " + imageFile.getOriginalFilename());
            }
        }

        private String setImage (String username){
            return ServletUriComponentsBuilder.fromCurrentContextPath().path(
                    USER_IMAGE_PATH + username + FORWARD_SLASH + username + DOT + JPG_EXSTENSION).toUriString();
        }

        private boolean isExistByUsername (String username){
            if (userRepository.existsByUsername(username)) {
                return true;
            }
            return false;
        }

        private boolean isExistByEmail (String email){
            if (userRepository.existsByUsername(email)) {
                return true;
            }
            return false;
        }

        private User getByUsername (String username){
            return userRepository.findByUsername(username);
        }

        private User getByEmail (String email){
            return userRepository.findByEmail(email);
        }
    }
