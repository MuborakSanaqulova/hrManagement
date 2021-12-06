package uz.pdp.hrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagement.common.ResponseData;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.payload.LoginDto;
import uz.pdp.hrmanagement.payload.UserDto;
import uz.pdp.hrmanagement.payload.UserVerifyDto;
import uz.pdp.hrmanagement.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserService userService;

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

        return userService.login(loginDto);

    }

}
