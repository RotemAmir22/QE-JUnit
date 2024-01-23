package ac.il.bgu.qa;

import ac.il.bgu.qa.errors.*;
import ac.il.bgu.qa.services.DatabaseService;
import ac.il.bgu.qa.services.NotificationService;
import ac.il.bgu.qa.services.ReviewService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TestLibrary {

    Library testLibrary;
    @Mock
    Book mockBookApiClient;

    @Mock
    DatabaseService mockDBApiServer;

    @Mock
    ReviewService mockReviewApiServer;

    @Mock
    User mockUserApiClient;

    @Mock
    NotificationService mockNotificationApiClient;

    @Spy
    List<String> spyReviewsApiClient;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        testLibrary = new Library(mockDBApiServer,mockReviewApiServer);
        spyReviewsApiClient = new ArrayList<>();
    }

    //Add book
    @Test
    void GivenNullBook_WhenaddBook_ThenIllegalArgumentException_Invalidbook() {
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() -> testLibrary.addBook(null));
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid book.",thrown.getMessage());

        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).getTitle();
        verify(mockBookApiClient, never()).isBorrowed();
        verify(mockBookApiClient,times(2)).getISBN();
    }

    @Test
    void GivenNullISBN_WhenaddBook_ThenIllegalArgumentException_InvalidISBN() {
        // Mock the behavior of the mockBookApiClient
        when(mockBookApiClient.getISBN()).thenReturn(null);
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() -> testLibrary.addBook(mockBookApiClient));
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid ISBN.",thrown.getMessage());

        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).getTitle();
        verify(mockBookApiClient, never()).isBorrowed();
        verify(mockBookApiClient,times(3)).getISBN();

    }

    @ParameterizedTest
    @ValueSource(strings = {"978-3-16-148410-2","978-3-16-148410!","978-3-16-148410","aab-fjfks-gnd"})
    void GivenInvalidISBN_WhenaddBook_ThenIllegalArgumentException_InvalidISBN(String isbn) {
        // Mock the behavior of the mockBookApiClient

        when(mockBookApiClient.getISBN()).thenReturn(isbn);
        // Act

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() -> testLibrary.addBook(mockBookApiClient));
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid ISBN.",thrown.getMessage());

        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).getTitle();
        verify(mockBookApiClient, never()).isBorrowed();
    }

    @Test
    void GivenNullTitle_WhenaddBook_ThenIllegalArgumentException_Invalidtitle() {
        // Mock the behavior of the mockBookApiClient
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn(null);
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() -> testLibrary.addBook(mockBookApiClient));
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid title.",thrown.getMessage());

        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).isBorrowed();
        verify(mockBookApiClient,times(3)).getISBN();
        verify(mockBookApiClient,times(1)).getTitle();
    }

    @Test
    void GivenEmptyTitle_WhenaddBook_ThenIllegalArgumentException_Invalidtitle() {
        // Mock the behavior of the mockBookApiClient
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn("");
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() -> testLibrary.addBook(mockBookApiClient));
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid title.",thrown.getMessage());
        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).isBorrowed();
        verify(mockBookApiClient,times(3)).getISBN();
        verify(mockBookApiClient,times(2)).getTitle();
    }
    @Test
    void GivenNullAuthor_WhenaddBook_ThenIllegalArgumentException_Invalidauthor() {
        // Mock the behavior of the mockBookApiClient
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn("New-Book");
        when(mockBookApiClient.getAuthor()).thenReturn(null);
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() -> testLibrary.addBook(mockBookApiClient));
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid author.",thrown.getMessage());
        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).isBorrowed();
        verify(mockBookApiClient,times(3)).getISBN();
        verify(mockBookApiClient,times(2)).getTitle();
        verify(mockBookApiClient,times(1)).getAuthor();
    }

    @ParameterizedTest
    @ValueSource(strings = {"","Harry--Barry","!@#James", "James!@#", "12345", "James!@#Bond"})
    void GivenInvalidAuthorName_WhenAddBook_ThenIllegalArgumentException(String authorName) {
        // Mock the behavior of the mockBookApiClient
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn("New-Book");
        when(mockBookApiClient.getAuthor()).thenReturn(authorName);
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() -> testLibrary.addBook(mockBookApiClient));
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid author.",thrown.getMessage());
        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer, never()).addBook(mockBookApiClient.getISBN(), mockBookApiClient);
        verify(mockBookApiClient, never()).isBorrowed();
        verify(mockBookApiClient, times(3)).getISBN();
        verify(mockBookApiClient, times(2)).getTitle();
        verify(mockBookApiClient, times(1)).getAuthor();
    }

    @Test
    void GivenBorrowedBook_WhenaddBook_ThenIllegalArgumentException_Bookwithinvalidborrowedstate() {
        // Mock the behavior of the mockBookApiClient
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn("New-Book");
        when(mockBookApiClient.getAuthor()).thenReturn("James Bond");
        when(mockBookApiClient.isBorrowed()).thenReturn(true);
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() -> testLibrary.addBook(mockBookApiClient));
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Book with invalid borrowed state.",thrown.getMessage());
        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient,times(3)).getISBN();
        verify(mockBookApiClient,times(2)).getTitle();
        verify(mockBookApiClient,times(1)).getAuthor();
        verify(mockBookApiClient,times(1)).isBorrowed();

    }
    @Test
    void GivenExistBook_WhenaddBook_ThenIllegalArgumentException_Bookalreadyexists() {
        // Mock the behavior of the mockBookApiClient
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn("New-Book");
        when(mockBookApiClient.getAuthor()).thenReturn("James Bond");
        when(mockBookApiClient.isBorrowed()).thenReturn(false);
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() -> testLibrary.addBook(mockBookApiClient));
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Book already exists.",thrown.getMessage());
        // Verify
        verify(mockDBApiServer, times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient,times(5)).getISBN();
        verify(mockBookApiClient,times(2)).getTitle();
        verify(mockBookApiClient,times(1)).getAuthor();
        verify(mockBookApiClient,times(1)).isBorrowed();


    }
    @Test
    void GivenNewBook_WhenaddBook_ThenBookAdded() {
        // Mock the behavior of the databaseService and mockBookApiClient
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn("New-Book");
        when(mockBookApiClient.getAuthor()).thenReturn("James Bond");
        when(mockBookApiClient.isBorrowed()).thenReturn(false);
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(null);
        //Act
        testLibrary.addBook(mockBookApiClient);
        // Verify
        verify(mockDBApiServer, times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,times(1)).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient,times(6)).getISBN();
        verify(mockBookApiClient,times(2)).getTitle();
        verify(mockBookApiClient,times(1)).getAuthor();
        verify(mockBookApiClient,times(1)).isBorrowed();

    }

    //Borrow Book
    @Test
    void GivenInvalidBookISBN_WhenborrowBook_ThenIllegalArgumentException_InvalidISBN() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410");
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() ->testLibrary.borrowBook(mockBookApiClient.getISBN(), mockUserApiClient.getId()));
        // Assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid ISBN.",thrown.getMessage());
        // verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockBookApiClient, never()).borrow();
        verify(mockDBApiServer, never()).borrowBook(mockBookApiClient.getISBN(),mockUserApiClient.getId());

    }
    @Test
    void GivenNoBookISBN_WhenborrowBook_ThenBookNotFoundException_Booknotfound() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(null);
        when(mockUserApiClient.getId()).thenReturn("12345");
        // Act
        BookNotFoundException thrown = assertThrows(BookNotFoundException.class,() ->testLibrary.borrowBook(mockBookApiClient.getISBN(), mockUserApiClient.getId()));
        // Assertions
        assertEquals(BookNotFoundException.class,thrown.getClass());
        assertEquals("Book not found!",thrown.getMessage());
        // verify
        verify(mockDBApiServer, times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockBookApiClient, never()).borrow();
        verify(mockDBApiServer, never()).borrowBook(mockBookApiClient.getISBN(),mockUserApiClient.getId());

    }
    @Test
    void GivenBrrowedISBNBook_WhenborrowBook_ThenBookNotFoundException_Bookisalreadyborrowed() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("111111111111");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);
        when(mockBookApiClient.isBorrowed()).thenReturn(true);
        // Act
        BookAlreadyBorrowedException thrown = assertThrows(BookAlreadyBorrowedException.class,() ->testLibrary.borrowBook(mockBookApiClient.getISBN(), mockUserApiClient.getId()));
        // Assertions
        assertEquals(BookAlreadyBorrowedException.class,thrown.getClass());
        assertEquals("Book is already borrowed!",thrown.getMessage());
        // verify
        verify(mockDBApiServer, times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockBookApiClient, never()).borrow();
        verify(mockDBApiServer, never()).borrowBook(mockBookApiClient.getISBN(),mockUserApiClient.getId());

    }

    @Test
    void GivenInvaliduserId_WhenborrowBook_ThenIllegalArgumentException_InvaliduserId() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("12345");
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() ->testLibrary.borrowBook(mockBookApiClient.getISBN(), mockUserApiClient.getId()));
        // Assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid user Id.",thrown.getMessage());
        // verify
        verify(mockDBApiServer, times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockBookApiClient, never()).borrow();
        verify(mockDBApiServer, never()).borrowBook(mockBookApiClient.getISBN(),mockUserApiClient.getId());

    }
    @Test
    void GivenNulluserId_WhenborrowBook_ThenIllegalArgumentException_InvaliduserId() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn(null);
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() ->testLibrary.borrowBook(mockBookApiClient.getISBN(), mockUserApiClient.getId()));
        // Assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid user Id.",thrown.getMessage());
        // verify
        verify(mockDBApiServer, times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockBookApiClient, never()).borrow();
        verify(mockDBApiServer, never()).borrowBook(mockBookApiClient.getISBN(),mockUserApiClient.getId());

    }
    @Test
    void GivenNoRealuserId_WhenborrowBook_ThenUserNotRegisteredException_Usernotfound() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("111111111111");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(null);
        // Act
        UserNotRegisteredException thrown = assertThrows(UserNotRegisteredException.class,() ->testLibrary.borrowBook(mockBookApiClient.getISBN(), mockUserApiClient.getId()));
        // Assertions
        assertEquals(UserNotRegisteredException.class,thrown.getClass());
        assertEquals("User not found!",thrown.getMessage());
        // verify
        verify(mockDBApiServer, times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockBookApiClient, never()).borrow();
        verify(mockDBApiServer, never()).borrowBook(mockBookApiClient.getISBN(),mockUserApiClient.getId());

    }

    @Test
    void GivenISBNuserId_WhenborrowBook_ThenborrowBook() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("111111111111");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);
        when(mockBookApiClient.isBorrowed()).thenReturn(false);
        //Act
        testLibrary.borrowBook(mockBookApiClient.getISBN(), mockUserApiClient.getId());
        // verify
        verify(mockDBApiServer, times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockBookApiClient, times(1)).borrow();
        verify(mockDBApiServer, times(1)).borrowBook(mockBookApiClient.getISBN(),mockUserApiClient.getId());

    }

    @Test
    void GivenNoBorrowedBookISBN_WhenreturnBook_ThenBookNotBorrowedException_Bookwasntborrowed() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockBookApiClient.isBorrowed()).thenReturn(false);
        //Act
        BookNotBorrowedException thrown = assertThrows(BookNotBorrowedException.class,() ->testLibrary.returnBook(mockBookApiClient.getISBN()));
        //Assertions
        assertEquals(BookNotBorrowedException.class,thrown.getClass());
        assertEquals("Book wasn't borrowed!",thrown.getMessage());
        // verify
        verify(mockBookApiClient,never()).returnBook();
        verify(mockDBApiServer,never()).returnBook(mockBookApiClient.getISBN());
    }
    @Test
    void GivenNullBorrowedBookISBN_WhenreturnBook_ThenBookNotFoundException_Booknotfound() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(null);
        //Act
        BookNotFoundException thrown = assertThrows(BookNotFoundException.class,() ->testLibrary.returnBook(mockBookApiClient.getISBN()));
        //Assertions
        assertEquals(BookNotFoundException.class,thrown.getClass());
        assertEquals("Book not found!",thrown.getMessage());
        // verify
        verify(mockBookApiClient,never()).returnBook();
        verify(mockDBApiServer,never()).returnBook(mockBookApiClient.getISBN());
    }
    @Test
    void GivenInvalidBorrowedBookISBN_WhenreturnBook_ThenBookNotBorrowedException_Bookwasntborrowed() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-");
        //Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() ->testLibrary.returnBook(mockBookApiClient.getISBN()));
        //Assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid ISBN.",thrown.getMessage());
        // verify
        verify(mockBookApiClient,never()).returnBook();
        verify(mockDBApiServer,never()).returnBook(mockBookApiClient.getISBN());
    }
    @Test
    void GivenBorrowedBookISBN_WhenreturnBook_ThenreturnBook() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockBookApiClient.isBorrowed()).thenReturn(true);
        //Act
        testLibrary.returnBook(mockBookApiClient.getISBN());
        // verify
        verify(mockBookApiClient,times(1)).returnBook();
        verify(mockDBApiServer,times(1)).returnBook(mockBookApiClient.getISBN());
    }

    //Register User
    @Test
    void GivenUserNull_whenregisterUser_ThenIllegalArgumentException_Invaliduser() {
        //Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() ->testLibrary.registerUser(null));
        //Assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid user.",thrown.getMessage());
        //Verify
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);
    }

    @Test
    void GivenUserNullID_whenregisterUser_ThenIllegalArgumentException_InvaliduserId() {
        // Mock the behavior 
        when(mockUserApiClient.getId()).thenReturn(null);
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() ->testLibrary.registerUser(mockUserApiClient));
        // Assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid user Id.",thrown.getMessage());
        //Verify
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);
        verify(mockUserApiClient,times(3)).getId();
    }

    @Test
    void GivenUserIDLenNot12_whenregisterUser_ThenIllegalArgumentException_InvaliduserId() {
        // Mock the behavior 
        when(mockUserApiClient.getId()).thenReturn("123456");
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() ->testLibrary.registerUser(mockUserApiClient));
        // Assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid user Id.",thrown.getMessage());
        //Verify
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);
        verify(mockUserApiClient,times(4)).getId();
    }

    @Test
    void GivenUserNameNull_whenregisterUser_ThenIllegalArgumentException_Invalidusername() {
        // Mock the behavior 
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockUserApiClient.getName()).thenReturn(null);
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() ->testLibrary.registerUser(mockUserApiClient));
        // Assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid user name.",thrown.getMessage());
        //Verify
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);
        verify(mockUserApiClient,times(4)).getId();
        verify(mockUserApiClient,times(1)).getName();
    }

    @Test
    void GivenUserEmptyName_whenregisterUser_ThenIllegalArgumentException_Invalidusername() {
        // Mock the behavior 
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockUserApiClient.getName()).thenReturn("");
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() ->testLibrary.registerUser(mockUserApiClient));
        // Assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid user name.",thrown.getMessage());
        //Verify
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);
        verify(mockUserApiClient,times(4)).getId();
        verify(mockUserApiClient,times(2)).getName();
    }

    @Test
    void GivenUserwithNullNotificationService_whenregisterUser_ThenIllegalArgumentException_Invalidnotificationservice() {
        // Mock the behavior 
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockUserApiClient.getName()).thenReturn("Noa");
        when(mockUserApiClient.getNotificationService()).thenReturn(null);
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() ->testLibrary.registerUser(mockUserApiClient));
        // Assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid notification service.",thrown.getMessage());
        //Verify
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);
    }

    @Test
    void GivenExsistingUser_whenregisterUser_ThenIllegalArgumentException_Useralreadyexists() {
        // Mock the behavior 
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockUserApiClient.getName()).thenReturn("Noa");
        when(mockUserApiClient.getNotificationService()).thenReturn(mockNotificationApiClient);
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);
        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() ->testLibrary.registerUser(mockUserApiClient));
        // Assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("User already exists.",thrown.getMessage());
        //Verify
        verify(mockDBApiServer,times(1)).getUserById(mockUserApiClient.getId());
        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);

    }

    @Test
    void GivenNewUser_whenregisterUser_ThenAddUser() {
        // Mock the behavior 
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockUserApiClient.getName()).thenReturn("Noa");
        when(mockUserApiClient.getNotificationService()).thenReturn(mockNotificationApiClient);
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(null);
        //Act
        testLibrary.registerUser(mockUserApiClient);
        //verify
        verify(mockDBApiServer,times(1)).registerUser(mockUserApiClient.getId(),mockUserApiClient);

    }
    //Notify User
    @Test
    void GivenInvalidBookISBN_WhennotifyUserWithBookReviews_ThenIllegalArgumentException_InvalidISBN() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410");
        //act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() ->testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId()));
        //Assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid ISBN.",thrown.getMessage());
        assertEquals(0, spyReviewsApiClient.size());
        //Verify
        String notification = "Reviews for 'Lord of the Rings':\n" + "review 1\n" + "review 2";
        verify(mockDBApiServer,never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockReviewApiServer,never()).getReviewsForBook(mockBookApiClient.getISBN());
        verify(mockUserApiClient, never()).sendNotification(notification); // Verifying retry logic
        verify(mockReviewApiServer, never()).close();

    }
    @Test
    void GivenInvalidId_WhennotifyUserWithBookReviews_ThenIllegalArgumentException_InvaliduserId() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockUserApiClient.getId()).thenReturn("123");
        //act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() ->testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId()));
        //Assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid user Id.",thrown.getMessage());
        assertEquals(0, spyReviewsApiClient.size());
        //Verify
        String notification = "Reviews for 'Lord of the Rings':\n" + "review 1\n" + "review 2";
        verify(mockDBApiServer,never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockReviewApiServer,never()).getReviewsForBook(mockBookApiClient.getISBN());
        verify(mockUserApiClient, never()).sendNotification(notification); // Verifying retry logic
        verify(mockReviewApiServer, never()).close();

    }

    @Test
    void GivenNullId_WhennotifyUserWithBookReviews_ThenIllegalArgumentException_InvaliduserId() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockUserApiClient.getId()).thenReturn(null);
        //act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() ->testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId()));
        //Assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid user Id.",thrown.getMessage());
        assertEquals(0, spyReviewsApiClient.size());
        //Verify
        String notification = "Reviews for 'Lord of the Rings':\n" + "review 1\n" + "review 2";
        verify(mockDBApiServer,never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockReviewApiServer,never()).getReviewsForBook(mockBookApiClient.getISBN());
        verify(mockUserApiClient, never()).sendNotification(notification); // Verifying retry logic
        verify(mockReviewApiServer, never()).close();

    }
    @Test
    void GivenNullBook_WhennotifyUserWithBookReviews_ThenBookNotFoundException_Booknotfound() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockUserApiClient.getId()).thenReturn("111111111111");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(null);
        //act
        BookNotFoundException thrown = assertThrows(BookNotFoundException.class,() ->testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId()));
        //Assertions
        assertEquals(BookNotFoundException.class,thrown.getClass());
        assertEquals("Book not found!",thrown.getMessage());
        assertEquals(0, spyReviewsApiClient.size());
        //Verify
        String notification = "Reviews for 'Lord of the Rings':\n" + "review 1\n" + "review 2";
        verify(mockDBApiServer,times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockReviewApiServer,never()).getReviewsForBook(mockBookApiClient.getISBN());
        verify(mockUserApiClient, never()).sendNotification(notification); // Verifying retry logic
        verify(mockReviewApiServer, never()).close();

    }

    @Test
    void GivenNullUser_WhennotifyUserWithBookReviews_ThenUserNotRegisteredException_Usernotfound() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockUserApiClient.getId()).thenReturn("111111111111");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(null);
        //act
        UserNotRegisteredException thrown = assertThrows(UserNotRegisteredException.class,() ->testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId()));
        //Assertions
        assertEquals(UserNotRegisteredException.class,thrown.getClass());
        assertEquals("User not found!",thrown.getMessage());
        assertEquals(0, spyReviewsApiClient.size());
        //Verify
        String notification = "Reviews for 'Lord of the Rings':\n" + "review 1\n" + "review 2";
        verify(mockDBApiServer,times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,times(1)).getUserById(mockUserApiClient.getId());
        verify(mockReviewApiServer,never()).getReviewsForBook(mockBookApiClient.getISBN());
        verify(mockUserApiClient, never()).sendNotification(notification); // Verifying retry logic
        verify(mockReviewApiServer, never()).close();

    }

    @Test
    void GivenBookWithNullReviews_WhennotifyUserWithBookReviews_ThenNoReviewsFoundException_Noreviewsfound() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);
        when(mockReviewApiServer.getReviewsForBook(mockBookApiClient.getISBN())).thenReturn(null);
        //act
        NoReviewsFoundException thrown = assertThrows(NoReviewsFoundException.class,() ->testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId()));
        //Assertions
        assertEquals(NoReviewsFoundException.class,thrown.getClass());
        assertEquals("No reviews found!",thrown.getMessage());
        assertEquals(0, spyReviewsApiClient.size());
        //Verify
        String notification = "Reviews for 'Lord of the Rings':\n" + "review 1\n" + "review 2";
        verify(mockDBApiServer,times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,times(1)).getUserById(mockUserApiClient.getId());
        verify(mockReviewApiServer,times(1)).getReviewsForBook(mockBookApiClient.getISBN());
        verify(mockUserApiClient, never()).sendNotification(notification); // Verifying retry logic
        verify(mockReviewApiServer, times(1)).close();

    }

    @Test
    void GivenBookWithEmptyReviews_WhennotifyUserWithBookReviews_ThenNoReviewsFoundException_Noreviewsfound() {
        // Mock the behavior 
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);
        when(mockReviewApiServer.getReviewsForBook(mockBookApiClient.getISBN())).thenReturn(spyReviewsApiClient);
        //act
        NoReviewsFoundException thrown = assertThrows(NoReviewsFoundException.class,() ->testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId()));
        //assertions
        assertEquals(NoReviewsFoundException.class,thrown.getClass());
        assertEquals("No reviews found!",thrown.getMessage());
        assertEquals(0, spyReviewsApiClient.size());
        //Verify
        String notification = "Reviews for 'Lord of the Rings':\n" + "review 1\n" + "review 2";
        verify(mockDBApiServer,times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,times(1)).getUserById(mockUserApiClient.getId());
        verify(mockReviewApiServer,times(1)).getReviewsForBook(mockBookApiClient.getISBN());
        verify(mockUserApiClient, never()).sendNotification(notification); // Verifying retry logic
        verify(mockReviewApiServer, times(1)).close();
    }

    @Test
    void GivenReviewwithanIssue_WhennotifyUserWithBookReviews_ThenReviewException_Reviewserviceunavailable() {
        // Mock the behavior
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);
        when(mockReviewApiServer.getReviewsForBook(mockBookApiClient.getISBN())).thenThrow(new ReviewException("Review service unavailable!"));
        //act
        ReviewServiceUnavailableException thrown = assertThrows(ReviewServiceUnavailableException.class,() ->testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId()));
        //assertions
        assertEquals(ReviewServiceUnavailableException.class,thrown.getClass());
        assertEquals("Review service unavailable!",thrown.getMessage());
        assertEquals(0, spyReviewsApiClient.size());
        //Verify
        String notification = "Reviews for 'Lord of the Rings':\n" + "review 1\n" + "review 2";
        verify(mockDBApiServer,times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,times(1)).getUserById(mockUserApiClient.getId());
        verify(mockReviewApiServer,times(1)).getReviewsForBook(mockBookApiClient.getISBN());
        verify(mockUserApiClient, never()).sendNotification(notification); // Verifying retry logic
        verify(mockReviewApiServer, times(1)).close();
    }

    @Test
    void GivenBookWithReviewsAndNotifyServiceNotWorking_WhenNotifyUserWithBookReviews_ThenThrowNotificationException_Notificationfailed() {
        // Mock setup for dependent services
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);

        spyReviewsApiClient.add("review 1");
        spyReviewsApiClient.add("review 2");

        when(mockBookApiClient.getTitle()).thenReturn("Lord of the Rings");
        when(mockReviewApiServer.getReviewsForBook(mockBookApiClient.getISBN())).thenReturn(spyReviewsApiClient);

        // Setting up the mock to throw an exception for the first call, and do nothing for the next four calls
        String notification = "Reviews for 'Lord of the Rings':\n" + "review 1\n" + "review 2";
        doThrow(new NotificationException("Notification failed!")).when(mockUserApiClient).sendNotification(notification);

        NotificationException thrown = assertThrows(NotificationException.class,() ->testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId()));

        // Assertions
        assertEquals(NotificationException.class,thrown.getClass());
        assertEquals("Notification failed!",thrown.getMessage());
        assertEquals(2, spyReviewsApiClient.size());

        //Verify
        verify(mockDBApiServer,times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,times(1)).getUserById(mockUserApiClient.getId());
        verify(mockReviewApiServer,times(1)).getReviewsForBook(mockBookApiClient.getISBN());
        verify(mockUserApiClient, times(5)).sendNotification(notification); // Verifying retry logic
        verify(mockReviewApiServer, times(1)).close();
    }

    @Test
    void GivenBookwithReviews_WhennotifyUserWithBookReviews_ThenNotifyUser() {
        // Mock the behavior
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);

        when(mockBookApiClient.getTitle()).thenReturn("Lord of te Rings");
        when(mockReviewApiServer.getReviewsForBook(mockBookApiClient.getISBN())).thenReturn(spyReviewsApiClient);
        //act
        spyReviewsApiClient.add("review 1");
        spyReviewsApiClient.add("review 2");

        String notification = "Reviews for 'Lord of the Rings':\n" + "review 1\n" + "review 2";

        testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId());
        //assertion
        assertEquals(2, spyReviewsApiClient.size());

        //Verify
        verify(mockDBApiServer,times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,times(1)).getUserById(mockUserApiClient.getId());
        verify(mockReviewApiServer,times(1)).getReviewsForBook(mockBookApiClient.getISBN());
        verify(mockUserApiClient, never()).sendNotification(notification);
        verify(mockReviewApiServer, times(1)).close();

    }

    @ParameterizedTest
    @ValueSource(strings = {"978-3-16-148410-2","978-3-16-148410!","978-3-16-148410","aab-fjfks-gnd"})
    void GivenInvalidISBN_WhengetBookByISBN_Then_IllegalArgumentException_InvalidISBN(String isbn){
        // Mock the behavior
        when(mockBookApiClient.getISBN()).thenReturn(isbn);
        when(mockUserApiClient.getId()).thenReturn("111111111111");
        //act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() -> testLibrary.getBookByISBN(mockBookApiClient.getISBN(),mockUserApiClient.getId()));
        //assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid ISBN.",thrown.getMessage());
        //verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());

    }

    @Test
    void GivenNullISBN_WhengetBookByISBN_Then_IllegalArgumentException_InvalidISBN(){
        // Mock the behavior
        when(mockBookApiClient.getISBN()).thenReturn(null);
        when(mockUserApiClient.getId()).thenReturn("111111111111");
        //act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() -> testLibrary.getBookByISBN(mockBookApiClient.getISBN(),mockUserApiClient.getId()));
        //assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid ISBN.",thrown.getMessage());
        //verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());

    }
    @Test
    void GivenNullId_WhengetBookByISBN_ThenIllegalArgumentException_InvaliduserId(){
        // Mock the behavior
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockUserApiClient.getId()).thenReturn(null);
        //act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() -> testLibrary.getBookByISBN(mockBookApiClient.getISBN(),mockUserApiClient.getId()));
        //assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid user Id.",thrown.getMessage());
        //verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());

    }
    @ParameterizedTest
    @ValueSource(strings = {"","111111","11111111!!!","aasgdff"})
    void GivenInvalidId_WhengetBookByISBN_ThenIllegalArgumentException_InvaliduserId(String id){
        // Mock the behavior
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockUserApiClient.getId()).thenReturn(id);
        //act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() -> testLibrary.getBookByISBN(mockBookApiClient.getISBN(),mockUserApiClient.getId()));
        //assertions
        assertEquals(IllegalArgumentException.class,thrown.getClass());
        assertEquals("Invalid user Id.",thrown.getMessage());
        //verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());

    }

    @Test
    void GivenNoBookISBN_WhengetBookByISBN_ThenBookNotFoundException_Booknotfound(){
        // Mock the behavior
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockUserApiClient.getId()).thenReturn("111111111111");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(null);
        //act
        BookNotFoundException thrown = assertThrows(BookNotFoundException.class,() -> testLibrary.getBookByISBN(mockBookApiClient.getISBN(),mockUserApiClient.getId()));
        //assertions
        assertEquals(BookNotFoundException.class,thrown.getClass());
        assertEquals("Book not found!",thrown.getMessage());
        //verify
        verify(mockDBApiServer, times(1)).getBookByISBN(mockBookApiClient.getISBN());

    }
    @Test
    void GivenBorrowedBookISBN_WhengetBookByISBN_ThenBookAlreadyBorrowedException_Bookwasalreadyborrowed(){
        // Mock the behavior
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockUserApiClient.getId()).thenReturn("111111111111");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockBookApiClient.isBorrowed()).thenReturn(true);
        //act
        BookAlreadyBorrowedException thrown = assertThrows(BookAlreadyBorrowedException.class,() -> testLibrary.getBookByISBN(mockBookApiClient.getISBN(),mockUserApiClient.getId()));
        //assertions
        assertEquals(BookAlreadyBorrowedException.class,thrown.getClass());
        assertEquals("Book was already borrowed!",thrown.getMessage());
        //verify
        verify(mockDBApiServer, times(1)).getBookByISBN(mockBookApiClient.getISBN());

    }
    @Test
    void GivenBookISBNAndUserId_WhengetBookByISBN_ThenreturnBook_Notificationfailed(){
        // Mock the behavior
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockUserApiClient.getId()).thenReturn("111111111111");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);
        when(mockBookApiClient.isBorrowed()).thenReturn(false);
        //act
        Book book = testLibrary.getBookByISBN(mockBookApiClient.getISBN(),mockUserApiClient.getId());
        //assertion
        assertEquals(book, mockBookApiClient);
        //verify
        verify(mockDBApiServer, times(2)).getBookByISBN(mockBookApiClient.getISBN());


    }

    @Test
    void GivenBookISBNAndUserId_WhengetBookByISBN_ThenreturnBook(){
        // Mock the behavior
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockUserApiClient.getId()).thenReturn("111111111111");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);
        when(mockBookApiClient.isBorrowed()).thenReturn(false);
        when(mockBookApiClient.getTitle()).thenReturn("Lord of te Rings");
        when(mockReviewApiServer.getReviewsForBook(mockBookApiClient.getISBN())).thenReturn(spyReviewsApiClient);
        //act
        spyReviewsApiClient.add("review 1");
        spyReviewsApiClient.add("review 2");

        String notification = "Reviews for 'Lord of the Rings':\n" + "review 1\n" + "review 2";

        Book book = testLibrary.getBookByISBN(mockBookApiClient.getISBN(),mockUserApiClient.getId());
        //assert
        assertEquals(book, mockBookApiClient);
        //verify
        verify(mockDBApiServer, times(2)).getBookByISBN(mockBookApiClient.getISBN());


    }
}
