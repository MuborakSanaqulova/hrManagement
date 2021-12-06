package uz.pdp.hrmanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagement.model.Role;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.model.enums.RoleName;
import uz.pdp.hrmanagement.payload.*;
import uz.pdp.hrmanagement.repository.RoleRepository;
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

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);

        User employer = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (employer.getRole().getName().equals(RoleName.ROLE_DIRECTOR))
            user.setRole(new Role(2L, RoleName.ROLE_MANAGER));
        if (employer.getRole().getName().equals(RoleName.ROLE_HR_MANAGER))
            user.setRole(new Role(3L, RoleName.ROLE_WORKER));
        user.setEmailCode(UUID.randomUUID().toString());
        user.setEnabled(true);
        User result = addUserEntity(user);
        mailService.sendVerify(result.getEmail(), result.getEmailCode());
        return userMapper.toDto(result);
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
    public Page<UserDto> findAll(Pageable pageable) {
        return findAllEntity(pageable).map(userMapper::toDto);
    }

    @Override
    public Page<User> findAllEntity(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDto changeRole(User user, ChangeRoleDto roleName) {
        return userMapper.toDto(changeRoleEntity(user, roleName));
    }

    @Override
    public User changeRoleEntity(User user, ChangeRoleDto roleName) {
        Optional<Role> byName = roleRepository.findByName(roleName.getRoleName());
        byName.ifPresent(user::setRole);
        return addUserEntity(user);
    }

    @Override
    public Optional<UserDto> findOne(Long id) {
        return findById(id).map(userMapper::toDto);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto editUser(User user, EditUserDto editUserDto) {
        user.setPassword(editUserDto.getPassword());
        user.setFirstName(editUserDto.getFirstName());
        user.setLastName(editUserDto.getLastName());
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public Page<UserDto> findWorkers(Pageable pageable) {
        return findWorkersEntity(pageable).map(userMapper::toDto);
    }

    @Override
    public Page<User> findWorkersEntity(Pageable pageable) {
        return userRepository.findAllByRoleId(4L, pageable);
    }

}
