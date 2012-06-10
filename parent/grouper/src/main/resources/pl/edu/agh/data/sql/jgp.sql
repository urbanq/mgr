------------------------------------------------------------------------------------------------------------------------
-- zakresy JGP - jednorodne grupy pacjentów
------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------
-- import data from CSV
CREATE TEMPORARY TABLE JGP_TMP(
    PRODUCT_CODE VARCHAR(15) NOT NULL,  -- kod produktu (zgodny z CZS)
    CODE VARCHAR(5) NOT NULL,           -- Kod JGP
    NAME VARCHAR(255) NOT NULL,         -- Nazwa JGP
    VALUE_HOSP INT,                     -- wartość punktowa - hospitalizacja
    VALUE_PLANNED_HOSP INT,             -- wartość punktowa - hospitalizacja planowa
    VALUE_ONE_DAY INT                   -- wartość punktowa - leczenie jednego dnia
) AS SELECT PRODUCT_CODE, CODE, NAME, VALUE_HOSP, VALUE_PLANNED_HOSP, VALUE_ONE_DAY FROM CSVREAD('jgp.csv');
------------------------------------------------------------------------------------------------------------------------
CREATE INDEX JGP_TMP_CODE_IDX ON JGP_TMP(CODE);
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
-- save data structure without duplicates etc.
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE JGP(
    PRODUCT_CODE VARCHAR(15) UNIQUE,
    CODE VARCHAR(5) PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO JGP (PRODUCT_CODE, CODE, NAME)
 SELECT PRODUCT_CODE, CODE, NAME FROM JGP_TMP ORDER BY CODE;
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE JGP_POINT_VALUE(
    JGP_CODE VARCHAR(5) UNIQUE,
    VALUE_HOSP INT,
    VALUE_PLANNED_HOSP INT,
    VALUE_ONE_DAY INT,
    FOREIGN KEY(JGP_CODE) REFERENCES JGP(CODE)
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO JGP_POINT_VALUE (JGP_CODE, VALUE_HOSP, VALUE_PLANNED_HOSP, VALUE_ONE_DAY)
 SELECT CODE, VALUE_HOSP, VALUE_PLANNED_HOSP, VALUE_ONE_DAY FROM JGP_TMP ORDER BY CODE;
------------------------------------------------------------------------------------------------------------------------
DROP TABLE JGP_TMP;
------------------------------------------------------------------------------------------------------------------------