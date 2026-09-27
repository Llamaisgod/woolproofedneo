# Wool Sound Dampener — setup instructions

## 1. Create your repo
Go to https://github.com/NeoForgeMDKs/MDK-1.21.1-ModDevGradle
Click the green **"Use this template"** button → **"Create a new repository"**.
Give it any name. Don't rename any folders or files in it.

## 2. Add these 3 files
In your new repo, use the **"Add file" → "Create new file"** button (top right
of the file list). When it asks for a file name, type the FULL path
(GitHub will create the folders automatically):

- `src/main/java/com/example/examplemod/mixin/SoundVolumeMixin.java`
  → paste the contents of the file with the same name from this zip.

- `src/main/resources/examplemod.mixins.json`
  → paste the contents of the file with the same name from this zip.

- `.github/workflows/build-jar.yml`
  → paste the contents of the file with the same name from this zip.

Commit each one directly to the `main` branch (the default option) after
pasting it in.

## 3. One manual edit — register the mixin
Open the existing file:
`src/main/templates/META-INF/neoforge.mods.toml`
(click "Edit" — the pencil icon)

Add this block anywhere in the file, then commit:

```
[[mixins]]
    config = "examplemod.mixins.json"
```

## 4. Let it build
Click the **"Actions"** tab at the top of your repo. You should see a
build running (it starts automatically after step 3's commit). Wait for
the green checkmark (takes a few minutes the first time).

Click into the finished run → scroll down to **"Artifacts"** →
download **wool-dampener-jar**. Unzip it — that's your mod jar.
Drop it in your server's `mods` folder.

## If the build fails (red X)
Click the failed run → click the "build" step → copy the red error text
and send it to me. The most likely failure point is the exact method
signature string in `SoundVolumeMixin.java` not matching this Minecraft
version's internal method names exactly — that's a one-line fix once I
see the actual error.

## What this actually does
- Detects vanilla wool blocks (`#minecraft:wool` tag) within 4 blocks of
  where a sound happens.
- If found, cuts that sound's volume to 25% (a 75% reduction) before it's
  sent to players. No client-side install needed — this all happens on
  the server before the sound packet goes out.
- Only affects sounds the server broadcasts (blocks, mobs, players,
  explosions, etc). Purely client-generated sounds (menu clicks, ambient
  cave noise, weather loops, jukebox music) are outside what a
  server-only mod can reach — there's no way around that in vanilla
  Minecraft's architecture.
