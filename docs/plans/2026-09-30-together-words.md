# Together Words MVP implementation plan

Goal: Build a local Android app where a child and adult can practise four useful phrases or use a gentle balloon activity.

Architecture: A single Kotlin/Compose activity owns a small navigation state. Android resources provide English and Spanish text. A local preference stores an explicit language choice, and Android TextToSpeech speaks the displayed phrase.

1. Scaffold a minimal Android Compose project and verify its toolchain.
2. Write failing tests for the four navigation transitions and language selection.
3. Implement the state, resource backed UI, phrase cards, and touch balloon.
4. Write and run a UI flow test on an emulator if one is available.
5. Build the debug APK, run tests, and document launch and test commands.

The balloon is touch controlled, with no microphone, score, timer, completion requirement, network service, or account.
