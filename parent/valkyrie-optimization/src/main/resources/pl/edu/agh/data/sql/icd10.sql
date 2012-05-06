------------------------------------------------------------------------------------------------------------------------
-- Elementy list rozpoznań ICD-10
------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------
-- import data from CSV
CREATE TEMPORARY TABLE ICD10_TMP(
    LIST_CODE_ID VARCHAR(5) NOT NULL, -- Kod listy - kod JGP
    LIST_TYPE_ID CHAR(1) NOT NULL,    -- Typ listy
    CODE VARCHAR(5) NOT NULL,         -- Kod rozpoznania ICD-10
    NAME VARCHAR(255) NOT NULL        -- Nazwa rozpoznania ICD-10
) AS SELECT LIST_CODE_ID, LIST_TYPE_ID, CODE, NAME FROM CSVREAD('icd10.csv');
------------------------------------------------------------------------------------------------------------------------
CREATE INDEX ICD10_TMP_CODE_IDX ON ICD10_TMP(CODE);
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
-- save data structure without duplicates etc.
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE ICD10(
    CODE VARCHAR(5) PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO ICD10 (CODE, NAME)
 SELECT DISTINCT CODE, NAME FROM ICD10_TMP ORDER BY CODE;
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE ICD10_JGP(
    ICD10_CODE VARCHAR(5) NOT NULL,
    JGP_CODE VARCHAR(5) NOT NULL,
    LIST_TYPE_ID CHAR(1) NOT NULL,
    FOREIGN KEY(ICD10_CODE) REFERENCES ICD10(CODE),
--  TODO  FOREIGN KEY(JGP_CODE) REFERENCES JGP(CODE),
    FOREIGN KEY(LIST_TYPE_ID) REFERENCES LIST_TYPE(ID)
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO ICD10_JGP (ICD10_CODE, JGP_CODE, LIST_TYPE_ID)
 SELECT CODE, LIST_CODE_ID, LIST_TYPE_ID FROM ICD10_TMP ORDER BY CODE;
------------------------------------------------------------------------------------------------------------------------
DROP TABLE ICD10_TMP;
------------------------------------------------------------------------------------------------------------------------