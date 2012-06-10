------------------------------------------------------------------------------------------------------------------------
-- JGP - jednorodne grupy pacjentów
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
-- import data from CSV
CREATE TEMPORARY TABLE JGP_HOSPITAL_TMP(
    JGP_CODE VARCHAR(5) NOT NULL,           -- Kod grupy JGP
    JGP_PRODUCT_CODE VARCHAR(15) NOT NULL,  -- kod produktu (zgodny z CZS)
    JGP_NAME VARCHAR(255) NOT NULL,         -- Nazwa JGP
    DAYS INT,                               -- liczba dni pobytu finansowana grupą
    VALUE_UNDER INT,                        -- wartość punktowa hospitalizacji < 2 dni
    VALUE_OVER INT                          -- wartość punktowa osobodnia ponad ryczałt finansowany grupą
) AS SELECT * FROM CSVREAD('jgp_hospital.csv');

------------------------------------------------------------------------------------------------------------------------
-- save data structure without duplicates etc.
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE JGP_HOSPITAL(
    JGP_CODE VARCHAR(5) NOT NULL,
    DAYS INT,
    VALUE_UNDER INT,
    VALUE_OVER INT,
    FOREIGN KEY(JGP_CODE) REFERENCES JGP(CODE)
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO JGP_HOSPITAL
 SELECT JGP_CODE, DAYS, VALUE_UNDER, VALUE_OVER FROM JGP_HOSPITAL_TMP ORDER BY JGP_CODE;
------------------------------------------------------------------------------------------------------------------------
DROP TABLE JGP_HOSPITAL_TMP;
------------------------------------------------------------------------------------------------------------------------