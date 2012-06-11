------------------------------------------------------------------------------------------------------------------------
-- SPECJALNOŚCI KOMÓREK ORGANIZACYJNYCH
-- oraz KODY ŚWIADCZENIA, dla których dane z pobytów wyłączone są przy wyznaczaniu JGP
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
-- import data from CSV
CREATE TEMPORARY TABLE SPECIALIZATION_UNIT_TMP(
    CODE CHAR(4),               -- Kod specjalności komórki organizacyjnej
    NAME VARCHAR(255) NOT NULL, -- Nazwa specjalności komórki organizacyjnej
    SERVICE_CODE FLOAT          -- Kod świadczenia
) AS SELECT CODE, NAME, SERVICE_CODE FROM CSVREAD('specialization_unit.csv');
------------------------------------------------------------------------------------------------------------------------
CREATE INDEX SPECIALIZATION_UNIT_TMP_CODE_IDX ON SPECIALIZATION_UNIT_TMP(CODE);
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
-- save data structure without duplicates etc.
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE SPECIALIZATION_UNIT(
    CODE CHAR(4) PRIMARY KEY ,  -- Kod specjalności komórki organizacyjnej
    NAME VARCHAR(255) NOT NULL, -- Nazwa specjalności komórki organizacyjnej
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO SPECIALIZATION_UNIT (CODE, NAME)
 SELECT DISTINCT CODE, NAME FROM SPECIALIZATION_UNIT_TMP ORDER BY CODE;
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE SPECIALIZATION_UNIT_EXCLUDE_SERVICE(
    SPECIALIZATION_UNIT_CODE CHAR(4),  -- Kod specjalności komórki organizacyjnej
    SERVICE_CODE FLOAT NOT NULL, -- Kod świadczenia
    FOREIGN KEY(SPECIALIZATION_UNIT_CODE) REFERENCES SPECIALIZATION_UNIT(CODE)
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO SPECIALIZATION_UNIT_EXCLUDE_SERVICE (SPECIALIZATION_UNIT_CODE, SERVICE_CODE)
 SELECT CODE, SERVICE_CODE FROM SPECIALIZATION_UNIT_TMP WHERE SERVICE_CODE IS NOT NULL ORDER BY CODE;
------------------------------------------------------------------------------------------------------------------------
DROP TABLE SPECIALIZATION_UNIT_TMP;
------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------------------
-- JAVA
-- public class SpecializationUnit {
--    String code;
--    String name;
--    List<Float> excludeServices;
-- }