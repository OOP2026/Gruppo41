🎓 Documentazione Finale di Progetto: Sistema Gestione Orari
Questo documento contiene la specifica tecnica di dettaglio di tutte le componenti implementate, i diagrammi di sequenza per i casi d'uso principali e il manuale utente per l'esecuzione del sistema.
1. Class Diagram di Dettaglio
Questo diagramma rappresenta la struttura tecnica reale di tutte le classi implementate nel progetto, suddivise per i relativi package di appartenenza secondo lo standard architetturale MVC + DAO.
classDiagram
    direction TB

    %% --- PACKAGE DATABASE CONNECTION ---
    class ConnessioneDatabase {
        -static ConnessioneDatabase instance
        -Connection connection
        -String url
        -String user
        -String password
        -ConnessioneDatabase()
        +static ConnessioneDatabase getInstance() Connection
        +getConnection() Connection
    }

    %% --- PACKAGE MODEL (ENTITIES) ---
    class Utente {
        -String nome
        -String cognome
        -String email
        -String login
        -String password
        +Utente(String, String, String, String, String)
        +getNome() String
        +setNome(String)
        +getCognome() String
        +setCognome(String)
        +getEmail() String
        +setEmail(String)
        +getLogin() String
        +setLogin(String)
        +getPassword() String
        +setPassword(String)
        +equals(Object) boolean
        +hashCode() int
    }

    class Studente {
        -String matricola
        +Studente(String, String, String, String, String, String)
        +getMatricola() String
        +setMatricola(String)
    }

    class Docente {
        -List~Insegnamento~ insegnamenti
        -List~Vincolo~ vincoli
        +Docente(String, String, String, String, String)
        +getInsegnamenti() List~Insegnamento~
        +setInsegnamenti(List~Insegnamento~)
        +getVincoli() List~Vincolo~
        +setVincoli(List~Vincolo~)
        +aggiungiVincolo(Vincolo) boolean
    }

    class ResponsabileOrario {
        +ResponsabileOrario(String, String, String, String, String)
    }

    class Coordinatore {
        +Coordinatore(String, String, String, String, String)
    }

    class Aula {
        -String nome
        +Aula(String)
        +getNome() String
        +setNome(String)
        +equals(Object) boolean
        +hashCode() int
    }

    class Insegnamento {
        -String nome
        -int cfu
        -String annoCorso
        -Docente docente
        +Insegnamento(String, int, String, Docente)
        +getNome() String
        +setNome(String)
        +getCfu() int
        +setCfu(int)
        +getAnnoCorso() String
        +setAnnoCorso(String)
        +getDocente() Docente
        +setDocente(Docente)
    }

    class Lezione {
        -Insegnamento insegnamento
        -String giornoSettimana
        -LocalTime oraInizio
        -LocalTime oraFine
        -Aula aula
        +Lezione(Insegnamento, String, LocalTime, LocalTime, Aula)
        +getInsegnamento() Insegnamento
        +setInsegnamento(Insegnamento)
        +getGiornoSettimana() String
        +setGiornoSettimana(String)
        +getOraInizio() LocalTime
        +setOraInizio(LocalTime)
        +getOraFine() LocalTime
        +setOraFine(LocalTime)
        +getAula() Aula
        +setAula(Aula)
        +equals(Object) boolean
        +hashCode() int
    }

    class Vincolo {
        -String giorno
        -LocalTime oraInizio
        -LocalTime oraFine
        +Vincolo(String, LocalTime, LocalTime)
        +getGiorno() String
        +setGiorno(String)
        +getOraInizio() LocalTime
        +setOraInizio(LocalTime)
        +getOraFine() LocalTime
        +setOraFine(LocalTime)
    }

    class SpostamentoLezione {
        -Lezione lezione
        -String nuovoGiorno
        -LocalTime nuovaOraInizio
        -LocalTime nuovaOraFine
        -String stato
        +SpostamentoLezione(Lezione, String, LocalTime, LocalTime)
        +getLezione() Lezione
        +setLezione(Lezione)
        +getNuovoGiorno() String
        +setNuovoGiorno(String)
        +getNuovaOraInizio() LocalTime
        +setNuovaOraInizio(LocalTime)
        +getNuovaOraFine() LocalTime
        +setNuovaOraFine(LocalTime)
        +getStato() String
        +setStato(String)
    }

    %% --- PACKAGE CONTROLLER ---
    class Controller {
        -List~Lezione~ lezioni
        -UtenteDAO utenteDao
        -LezioneDAO lezioneDao
        -Utente utenteLoggato
        +Controller()
        +avviaApplicazione() void
        +login(String, String) boolean
        +getUtenteLoggato() Utente
        +logout() void
        +getLezioni() List~Lezione~
        +aggiungiLezione(Lezione) boolean
        -verificaConflitti(Lezione) boolean
        -verificaVincoliDocente(Lezione) boolean
    }

    %% --- PACKAGE DAO & IMPLEMENTATION ---
    class UtenteDAO {
        <<interface>>
        +login(String, String) Utente
        +registraStudente(Studente) boolean
        +registraDocente(Docente) boolean
    }

    class LezioneDAO {
        <<interface>>
        +getTutteLeLezioni() List~Lezione~
        +getLezioniPerAnno(String) List~Lezione~
        +getLezioniPerDocente(String) List~Lezione~
        +inserisciLezione(Lezione) boolean
        +getAuleDisponibili() List~Aula~
        +getInsegnamentiAttivi() List~Insegnamento~
        +inserisciVincolo(String, Vincolo) boolean
        +richiediSpostamento(SpostamentoLezione) boolean
        +getRichiesteSpostamento() List~SpostamentoLezione~
        +aggiornaStatoSpostamento(SpostamentoLezione, String) boolean
    }

    class UtentePostgresDAO {
        -Connection connection
        +UtentePostgresDAO()
    }

    class LezionePostgresDAO {
        -Connection connection
        +LezionePostgresDAO()
    }

    %% --- PACKAGE GUI ---
    class LoginFrame {
        -Controller controller
        -JTextField txtLogin
        -JPasswordField txtPassword
        -JButton btnAccedi
        +LoginFrame(Controller)
    }

    class MainFrame {
        -Controller controller
        -JTable tblOrari
        +MainFrame(Controller)
    }

    class StudenteFrame {
        -Controller controller
        -JComboBox~String~ cmbAnno
        +StudenteFrame(Controller)
    }

    class DocenteFrame {
        -Controller controller
        -JButton btnInserisciVincolo
        -JButton btnRichiediSpostamento
        +DocenteFrame(Controller)
    }

    class ResponsabileOrarioFrame {
        -Controller controller
        -JButton btnAggiungiLezione
        +ResponsabileOrarioFrame(Controller)
    }

    class CoordinatoreFrame {
        -Controller controller
        -JButton btnApprova
        -JButton btnRifiuta
        +CoordinatoreFrame(Controller)
    }

    %% --- RELAZIONI DI EREDITARIETA' ---
    Utente <|-- Studente
    Utente <|-- Docente
    Docente <|-- ResponsabileOrario
    ResponsabileOrario <|-- Coordinatore

    %% --- REALIZZAZIONI DELLE INTERFACCE ---
    UtenteDAO <|.. UtentePostgresDAO
    LezioneDAO <|.. LezionePostgresDAO

    %% --- ASSOCIAZIONI E DIPENDENZE ---
    Insegnamento "1" *-- "1" Docente : assegnato_a
    Lezione "*" *-- "1" Insegnamento : relativa_a
    Lezione "*" *-- "1" Aula : programmata_in
    Docente "1" *-- "0..3" Vincolo : dichiara
    SpostamentoLezione "*" *-- "1" Lezione : richiede_per
    
    Controller --> UtenteDAO : utilizza
    Controller --> LezioneDAO : utilizza
    Controller --> Utente : gestisce_sessione
    UtentePostgresDAO --> ConnessioneDatabase : richiede_connessione
    LezionePostgresDAO --> ConnessioneDatabase : richiede_connessione

    LoginFrame --> Controller : invoca_servizi
    MainFrame --> Controller : invoca_servizi
    StudenteFrame --> Controller : invoca_servizi
    DocenteFrame --> Controller : invoca_servizi
    ResponsabileOrarioFrame --> Controller : invoca_servizi
    CoordinatoreFrame --> Controller : invoca_servizi


