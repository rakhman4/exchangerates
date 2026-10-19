# Kurzovní lístek

Aplikace zobrazuje kurzy České spořitelny ze sandboxového API a ukládá je do PostgreSQL. Frontend obsahuje přehled kurzů a detail vybrané měny.

Použité technologie: Java 21, Spring Boot, Maven, PostgreSQL, HTML, CSS a JavaScript.

## Spuštění

Je potřeba JDK 21 a spuštěný Docker s Docker Compose. Příkazy spouštějte v kořeni projektu.

Nejprve spusťte databázi:

```powershell
docker compose up -d
```

Po jejím naběhnutí spusťte aplikaci:

```powershell
.\mvnw.cmd spring-boot:run
```

Otevřete [http://localhost:8080/](http://localhost:8080/).

Při načtení stránky frontend zavolá lokální backend, který stáhne kurzy z banky a uloží je do databáze. Kliknutím na kód měny otevřete detail.

Databáze běží na portu `5433`, její název je `exchangerates`. Uživatel i heslo jsou `postgres`.

## API

- [GET /api/rates?usedb=false](http://localhost:8080/api/rates?usedb=false) načte kurzy z banky, nahradí uložená data a vrátí výsledek.
- [GET /api/rates?usedb=true](http://localhost:8080/api/rates?usedb=true) vrátí uložené kurzy bez volání bankovního API.

Bez uložených dat vrací `usedb=true` prázdný seznam. Chybějící parametr nebo jiná hodnota vrací HTTP 400.

## Testy a sestavení

```powershell
.\mvnw.cmd clean verify
```

Dva jednotkové testy ověřují oba režimy služby a mapování dat. Používají Mockito, takže nepotřebují běžící databázi ani bankovní API.

Sestavený JAR najdete ve složce `target/`.

Na Linuxu a macOS použijte v příkazech `sh mvnw` místo `.\mvnw.cmd`.

## Ukončení

Aplikaci ukončíte pomocí `Ctrl+C`, databázi příkazem:

```powershell
docker compose stop
```

Data zůstanou uložená v Docker volume.