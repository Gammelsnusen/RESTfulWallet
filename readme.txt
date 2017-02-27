
*** Databasen ***
För att starta applikationen krävs det en MySQL-server som körs på localhost på port 3306
jdbc:mysql://localhost:3306/leotest?createDatabaseIfNotExist=true

Skapa MySQL-användaren genom att köra kommandon:
mysql> create user leo;
mysql> grant all on leotest.* to 'db_leo'@'localhost' identified by 'password';

Kontrollera att allt gått som det ska:
mysql> show grants for db_leo;

Eventuellt måste databasen skapas först:
create database leotest;


Vill man ha en annan user/annan databas måste följande filer uppdateras:
src/main/resources/hibernate.cfg.xml
src/main/webapp/META-INF/persistence.xml


Hibernate är inställt på att Skapa databasen första gången applikationen körs
Hibernate skapar och droppar även tabellerna när applikationen startas/stoppas
Inget data kommer med andra ord persisteras längre än man har applikationen deployad

*****************

*** REST API ***
6st REST metoder finns exponerade:

Registrerar en ny användare med användarnamnet {username}
/account/register/{username}

Hämtar {username}'s nuvarande summa
/account/balance/{username}

Drar {amount} från {username} och registrerar transaktionen med {transid}
/transaction/debit/{username}/{transid}/{amount}

Sätter in {amount} till {username} och registrerar transaktionen med {transid}
/transaction/debit/{username}/{transid}/{amount}

Returnerar en lista över alla registrerade users
/admin/users

Returnerar en lista över alla registrerade transaktioner
/admin/transactions


Samtliga returvärden sker med MIME-typen 'text/plain'
Skulle gärna gjort det med JSON men jag ville inte gambla med tiden, så jag valde det säkrare alternativet

***************

*** Deploy ***
Enklaste sättet att deploya är att dra ner applikationen från GIT och låta Maven göra jobbet.

Från roten i applikationen:
mvn clean install jetty:run -Djetty.port=8888

Så bör man nå första resttjänsten via:
localhost:8888/account/register/mittusername

Annars går det bra att ta .war-filen under /target/RESTfulExample.war och deploya hur man nu vill.

***************

*** Övriga kommentarer ***
Det finns en hel del jag skulle vilja förbättra med koden men som inte hanns med.
Fick testa en del nya tekniker och försöka få dessa att lira tillsammans som drog ut på tiden.
Maven, Jersy, Jetty

Felhanteringen är bristfällig, fick skjutas åt sidan till största delen.

Hade velat använda korrekta HTTP-metoder för mina REST-tjänster, men det strulade en del när jag
ville göra t.ex. Skapa Konto till en @POST. @GET fungerade rakt av så den fick genomsyra samtliga av tjänsterna.

REST API:t blev lite feltänkt också, nu i efterhand känns det som att QueryParametrar hade varit ett snyggare val än Path-parametrar.

Jag hade också velat returnera JSON istället för vanlig text.
Hade nog varit riktigt snyggt för t.ex. /admin/transactions

Entitetsobjekten läcker ut lite överallt i koden, hade hellre skapat några vanliga värdeobjekt/POJOs som skickades runt
och låtit Entiteterna endast användas vid kommunikation mot databasen.

I metoderna AccountManager.doesUserExist() och TransactionManager.doesTransactionExist() stötte jag på ett Concurrency-problem.
Innan t.ex. ett konto skapas kontrollerar koden om användarnamnet är upptaget.
I någon bråkdels sekund kommer man potentiellt sett kunna tro att det är ledigt samtidigt som någon annan skapar upp ett konto med användarnamnet.
Förmodligen kan man komma undan med att göra någon sorts låsning här på bekostnad av prestandan, SkapaKonto är ju inte en tjänst man bör ropa på
allt för många gånger.

***************
