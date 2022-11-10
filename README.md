# Co-Working-Space

## Erste Schritte

- Docker installieren
- Visual Studio Code muss die Erweiterung Remote Container installiert haben
- Projekt mit VSC öffnen
- Projekt in Dev Container öffnen
- Projekt mit folgendem Command starten: `Quarkus: Debug current Quarkus Project`
- Die Application ist unter http://localhost:8080 erreichbar
- Die API kann auf http://localhost8080/q/swagger-ui/ angesehen werden

## Tests und Testdaten

Beim Programmstart werden automatische Testdaten in die Datenbank geschrieben. Die Daten werden in der Klasse GenerateTestDataService.java generiert.

Die automatischen Tests können mit `./mvnw quarkus:test` ausgeführt werden. Für die automatischen Tests wird nicht die PostgreSQL-Datenbank verwendet, sondern eine H2-Datenbank, welche sich im Arbeitsspeicher während der Ausführung befindet.

## Datenbank

Die Daten werden in einer PostgreSQL-Datenbank gespeichert. In der Entwicklungsumgebung wird diese in der [docker-compose-yml](./.devcontainer/docker-compose.yml) konfiguriert.

### Datenbankkonfiguration

Über http://localhost:5050 ist PgAdmin4 erreichbar damit kann die Datenbank verwalten werden.
Der Benutzername lautet `zli@example.com` und das Passwort `zli*123`.
Ist man eingeloggt muss die Verbindung zur PostgreSQL-Datenbank mit folgenden Daten konfiguriert werden:
 - Host name/address: `db`
 - Port: `5432`
 - Maintenance database: `postgres`
 - Username: `postgres`
 - Password: `postgres`

## Planungsabweichungen

### Adminrechte vergeben
Adminrechte sollten über den Endpunkt `/members/rights/{memberId}` geändert werden können. Während der Implementierung habe ich bemerkt, dass es praktisch wäre, die neue Rolle auch direkt als Parameter zu erhalten. So können zum Beispiel Adminrechte auch einfach wieder genommen werden. Der neue Endpoint lautet deshalb `/members/rights/{memberId}/{role}`

### HTTP Response Codes
Der DELETE Request von /bookings gibt nun einen 204 No Content Response Code zurück anstelle eines 200 OK, da ich die Methode im Service ohne return type implementiert habe, was ich sinnvoller fand. 