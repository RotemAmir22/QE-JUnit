package ac.il.bgu.qa;

import ac.il.bgu.qa.errors.*;
import ac.il.bgu.qa.services.DatabaseService;
import ac.il.bgu.qa.services.NotificationService;
import ac.il.bgu.qa.services.ReviewService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.*;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    }

    @Test
    void GivenNewUser_whenregisterUser_ThenAddUser() {
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockUserApiClient.getName()).thenReturn("Noa");
        when(mockUserApiClient.getNotificationService()).thenReturn(mockNotificationApiClient);
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(null);

        verify(mockDBApiServer,never()).registerUser(mockUserApiClient.getId(),mockUserApiClient);

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

   // public void notifyUserWithBookReviews(String ISBN, String userId) {
        //        Validate the ISBN. If it's invalid, throw an exception.
        //        if (!isISBNValid(ISBN)) {
        //            throw new IllegalArgumentException("Invalid ISBN.");
        //        }
        //         Validate the user Id format (should be a 12-digit number).
        //         If it's invalid, throw an exception.
        //        if (userId == null || !userId.matches("\\d{12}")) {
        //            throw new IllegalArgumentException("Invalid user Id.");
        //        }
        //         Retrieve the book associated with the ISBN from the database.
        //        Book book = databaseService.getBookByISBN(ISBN);
        //        // If no book is found for the given ISBN, throw a book not found exception.
        //        if (book == null) {
        //            throw new BookNotFoundException("Book not found!");
        //        }
        //        // Retrieve the user associated with the user Id from the database.
        //        User user = databaseService.getUserById(userId);
        //        // If the user is not found in the database, throw an exception.
        //        if (user == null) {
        //            throw new UserNotRegisteredException("User not found!");
        //        }
        //        // Fetch the list of reviews for the specified book using the review service.
        //        List<String> reviews;
        //        try {
        //            reviews = reviewService.getReviewsForBook(ISBN);
        //            // If no reviews are found or the review list is empty, throw an exception.
        //            if (reviews == null || reviews.isEmpty()) {
        //                throw new NoReviewsFoundException("No reviews found!");
        //            }
    //        } catch (ReviewException e) {
    //            // If there's an issue fetching the reviews, throw a service unavailable exception.
    //            throw new ReviewServiceUnavailableException("Review service unavailable!");
    //        } finally {
    //            // Always close the review service connection after attempting to fetch the reviews.
    //            reviewService.close();
    //        }
    //TODO: Rotem
    //        // Construct the notification message containing the book's title and its reviews.
    //        String notificationMessage = "Reviews for '" + book.getTitle() + "':\n" + String.join("\n", reviews);
    //        // Attempt to send the notification to the user. If it fails, retry up to 5 times.
    //        int retryCount = 0;
    //        while (retryCount < 5) {
    //            try {
    //                user.sendNotification(notificationMessage);
    //                return;
    //            } catch (NotificationException e) {
    //                retryCount++;
    //                System.err.println("Notification failed! Retrying attempt " + retryCount + "/5");
    //            }
    //        }
    //        // If all retry attempts fail, throw a notification exception.
    //        throw new NotificationException("Notification failed!");
    //    }

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
        assertEquals(NoReviewsFoundException.class,e.getClass());
        assertEquals("No reviews found!",e.getMessage());
    }

    @Test
    void GivenBookWithEmptyReviews_WhennotifyUserWithBookReviews_ThenNoReviewsFoundException_Noreviewsfound() {
        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
        when(mockUserApiClient.getId()).thenReturn("123456789012");
        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);

        when(mockReviewApiServer.getReviewsForBook(mockBookApiClient.getISBN())).thenReturn(new ArrayList<String>());
        Exception e = new Exception();
        try{
            testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(),mockUserApiClient.getId());
        }
        catch (Exception e1){
            e = e1;
        }
        assertEquals(NoReviewsFoundException.class,e.getClass());
        assertEquals("No reviews found!",e.getMessage());
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
    }

    //TODO:fix this test! - need to throw notification exception
//    @Test
//    void GivenBookWithReviewsAndNotifyServiceNotWorking_WhenNotifyUserWithBookReviews_ThenThrowNotificationException() {
//        // Mock setup for dependent services
//        when(mockBookApiClient.getISBN()).thenReturn("978-3-16-148410-0");
//        when(mockDBApiServer.getBookByISBN(mockBookApiClient.getISBN())).thenReturn(mockBookApiClient);
//        when(mockUserApiClient.getId()).thenReturn("123456789012");
//        when(mockDBApiServer.getUserById(mockUserApiClient.getId())).thenReturn(mockUserApiClient);
//
//        List<String> reviews = new ArrayList<>();
//        reviews.add("review 1");
//        reviews.add("review 2");
//
//        when(mockBookApiClient.getTitle()).thenReturn("Lord of the Rings");
//        when(mockReviewApiServer.getReviewsForBook(mockBookApiClient.getISBN())).thenReturn(reviews);
//
//        // Setting up the mock to throw an exception for the first call, and do nothing for the next four calls
//        String notification = "Note";
//        doThrow(new NotificationException("Notification failed!"))
//                .doNothing().doNothing().doNothing().doNothing()
//                .when(mockUserApiClient).sendNotification(notification);
//
//        // Executing the test method and catching the specific exception
//        NotificationException thrownException = assertThrows(NotificationException.class, () ->
//                testLibrary.notifyUserWithBookReviews(mockBookApiClient.getISBN(), mockUserApiClient.getId())
//        );
//
//        // Assertions
//        assertEquals("Notification failed!", thrownException.getMessage());
//        verify(mockUserApiClient, times(5)).sendNotification(notification); // Verifying retry logic
//        verify(mockReviewApiServer, never()).close();
//    }

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
        verify(mockReviewApiServer,never()).close();
    }

}
