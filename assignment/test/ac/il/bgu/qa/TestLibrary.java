package ac.il.bgu.qa;

import ac.il.bgu.qa.errors.BookNotBorrowedException;
import ac.il.bgu.qa.errors.BookNotFoundException;
import ac.il.bgu.qa.errors.UserNotRegisteredException;
import ac.il.bgu.qa.services.DatabaseService;
import ac.il.bgu.qa.services.ReviewService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.mockito.*;

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

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        testLibrary = new Library(mockDBApiServer,mockReviewApiServer);
    }

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
    }

    @Test
    void GivenNullISBN_WhenaddBook_ThenIllegalArgumentException_InvalidISBN() {
        // Mock the behavior of the databaseService

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
    }

    @Test
    void GivenNot13LenISBN_WhenaddBook_ThenIllegalArgumentException_InvalidISBN() {
        // Mock the behavior of the databaseService

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
    }

    @Test
    void GivenInvalidISBN_WhenaddBook_ThenIllegalArgumentException_InvalidISBN() {
        // Mock the behavior of the databaseService

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
    }

    @Test
    void GivenNullTitle_WhenaddBook_ThenIllegalArgumentException_Invalidtitle() {
        // Mock the behavior of the databaseService

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
    }

    @Test
    void GivenEmptyTitle_WhenaddBook_ThenIllegalArgumentException_Invalidtitle() {
        // Mock the behavior of the databaseService

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
    }
    @Test
    void GivenNullAuthor_WhenaddBook_ThenIllegalArgumentException_Invalidauthor() {
        // Mock the behavior of the databaseService

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
    }

    @Test
    void GivenEmptyAuthor_WhenaddBook_ThenIllegalArgumentException_Invalidauthor() {
        // Mock the behavior of the databaseService

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
    }

    //TODO: add AuthorInvalid test

    @Test
    void GivenBorrowedBook_WhenaddBook_ThenIllegalArgumentException_Bookwithinvalidborrowedstate() {
        // Mock the behavior of the databaseService

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
    }
    @Test
    void GivenExistBook_WhenaddBook_ThenIllegalArgumentException_Bookalreadyexists() {
        // Mock the behavior of the databaseService

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
    }
    @Test
    void GivenNewBook_WhenaddBook_ThenBookAdded() {
        // Mock the behavior of the databaseService

        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockBookApiClient.getTitle()).thenReturn("New-Book");
        when(mockBookApiClient.getAuthor()).thenReturn("James Bond");
        when(mockBookApiClient.isBorrowed()).thenReturn(false);
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(null);

        // Verify
        verify(mockDBApiServer,never()).addBook(mockBookApiClient.getISBN(),mockBookApiClient);

    }


    //TODO: Rotem
    @Test
    void registerUser() {
    }

    //TODO: Noam
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
        // verify
        verify(mockBookApiClient,never()).borrow();
        verify(mockDBApiServer,never()).borrowBook(mockBookApiClient.getISBN(), mockUserApiClient.getId());

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
        // verify
        verify(mockBookApiClient,never()).returnBook();
        verify(mockDBApiServer,never()).returnBook(mockBookApiClient.getISBN());
    }

    //TODO: Rotem
    @Test
    void notifyUserWithBookReviews() {
    }

// Implement here
}
