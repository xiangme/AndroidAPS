name: Build AndroidAPS APK

on:
  push:
    branches:
      - master
  pull_request:
    branches:
      - master
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest

    env:
      STORE_FILE: release.keystore
      KEYSTORE_PASSWORD: ${{ secrets.KEYSTORE_PASSWORD }}
      KEY_ALIAS: ${{ secrets.KEY_ALIAS }}
      KEY_PASSWORD: ${{ secrets.KEY_PASSWORD }}

    steps:
      - name: Checkout repo
        uses: actions/checkout@v4

      - name: Set up JDK
        uses: actions/setup-java@v4
        with:
          distribution: 'zulu'
          java-version: '17'

      - name: Decode keystore
        run: |
          echo "${{ secrets.KEYSTORE_BASE64 }}" | base64 -d > $STORE_FILE
          mkdir -p keystore
          mv $STORE_FILE keystore/$STORE_FILE

      - name: Grant execute permissions to Gradle wrapper
        run: chmod +x ./gradlew

      - name: Build APK
        run: ./gradlew assembleFullRelease

      - name: Upload APK artifact
        uses: actions/upload-artifact@v4
        with:
          name: AndroidAPS-FullRelease-APK
          path: app/build/outputs/apk/full/release/*.apk
