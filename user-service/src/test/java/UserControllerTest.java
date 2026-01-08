import com.eraste.common.exception.ResourceNotFoundException;
import com.eraste.common.response.ApiResponse;
import com.eraste.userservice.domain.model.User;
import com.eraste.userservice.domain.port.in.UserUseCase;
import com.eraste.userservice.infrastructure.adapter.in.web.UserController;
import com.eraste.userservice.infrastructure.adapter.in.web.dto.UserRequest;
import com.eraste.userservice.infrastructure.adapter.in.web.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("UserController Unit Tests")
public class UserControllerTest {

    @Mock
    private UserUseCase userUseCase;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Create User")
    class CreateUser {

        @Test
        @DisplayName("Should create a user successfully")
        void createUserSuccessfully() {
            UserRequest request = new UserRequest("username", "email@example.com", "First", "Last");
            User user = new User(null, "username", "email@example.com", "First", "Last", null, null);
            User createdUser = new User(1L, "username", "email@example.com", "First", "Last", null, null);
            when(userUseCase.createUser(user)).thenReturn(createdUser);

            ResponseEntity<ApiResponse<UserResponse>> response = userController.createUser(request);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals("username", response.getBody().getData().getUsername());
            verify(userUseCase, times(1)).createUser(any(User.class));
        }
    }

    @Nested
    @DisplayName("Get User By ID")
    class GetUserById {

        @Test
        @DisplayName("Should return user when found")
        void getUserByIdSuccessfully() {
            User user = new User(1L, "username", "email@example.com", "First", "Last", null, null);
            when(userUseCase.getUserById(1L)).thenReturn(Optional.of(user));

            ResponseEntity<ApiResponse<UserResponse>> response = userController.getUserById(1L);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1L, response.getBody().getData().getId());
            verify(userUseCase, times(1)).getUserById(1L);
        }

        @Test
        @DisplayName("Should throw ResourceNotFoundException when user not found")
        void getUserByIdNotFound() {
            when(userUseCase.getUserById(1L)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> userController.getUserById(1L));
            verify(userUseCase, times(1)).getUserById(1L);
        }
    }

    @Nested
    @DisplayName("Get All Users")
    class GetAllUsers {

        @Test
        @DisplayName("Should return all users")
        void getAllUsersSuccessfully() {
            List<User> users = List.of(
                    new User(1L, "user1", "email1@example.com", "First1", "Last1", null, null),
                    new User(2L, "user2", "email2@example.com", "First2", "Last2", null, null)
            );
            when(userUseCase.getAllUsers()).thenReturn(users);

            ResponseEntity<ApiResponse<List<UserResponse>>> response = userController.getAllUsers();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(2, response.getBody().getData().size());
            verify(userUseCase, times(1)).getAllUsers();
        }
    }

    @Nested
    @DisplayName("Update User")
    class UpdateUser {

        @Test
        @DisplayName("Should update user successfully")
        void updateUserSuccessfully() {
            UserRequest request = new UserRequest("updatedUsername", "updatedEmail@example.com", "UpdatedFirst", "UpdatedLast");
            User user = new User(null, "updatedUsername", "updatedEmail@example.com", "UpdatedFirst", "UpdatedLast", null, null);
            User updatedUser = new User(1L, "updatedUsername", "updatedEmail@example.com", "UpdatedFirst", "UpdatedLast", null, null);
            when(userUseCase.updateUser(1L, user)).thenReturn(updatedUser);

            ResponseEntity<ApiResponse<UserResponse>> response = userController.updateUser(1L, request);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("updatedUsername", response.getBody().getData().getUsername());
            verify(userUseCase, times(1)).updateUser(eq(1L), any(User.class));
        }
    }

    @Nested
    @DisplayName("Delete User")
    class DeleteUser {

        @Test
        @DisplayName("Should delete user successfully")
        void deleteUserSuccessfully() {
            doNothing().when(userUseCase).deleteUser(1L);

            ResponseEntity<ApiResponse<Void>> response = userController.deleteUser(1L);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            verify(userUseCase, times(1)).deleteUser(1L);
        }
    }
}
