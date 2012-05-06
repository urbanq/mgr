------------------------------------------------------------------------------------------------------------------------
-- Elementy list procedur ICD-9
------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------
-- import data from CSV
CREATE TEMPORARY TABLE ICD9_TMP(
    LIST_CODE_ID VARCHAR(5) NOT NULL, -- Kod listy - kod JGP
    LIST_TYPE_ID CHAR(1) NOT NULL,    -- Typ listy
    CODE VARCHAR(7) NOT NULL,         -- Kod procedury ICD-9
    RANGE INT,                        -- Ranga procedury ICD-9
    NAME VARCHAR(255) NOT NULL        -- Nazwa procedury ICD-9
) AS SELECT LIST_CODE_ID, LIST_TYPE_ID, CODE, RANGE, NAME FROM CSVREAD('icd9.csv');
------------------------------------------------------------------------------------------------------------------------
CREATE INDEX ICD9_TMP_CODE_IDX ON ICD9_TMP(CODE);
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
-- save data structure without duplicates etc.
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE LIST_TYPE(
    ID CHAR(1) PRIMARY KEY,
    NAME VARCHAR(10) NOT NULL
);
INSERT INTO LIST_TYPE (ID, NAME) VALUES ('G', 'globalna');
INSERT INTO LIST_TYPE (ID, NAME) VALUES ('U', 'do sekcji');
INSERT INTO LIST_TYPE (ID, NAME) VALUES ('H', 'do grupy');
INSERT INTO LIST_TYPE (ID, NAME) VALUES ('N', 'negatywna');
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE ICD9(
    CODE VARCHAR(7) PRIMARY KEY,
    RANGE INT,
    NAME VARCHAR(255) NOT NULL
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO ICD9 (CODE, RANGE, NAME)
 SELECT DISTINCT CODE, RANGE, NAME FROM ICD9_TMP ORDER BY CODE;
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE ICD9_JGP(
    ICD9_CODE VARCHAR(7) NOT NULL,
    JGP_CODE VARCHAR(5) NOT NULL,
    LIST_TYPE_ID CHAR(1) NOT NULL,
    FOREIGN KEY(ICD9_CODE) REFERENCES ICD9(CODE),
--  TODO  FOREIGN KEY(JGP_CODE) REFERENCES JGP(CODE),
    FOREIGN KEY(LIST_TYPE_ID) REFERENCES LIST_TYPE(ID)
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO ICD9_JGP (ICD9_CODE, JGP_CODE, LIST_TYPE_ID)
 SELECT CODE, LIST_CODE_ID, LIST_TYPE_ID FROM ICD9_TMP ORDER BY CODE;
------------------------------------------------------------------------------------------------------------------------
DROP TABLE ICD9_TMP;
------------------------------------------------------------------------------------------------------------------------