CREATE TABLE RECIPES
(
    ID           INTEGER AUTO_INCREMENT PRIMARY KEY,
    NAME         VARCHAR(255) NOT NULL,
    "TIME"       ENUM ('QUICK', 'MEDIUM', 'LONG'),
    DIFFICULTY   ENUM ('EASY', 'MEDIUM', 'HARD'),
    DESCRIPTION  TEXT,
    INSTRUCTIONS TEXT
);

CREATE TABLE INGREDIENTS
(
    ID   INTEGER AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL
);

CREATE TABLE RECIPE_INGREDIENTS
(
    RECIPE_ID     INTEGER NOT NULL,
    INGREDIENT_ID INTEGER NOT NULL,
    QUANTITY      INTEGER NOT NULL,
    OPTIONAL      BOOLEAN NOT NULL,

    CONSTRAINT FK_RECIPE_ID FOREIGN KEY (RECIPE_ID) REFERENCES RECIPES (ID),
    CONSTRAINT FK_INGREDIENT_ID FOREIGN KEY (INGREDIENT_ID) REFERENCES INGREDIENTS (ID)
);

CREATE TABLE TAGS
(
    ID   INTEGER AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL
);

CREATE TABLE INGREDIENT_TAGS
(
    INGREDIENT_ID INTEGER,
    TAG_ID        INTEGER,

    CONSTRAINT FK_INGREDIENT_TAG_INGREDIENT_ID FOREIGN KEY (INGREDIENT_ID) REFERENCES INGREDIENTS (ID),
    CONSTRAINT FK_INGREDIENT_TAG_TAG_ID FOREIGN KEY (TAG_ID) REFERENCES TAGS (ID)
);

CREATE TABLE END_USERS
(
    ID           INTEGER AUTO_INCREMENT PRIMARY KEY,
    USERNAME     VARCHAR(255) NOT NULL UNIQUE,
    EMAIL        VARCHAR(255) NOT NULL UNIQUE,
    PASSWORD     VARCHAR(255) NOT NULL,
    FIRST_NAME   VARCHAR(255),
    LAST_NAME    VARCHAR(255),
    CITY         VARCHAR(255),
    STATE_REGION VARCHAR(255),
    "ZIP"          VARCHAR(255),
    COUNTRY      VARCHAR(255)
);

CREATE TABLE PANTRY_ZONES
(
    ID              INTEGER AUTO_INCREMENT PRIMARY KEY,
    USER_ID         INTEGER      NOT NULL,
    NAME            VARCHAR(255) NOT NULL,
    MIN_TEMP        INTEGER      NOT NULL,
    MAX_TEMP        INTEGER      NOT NULL,
    MIN_HUM         INTEGER      NOT NULL,
    MAX_HUM         INTEGER      NOT NULL,
    MIN_BRIGHT      INTEGER      NOT NULL,
    MAX_BRIGHT      INTEGER      NOT NULL,

    CONSTRAINT FK_PANTRY_USER_ID FOREIGN KEY (USER_ID) REFERENCES END_USERS (ID)
);

CREATE TABLE SENSOR_DATA
(
    ID                  INTEGER AUTO_INCREMENT PRIMARY KEY,
    PANTRY_ZONE_ID      INTEGER NOT NULL,
    TIME_STAMP          TIMESTAMP,
    SENSOR_VALUE        INTEGER NOT NULL,
    SENSOR_TYPE ENUM ('HUMIDITY', 'TEMPERATURE', 'BRIGHTNESS'),

    CONSTRAINT FK_PANTRY_ID FOREIGN KEY (PANTRY_ZONE_ID) REFERENCES PANTRY_ZONES (ID)
);

CREATE TABLE USER_PREFERENCES
(
    USER_ID     INTEGER NOT NULL,
    TAG_ID      INTEGER NOT NULL,
    "LIKE"      BOOLEAN NOT NULL,

    CONSTRAINT FK_USER_ID FOREIGN KEY (USER_ID) REFERENCES END_USERS (ID),
    CONSTRAINT FK_TAG_ID FOREIGN KEY (TAG_ID) REFERENCES TAGS (ID)
);


CREATE TABLE PRODUCTS
(
    ID           INTEGER AUTO_INCREMENT PRIMARY KEY,
    INGREDIENT_ID     INTEGER NOT NULL,
    PRODUCT_NAME              VARCHAR(255) NOT NULL,
    CODE              VARCHAR(255) NOT NULL UNIQUE,
    SIZE              INTEGER NOT NULL,

    CONSTRAINT FK_ITEM_INGREDIENT_ID FOREIGN KEY (INGREDIENT_ID) REFERENCES INGREDIENTS (ID)
);

CREATE TABLE PANTRY_ZONE_PRODUCTS
(
    PRODUCT_ID          INTEGER NOT NULL,
    PANTRY_ZONE_ID      INTEGER NOT NULL,
    QUANTITY            INTEGER NOT NULL,
    AMOUNT_USED         INTEGER NOT NULL,
    DATE_ENTERED        DATE,

    CONSTRAINT FK_PANTRY_ZONE_ITEM_ID FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCTS (ID),
    CONSTRAINT FK_PANTRY_ZONE_INGREDIENT_PANTRY_ID FOREIGN KEY (PANTRY_ZONE_ID) REFERENCES PANTRY_ZONES (ID)
);

CREATE TABLE SHOPPING_LISTS
(
    ID      INTEGER AUTO_INCREMENT PRIMARY KEY,
    USER_ID INTEGER NOT NULL,

    CONSTRAINT FK_USER_SHOPPING_LIST FOREIGN KEY (USER_ID) REFERENCES END_USERS (ID)
);

CREATE TABLE SHOPPING_LIST_INGREDIENTS
(
    SHOPPING_LIST_ID    INTEGER NOT NULL,
    INGREDIENT_ID       INTEGER NOT NULL,
    AMOUNT              INTEGER NOT NULL,

    CONSTRAINT FK_SHOPPING_LIST_INGREDIENT FOREIGN KEY (INGREDIENT_ID) REFERENCES INGREDIENTS (ID),
    CONSTRAINT FK_SHOPPING_LIST FOREIGN KEY (SHOPPING_LIST_ID) REFERENCES SHOPPING_LISTS (ID)
);
