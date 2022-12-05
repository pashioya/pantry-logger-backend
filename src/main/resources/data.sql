INSERT INTO RECIPES(NAME, "TIME", DIFFICULTY, DESCRIPTION, INSTRUCTIONS)
VALUES ('Chili',
        'LONG',
        'MEDIUM',
        'This traditional chili recipe is just like mom used to make with ground beef, beans, and a simple homemade blend of chili seasonings.',
        'https://www.food.com/recipe/award-winning-chili-105865');

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
        'https://www.food.com/recipe/rock-roll-spaghetti-carbonara-81668');

INSERT INTO RECIPES(NAME, "TIME", DIFFICULTY, INSTRUCTIONS)
VALUES ('Mac & Cheese',
        'QUICK',
        'EASY',
        'https://www.food.com/recipe/fannie-farmers-classic-baked-macaroni-cheese-135350');

INSERT INTO RECIPES(NAME, "TIME", DIFFICULTY, INSTRUCTIONS)
VALUES ('Chinese fried rice',
        'QUICK',
        'EASY',
        'https://www.food.com/recipe/chinese-fried-rice-38748');

INSERT INTO RECIPES(NAME, "TIME", DIFFICULTY, INSTRUCTIONS)
VALUES ('spaghetti with meatballs',
        'QUICK',
        'EASY',
        'Decadent strips of gluten with balls of meat on top, covered in meat juice'
        '');
INSERT INTO RECIPES(NAME, "TIME", DIFFICULTY, INSTRUCTIONS)
VALUES ('Pork chops',
        'MEDIUM',
        'MEDIUM',
        'Fry some pork chops');



INSERT INTO TAGS (NAME)
VALUES ('meat'),
       ('dairy'),
       ('nuts & seeds'),
       ('alcohol'),
       ('gluten'),
       ('soy'),
       ('eggs');


INSERT INTO INGREDIENTS (NAME)
VALUES ('onion'),
       ('garlic'),
       ('ground beef'),
       ('beef sirloin'),
       ('tomato'), -- 5
       ('dark beer'),
       ('green pepper'),
       ('tomato sauce'),
       ('paprika'),
       ('kidney beans'), --10
       ('black beans'),
       ('sour cream'),
       ('cheddar'),
       ('green onion'),
       ('carrot'), --15
       ('italian sausage'),
       ('chicken broth'),
       ('lentils'),
       ('spaghetti'),
       ('egg'), --20
       ('heavy cream'),
       ('shallots'),
       ('bacon'),
       ('parmesan'),
       ('macaroni'), --25
       ('butter'),
       ('flour'),
       ('milk'),
       ('cream'),
       ('jalapenos'), --30
       ('soy sauce'),
       ('sesame oil'),
       ('chicken breast'),
       ('peas'),
       ('rice'); --35

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

INSERT INTO END_USERS(USERNAME, EMAIL, PASSWORD)
VALUES ('tom', 'tom@gmail.com', '12'),
       ('noah', 'noah@gmail.com', '34'),
       ('henry', 'henry@gmail.com', '56'),
       ('paul', 'paul@gmail.com', '78');

INSERT INTO PANTRY_ZONES(USER_ID, NAME, MIN_TEMP, MAX_TEMP, MIN_HUM, MAX_HUM, MIN_BRIGHT, MAX_BRIGHT)
VALUES (1, 'Fridge', 3, 8, 100, 200, 100, 200),
       (1, 'Pantry', 10, 15, 100, 200, 100, 200),
       (2, 'Fridge', 10, 15, 100, 200, 100, 200);

INSERT INTO PRODUCTS(INGREDIENT_ID, PRODUCT_NAME, CODE, SIZE)
VALUES (1, '', '1', 1),
       (2, '', '2', 1),
       (3, '', '3', 200),
       (5, '', '4', 5),
       (9, '', '5', 3),
       (12, '', '6', 200),
       (15, '', '7', 3),
       (19, '', '8', 500),
       (20, '', '9', 6),
       (23, '', '10', 100),
       (26, '', '11', 100),
       (28, '', '12', 1000);

