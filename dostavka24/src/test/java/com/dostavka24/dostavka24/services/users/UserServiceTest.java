package com.dostavka24.dostavka24.services.users;

import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;
import com.dostavka24.dostavka24.domain.dtos.addresses.AddressUpdateDto;
import com.dostavka24.dostavka24.domain.dtos.users.UserCreationDto;
import com.dostavka24.dostavka24.domain.dtos.users.UserUpdateDto;
import com.dostavka24.dostavka24.domain.entities.addresses.Address;
import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.domain.entities.users.Role;
import com.dostavka24.dostavka24.domain.entities.users.User;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.repository.RoleRepository;
import com.dostavka24.dostavka24.repository.UserRepository;
import com.dostavka24.dostavka24.service.addresses.AddressService;
import com.dostavka24.dostavka24.service.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AddressService addressService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserCreationDto userCreationDto;
    private UserUpdateDto userUpdateDto;
    private Role role;
    private Address address;

    @BeforeEach
    public void setUp() {
        // Initialize mock data
        role = new Role();
        role.setId(1L);
        role.setName("USER");

        address = new Address();
        address.setId(1L);
        address.setCity("Test City");
        address.setStreet("Test Street");
        address.setLatitude(0.0);
        address.setLongitude(0.0);

        user = new User();
        user.setId(1L);
        user.setUserName("testUser");
        user.setPassword("password");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@example.com");
        user.setRole(role);
        user.setAddress(address);

        userCreationDto = new UserCreationDto();
        userCreationDto.setUserName("newUser");
        userCreationDto.setPassword("newPassword");
        userCreationDto.setFirstName("New");
        userCreationDto.setLastName("User");
        userCreationDto.setEmail("new@example.com");
        userCreationDto.setRole("USER");

        AddressCreationDto addressCreationDto = new AddressCreationDto();
        addressCreationDto.setCity("New City");
        addressCreationDto.setStreet("New Street");
        addressCreationDto.setLatitude(1.0);
        addressCreationDto.setLongitude(1.0);
        userCreationDto.setAdress(addressCreationDto);

        userUpdateDto = new UserUpdateDto();
        userUpdateDto.setUserName("updatedUser");
        userUpdateDto.setPassword("updatedPassword");
        userUpdateDto.setFirstName("Updated");
        userUpdateDto.setLastName("User");
        userUpdateDto.setEmail("updated@example.com");
        userUpdateDto.setRole("USER");

        AddressUpdateDto addressUpdateDto = new AddressUpdateDto();
        addressUpdateDto.setCity("Updated City");
        addressUpdateDto.setStreet("Updated Street");
        addressUpdateDto.setLatitude(2.0);
        addressUpdateDto.setLongitude(2.0);
        userUpdateDto.setAddress(addressUpdateDto);
    }

    @Test
    public void shouldCreateUserSuccessfully() {
        when(roleRepository.getByName(anyString())).thenReturn(role);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(addressService.create(any(AddressCreationDto.class))).thenReturn(address);

        User result = userService.create(userCreationDto);

        assertNotNull(result);
        assertEquals(userCreationDto.getUserName(), result.getUserName());
        verify(userRepository).save(any(User.class));
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    public void shouldDeleteUserSuccessfully() {
        when(userRepository.getById(anyLong())).thenReturn(user);

        userService.delete(1L);

        verify(userRepository).delete(any(User.class));
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenUserNotFoundForDelete() {
        when(userRepository.getById(anyLong())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> userService.delete(1L));
    }
    @Test
    public void shouldUpdateUserSuccessfully() {
        // Set the address ID in userUpdateDto
        userUpdateDto.setAddressId(user.getAddress().getId());

        // Stub the userRepository and addressService
        when(userRepository.getById(anyLong())).thenReturn(user);
        when(addressService.update(eq(user.getAddress().getId()), any(AddressUpdateDto.class))).thenReturn(address);
        when(userRepository.save(any())).thenReturn(user);

        // Call the update method in UserService
        User updatedUser = userService.update(1L, userUpdateDto);

        // Assertions and verifications
        assertNotNull(updatedUser);
        assertEquals(userUpdateDto.getUserName(), updatedUser.getUserName());
        verify(userRepository).save(any(User.class));
        verify(addressService).update(eq(user.getAddress().getId()), any(AddressUpdateDto.class));
    }


    @Test
    public void shouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.getAll();

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
    }

    @Test
    public void shouldReturnUserById() {
        when(userRepository.getById(anyLong())).thenReturn(user);

        User foundUser = userService.getById(1L);

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenUserNotFoundById() {
        when(userRepository.getById(anyLong())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> userService.getById(1L));
    }

    @Test
    public void shouldReturnUserId() {
        when(userRepository.findByUserName(anyString())).thenReturn(user);

        Long userId = userService.getIdOfUser("testUser");

        assertNotNull(userId);
        assertEquals(user.getId(), userId);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenUsernameNotFound() {
        when(userRepository.findByUserName(anyString())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> userService.getIdOfUser("nonExistingUser"));
    }

    @Test
    public void shouldReturnTrueWhenUsernameExists() {
        when(userRepository.existsByUserName(anyString())).thenReturn(true);

        Boolean exists = userService.checkUserName("existingUser");

        assertTrue(exists);
    }

    @Test
    public void shouldReturnFalseWhenUsernameDoesNotExist() {
        when(userRepository.existsByUserName(anyString())).thenReturn(false);

        Boolean exists = userService.checkUserName("nonExistingUser");

        assertFalse(exists);
    }



}

