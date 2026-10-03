# Adult guide and brand implementation plan

**Goal:** Apply the approved Words & Pause identity and add the bilingual adult guide with a discreet language setting.

**Architecture:** Keep the existing single-activity Jetpack Compose flow. Add an ADULTS destination to `AppFlow`, keep all guide copy in Android string resources, and use a language choice that resolves to the device language by default. Keep phrase speaking tied to the effective language. Use Android vector resources for the launcher mark and system splash.

**Tech stack:** Kotlin, Jetpack Compose Material 3, Android string resources, Android TextToSpeech, JUnit and Compose UI tests.

## Tasks

1. Add failing unit tests for adult navigation and device-language reset; run `:app:testDebugUnitTest` and confirm the expected failures.
2. Implement the flow and language choice model; rerun unit tests.
3. Update UI tests for Home → Adults, bilingual guide, language changes, and return to child activities; run instrumentation and confirm failure before implementing UI.
4. Add English and Spanish guide strings, adult screen, Home entry, and remove language controls from child screens. Match the approved palette and card layout.
5. Add adaptive launcher icon and system splash from the approved symbol, update app name and README.
6. Run unit tests, emulator UI tests, and debug build; inspect the installed app if an emulator is available.
