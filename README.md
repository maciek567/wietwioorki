# Library Project #
``` created by Maciej Banaś, Monika Dziedzic, Hubert Guzowski, Sebastian Wilk. ```

**A library system.**
After registration you can view recomendations, search for books by title, author, genre etc. and reserve them.
As a librarian you can add new books, borrow them and view statistics.
Moreover readers get customized e-mail notifications regarding theirs reservations.

Requirements:
* java 11/12
* lombok plugin (przy uruchamianiu w Intellij)
* PostgreSQL - Local instance of PostgreSQL database
    * [Windows](https://www.microfocus.com/documentation/idol/IDOL_12_0/MediaServer/Guides/html/English/Content/Getting_Started/Configure/_TRN_Set_up_PostgreSQL.htm) 
    * [Fedora](https://computingforgeeks.com/how-to-install-postgresql-on-fedora)

Launching:
* After downloading import project in Intellij Idea.
* In case of error choose Java version 11 or 12
* Run commend ./gradlew from projects's root
* Install or update Lombok and enable annotation processing option

### Add new books ###
![new_book](screens/new_book.png)

### Filter and reserve books that interest you ###
![filter_books](screens/filtering_books.png)

### As librarian filter reservations and lend books to customers ###
![reservations](screens/reservations.png)

### Choose when you want to get e-mail notification ###
![email_notification](screens/email_notifications.png)

``` And much more! ```
