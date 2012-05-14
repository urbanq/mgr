------------------------------------------------------------------------------------------------------------------------
-- Elementy list procedur ICD-9
------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------
-- import data from CSV
CREATE TEMPORARY TABLE ICD9_TMP(
    LIST_CODE_ID VARCHAR(5) NOT NULL, -- Kod listy
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
CREATE TABLE ICD9(
    CODE VARCHAR(7) PRIMARY KEY,
    RANGE INT,
    NAME VARCHAR(255) NOT NULL
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO ICD9 (CODE, RANGE, NAME)
 SELECT DISTINCT CODE, RANGE, NAME FROM ICD9_TMP ORDER BY CODE;
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE ICD9_LIST(
    CODE VARCHAR(5) PRIMARY KEY
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO ICD9_LIST (CODE)
 SELECT DISTINCT LIST_CODE_ID FROM ICD9_TMP ORDER BY LIST_CODE_ID;
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE ICD9_LIST_CODE(
    ICD9_CODE VARCHAR(7) NOT NULL,
    LIST_CODE VARCHAR(5) NOT NULL,
    LIST_TYPE CHAR(1) NOT NULL,
    PRIMARY KEY (ICD9_CODE, LIST_CODE),
    FOREIGN KEY(ICD9_CODE) REFERENCES ICD9(CODE),
    FOREIGN KEY(LIST_TYPE) REFERENCES LIST_TYPE(ID),
    FOREIGN KEY(LIST_CODE) REFERENCES ICD9_LIST(CODE)
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO ICD9_LIST_CODE (ICD9_CODE, LIST_CODE, LIST_TYPE)
 SELECT CODE, LIST_CODE_ID, LIST_TYPE_ID FROM ICD9_TMP ORDER BY CODE;
------------------------------------------------------------------------------------------------------------------------
DROP TABLE ICD9_TMP;
------------------------------------------------------------------------------------------------------------------------