./gradlew clean
./gradlew assembleDebug
curl -F "token=${DEPLOY_GATE_API_KEY}" -F "file=@app/build/outputs/apk/app-debug.apk" -F "message=deployed" https://deploygate.com/api/users/kazuyuka76/apps