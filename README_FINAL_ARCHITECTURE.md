# GATE Prep Studio – Final Architecture

## Question bank
assets/questions/
- DA.json, CS.json, EC.json, EE.json, ME.json, CE.json: normal practice/mock bank
- GENERAL_APTITUDE.json: common aptitude
- DA/PYQ/DA_2024.json
- DA/PYQ/DA_2025.json (add when available)

PYQ cards are generated from filenames. Filenames are case-insensitive and must contain a four-digit year.
Each PYQ click loads that file directly, not from Room, so years never merge.

## Adding a new PYQ
Create: app/src/main/assets/questions/DA/PYQ/DA_2023.json
Rebuild. The 2023 card appears automatically.

## Important
The full mock uses the branch JSON plus common aptitude and takes up to the requested question count. To create a true 65-question mock, the branch bank must contain at least enough questions; the app does not invent or duplicate questions.
