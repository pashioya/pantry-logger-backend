INSERT INTO RECIPES(NAME, "TIME", DIFFICULTY, DESCRIPTION, INSTRUCTIONS)
VALUES ('Chili',
        'LONG',
        'MEDIUM',
        'This traditional chili recipe is just like mom used to make with ground beef, beans, and a simple homemade blend of chili seasonings.',
        'https://www.food.com/recipe/the-best-chili-you-will-ever-taste-73166');

INSERT INTO RECIPES(NAME, "TIME", DIFFICULTY, INSTRUCTIONS)
VALUES ('Sausage Lentil Soup',
        'MEDIUM',
        'MEDIUM',
        'https://www.food.com/recipe/spicy-sausage-lentil-soup-279315');

INSERT INTO RECIPES(NAME, "TIME", DIFFICULTY, DESCRIPTION, INSTRUCTIONS)
VALUES ('Spaghetti Carbonara',
        'MEDIUM',
        'EASY',
        'Comfort food. Spaghetti, cheese, bacon, creamy and filling. Sure to be a favorite for you. It is for me.',
        'https://www.food.com/recipe/the-best-chili-you-will-ever-taste-73166');

INSERT INTO RECIPES(NAME, "TIME", DIFFICULTY, INSTRUCTIONS)
VALUES ('Mac & Cheese',
        'QUICK',
        'EASY',
        'https://www.food.com/recipe/spicy-sausage-lentil-soup-279315');


insert into TAGS (NAME)
VALUES ('meat'),
       ('dairy'),
       ('nuts & seeds'),
       ('alcohol'),
       ('gluten'),
       ('soy'),
       ('eggs');


INSERT INTO INGREDIENTS(NAME)
VALUES ('onion'),
       ('garlic'),
       ('ground beef'),
       ('beef sirloin'),
       ('tomato'),
       ('dark beer'),
       ('green pepper'),
       ('tomato sauce'),
       ('paprika'),
       ('kidney beans'),
       ('black beans'),
       ('sour cream'),
       ('cheddar'),
       ('green onion'),
       ('carrot'),
       ('italian sausage'),
       ('chicken broth'),
       ('lentils'),
       ('spaghetti'),
       ('egg'),
       ('heavy cream'),
       ('shallots'),
       ('bacon'),
       ('parmesan'),
       ('macaroni'),
       ('butter'),
       ('flour'),
       ('milk'),
       ('cream');



INSERT INTO INGREDIENT_TAGS(INGREDIENT_ID, TAG_ID)
VALUES (3, 1),
       (4, 1),
       (6, 4),
       (18, 2),
       (19, 2),
       (22, 1),
       (23, 1),
       (25, 2),
       (25, 6),
       (26, 2),
       (28, 2),
       (29, 2);


INSERT INTO END_USERS(USERNAME, FIRST_NAME, LAST_NAME, EMAIL, CITY, STATE_REGION, ZIP, COUNTRY)
VALUES ('bleyHenr', 'Henry', 'Bley', 'henry.bley@student.kdg.be', 'Antwerp', 'Antwerp', 2018, 'BE'),
       ('ellmthom', 'Thomas', 'Ellmen...', 'thomas.elm@student.kdg.be', 'Antwerp', 'Antwerp', 2018, 'BE'),
       ('didenoah', 'Noah', 'diderich', 'noah.diderich@student.kdg.be', 'Antwerp', 'Antwerp', 2018, 'BE'),
       ('ashipaul', 'Paul', 'ashioya', 'paul.ashiyoa@student.kdg.be', 'Antwerp', 'Antwerp', 2018, 'BE');

INSERT INTO PANTRY_ZONES(USER_ID, NAME, MIN_TEMP, MAX_TEMP, MIN_HUM, MAX_HUM, MIN_BRIGHT, MAX_BRIGHT)
VALUES (1, 'Fridge', 3, 8, 100, 200, 100, 200),
       (1, 'Pantry', 10, 15, 100, 200, 100, 200);

INSERT INTO SENSOR_DATA(PANTRY_ZONE_ID, TIME_STAMP, SENSOR_VALUE, SENSOR_TYPE)
VALUES (1, '2022-11-23 00:00:00.000', 5, 'TEMPERATURE'),
       (1, '2022-11-23 01:00:00.000', 6, 'TEMPERATURE'),
       (1, '2022-11-23 02:00:00.000', 4, 'TEMPERATURE'),
       (1, '2022-11-23 00:00:00.000', 100, 'HUMIDITY'),
       (1, '2022-11-23 01:00:00.000', 110, 'HUMIDITY'),
       (1, '2022-11-23 02:00:00.000', 120, 'HUMIDITY'),
       (1, '2022-11-23 00:00:00.000', 100, 'BRIGHTNESS'),
       (1, '2022-11-23 01:00:00.000', 110, 'BRIGHTNESS'),
       (1, '2022-11-23 02:00:00.000', 120, 'BRIGHTNESS'),
       (2, '2022-11-23 00:00:00.000', 5, 'TEMPERATURE'),
       (2, '2022-11-23 01:00:00.000', 8, 'TEMPERATURE'),
       (2, '2022-11-23 02:00:00.000', 9, 'TEMPERATURE'),
       (2, '2022-11-23 00:00:00.000', 100, 'HUMIDITY'),
       (2, '2022-11-23 01:00:00.000', 110, 'HUMIDITY'),
       (2, '2022-11-23 02:00:00.000', 120, 'HUMIDITY'),
       (2, '2022-11-23 00:00:00.000', 100, 'BRIGHTNESS'),
       (2, '2022-11-23 01:00:00.000', 110, 'BRIGHTNESS'),
       (2, '2022-11-23 02:00:00.000', 120, 'BRIGHTNESS');