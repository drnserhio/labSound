package com.sound.labsound.resource;

import com.sound.labsound.exception.domain.*;
import com.sound.labsound.model.User;
import com.sound.labsound.model.principal.CustomUserPrincipal;
import com.sound.labsound.service.UserService;
import com.sound.labsound.utils.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/usr")
public class UserResource {


    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<User> registerAccount(
            @RequestBody User user)
            throws PasswordIsLowException, UserNameExistsException, EmailExistsException, NullOrEmtpyFieldUserException {
        User usr = userService.registerAccount(user);
        return new ResponseEntity<>(usr, OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginIn(
            @RequestBody User user) {
        authenticated(user.getUsername(), user.getPassword());
        User usr = userService.getUserByUsername(user.getUsername());
        CustomUserPrincipal principal = new CustomUserPrincipal(usr);
       HttpHeaders httpHeaders = getJwtHeaders(principal);
       return new ResponseEntity<>(usr, httpHeaders, OK);
    }

    @PutMapping("/update_account/{currentUsername}")
    public ResponseEntity<User> updateAccount(
            @PathVariable("currentUsername") String currentUsername,
            @RequestBody User user)
            throws PasswordIsLowException, UserNameExistsException, EmailExistsException, UserNotFoundException, NullOrEmtpyFieldUserException {
        User usr = userService.updateAccount(currentUsername, user);
        return new ResponseEntity<>(usr, CREATED);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<Boolean> resetPassword(
            @RequestParam("email") String email)
            throws EmailExistsException {
        boolean isReset = userService.resetPassword(email);
        return new ResponseEntity<>(isReset, CREATED);
    }

    @DeleteMapping("/delete/{username}")
    public boolean removeAccount(
            @PathVariable("username") String username) {
        return userService.removeAccount(username);
    }

    @GetMapping("/get_username/{username}")
    public ResponseEntity<User> getUserByUsername(
            @PathVariable("username") String username) {
        User usr = userService.getUserByUsername(username);
        return new ResponseEntity<>(usr, OK);
    }

    @GetMapping("/get_email/{email}")
    public ResponseEntity<User> getUserByEmail(
            @PathVariable("email") String email) {
        User usr = userService.getUserByEmail(email);
        return new ResponseEntity<>(usr, OK);
    }

    public void authenticated(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    public HttpHeaders getJwtHeaders(CustomUserPrincipal principal) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("access_token", jwtTokenProvider.generateToken(principal));
        return httpHeaders;
    }

}
