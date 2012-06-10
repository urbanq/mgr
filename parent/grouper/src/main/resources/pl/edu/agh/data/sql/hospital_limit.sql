------------------------------------------------------------------------------------------------------------------------
-- Ograniczenia na długość pobytu (hospitalizacji)
-- Jednostka DZIEŃ
------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------
CREATE TABLE HOSPITAL_LIMIT(
    ID INT PRIMARY KEY, -- Kod ograniczenia na pobyt
    UNDER INT,          -- Poniżej (Górna granica <)
    OVER INT,           -- Powyżej Dolna granica >)
    TIME_UNIT CHAR(1) NOT NULL,  -- Jednostka
    FOREIGN KEY(TIME_UNIT) REFERENCES TIME_UNIT(ID)
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO HOSPITAL_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (1, 2, null, 'D');
INSERT INTO HOSPITAL_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (2, null, 1, 'D');
INSERT INTO HOSPITAL_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (3, null, 3, 'D');
INSERT INTO HOSPITAL_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (4, 4, null, 'D');
INSERT INTO HOSPITAL_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (5, null, 7, 'D');
INSERT INTO HOSPITAL_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (6, null, 10, 'D');
INSERT INTO HOSPITAL_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (7, 3, null, 'D');
INSERT INTO HOSPITAL_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (8, null, 2, 'D');
INSERT INTO HOSPITAL_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (9, null, 11, 'D');
INSERT INTO HOSPITAL_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (10, null, 30, 'D');
INSERT INTO HOSPITAL_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (11, null, 14, 'D');
INSERT INTO HOSPITAL_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (12, null, 5, 'D');
INSERT INTO HOSPITAL_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (13, 11, 1, 'D');
