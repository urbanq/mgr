------------------------------------------------------------------------------------------------------------------------
-- PARAMETRY JGP - jednorodne grupy pacjentów
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
-- import data from CSV
CREATE TEMPORARY TABLE JGP_PARAMETER_TMP(
    LIST_TYPE VARCHAR(5) NOT NULL,  -- Typ listy (ICD-9, ICD-10)
    LIST_CODE VARCHAR(5) NOT NULL,  -- Kod listy (kod listy procedury ICD-9 lub rozpoznań ICD-10 adekwatny do wybranego typu)
    JGP_CODE VARCHAR(5) NOT NULL,   -- Kod grupy JGP
    ALGORITHM_CODE CHAR(1) NOT NULL,-- Kod algorytmu
    HOSPITAL_LIMIT INT,             -- Ograniczenie długości pobytu (hospitalizacji)
    AGE_LIMIT INT,                  -- Ograniczenie wiekowe
    COND1_ICD9_LIST_CODE VARCHAR(5),-- Pierwszy warunek na procedury dodatkowe - Kod listy procedur
    COND1_ICD9_COUNT INT,           -- Minimalna liczba wystąpień procedur z listy o różnych datach realizacji
    COND2_ICD9_LIST_CODE VARCHAR(5),-- Drugi warunek na procedury dodatkowe  - Kod listy procedur
    COND2_ICD9_COUNT INT,           -- Minimalna liczba wystąpień procedur z listy o różnych datach realizacji
    COND_MAIN_ICD10_LIST_CODE VARCHAR(5),-- Warunek na rozpoznanie zasadnicze
    COND1_ICD10_LIST_CODE VARCHAR(5),-- Pierwszy warunek na rozpoznania współistniejące
    COND2_ICD10_LIST_CODE VARCHAR(5),-- Drugi  warunek na rozpoznania współistniejące
    LIMIT_SEX CHAR(1),              -- Ograniczenie ze względu na płeć pacjenta (Płeć K - kobieta M - mężczyzna)
    LIMIT_ACCEPT INT,               -- Kod ograniczenia dla trybu przyjęcia
    LIMIT_EXTRACT INT,              -- Kod ograniczenia dla trybu wypisu
    NEGATIVE_ICD9_LIST_CODE VARCHAR(5),-- Lista negatywna procedur - Kod listy procedur
    NEGATIVE_ICD10_LIST_CODE VARCHAR(5)-- Lista negatywna rozpoznań zasadniczych - Kod listy rozpoznań
) AS SELECT LIST_TYPE,LIST_CODE,JGP_CODE,ALGORITHM_CODE,HOSPITAL_LIMIT,AGE_LIMIT,COND1_ICD9_LIST_CODE,COND1_ICD9_COUNT,COND2_ICD9_LIST_CODE,COND2_ICD9_COUNT,COND_MAIN_ICD10_LIST_CODE,COND1_ICD10_LIST_CODE,COND2_ICD10_LIST_CODE,LIMIT_SEX,LIMIT_ACCEPT,LIMIT_EXTRACT,NEGATIVE_ICD9_LIST_CODE,NEGATIVE_ICD10_LIST_CODE
  FROM CSVREAD('jgp_parameter.csv');

CREATE INDEX JGP_PARAMETER_TMP_LIST_TYPE_IDX ON JGP_PARAMETER_TMP(LIST_TYPE);


------------------------------------------------------------------------------------------------------------------------
-- save data structure without duplicates etc.
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE JGP_PARAMETER(
    LIST_TYPE VARCHAR(5) NOT NULL,
    LIST_CODE VARCHAR(5) NOT NULL,
    JGP_CODE VARCHAR(5) NOT NULL,
    ALGORITHM_CODE CHAR(1) NOT NULL,
    HOSPITAL_LIMIT INT,
    AGE_LIMIT INT,
    COND1_ICD9_LIST_CODE VARCHAR(5),
    COND1_ICD9_COUNT INT,
    COND2_ICD9_LIST_CODE VARCHAR(5),
    COND2_ICD9_COUNT INT,
    COND_MAIN_ICD10_LIST_CODE VARCHAR(5),
    COND1_ICD10_LIST_CODE VARCHAR(5),
    COND2_ICD10_LIST_CODE VARCHAR(5),
    LIMIT_SEX CHAR(1),
    LIMIT_ACCEPT INT,
    LIMIT_EXTRACT INT,
    NEGATIVE_ICD9_LIST_CODE VARCHAR(5),
    NEGATIVE_ICD10_LIST_CODE VARCHAR(5),
    FOREIGN KEY(JGP_CODE) REFERENCES JGP(CODE),
    FOREIGN KEY(HOSPITAL_LIMIT) REFERENCES HOSPITAL_LIMIT(ID),
    FOREIGN KEY(AGE_LIMIT) REFERENCES AGE_LIMIT(ID),
    FOREIGN KEY(COND1_ICD9_LIST_CODE) REFERENCES ICD9_LIST(CODE),
    FOREIGN KEY(COND2_ICD9_LIST_CODE) REFERENCES ICD9_LIST(CODE),
    FOREIGN KEY(COND_MAIN_ICD10_LIST_CODE) REFERENCES ICD10_LIST(CODE),
    FOREIGN KEY(COND1_ICD10_LIST_CODE) REFERENCES ICD10_LIST(CODE),
    FOREIGN KEY(COND2_ICD10_LIST_CODE) REFERENCES ICD10_LIST(CODE),
    FOREIGN KEY(NEGATIVE_ICD9_LIST_CODE) REFERENCES ICD9_LIST(CODE),
    FOREIGN KEY(NEGATIVE_ICD10_LIST_CODE) REFERENCES ICD10_LIST(CODE)
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO JGP_PARAMETER
 SELECT * FROM JGP_PARAMETER_TMP ORDER BY LIST_TYPE, LIST_CODE, JGP_CODE, ALGORITHM_CODE;
------------------------------------------------------------------------------------------------------------------------
CREATE INDEX JGP_PARAMETER_LIST_TYPE_IDX ON JGP_PARAMETER(LIST_TYPE);
------------------------------------------------------------------------------------------------------------------------
DROP TABLE JGP_PARAMETER_TMP;
------------------------------------------------------------------------------------------------------------------------