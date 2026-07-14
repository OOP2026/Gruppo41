CREATE TABLE utente (
    login VARCHAR(50) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    matricola VARCHAR(20) UNIQUE,
    tipo VARCHAR(30) NOT NULL CHECK (tipo IN ('STUDENTE', 'DOCENTE', 'RESPONSABILE', 'COORDINATORE'))
);

-- Tabella Aula
CREATE TABLE aula (
    nome VARCHAR(50) PRIMARY KEY
);

-- Tabella Insegnamento
CREATE TABLE insegnamento (
    nome VARCHAR(150) PRIMARY KEY,
    cfu INT NOT NULL,
    anno_corso VARCHAR(10) NOT NULL CHECK (anno_corso IN ('I', 'II', 'III')),
    docente_login VARCHAR(50) NOT NULL,
    FOREIGN KEY (docente_login) REFERENCES utente(login) ON DELETE CASCADE
);

-- Tabella Lezione
CREATE TABLE lezione (
    id SERIAL PRIMARY KEY,
    insegnamento_id VARCHAR(150) NOT NULL,
    giorno VARCHAR(20) NOT NULL,
    ora_inizio TIME NOT NULL,
    ora_fine TIME NOT NULL,
    aula_nome VARCHAR(50) NOT NULL,
    FOREIGN KEY (insegnamento_id) REFERENCES insegnamento(nome) ON DELETE CASCADE,
    FOREIGN KEY (aula_nome) REFERENCES aula(nome) ON DELETE CASCADE
);

-- Tabella Vincolo (Indisponibilità Docente)
CREATE TABLE vincolo (
    id SERIAL PRIMARY KEY,
    docente_login VARCHAR(50) NOT NULL,
    giorno VARCHAR(20) NOT NULL,
    ora_inizio TIME NOT NULL,
    ora_fine TIME NOT NULL,
    FOREIGN KEY (docente_login) REFERENCES utente(login) ON DELETE CASCADE
);

-- Tabella Spostamento Lezione
CREATE TABLE spostamento (
    id SERIAL PRIMARY KEY,
    insegnamento_id VARCHAR(150) NOT NULL,
    giorno_corrente VARCHAR(20) NOT NULL,
    nuovo_giorno VARCHAR(20) NOT NULL,
    nuova_ora_inizio TIME NOT NULL,
    nuova_ora_fine TIME NOT NULL,
    stato VARCHAR(20) NOT NULL DEFAULT 'IN ATTESA' CHECK (stato IN ('IN ATTESA', 'APPROVATA', 'RIFIUTATA')),
    FOREIGN KEY (insegnamento_id) REFERENCES insegnamento(nome) ON DELETE CASCADE
);

-- ============================================================================
-- 2. INSERIMENTO DATI DI TEST (DML)
-- ============================================================================

-- Inserimento Utenti di Prova
INSERT INTO utente (login, nome, cognome, email, password, matricola, tipo) VALUES
('stud1', 'Alessandro', 'Verdi', 'ale.verdi@studenti.it', 'pass123', 'M0100234', 'STUDENTE'),
('prof1', 'Mario', 'Rossi', 'mario.rossi@universita.it', 'docente123', NULL, 'DOCENTE'),
('coord1', 'Chiara', 'Bianchi', 'chiara.bianchi@universita.it', 'coord123', NULL, 'COORDINATORE');

-- Inserimento Aule
INSERT INTO aula (nome) VALUES
('Aula A1'),
('Aula B2'),
('Laboratorio Informatica');

-- Inserimento Insegnamenti
INSERT INTO insegnamento (nome, cfu, anno_corso, docente_login) VALUES
('Programmazione Oggetti', 6, 'II', 'prof1'),
('Database', 6, 'II', 'coord1');

-- Inserimento Lezioni Iniziali
INSERT INTO lezione (insegnamento_id, giorno, ora_inizio, ora_fine, aula_nome) VALUES
('Programmazione Oggetti', 'Lunedi', '09:00:00', '11:00:00', 'Aula A1'),
('Database', 'Mercoledi', '14:00:00', '16:00:00', 'Laboratorio Informatica');
