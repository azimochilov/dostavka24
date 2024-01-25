package com.dostavka24.dostavka24.rest.users;
import com.dostavka24.dostavka24.domain.dtos.users.UserCreationDto;
import com.dostavka24.dostavka24.domain.dtos.users.UserUpdateDto;
import com.dostavka24.dostavka24.domain.dtos.users.VerifyDto;
import com.dostavka24.dostavka24.domain.entities.users.User;
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
    public ResponseEntity<?> register(@RequestBody UserCreationDto user){
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

    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateDto> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userDto) {
        User user = userService.update(id, userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
