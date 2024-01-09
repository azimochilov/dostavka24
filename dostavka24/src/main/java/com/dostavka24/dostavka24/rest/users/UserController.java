package com.dostavka24.dostavka24.rest.users;
import com.dostavka24.dostavka24.domain.dtos.users.UserCreationDto;
import com.dostavka24.dostavka24.domain.dtos.users.VerifyDto;
import com.dostavka24.dostavka24.service.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity register(@RequestBody UserCreationDto user){
        if(!checkPasswordLength(user.getPassword())){
            return new ResponseEntity("Password's length less than 4 ", HttpStatus.BAD_REQUEST);
        }
        if(userService.checkUserName(user.getUserName())){
            return new ResponseEntity(" This user already exsists! ", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(userService.create(user));
    }

    private Boolean checkPasswordLength(String password){

        return (password.length()>4);
    }
    @PostMapping("/{id}/verify")
    public ResponseEntity<?> verifyUser(@PathVariable Long id, @RequestBody VerifyDto verifyDto){
        boolean verification = userService.verification(id, verifyDto.getCode());
        if (verification) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
