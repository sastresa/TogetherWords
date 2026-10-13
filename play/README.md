# Google Play release kit

This directory contains the first release's English and Spanish store text,
graphics, and authentic emulator screenshots. The Android package ID is
`com.togetherwords`, which the owner has confirmed matches the Play Console
app. Google Play package IDs are permanent after the first upload.

## 1. Complete the account decisions

- Select **Aged 5 and under** as the target age group. The large controls,
  simple spoken phrases, and adult-supported activities were designed for very
  young children. Complete the Families declarations for this child audience.
- Use `sastresa@gmail.com` as the public support email in Play Console. Google
  requires one and shows it on the store listing.
- Decide which countries to offer the app in and whether it is free or paid.
  There are no in-app purchases.
- If this is a new personal developer account created after 13 November 2023,
  check whether Google's closed-testing gate applies before production release.

## 2. Check the privacy and content declarations

Use `https://github.com/sastresa/TogetherWords/blob/main/PRIVACY.md` for the
public privacy-policy URL. The same link is available inside **For adults**.
Keep the policy public before submitting the app. It includes the public support
email for direct contact.

Review each answer in the Play Console forms against the final app and its
dependencies. The current app:

| Console item | Evidence for the first release |
| --- | --- |
| Ads | None |
| App access | All screens are accessible without an account |
| Data safety | No developer server, analytics, ads, microphone permission, or audio recording. An adult's language choice is stored only on device on Android 12 and earlier. A tapped phrase is passed to the installed Android text-to-speech engine; review that engine's provider behavior when answering data-handling questions. |
| Target audience | Aged 5 and under, with an adult alongside the child. The adult guide does not make adults a separate target age group. |
| Content rating | Answer the Play questionnaire from the actual app content. For “Is the app primarily a news or educational product?”, select **Yes**: its main purpose is to teach children useful phrases and offer practical guidance to adults. The app also has external source links in the adult section. |
| Sensitive permissions | No internet, microphone, or other sensitive permission. AndroidX adds an internal `DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION` in the merged manifest; it does not grant access to personal data. Confirm the final merged manifest again before upload. |

The privacy policy and Data safety answers must stay consistent with future
releases. Check the [Google Play Families policy](https://support.google.com/googleplay/android-developer/answer/9893335)
and [Data safety guidance](https://support.google.com/googleplay/android-developer/answer/10787469)
while completing the forms.

## 3. Add the store listing

Create an **app** with English (United States) as the default listing and add a
Spanish (Spain) translation. Copy the field values from
[`listing/en-US.md`](listing/en-US.md) and [`listing/es-ES.md`](listing/es-ES.md).
In Store settings, choose the **Education** app category: the child-facing core
teaches useful phrases and a simple calming skill. The adult guidance supports
that learning. Choose only tags that clearly match the app's actual experience.
The marketing materials are:

| Locale | Icon | Feature graphic | Phone screenshots, in order |
| --- | --- | --- | --- |
| English | `graphics/icon.png` | `graphics/feature-en-US.png` | `screenshots/en-US/01-home.png` through `04-balloon.png` |
| Spanish | `graphics/icon.png` | `graphics/feature-es-ES.png` | `screenshots/es-ES/01-home.png` through `04-balloon.png` |

The screenshots are unaltered captures of the running app, resized through the
emulator's display setting to 1080 × 1920 before capture. Graphics are generated
from the existing app brand. To regenerate graphics, run
`python3 play/tools/generate_graphics.py` with Pillow installed. To refresh the
screenshots after UI changes, install a debug build on a stable emulator and run
`python3 play/tools/capture_screenshots.py <adb-serial>` with Pillow installed.

## 4. Create and safeguard an upload key

Create a private keystore in `.release/`; this directory and
`play-upload.properties` are ignored by Git. Keep a secure backup of the
keystore and passwords outside this repository. Do not reuse a work or shared
key. For example, run `keytool` interactively so its password is not placed in
your shell history:

```bash
mkdir -p .release
keytool -genkeypair -v -keystore .release/upload-key.jks \
  -alias words-and-pause-upload -keyalg RSA -keysize 4096 -validity 10000
cp play-upload.properties.example play-upload.properties
```

Edit the ignored properties file with the passwords entered in `keytool`. Then
build the signed Android App Bundle:

```bash
./gradlew spotlessCheck :app:testDebugUnitTest :app:lintDebug :app:bundleRelease
```

The output is `app/build/outputs/bundle/release/app-release.aab`. The build
fails with a clear message if release signing is missing. Enroll in Play App
Signing when configuring the first release; the local key is the upload key.
For every subsequent release, increase `versionCode` in `app/build.gradle.kts`.

## 5. Upload and review

Upload the signed `.aab` to the appropriate testing track first, check Play
Console's pre-review results, and test the Play-delivered build on a device.
Complete the remaining dashboard forms and release notes from the localized
listing files. The Play Console account owner must confirm final declarations
and start the production rollout.

Official references:

- [Create and set up an app](https://support.google.com/googleplay/android-developer/answer/9859152)
- [Store graphics and screenshots](https://support.google.com/googleplay/android-developer/answer/9866151)
- [Play App Signing](https://support.google.com/googleplay/android-developer/answer/9842756)
- [Testing rules for new personal accounts](https://support.google.com/googleplay/android-developer/answer/14151465)
