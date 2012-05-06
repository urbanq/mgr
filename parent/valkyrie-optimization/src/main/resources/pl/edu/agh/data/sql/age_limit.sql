------------------------------------------------------------------------------------------------------------------------
-- Ograniczenia wiekowe
------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------
CREATE TABLE AGE_LIMIT(
    ID INT PRIMARY KEY, -- Kod ograniczenia wiekowego
    UNDER INT,          -- Poniżej (Górna granica <)
    OVER INT,           -- Powyżej Dolna granica >)
    TIME_UNIT CHAR(1) NOT NULL,  -- Jednostka
    FOREIGN KEY(TIME_UNIT) REFERENCES TIME_UNIT(ID)
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO AGE_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (1, 18, null, 'R');
INSERT INTO AGE_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (2, null, 17, 'R');
INSERT INTO AGE_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (3, null, 69, 'R');
INSERT INTO AGE_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (4, 70, null, 'R');
INSERT INTO AGE_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (5, 70, 17, 'R');
INSERT INTO AGE_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (6, 1, null, 'R');
INSERT INTO AGE_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (7, 18, 0, 'R');
INSERT INTO AGE_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (8, 26, null, 'T');
INSERT INTO AGE_LIMIT (ID, UNDER, OVER, TIME_UNIT) VALUES (9, 31, null, 'D');
