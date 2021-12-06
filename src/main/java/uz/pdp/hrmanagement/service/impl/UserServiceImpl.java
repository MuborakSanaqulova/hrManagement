package uz.pdp.hrmanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagement.common.ResponseData;
import uz.pdp.hrmanagement.model.Role;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.model.enums.RoleName;
import uz.pdp.hrmanagement.payload.LoginDto;
import uz.pdp.hrmanagement.payload.UserDto;
import uz.pdp.hrmanagement.payload.UserVerifyDto;
import uz.pdp.hrmanagement.repository.UserRepository;
import uz.pdp.hrmanagement.security.JwtProvider;
import uz.pdp.hrmanagement.service.MailService;
import uz.pdp.hrmanagement.service.UserService;
import uz.pdp.hrmanagement.service.mapper.UserMapper;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = new User();
        User employer = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (employer.getRole().getName().equals(RoleName.ROLE_DIRECTOR))
            user.setRole(new Role(RoleName.ROLE_MANAGER));
        if (employer.getRole().getName().equals(RoleName.ROLE_HR_MANAGER))
            user.setRole(new Role(RoleName.ROLE_WORKER));
        user.setEmailCode(UUID.randomUUID().toString());
        user = addUserEntity(userMapper.toEntity(userDto));
        mailService.sendVerify(user.getEmail(), user.getEmailCode());
        return userMapper.toDto(user);
    }

    @Override
    public User addUserEntity(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmailAndEmailCode(String email, String emailCode) {
        return userRepository.findByEmailAndEmailCode(email, emailCode);
    }

    @Override
    public void saveWithPassword(User user, UserVerifyDto userVerifyDto) {
        user.setPassword(passwordEncoder.encode(userVerifyDto.getPassword()));
        user.setEmailCode(null);
        addUserEntity(user);
    }

    @Override
    public Optional<User> findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public ResponseEntity<ResponseData<String>> login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword()
            ));

            User user = (User) authenticate.getPrincipal();

            String token = jwtProvider.generateToken(loginDto.getUsername());

            return ResponseData.response(token);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
        }
        return ResponseData.response("login yoki parol xato", HttpStatus.BAD_REQUEST);
    }


}
