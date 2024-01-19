package ac.il.bgu.qa;

import ac.il.bgu.qa.errors.*;
import ac.il.bgu.qa.services.DatabaseService;
import ac.il.bgu.qa.services.NotificationService;
import ac.il.bgu.qa.services.ReviewService;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        testLibrary = new Library(mockDBApiServer,mockReviewApiServer);
    }

    //Add book
    @Test
    void GivenNullBook_WhenaddBook_ThenIllegalArgumentException_Invalidbook() {
        // Act
        Exception e = new Exception();
        try{
            testLibrary.addBook(null);
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid book.",e.getMessage());

        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).getTitle();
        verify(mockBookApiClient, never()).isBorrowed();
    }

    @Test
    void GivenNullISBN_WhenaddBook_ThenIllegalArgumentException_InvalidISBN() {
        // Mock the behavior of the mockBookApiClient

        when(mockBookApiClient.getISBN()).thenReturn(null);
        // Act
        Exception e = new Exception();
        try{
            testLibrary.addBook(mockBookApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid ISBN.",e.getMessage());

        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).getTitle();
        verify(mockBookApiClient, never()).isBorrowed();
    }

    @Test
    void GivenNot13LenISBN_WhenaddBook_ThenIllegalArgumentException_InvalidISBN() {
        // Mock the behavior of the mockBookApiClient

        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410");
        // Act
        Exception e = new Exception();
        try{
            testLibrary.addBook(mockBookApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid ISBN.",e.getMessage());

        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).getTitle();
        verify(mockBookApiClient, never()).isBorrowed();
    }

    @Test
    void GivenInvalidISBN_WhenaddBook_ThenIllegalArgumentException_InvalidISBN() {
        // Mock the behavior of the mockBookApiClient

        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410!");
        // Act
        Exception e = new Exception();
        try{
            testLibrary.addBook(mockBookApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid ISBN.",e.getMessage());

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
        Exception e = new Exception();
        try{
            testLibrary.addBook(mockBookApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid title.",e.getMessage());

        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).isBorrowed();
    }

    @Test
    void GivenEmptyTitle_WhenaddBook_ThenIllegalArgumentException_Invalidtitle() {
        // Mock the behavior of the mockBookApiClient

        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn("");
        // Act
        Exception e = new Exception();
        try{
            testLibrary.addBook(mockBookApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid title.",e.getMessage());

        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).isBorrowed();
    }
    @Test
    void GivenNullAuthor_WhenaddBook_ThenIllegalArgumentException_Invalidauthor() {
        // Mock the behavior of the mockBookApiClient

        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn("New-Book");
        when(mockBookApiClient.getAuthor()).thenReturn(null);
        // Act
        Exception e = new Exception();
        try{
            testLibrary.addBook(mockBookApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid author.",e.getMessage());

        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).isBorrowed();
    }

    @Test
    void GivenEmptyAuthor_WhenaddBook_ThenIllegalArgumentException_Invalidauthor() {
        // Mock the behavior of the mockBookApiClient

        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn("New-Book");
        when(mockBookApiClient.getAuthor()).thenReturn("");
        // Act
        Exception e = new Exception();
        try{
            testLibrary.addBook(mockBookApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid author.",e.getMessage());

        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).isBorrowed();
    }

    @Test
    void GivenInvalidAuthorNameEnd_WhenaddBook_ThenIllegalArgumentException_Invalidauthor() {
        // Mock the behavior of the mockBookApiClient

        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn("New-Book");
        when(mockBookApiClient.getAuthor()).thenReturn("James!@#");
        // Act
        Exception e = new Exception();
        try{
            testLibrary.addBook(mockBookApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid author.",e.getMessage());

        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).isBorrowed();
    }
    @Test
    void GivenInvalidAuthorNameMiddle_WhenaddBook_ThenIllegalArgumentException_Invalidauthor() {
        // Mock the behavior of the mockBookApiClient

        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn("New-Book");
        when(mockBookApiClient.getAuthor()).thenReturn("James!@#Bond");
        // Act
        Exception e = new Exception();
        try{
            testLibrary.addBook(mockBookApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid author.",e.getMessage());

        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
        verify(mockBookApiClient, never()).isBorrowed();
    }

    @Test
    void GivenBorrowedBook_WhenaddBook_ThenIllegalArgumentException_Bookwithinvalidborrowedstate() {
        // Mock the behavior of the mockBookApiClient

        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn("New-Book");
        when(mockBookApiClient.getAuthor()).thenReturn("James Bond");
        when(mockBookApiClient.isBorrowed()).thenReturn(true);

        // Act
        Exception e = new Exception();
        try{
            testLibrary.addBook(mockBookApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Book with invalid borrowed state.",e.getMessage());

        // Verify
        verify(mockDBApiServer, never()).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
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
        Exception e = new Exception();
        try{
            testLibrary.addBook(mockBookApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Book already exists.",e.getMessage());

        // Verify
        verify(mockDBApiServer, times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);
    }
    @Test
    void GivenNewBook_WhenaddBook_ThenBookAdded() {
        // Mock the behavior of the databaseService and mockBookApiClient

        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn("New-Book");
        when(mockBookApiClient.getAuthor()).thenReturn("James Bond");
        when(mockBookApiClient.isBorrowed()).thenReturn(false);
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(null);

        testLibrary.addBook(mockBookApiClient);

        // Verify
        verify(mockDBApiServer, times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,times(1)).addBook(mockBookApiClient.getISBN(),mockBookApiClient);

    }

    //Register User
    @Test
    void GivenUserNull_whenregisterUser_ThenIllegalArgumentException_Invaliduser() {
        Exception e = new Exception();
        try{
            testLibrary.registerUser(null);
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid user.",e.getMessage());

        //Verify
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);
    }

    @Test
    void GivenUserNullID_whenregisterUser_ThenIllegalArgumentException_InvaliduserId() {
        when(mockUserApiClient.getId()).thenReturn(null);
        // Act
        Exception e = new Exception();
        try{
            testLibrary.registerUser(mockUserApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        // Assertions
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid user Id.",e.getMessage());

        //Verify
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);
    }

    @Test
    void GivenUserIDLenNot12_whenregisterUser_ThenIllegalArgumentException_InvaliduserId() {
        when(mockUserApiClient.getId()).thenReturn("123456");
        // Act
        Exception e = new Exception();
        try{
            testLibrary.registerUser(mockUserApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        // Assertions
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid user Id.",e.getMessage());

        //Verify
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);
    }

    @Test
    void GivenUserNameNull_whenregisterUser_ThenIllegalArgumentException_Invalidusername() {
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockUserApiClient.getName()).thenReturn(null);
        // Act
        Exception e = new Exception();
        try{
            testLibrary.registerUser(mockUserApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        // Assertions
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid user name.",e.getMessage());

        //Verify
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);
    }

    @Test
    void GivenUserEmptyName_whenregisterUser_ThenIllegalArgumentException_Invalidusername() {
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockUserApiClient.getName()).thenReturn("");
        // Act
        Exception e = new Exception();
        try{
            testLibrary.registerUser(mockUserApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        // Assertions
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid user name.",e.getMessage());

        //Verify
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);
    }

    @Test
    void GivenUserwithNullNotificationService_whenregisterUser_ThenIllegalArgumentException_Invalidnotificationservice() {
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockUserApiClient.getName()).thenReturn("Noa");
        when(mockUserApiClient.getNotificationService()).thenReturn(null);
        // Act
        Exception e = new Exception();
        try{
            testLibrary.registerUser(mockUserApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        // Assertions
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid notification service.",e.getMessage());

        //Verify
        verify(mockDBApiServer,never()).getUserById(mockUserApiClient.getId());
        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);
    }

    @Test
    void GivenExsistingUser_whenregisterUser_ThenIllegalArgumentException_Useralreadyexists() {
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockUserApiClient.getName()).thenReturn("Noa");
        when(mockUserApiClient.getNotificationService()).thenReturn(mockNotificationApiClient);
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);
        // Act
        Exception e = new Exception();
        try{
            testLibrary.registerUser(mockUserApiClient);
        }
        catch (Exception e1){
            e = e1;
        }
        // Assertions
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("User already exists.",e.getMessage());

        //Verify
        verify(mockDBApiServer,times(1)).getUserById(mockUserApiClient.getId());
        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);

    }

    @Test
    void GivenNewUser_whenregisterUser_ThenAddUser() {
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockUserApiClient.getName()).thenReturn("Noa");
        when(mockUserApiClient.getNotificationService()).thenReturn(mockNotificationApiClient);
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(null);

        testLibrary.registerUser(mockUserApiClient);

        verify(mockDBApiServer,times(1)).registerUser(mockUserApiClient.getId(),mockUserApiClient);

    }

    //TODO: Noam
    //Borrow Book
    @Test
    void GivenNoBookISBN_WhenborrowBook_ThenBookNotFoundException_Booknotfound() {
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(null);
        when(mockUserApiClient.getId()).thenReturn("12345");
        // Act
        Exception e = new Exception();
        try{
            testLibrary.borrowBook(mockBookApiClient.getISBN(), mockUserApiClient.getId());
        }
        catch (Exception e1){
            e = e1;
        }
        // Assertions
        assertEquals(BookNotFoundException.class,e.getClass());
        assertEquals("Book not found!",e.getMessage());

    }

    @Test
    void GivenInvaliduserId_WhenborrowBook_ThenIllegalArgumentException_InvaliduserId() {
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("12345");
        // Act
        Exception e = new Exception();
        try{
            testLibrary.borrowBook(mockBookApiClient.getISBN(), mockUserApiClient.getId());
        }
        catch (Exception e1){
            e = e1;
        }
        // Assertions
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid user Id.",e.getMessage());

    }
    @Test
    void GivenNulluserId_WhenborrowBook_ThenIllegalArgumentException_InvaliduserId() {
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn(null);
        // Act
        Exception e = new Exception();
        try{
            testLibrary.borrowBook(mockBookApiClient.getISBN(), mockUserApiClient.getId());
        }
        catch (Exception e1){
            e = e1;
        }
        // Assertions
        assertEquals(IllegalArgumentException.class,e.getClass());
        assertEquals("Invalid user Id.",e.getMessage());

    }
    @Test
    void GivenNoRealuserId_WhenborrowBook_ThenUserNotRegisteredException_Usernotfound() {
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("111111111111");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(null);
        // Act
        Exception e = new Exception();
        try{
            testLibrary.borrowBook(mockBookApiClient.getISBN(), mockUserApiClient.getId());
        }
        catch (Exception e1){
            e = e1;
        }
        // Assertions
        assertEquals(UserNotRegisteredException.class,e.getClass());
        assertEquals("User not found!",e.getMessage());

    }

    @Test
    void GivenISBNuserId_WhenborrowBook_ThenborrowBook() {
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("111111111111");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);
        when(mockBookApiClient.isBorrowed()).thenReturn(false);

        testLibrary.borrowBook(mockBookApiClient.getISBN(), mockUserApiClient.getId());

        // verify
        verify(mockBookApiClient,times(1)).borrow();
        verify(mockDBApiServer,times(1)).borrowBook(mockBookApiClient.getISBN(), mockUserApiClient.getId());

    }

    @Test
    void GivenNoBorrowedBookISBN_WhenreturnBook_ThenBookNotBorrowedException_Bookwasntborrowed() {
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockBookApiClient.isBorrowed()).thenReturn(false);

        Exception e = new Exception();
        try{
            testLibrary.returnBook(mockBookApiClient.getISBN());
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(BookNotBorrowedException.class,e.getClass());
        assertEquals("Book wasn't borrowed!",e.getMessage());
    }
    @Test
    void GivenBorrowedBookISBN_WhenreturnBook_ThenreturnBook() {
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockBookApiClient.isBorrowed()).thenReturn(true);

        testLibrary.returnBook(mockBookApiClient.getISBN());

        // verify
        verify(mockBookApiClient,times(1)).returnBook();
        verify(mockDBApiServer,times(1)).returnBook(mockBookApiClient.getISBN());
    }

    //Notify User
    @Test
    void GivenBookWithNullReviews_WhennotifyUserWithBookReviews_ThenNoReviewsFoundException_Noreviewsfound() {
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);
        when(mockReviewApiServer.getReviewsForBook(mockBookApiClient.getISBN())).thenReturn(null);

        Exception e = new Exception();
        try{
            testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId());
        }
        catch (Exception e1){
            e = e1;
        }

        //Assertions
        assertEquals(NoReviewsFoundException.class,e.getClass());
        assertEquals("No reviews found!",e.getMessage());

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
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);
        when(mockReviewApiServer.getReviewsForBook(mockBookApiClient.getISBN())).thenReturn(new ArrayList<>());

        Exception e = new Exception();
        try{
            testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId());
        }
        catch (Exception e1){
            e = e1;
        }

        assertEquals(NoReviewsFoundException.class,e.getClass());
        assertEquals("No reviews found!",e.getMessage());

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
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);
        when(mockReviewApiServer.getReviewsForBook(mockBookApiClient.getISBN())).thenThrow(new ReviewServiceUnavailableException("Review service unavailable!"));

        Exception e = new Exception();
        try{
            testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId());
        }
        catch (Exception e1){
            e = e1;
        }

        assertEquals(ReviewServiceUnavailableException.class,e.getClass());
        assertEquals("Review service unavailable!",e.getMessage());

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

        List<String> reviews = new ArrayList<>();
        reviews.add("review 1");
        reviews.add("review 2");

        when(mockBookApiClient.getTitle()).thenReturn("Lord of the Rings");
        when(mockReviewApiServer.getReviewsForBook(mockBookApiClient.getISBN())).thenReturn(reviews);

        // Setting up the mock to throw an exception for the first call, and do nothing for the next four calls
        String notification = "Reviews for 'Lord of the Rings':\n" + "review 1\n" + "review 2";
        doThrow(new NotificationException("Notification failed!")).when(mockUserApiClient).sendNotification(notification);

        Exception e = new Exception();
        try{
            testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId());
        }
        catch (Exception e1){
            e = e1;
        }

        // Assertions
        assertEquals(NotificationException.class,e.getClass());
        assertEquals("Notification failed!",e.getMessage());

        //Verify
        verify(mockDBApiServer,times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,times(1)).getUserById(mockUserApiClient.getId());
        verify(mockReviewApiServer,times(1)).getReviewsForBook(mockBookApiClient.getISBN());
        verify(mockUserApiClient, times(5)).sendNotification(notification); // Verifying retry logic
        verify(mockReviewApiServer, times(1)).close();
    }

    @Test
    void GivenBookwithReviews_WhennotifyUserWithBookReviews_ThenNotifyUser() {
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);

        List<String> reviews = new ArrayList<>();
        reviews.add("review 1");
        reviews.add("review 2");

        when(mockBookApiClient.getTitle()).thenReturn("Lord of te Rings");
        when(mockReviewApiServer.getReviewsForBook(mockBookApiClient.getISBN())).thenReturn(reviews);

        String notification = "Reviews for 'Lord of the Rings':\n" + "review 1\n" + "review 2";

        testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId());

        //Verify
        verify(mockDBApiServer,times(1)).getBookByISBN(mockBookApiClient.getISBN());
        verify(mockDBApiServer,times(1)).getUserById(mockUserApiClient.getId());
        verify(mockReviewApiServer,times(1)).getReviewsForBook(mockBookApiClient.getISBN());
        verify(mockUserApiClient, never()).sendNotification(notification);
        verify(mockReviewApiServer, times(1)).close();

    }

}