2. Diagrammi di Sequenza
2.1 Autenticazione Utente (Login)
Il diagramma seguente mostra il flusso di controllo e di dati tra i diversi package e livelli architetturali (GUI -> Controller -> DAO -> DB Postgres) durante l'accesso al sistema.
sequenceDiagram
    autonumber
    actor Utente
    participant GUI as LoginFrame (gui)
    participant CTRL as Controller (controller)
    participant DAO as UtentePostgresDAO (implementazioneDao)
    participant DB as PostgreSQL

    Utente->>GUI: Inserisce login e password, clicca su "Accedi"
    GUI->>CTRL: login(login, password)
    CTRL->>DAO: login(login, password)
    DAO->>DB: PreparedStatement (SELECT * FROM utente WHERE login=? AND password=?)
    DB-->>DAO: ResultSet (Dati dell'utente se presente)
    
    alt Utente Trovato (Studente o Docente)
        DAO-->>CTRL: Ritorna istanza Studente o Docente (polimorfismo)
        CTRL-->>GUI: true
        GUI->>CTRL: getUtenteLoggato()
        CTRL-->>GUI: UtenteLoggato
        GUI->>GUI: Nascondi LoginFrame e avvia la GUI specifica (es. StudenteFrame, DocenteFrame...)
    else Credenziali Errate / Utente non esistente
        DAO-->>CTRL: null
        CTRL-->>GUI: false
        GUI->>Utente: Mostra messaggio popup "Credenziali non valide"
    end


2.2 Inserimento Lezione e Controllo Conflitti
Questo diagramma rappresenta la logica di pianificazione oraria gestita dal Responsabile Orario, mostrando come il Controller valida l'orario in base a aule, docenti e vincoli d'indisponibilità.
sequenceDiagram
    autonumber
    actor Resp as Responsabile Orario
    participant GUI as ResponsabileOrarioFrame (gui)
    participant CTRL as Controller (controller)
    participant DAO as LezionePostgresDAO (implementazioneDao)
    participant DB as PostgreSQL

    Resp->>GUI: Seleziona Insegnamento, Aula, Giorno, Orario Inizio/Fine
    Resp->>GUI: Clicca su "Aggiungi Lezione"
    GUI->>CTRL: aggiungiLezione(nuovaLezione)
    
    rect rgb(240, 248, 255)
        note right of CTRL: Controllo Interno dei Conflitti (in-memory)
        CTRL->>CTRL: verificaConflitti(nuovaLezione)
        note over CTRL: Verifica se l'aula o il docente sono già impegnati<br/>nello stesso orario e giorno
        CTRL->>CTRL: verificaVincoliDocente(nuovaLezione)
        note over CTRL: Verifica se l'orario viola i vincoli di<br/>indisponibilità dichiarati dal docente
    end

    alt Nessun Conflitto Rilevato
        CTRL->>DAO: inserisciLezione(nuovaLezione)
        DAO->>DB: INSERT INTO lezione VALUES (...)
        DB-->>DAO: Record inserito correttamente (OK)
        DAO-->>CTRL: true
        CTRL-->>GUI: true
        GUI->>Resp: Mostra notifica "Lezione registrata con successo!"
    else Conflitto Rilevato (Aula, Docente o Vincolo Orario)
        CTRL-->>GUI: false
        GUI->>Resp: Mostra notifica di errore "Conflitto orario o vincolo violato!"
    end


3. Manuale Breve d'Uso dell'Applicazione
3.1 Prerequisiti di Esecuzione
Per avviare correttamente l'applicazione, assicurati di avere installato:
Java Development Kit (JDK) 17 o superiore.
PostgreSQL attivo e funzionante localmente sulla porta standard 5432.
Aver eseguito il file schema.sql (disponibile nella cartella principale del progetto) all'interno del tuo database per generare le tabelle e caricare gli utenti e i dati di test di partenza.
3.2 Avvio dell'Applicazione
Per avviare il software:
Apri il tuo IDE (IntelliJ IDEA o Eclipse) ed esegui la classe Main.java situata direttamente nella cartella src/main/java.
Verrà mostrata la schermata di Login.
3.3 Interfacce Utente e Funzionalità
A. Schermata di Login
Inserisci la tua login e la tua password (puoi utilizzare gli utenti di test inclusi nel file SQL, ad esempio mrossi per un docente, fverdi per uno studente).
Clicca su Accedi: l'applicazione verificherà il tuo tipo di utente (Studente, Docente, Responsabile Orario o Coordinatore) e aprirà l'interfaccia personalizzata per il tuo ruolo.
B. Interfaccia Studente (StudenteFrame)
Visualizzazione Orario: Mostra l'orario completo di tutte le lezioni programmate.
Filtro Anno di Corso: Utilizza la casella di selezione in alto per filtrare e mostrare solo le lezioni del primo anno, secondo anno o terzo anno.
C. Interfaccia Docente (DocenteFrame)
Le Mie Lezioni: Mostra l'elenco delle proprie lezioni settimanali.
Inserimento Vincoli (Massimo 3): Permette al docente di indicare fino a 3 fasce orarie in cui è impossibilitato a fare lezione (es. Lunedì dalle 09:00 alle 11:00). Il sistema impedirà la pianificazione di lezioni in tali fasce.
Richiesta Spostamento: Seleziona una lezione e inserisci il nuovo giorno ed orario proposti per inviare una richiesta di modifica al Coordinatore.
D. Interfaccia Responsabile Orario (ResponsabileOrarioFrame)
Pianificazione Lezioni: Compila il modulo inserendo insegnamento, aula, giorno e ora. Clicca su "Aggiungi Lezione".
Verifica Automatica Conflitti: Se l'aula è occupata, il docente ha già un'altra lezione, o se viene violato un vincolo del docente, il sistema bloccherà l'inserimento mostrando un messaggio descrittivo.
E. Interfaccia Coordinatore (CoordinatoreFrame)
Approvazione Richieste: Mostra in una tabella tutte le richieste di spostamento orario inviate dai docenti.
Accetta / Rifiuta: Cliccando sulle relative azioni, il coordinatore può approvare (aggiornando l'orario ufficiale) o rifiutare la proposta di spostamento della lezione.
