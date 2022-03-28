
-- Oblig3 for Gruppe 39

-- --------------------------------------------------------

-- Oppretter nytt schema

DROP SCHEMA IF EXISTS oblig3 CASCADE;

CREATE SCHEMA oblig3;
SET search_path TO oblig3;


-- --------------------------------------------------------

-- Oppretter tabellene

CREATE TABLE avdeling
(
    avdelingid      SERIAL,
    avdelingnavn    VARCHAR(20) NOT NULL,
    avdelingsjef    INTEGER,
    CONSTRAINT avdelingPK PRIMARY KEY (avdelingid)
);

CREATE TABLE ansatt
(
    ansattid    SERIAL,
    brukernavn  VARCHAR(4) UNIQUE NOT NULL,
    fornavn     VARCHAR(30) NOT NULL,
    etternavn   VARCHAR(30) NOT NULL,
    ansattdato  DATE NOT NULL,
    stilling    VARCHAR(30) NOT NULL,
    maanedslonn INTEGER NOT NULL,
    avdeling    INTEGER NOT NULL,
    CONSTRAINT ansattPK PRIMARY KEY (ansattid),
    CONSTRAINT ansattAvdelingFK FOREIGN KEY (avdeling) REFERENCES avdeling (avdelingid)    
);

CREATE TABLE prosjekt
(
    prosjektid      SERIAL,
    navn            VARCHAR(5) UNIQUE NOT NULL,
    beskrivelse     VARCHAR(150), -- Ville brukt text i et realistisk scenario
    CONSTRAINT prosjektPK PRIMARY KEY (prosjektid)
);

CREATE TABLE prosjektdeltakelse
(
    prosjektdeltakelseid    SERIAL,
    prosjektid              INTEGER,
    ansattid                INTEGER,
    rolle                   VARCHAR(20) NOT NULL,
    arbeidstimer            INTEGER NOT NULL,
    CONSTRAINT prosjektdeltakelsePK PRIMARY KEY (prosjektdeltakelseid),
    CONSTRAINT deltakelseAnsattFK FOREIGN KEY (ansattid) REFERENCES ansatt (ansattid),
    CONSTRAINT deltakelseProsjektFK FOREIGN KEY (prosjektid) REFERENCES prosjekt (prosjektid)
);


-- --------------------------------------------------------

-- Legger inn verdier i tabellene

INSERT INTO avdeling 
    (avdelingnavn, avdelingsjef)
VALUES
    ('AvdelingVest', 2),
    ('AvdelingØst', 3),
    ('AvdelingNord', 1);
--


INSERT INTO ansatt
    (brukernavn, fornavn, etternavn, ansattdato, stilling, maanedslonn, avdeling)
VALUES
    ('dsf', 'Daniel', 'Fosse', '2020-01-01', 'Bedriftsleder', 75000, 3),
    ('lds', 'Lars', 'Dale', '2020-01-03', 'Avdelingsleder', 62000, 1),
    ('sda', 'Simen', 'Andal', '2020-01-14', 'Avdelingsleder', 63000, 2),
    ('dlss', 'David', 'Lone', '2020-03-23', 'Økonom', 42000, 3),
    ('asds', 'Amalie', 'Strand', '2020-05-11', 'ProsjektManager', 40000, 2),
    ('fsp', 'Fredrik', 'Paulsen', '2020-09-12', 'Utvilker', 48000, 1),
    ('sls', 'Sindre', 'Strand', '2020-09-20', 'Utvilker', 45000, 2),
    ('ospa', 'Oddvar', 'Arendal', '2020-11-30', 'Prosjektarbeider', 36000, 1),
    ('lfds', 'Lisa', 'Sandal', '2021-03-31', 'Prosjektarbeider', 39000, 2);
--

INSERT INTO prosjekt
    (navn, beskrivelse)
VALUES  
    ('F202', 'Opprette firma-nettsider'),
    ('RS32', 'Finans-styring'),
    ('S2163', 'Opplæring i skolen'),
    ('SD231', 'System for turnering'),
    ('AA12', 'Administrativt arbeid'),
    ('S111', 'Oppstartsprosjekt');
--

INSERT INTO prosjektdeltakelse
    (prosjektid, ansattid, rolle, arbeidstimer)
VALUES
    (1, 1, 'ProsjektLeder', 3),
    (1, 6, 'WEB-utvikler', 14),
    (1, 4, 'Budsjett-kontroll', 1),
    (2, 4, 'ProsjektLeder', 20),
    (2, 1, 'ProsjektOppfølger', 2),
    (3, 3, 'ProsjektLeder', 5), 
    (3, 9, 'Hjelper', 5),
    (3, 5, 'Hjelper', 2),
    (4, 2, 'Prosjektleder', 14),
    (4, 6, 'Utvikler', 10),
    (4, 7, 'Utvikler', 12),
    (4, 5, 'DesignAnsvarlig', 6),
    (5, 1, 'Leder', 4),
    (5, 5, 'Hjelper', 3),
    (5, 4, 'Hjelper', 3),
    (6, 3, 'Prosjektleder', 8),
    (6, 8, 'Arbeider', 12),
    (6, 7, 'Utvikler', 18);
--

-- --------------------------------------------------------

ALTER TABLE avdeling ADD CONSTRAINT avdelingAnsattFK FOREIGN KEY (avdelingsjef) REFERENCES ansatt (ansattid);
    