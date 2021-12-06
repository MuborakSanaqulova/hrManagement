package uz.pdp.hrmanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.payload.ChangeRoleDto;
import uz.pdp.hrmanagement.payload.EditUserDto;
import uz.pdp.hrmanagement.payload.UserDto;
import uz.pdp.hrmanagement.payload.UserVerifyDto;

import java.util.Optional;


public interface UserService {

    UserDto addUser(UserDto userDto);

    User addUserEntity(User user);

    boolean existByEmail(String email);

    Optional<User> findByEmailAndEmailCode(String email, String emailCode);

    void saveWithPassword(User user, UserVerifyDto userVerifyDto);

    Optional<User> findByEmail(String username);

    Page<UserDto> findAll(Pageable pageable);

    Page<User> findAllEntity(Pageable pageable);

    Optional<User> findById(Long id);

    UserDto changeRole(User user, ChangeRoleDto roleName);

    User changeRoleEntity(User user, ChangeRoleDto roleName);

    Optional<UserDto> findOne(Long id);

    void deleteUser(Long id);

    UserDto editUser(User user, EditUserDto editUserDto);

    Page<UserDto> findWorkers(Pageable pageable);

    Page<User> findWorkersEntity(Pageable pageable);
}

