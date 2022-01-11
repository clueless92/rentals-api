package bg.tilchev.rentalsapi.controller;

import bg.tilchev.rentalsapi.entity.User;
import bg.tilchev.rentalsapi.repo.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserRepo userRepoMock;

    @InjectMocks
    private UserController testedController;

    @Test
    public void testGetUsers() {
        // given
        List<User> expectedUsers = Collections.singletonList(new User());
        Mockito.when(userRepoMock.findAll()).thenReturn(expectedUsers);

        // when
        ResponseEntity<List<User>> actual = testedController.getUsers();

        // then
        Mockito.verify(userRepoMock, times(1)).findAll();
        Assertions.assertEquals(expectedUsers, actual.getBody());
    }
}
