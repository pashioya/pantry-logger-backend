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