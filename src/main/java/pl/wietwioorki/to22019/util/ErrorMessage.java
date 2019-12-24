package pl.wietwioorki.to22019.util;

public class ErrorMessage {
    //headers
    public static String loginErrorHeader = "Login error";
    public static String generalErrorHeader = "Error occurred when ";

    //content
    public static String wrongCredentialsErrorContent = "Wrong credentials given";
    public static String wrongDateErrorContent = "Wrong date format. Proper format is dd/mm/yyyy";
    public static String wrongPeselErrorContent = "Wrong pesel format";
    public static String wrongNamesErrorContent = "Wrong name format";

    public static String emptyFieldErrorContent = " should not be empty";

    public static String readerWithGivenPeselExistsErrorContent = "Reader with given pesel already exists";
    public static String readerWithGivenPeselDoesNotExistErrorContent = "Reader with given pesel does not exist";
    public static String bookWithGivenTitleDoesNotExistErrorContent = "Book with given title does not exist";

}