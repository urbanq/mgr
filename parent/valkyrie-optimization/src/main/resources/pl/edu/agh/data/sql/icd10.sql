------------------------------------------------------------------------------------------------------------------------
-- Elementy list rozpoznań ICD-10
------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------
-- import data from CSV
CREATE TEMPORARY TABLE ICD10_TMP(
    LIST_CODE_ID VARCHAR(5) NOT NULL, -- Kod listy
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
CREATE TABLE ICD10_LIST(
    CODE VARCHAR(5) PRIMARY KEY
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO ICD10_LIST (CODE)
 SELECT DISTINCT LIST_CODE_ID FROM ICD10_TMP ORDER BY LIST_CODE_ID;
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE ICD10_LIST_CODE(
    ICD10_CODE VARCHAR(5) NOT NULL,
    LIST_CODE VARCHAR(5) NOT NULL,
    LIST_TYPE CHAR(1) NOT NULL,
    PRIMARY KEY (ICD10_CODE, LIST_CODE),
    FOREIGN KEY(ICD10_CODE) REFERENCES ICD10(CODE),
    FOREIGN KEY(LIST_TYPE) REFERENCES LIST_TYPE(ID),
    FOREIGN KEY(LIST_CODE) REFERENCES ICD10_LIST(CODE)
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO ICD10_LIST_CODE (ICD10_CODE, LIST_CODE, LIST_TYPE)
 SELECT CODE, LIST_CODE_ID, LIST_TYPE_ID FROM ICD10_TMP ORDER BY CODE;
------------------------------------------------------------------------------------------------------------------------
DROP TABLE ICD10_TMP;
------------------------------------------------------------------------------------------------------------------------