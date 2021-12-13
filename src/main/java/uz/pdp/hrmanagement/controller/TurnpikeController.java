package uz.pdp.hrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hrmanagement.common.ResponseData;
import uz.pdp.hrmanagement.model.User;
import uz.pdp.hrmanagement.service.TurnpikeService;
import uz.pdp.hrmanagement.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("api/turnpike")
public class TurnpikeController {

    @Autowired
    TurnpikeService turnpikeService;

    @Autowired
    UserService userService;

    @PostMapping("enter/{userId}")
    public ResponseEntity<?> enter(@PathVariable Long userId){
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty())
            return ResponseData.response("you can not enter ", HttpStatus.NOT_FOUND);

        boolean userIdAndDay = turnpikeService.existByUserIdAndDay(optionalUser.get().getId());
        if (userIdAndDay)
            return ResponseData.response("user can not enter two times", HttpStatus.BAD_REQUEST);

        turnpikeService.enter(optionalUser.get());

        return ResponseData.response("user entered", HttpStatus.ACCEPTED);
    }

    @PutMapping("exit/{userId}")
    public ResponseEntity<?> exit(@PathVariable Long userId){
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty())
            return ResponseData.response("you can not enter ", HttpStatus.NOT_FOUND);

        turnpikeService.exit(optionalUser.get());

        return ResponseData.response("user went out", HttpStatus.OK);
    }
}
