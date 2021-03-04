package pl.kubaretip.cpo.api.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.constants.AuthoritiesConstants;
import pl.kubaretip.cpo.api.dto.UserDTO;
import pl.kubaretip.cpo.api.dto.mapper.UserMapper;
import pl.kubaretip.cpo.api.service.UserService;
import pl.kubaretip.cpo.api.util.ExceptionUtils;
import pl.kubaretip.cpo.api.web.rest.request.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ExceptionUtils exceptionUtils;

    public UserController(UserService userService,
                          UserMapper userMapper,
                          ExceptionUtils exceptionUtils) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.exceptionUtils = exceptionUtils;
    }

    @Secured(AuthoritiesConstants.Code.MODERATOR)
    @PostMapping
    public ResponseEntity<UserDTO> registerNewUser(@Valid @RequestBody UserRequest request,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        var newUser = userService.createUser(userMapper.mapUserRequestToUserDTO(request));
        var responseUserDTO = userMapper.mapToDTO(newUser);
        var locationURI = uriComponentsBuilder.path("/users/{id}")
                .buildAndExpand(responseUserDTO.getId()).toUri();
        return ResponseEntity.created(locationURI).body(responseUserDTO);
    }

    @PatchMapping("/activate")
    public ResponseEntity<UserDTO> activateUser(@Valid @RequestBody ActivateUserRequest request) {
        var user = userService.activateUser(request.getUsername(),
                request.getPassword(), request.getActivationKey());
        return ResponseEntity.ok(userMapper.mapToDTO(user));
    }

    @Secured(AuthoritiesConstants.Code.MODERATOR)
    @PatchMapping(path = "/{id}/authority")
    public ResponseEntity<Void> assignUserToAuthority(@PathVariable("id") Long userId,
                                                      @Valid @RequestBody UserAuthorityRequest request) {
        if (!userId.equals(request.getUserId())) {
            throw exceptionUtils.pathIdNotEqualsBodyId();
        }
        userService.assignUserToNewAuthority(request.getUserId(), request.getAuthorityName());
        return ResponseEntity.noContent().build();
    }

    @Secured(AuthoritiesConstants.Code.MODERATOR)
    @DeleteMapping(path = "/{id}/authority")
    public ResponseEntity<Void> removeUserAuthority(@PathVariable("id") Long userId,
                                                    @Valid @RequestBody UserAuthorityRequest request) {
        if (!userId.equals(request.getUserId())) {
            throw exceptionUtils.pathIdNotEqualsBodyId();
        }
        userService.removeUserAuthority(request.getUserId(), request.getAuthorityName());
        return ResponseEntity.noContent().build();
    }

    @Secured(AuthoritiesConstants.Code.USER)
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userMapper.mapToDTO(userService.getUserById(userId)));
    }

    @PreAuthorize("#userId == T(pl.kubaretip.cpo.api.util.SecurityUtils).principal(authentication.principal).id")
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> editUserById(@PathVariable("id") Long userId,
                                                @Valid @RequestBody EditUserRequest request) {
        var userDTO = userMapper.mapEditUserRequestToUserDTO(request);
        userDTO.setId(userId);
        return ResponseEntity.ok(userMapper.mapToDTO(userService.editUserById(userDTO)));
    }

    @Secured(AuthoritiesConstants.Code.MODERATOR)
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable("id") Long userId,
                                                  @Valid @RequestBody UpdateUserRequest request) {
        if (!userId.equals(request.getId())) {
            throw exceptionUtils.pathIdNotEqualsBodyId();
        }
        var userDTO = userMapper.mapUpdateUserRequestToUserDTO(request);
        return ResponseEntity.ok(userMapper.mapToDTO(userService.updateUser(userDTO)));
    }

}
