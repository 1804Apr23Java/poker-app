SET REFERENTIAL_INTEGRITY FALSE;

DELETE FROM CURRENTHANDS;
DELETE FROM STATS;
DELETE FROM GAMESTATES;
DELETE FROM USERS;

ALTER SEQUENCE SQ_USER_PK RESTART WITH 1;
ALTER SEQUENCE SQ_GAME_PK RESTART WITH 1;

INSERT INTO GAMESTATES (GAME_ID, DECK_STATE, CURRENT_TURN, POT, STATUS, TIME_LEFT, TABLE_STATE)
VALUES (SQ_GAME_PK.NEXTVAL, 'A', 1, 100, 0, 60, 'A');

INSERT INTO USERS (USER_ID, GAME_ID, EMAIL, PASSWORD, FNAME, LNAME, USERNAME, ISHOST)
VALUES (SQ_USER_PK.NEXTVAL, SQ_GAME_PK.CURRVAL, 'km@gmail.com', 'password', 'Kevin', 'Magno', 'km2018', FALSE);

INSERT INTO STATS (USER_ID, TOTAL_WINNINGS, WINS, LOSSES)
VALUES (SQ_USER_PK.CURRVAL, 100, 1, 0);

INSERT INTO CURRENTHANDS (USER_ID, HAND, WINNINGS, HAS_FOLDED, PLAYER_ORDER)
VALUES (SQ_USER_PK.CURRVAL, 'A', 100, FALSE, 1);

INSERT INTO USERS (USER_ID, GAME_ID, EMAIL, PASSWORD, FNAME, LNAME, USERNAME, ISHOST)
VALUES (SQ_USER_PK.NEXTVAL, SQ_GAME_PK.CURRVAL, 'id@gmail.com', 'password', 'Ian', 'Drummond', 'id2018', FALSE);

INSERT INTO GAMESTATES (GAME_ID, DECK_STATE, CURRENT_TURN, POT, STATUS, TIME_LEFT, TABLE_STATE)
VALUES (SQ_GAME_PK.NEXTVAL, '2', 2, 200, 0, 60, '2');

INSERT INTO STATS (USER_ID, TOTAL_WINNINGS, WINS, LOSSES)
VALUES (SQ_USER_PK.CURRVAL, 200, 2, 0);

INSERT INTO CURRENTHANDS (USER_ID, HAND, WINNINGS, HAS_FOLDED, PLAYER_ORDER)
VALUES (SQ_USER_PK.CURRVAL, '2', 200, FALSE, 2);



