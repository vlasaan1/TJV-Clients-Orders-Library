# TJV semestrální úkol
FOTO 

Databáze obsahuje 3 entity - Customer, Photographer a Order.
Serverové API na všech entitách zpřístupňuje CRUD operace a dotaz, který umožňuje vyhledávat objednávky v zadaném cenovém rozmezí. 
Webový klient také umožňuje provádět všechny CRUD operace na všech entitách a komplexní operaci objednání objednávky. (kontrola existence a dostupnosti všech požadovaných fotografů, přidání k uživateli a zúčasněným fotografům)

Zdrojový kód k serverové části se nachází uvnitř složky foto, zdrojový kód klienta se nachází ve složce foto-client.

Ke spuštění aplikace je potřeba zkompilované soubory .jar stáhnout. Ty se nacházejí:
- /foto/build/libs/foto-1.0.jar
- /foto-client/build/libs/foto-client-0.0.1-SNAPSHOT.jar
Spuštění .jar souborů serveru a klienta je poté možné pomocí ("java -jar foto-1.0.jar", "java -jar foto-client-0.0.1-SNAPSHOT.jar").

Server se nachází na adrese - http://localhost:8080/
Webový klient se nachází na adrese - http://localhost:9080/

Dokumentace je po spuštění na adrese - http://localhost:8080/swagger-ui/index.html

Pokud dojde k úspěšnému spuštění (pomocí java -jar ...),znamená to, že v testech nedošlo k problému, jelikož jsou automaticky spuštěny během kompilace.
