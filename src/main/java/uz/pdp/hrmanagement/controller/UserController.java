package uz.pdp.hrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagement.common.ResponseData;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.model.enums.RoleName;
import uz.pdp.hrmanagement.payload.*;
import uz.pdp.hrmanagement.security.JwtProvider;
import uz.pdp.hrmanagement.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR','ROLE_HR_MANAGER')")
    @PostMapping("/addUser")
    public ResponseEntity<ResponseData<UserDto>> addUser(@RequestBody UserDto userDto) {
        boolean byEmail = userService.existByEmail(userDto.getEmail());
        if (byEmail)
            return ResponseData.response("already exist", HttpStatus.BAD_REQUEST);
        UserDto user = userService.addUser(userDto);

        return ResponseData.response(user);
    }

    @PostMapping("/savePassword")
    public ResponseEntity<?> savePassword(@RequestParam String emailCode, @RequestParam String email, @RequestBody UserVerifyDto userVerifyDto) {
        Optional<User> user = userService.findByEmailAndEmailCode(email, emailCode);
        if (user.isEmpty())
            return ResponseData.response("mavjud emas", HttpStatus.BAD_REQUEST);

        userService.saveWithPassword(user.get(), userVerifyDto);
        return ResponseData.response("tasdiqlandi", HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody LoginDto loginDto) {

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

    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    @GetMapping("/getAll")
    public ResponseEntity<ResponseData<Page<UserDto>>> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {
       return ResponseData.response(userService.findAll(pageable));
    }

    @PreAuthorize("hasRole('ROLE_HR_MANAGER')")
    @GetMapping("getWorkers")
    public ResponseEntity<ResponseData<Page<UserDto>>> getWorkers(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseData.response(userService.findWorkers(pageable));
    }

    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    @PutMapping("/changeRole/{id}")
    public ResponseEntity<ResponseData<UserDto>> changeRole(@PathVariable Long id, @RequestBody ChangeRoleDto roleName){
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isEmpty())
            return ResponseData.response("user not found", HttpStatus.BAD_REQUEST);

       return ResponseData.response(userService.changeRole(optionalUser.get(), roleName));
    }

    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    @GetMapping("oneUser/{id}")
    public ResponseEntity<ResponseData<UserDto>> findOne(@PathVariable Long id){
        Optional<UserDto> optionalUser = userService.findOne(id);
        if (optionalUser.isEmpty())
            return ResponseData.response("user not found", HttpStatus.BAD_REQUEST);

        return ResponseData.response(optionalUser.get());
    }

    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    @DeleteMapping("/deleteManager/{id}")
    public ResponseEntity<?> deleteManager(@PathVariable Long id){
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isEmpty())
            return ResponseData.response("user not found", HttpStatus.BAD_REQUEST);
        userService.deleteUser(id);
        return ResponseData.response("successfully deleted");
    }

    @PreAuthorize("hasRole('ROLE_HR_MANAGER')")
    @DeleteMapping("/deleteWorker/{id}")
    public ResponseEntity<?> deleteWorker(@PathVariable Long id){
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isEmpty())
            return ResponseData.response("user not found", HttpStatus.BAD_REQUEST);

        if (!optionalUser.get().getRole().getName().equals(RoleName.ROLE_WORKER))
            return ResponseData.response("you can only delete workers", HttpStatus.BAD_REQUEST);

        userService.deleteUser(id);
        return ResponseData.response("successfully deleted");
    }

    @PutMapping("/changeUser/{id}")
    public ResponseEntity<ResponseData<UserDto>> editUser(@PathVariable Long id, @RequestBody EditUserDto editUserDto){
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isEmpty())
            return ResponseData.response("user not found", HttpStatus.BAD_REQUEST);

       return ResponseData.response(userService.editUser(optionalUser.get(), editUserDto));
    }

}

