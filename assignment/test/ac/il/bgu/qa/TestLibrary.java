package ac.il.bgu.qa;

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

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        testLibrary = new Library(mockDBApiServer,mockReviewApiServer);
    }

    //        } else if (book.getTitle() == null || book.getTitle().equals("")) {
    //            throw new IllegalArgumentException("Invalid title.");
    //        } else if (!isAuthorValid(book.getAuthor())) {
    //            throw new IllegalArgumentException("Invalid author.");
    //        } else if (book.isBorrowed()) {
    //            throw new IllegalArgumentException("Book with invalid borrowed state.");
    //        }
    //
    //        // If book already exists in the database, throw exception
    //        if (databaseService.getBookByISBN(book.getISBN()) != null)
    //            throw new IllegalArgumentException("Book already exists.");
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
        //verify(mockDBApiServer,times(1)).addBook(mockBookApiClient.getISBN(),mockBookApiClient);

    }


    @Test
    void registerUser() {
    }

    @Test
    void borrowBook() {
    }

    @Test
    void returnBook() {
    }

    @Test
    void notifyUserWithBookReviews() {
    }

    @Test
    void getBookByISBN() {
    }
// Implement here
}
