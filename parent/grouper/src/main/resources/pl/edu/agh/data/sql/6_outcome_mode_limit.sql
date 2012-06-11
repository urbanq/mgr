------------------------------------------------------------------------------------------------------------------------
-- Ograniczenia na tryb wypisu TW
------------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------
CREATE TABLE OUTCOME_MODE_LIMIT(
    ID INT PRIMARY KEY,        -- kod ograniczenia na tryb wypisu
    NAME VARCHAR(100) NOT NULL -- nazwa trybu wypisu
);
------------------------------------------------------------------------------------------------------------------------
INSERT INTO OUTCOME_MODE_LIMIT (ID, NAME) VALUES (1, 'zakończenie procesu terapeutycznego lub diagnostycznego');
INSERT INTO OUTCOME_MODE_LIMIT (ID, NAME) VALUES (2, 'skierowanie do dalszego leczenia w lecznictwie ambulatoryjnym');
INSERT INTO OUTCOME_MODE_LIMIT (ID, NAME) VALUES (3, 'skierowanie do dalszego leczenia w innym szpitalu');
INSERT INTO OUTCOME_MODE_LIMIT (ID, NAME) VALUES (4, 'skierowanie do dalszego leczenia w zakładzie opieki długoterminowej');
INSERT INTO OUTCOME_MODE_LIMIT (ID, NAME) VALUES (6, 'wypisanie na własne żądanie');
INSERT INTO OUTCOME_MODE_LIMIT (ID, NAME) VALUES (9, 'zgon pacjenta');