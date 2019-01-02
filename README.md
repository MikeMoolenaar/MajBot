# Majbot
This is a fork of the [original Majbot](https://github.com/majidkh/MajBot) by Majid Khosravi, Majbot is a chatbot written in Java.
This fork provides unit tests for a school assignment, some changes to the actual code were made to properly test everything.

The original README can be found in the `README_ORIGINAL.md`.

## Setup
Please follow this setup guide if you want to run the Unit tests, it assumes you have a recent version of Intelij IDEA and Java 10.

- Clone or download this repo and open the folder `Majbot` in Intelij.
- In the project-view, go to `src/java`, right-click on the `java` folder and mark the directory as `Sources Root`
- Go to `src/test`, right-click on the `test` folder and mark the directory as `Test Sources Root`.

- Go to `src/java/bot/lib`, select all the .jar files in there and right-click -> `Add as Library...`, then click OK.
- Click on the `BotTest` class in the `src/test/bot` folder, hold the cursor on the red marked `@Test` -> Alt+Enter(or click on the red lamp) -> `Add 'JUnit4' to classpath`.
- Make sure the Project language level is set to 10, you can do this by going to File -> Project structure... -> Project language level.

You should be all set!