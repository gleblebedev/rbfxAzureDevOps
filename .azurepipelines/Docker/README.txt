from docker folder

docker system prune -a 
export BUILDKIT_STEP_LOG_MAX_SIZE=1073741824
docker build --progress plain -t android_dev -f Dockerfile ./
docker images
docker run -it --mount type=bind,source=`git rev-parse --show-toplevel`,target=/rbfxAzureDevOps --rm android_dev bash

inside image

cd /rbfxAzureDevOps/android/java
gradle wrapper
gradle assembleRelease -Parm64-v8a --stacktrace --scan

pushing image

docker tag android_dev:latest gloomprojects.azurecr.io/pipelines/android_dev:latest
docker push gloomprojects.azurecr.io/pipelines/android_dev:latest