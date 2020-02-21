package com.app.bugtracker.users.controllers;

import com.app.bugtracker.users.dto.UserCreateRequest;
import com.app.bugtracker.users.dto.UserDTO;
import com.app.bugtracker.users.dto.UserUpdateRequest;
import com.app.bugtracker.users.services.IUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

import static com.app.bugtracker.Urls.USERS;
import static com.app.bugtracker.Urls.USER;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Api(tags = "users-controller")
public class UsersController implements IUsersController {

    /**
     * Users service.
     */
    private final IUsersService usersService;

    /**
     * Conversion service.
     */
    private final ConversionService conversionService;

    /**
     * Users service constructor.
     * @param usersService users service.
     */
    public UsersController(IUsersService usersService,
                           ConversionService conversionService) {
        this.usersService = usersService;
        this.conversionService = conversionService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = USERS)
    @ApiOperation(value = "Find all users.")
    public ResponseEntity<Page<UserDTO>> findAll(
            @PageableDefault(page = 0, size = 25)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "firstName", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "lastName", direction = Sort.Direction.ASC)
            })
            final Pageable request) {

        return new ResponseEntity<>(usersService.findAll(request)
                .map(u -> conversionService.convert(u, UserDTO.class)), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping(path = USER, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Find user by id.")
    public ResponseEntity<UserDTO> findById(@PathVariable final UUID id) {

        return new ResponseEntity<>(conversionService.convert(usersService.findById(id),
                UserDTO.class), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping(path = USERS,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("Create user.")
    public ResponseEntity<UserDTO> create(
            @RequestBody @Valid final UserCreateRequest request) {

        return new ResponseEntity<>(conversionService
                .convert(usersService.create(request), UserDTO.class), CREATED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PutMapping(path = USER,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("Update user.")
    public ResponseEntity<UserDTO> update(
            @PathVariable final UUID id,
            @RequestBody @Valid final UserUpdateRequest request) {

        return new ResponseEntity<>(conversionService.convert(
                usersService.update(id, request), UserDTO.class
        ), OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DeleteMapping(path = USER)
    @ApiOperation("Delete mapping.")
    public ResponseEntity delete(@PathVariable  final UUID id) {
        usersService.deleteById(id);

        return new ResponseEntity<>(NO_CONTENT);
    }
}
