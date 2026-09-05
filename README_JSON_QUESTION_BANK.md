# GATE Prep Studio — JSON Question Bank

The question bank is now maintained in:

`app/src/main/assets/questions/`

Current files:

- `DA.json` — Data Science & Artificial Intelligence
- `CS.json` — Computer Science & IT
- `EC.json` — Electronics & Communication
- `EE.json` — Electrical Engineering
- `ME.json` — Mechanical Engineering
- `CE.json` — Civil Engineering
- `GENERAL_APTITUDE.json` — common General Aptitude

## Adding questions

Add objects to the relevant JSON file using the same fields:

```json
{
  "questionText": "...",
  "optionA": "...",
  "optionB": "...",
  "optionC": "...",
  "optionD": "...",
  "correctAnswer": "B",
  "explanation": "...",
  "subject": "Machine Learning",
  "topic": "Classification",
  "branch": "DA",
  "difficulty": "Medium",
  "year": 2025,
  "questionType": "MCQ",
  "marks": 1,
  "isPYQ": false
}
```

`isPYQ` must only be `true` for a question that is actually a previous-year question. Original GATE-style practice questions should remain `false`.

After changing JSON files, rebuild/reinstall the app. The Room database is version 6 and is rebuilt on a fresh installation so the JSON bank is loaded.

## Runtime flow

JSON assets → `JsonQuestionLoader` → Room `questions` table → `QuestionDao` → Full Mock / PYQ / Topic Practice.

Topic practice never falls back to unrelated branch questions. If a topic has no matching records, it reports that the topic has no questions.
