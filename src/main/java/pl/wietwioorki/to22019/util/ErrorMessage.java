package pl.wietwioorki.to22019.util;

public class ErrorMessage {
    //headers
    public static String loginErrorHeader = "Login error";
    public static String generalErrorHeader = "Error occurred when ";
    public static String emptySelectionErrorHeader = "Empty selection";

    //content
    public static String wrongCredentialsErrorContent = "Wrong credentials given";
    public static String wrongDateErrorContent = "Wrong date format. Proper format is dd/mm/yyyy";
    public static String wrongPeselErrorContent = "Wrong pesel format";
    public static String wrongNamesErrorContent = "Wrong name format";
    public static String wrongEmailErrorContent = "Wrong email format";
    public static String wrongIdErrorContent = "Wrong id format";
    public static String wrongPasswordsErrorContent = "Password and password confirmation do not match";

    public static String emptyFieldErrorContent = " should not be empty";

    public static String noBookSelectedErrorContent = "No book selected";
    public static String noReservationSelectedErrorContent = "No reservation selected";
    //Todo: add more no ... selected errors

    public static String readerWithGivenPeselExistsErrorContent = "Reader with given pesel already exists";
    public static String userWithGivenPeselExistsErrorContent = "User with given login already exists";
    public static String readerWithGivenPeselDoesNotExistErrorContent = "Reader with given pesel does not exist";
    public static String bookWithGivenTitleDoesNotExistErrorContent = "Book with given title does not exist";
    public static String reservationWithGivenIdDoesNotExistErrorContent = "Reservation with given id does not exist";

    public static String noMoreBooksAvailableErrorContent = "No more book items of this title are available";

    public static String cannotReturnBookErrorContent = "You cannot return this book. Either it has already been returned, or it hasn't been borrowed yet";
    public static String userNotLoggedInErrorContent = "You should log in to take this action";
}