INSERT INTO PANTRY_ZONE_PRODUCTS(PRODUCT_ID, PANTRY_ZONE_ID, QUANTITY, AMOUNT_USED, DATE_ENTERED)
VALUES (1, 3, 1, 0, '2022-12-05'),
       (2, 3, 1, 0, '2022-12-05'),
       (3, 3, 1, 0, '2022-12-05'),
       (4, 3, 1, 0, '2022-12-05'),
       (5, 3, 1, 0, '2022-12-05'),
       (6, 3, 1, 0, '2022-12-05'),
       (7, 3, 1, 0, '2022-12-05'),
       (8, 3, 1, 0, '2022-12-05'),
       (9, 3, 1, 0, '2022-12-05'),
       (10, 3, 1, 0, '2022-12-05'),
       (11, 3, 1, 0, '2022-12-05'),
       (12, 3, 1, 0, '2022-12-05');

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

INSERT INTO RECIPE_INGREDIENTS(RECIPE_ID, INGREDIENT_ID, QUANTITY, OPTIONAL)
VALUES (1, 1, 2, FALSE),
       (1, 2, 3, TRUE),
       (1, 3, 1, FALSE),
       (1, 7, 1, FALSE),
       (1, 30, 1, TRUE),
       (1, 5, 14, FALSE),
       (1, 8, 16, FALSE),
       (1, 11, 28, FALSE),
       (1, 12, 1, TRUE),
       (1, 13, 1, TRUE),
       (1, 14, 1, TRUE),
       (2, 1, 1, FALSE),
       (2, 15, 0.75, FALSE),
       (2, 2, 1, TRUE),
       (2, 16, 1, FALSE),
       (2, 17, 5, TRUE),
       (2, 18, 1, FALSE),
       (2, 5, 1, FALSE),
       (3, 18, 1, FALSE),
       (3, 20, 3, FALSE),
       (3, 21, 1, FALSE),
       (3, 22, 3, TRUE),
       (3, 2, 3, TRUE),
       (3, 23, 0.5, TRUE),
       (3, 24, 0.5, FALSE),
       (4, 25, 8, FALSE),
       (4, 26, 4, TRUE),
       (4, 27, 4, FALSE),
       (4, 28, 1, FALSE),
       (4, 13, 2, FALSE),
       (5, 1, 0.75, FALSE),
       (5, 20, 1, FALSE),
       (5, 31, 3, TRUE),
       (5, 32, 3, TRUE),
       (5, 33, 8, FALSE),
       (5, 15, 0.5, TRUE),
       (5, 34, 0.5, TRUE),
       (5, 35, 4, FALSE),
       (5, 14, 4, FALSE);


INSERT INTO PRODUCTS(INGREDIENT_ID, PRODUCT_NAME, CODE, SIZE)
VALUES (19, 'Spaghetti Brand', '123', 200),
       (35, 'Rice Brand', '3253581087107', 1000),
       (28, 'Milk Brand', '125', 250);

INSERT INTO PANTRY_ZONE_PRODUCTS(PRODUCT_ID, PANTRY_ZONE_ID, QUANTITY, AMOUNT_USED, DATE_ENTERED)
VALUES (1, 2, 5, 0, '2022-11-25'),
       (2, 2, 2, 0, '2022-11-25'),
       (3, 1, 1, 0, '2022-11-25'),
       (1, 2, 1, 0, '2022-11-25');


INSERT INTO SHOPPING_LISTS(USER_ID)
VALUES (1);

INSERT INTO SHOPPING_LIST_INGREDIENTS(SHOPPING_LIST_ID, INGREDIENT_ID, AMOUNT)
VALUES (1, 19, 500),
       (1, 35, 250);


