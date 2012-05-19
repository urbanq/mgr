------------------------------------------------------------------------------------------------------------------------
-- Ograniczenia na tryb przyjęcia TP
------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------
CREATE TABLE INCOME_MODE_LIMIT(
    ID INT PRIMARY KEY,        -- kod ograniczenia na tryb przyjecia
    NAME VARCHAR(155) NOT NULL -- nazwa trybu przyjecia
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO INCOME_MODE_LIMIT (ID, NAME) VALUES (1, 'Przyjęcie planowe na podstawie skierowania');
INSERT INTO INCOME_MODE_LIMIT (ID, NAME) VALUES (2, 'Przyjęcie w trybie nagłym w wyniku przekazania przez zespół ratownictwa medycznego');
INSERT INTO INCOME_MODE_LIMIT (ID, NAME) VALUES (3, 'Przyjęcie w trybie nagłym - inne przypadki');
INSERT INTO INCOME_MODE_LIMIT (ID, NAME) VALUES (4, 'Przyjęcie nowordka w wyniku porodu w tym szpitalu');
INSERT INTO INCOME_MODE_LIMIT (ID, NAME) VALUES (5, 'Przyjęcie planowe osoby, która skorzystała ze świadczeń opieki zdrowotnej poza kolejnością, zgodnie z uprawnieniami przysługującymi jej na podstawie ustawy');
INSERT INTO INCOME_MODE_LIMIT (ID, NAME) VALUES (6, 'Przyjęcie planowe');
INSERT INTO INCOME_MODE_LIMIT (ID, NAME) VALUES (7, 'Przyjęcie w trybie nagłym bez skierowania');
