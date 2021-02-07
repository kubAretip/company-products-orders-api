package pl.kubaretip.cpo.api.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.dto.UserDTO;
import pl.kubaretip.cpo.api.service.UserService;
import pl.kubaretip.cpo.api.web.rest.vm.UserActivationVM;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> registerNewUser(@Valid @RequestBody UserDTO userDTO,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        var newUser = userService.createUser(userDTO);
        var locationURI = uriComponentsBuilder.path("/users/{id}")
                .buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(locationURI).body(newUser);
    }

    @PatchMapping("/activate")
    public ResponseEntity<UserDTO> activateUser(@Valid @RequestBody UserActivationVM userActivationVM) {
        return ResponseEntity.ok(userService.activateUser(userActivationVM.getUsername(),
                userActivationVM.getPassword(),
                userActivationVM.getActivationKey()));
    }

    @PatchMapping(path = "/role", params = {"name", "user_id", "assign"})
    public ResponseEntity<Void> assignUserToAuthority(@RequestParam(value = "user_id") long userId,
                                                      @RequestParam(value = "name") String name,
                                                      @RequestParam(value = "assign") boolean assign) {
        if (assign)
            userService.assignUserToNewAuthority(userId, name);
        else
            userService.removeUserAuthority(userId, name);

        return ResponseEntity.noContent().build();
    }

}
