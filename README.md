# Assecor Backend Assessment

[![CI/CD Pipeline](https://github.com/NMuzaffar/assecor-assessment-backend/actions/workflows/maven.yml/badge.svg)](https://github.com/NMuzaffar/assecor-assessment-backend/actions)

Dieses Projekt ist ein RESTful-Webservice zur Verwaltung von Personendaten, der im Rahmen eines Assessments für Assecor erstellt wurde. Es implementiert alle Kernanforderungen sowie alle Bonus-Features, einschließlich einer dualen Datenquellenarchitektur (CSV und JPA) und einer vollständigen CI/CD-Pipeline.

## Merkmale

- **REST-API**: Volle CRUD-Funktionalität (Erstellen, Lesen, Löschen) zur Verwaltung von Personendaten.
- **Duale Datenquellen**: Die Anwendung unterstützt zwei Persistenzschichten, die über Spring-Profile ausgewählt werden können:
  - **Standard (CSV)**: Ein In-Memory-Repository, das beim Start aus einer `.csv`-Datei gefüllt wird.
  - **JPA-Profil**: Eine vollständige Persistenzschicht mit Spring Data JPA und einer H2-In-Memory-Datenbank.
- **Profilbasierte Konfiguration**: Saubere Trennung der Konfiguration für verschiedene Umgebungen/Datenquellen.
- **Umfassende Tests**: Hohe Testabdeckung mit Unit-, Integrations- und Repository-Tests.
- **CI/CD**: Automatisierte Build- und Test-Pipeline mit GitHub Actions.
- **Zentralisierte Ausnahmebehandlung**: Bietet konsistente und aussagekräftige Fehlerantworten für API-Clients.

## Verwendete Technologien

- Java 21
- Spring Boot 3
- Spring Data JPA
- Maven
- H2 In-Memory-Datenbank
- OpenCSV
- Lombok
- JUnit 5, Mockito, AssertJ

## Erste Schritte

### Voraussetzungen

- JDK 21 oder neuer
- Apache Maven 3.6.3 oder neuer

### Projekt kompilieren

Klonen Sie das Repository und kompilieren Sie das Projekt mit Maven:

```bash
mvn clean install
```

### Anwendung ausführen

Die Anwendung kann in einem von zwei Modi ausgeführt werden, der durch das aktive Spring-Profil bestimmt wird.

**1. Standardmodus (CSV-Datenquelle)**

Dies ist der Standardmodus. Er verwendet ein In-Memory-Repository, das aus `persons.csv` liest.

```bash
java -jar target/assessment-app.jar
```

**2. JPA-Modus (H2-Datenbank)**

Um die Anwendung mit der H2-In-Memory-Datenbank auszuführen, aktivieren Sie das `jpa`-Profil.

```bash
java "-Dspring.profiles.active=jpa" -jar target/assessment-app.jar
```

Wenn die Anwendung im `jpa`-Modus läuft, können Sie auf die H2-Konsole unter `http://localhost:8080/h2-console` mit den folgenden Anmeldeinformationen zugreifen:
- **JDBC-URL**: `jdbc:h2:mem:testdb`
- **Benutzername**: `admin`
- **Passwort**: `admin`

## API-Endpunkte

Die folgenden Endpunkte sind verfügbar:

| Methode  | Pfad                  | Beschreibung                                         |
|----------|-----------------------|------------------------------------------------------|
| `GET`    | `/persons`            | Ruft eine Liste aller Personen ab.                   |
| `GET`    | `/persons/{id}`       | Ruft eine einzelne Person anhand ihrer ID ab.        |
| `GET`    | `/persons/color/{color}` | Ruft alle Personen mit einer bestimmten Lieblingsfarbe ab. |
| `POST`   | `/persons`            | Erstellt eine neue Person.                           |
| `DELETE` | `/persons/{id}`       | Löscht eine Person anhand ihrer ID.                  |

## Tests & Testabdeckung

Das Projekt verfügt über eine umfassende Testsuite, die die Service-, Controller- und Repository-Ebenen abdeckt. Ein hohes Maß an Testabdeckung wird aufrechterhalten, wie unten gezeigt:

![Testabdeckung](misc/test_coverage.png)

Um die Tests auszuführen, verwenden Sie den folgenden Maven-Befehl:

```bash
mvn test
```

## Continuous Integration

Dieses Projekt verwendet GitHub Actions für seine CI/CD-Pipeline. Der Workflow, definiert in `.github/workflows/maven.yml`, kompiliert das Projekt automatisch und führt bei jedem Push und Pull Request auf den `main`-Branch die vollständige Testsuite aus.

Sie können den Status der Pipeline und die letzten Ausführungen hier einsehen:
[**https://github.com/NMuzaffar/assecor-assessment-backend/actions**](https://github.com/NMuzaffar/assecor-assessment-backend/actions)
