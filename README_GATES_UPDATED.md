# GATE Prep Studio – Updated Project

This version fixes the main question-selection problem and refreshes the student-facing UI.

## What changed

- General Aptitude questions are no longer mixed randomly into branch technical practice.
- GATE practice mock generation now builds a 30-question paper from:
  - 5 General Aptitude
  - 5 Engineering Mathematics
  - 20 branch-specific technical questions
- Topic practice is strict: if a topic has no seeded questions, the app shows an unavailable-test message instead of silently replacing it with random branch questions.
- PYQ queries are branch-specific and no longer include `branch='ALL'` aptitude questions.
- Added Engineering Mathematics question sets for CSE, ECE, EE, ME and CE.
- Added extra original GATE-style technical questions so non-CSE branches have a larger practice bank.
- Database version bumped to 4 with destructive migration so a fresh database is reseeded with the new question bank.
- Refreshed Home and Tests screens with a cleaner modern card-based UI, clearer GATE structure, progress cards, and practice-mode hierarchy.
- App name updated to **GATE Prep Studio**.
- Version updated to 1.2 / versionCode 3.

## Important note

The included question bank contains original GATE-style practice questions and a small set of existing project questions. It does not reproduce a large copyrighted official-paper archive.

## Build

Open the `GATEApp` folder in Android Studio and let Gradle sync. The project uses Android SDK 34, Java 8 compatibility, Room, Material Components, Navigation, RecyclerView and ViewBinding.

If you already have an older installed build, uninstall it once before testing this version, or clear app data. The Room database is intentionally configured with destructive migration so the updated seed bank is recreated.

## v1.3 test-flow fix
- Database version bumped to 5 so existing installs rebuild the question bank.
- Database `onOpen` repairs an empty/incomplete question bank.
- PYQ years include branch questions plus the seeded General Aptitude PYQs.
- Topic tests resolve display-topic aliases to seeded subjects and have a branch-only fallback if a topic has no exact seeded questions.
- Branch normalization accepts full branch names such as `CSE - Computer Science & IT`.
- Full Mock, PYQ Practice, and Topic Test all use the same `ExamActivity` flow with explicit test type routing.
