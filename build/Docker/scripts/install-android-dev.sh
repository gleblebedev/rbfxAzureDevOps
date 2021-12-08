#!/bin/bash

apt-get update -y
apt-get install -y wget openjdk-11-jdk android-sdk unzip

GRADLE_VERSION=$1
NDK_VERSION=$2
wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp
unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip
ln -s /opt/gradle/gradle-${GRADLE_VERSION} /opt/gradle/latest
GRADLE_HOME=/opt/gradle/latest
PATH=${GRADLE_HOME}/bin:${PATH}
rm -rf /tmp/gradle-${GRADLE_VERSION}-bin.zip

wget https://dl.google.com/android/repository/commandlinetools-linux-7583922_latest.zip -P /tmp
unzip -d /usr/lib/android-sdk/ /tmp/commandlinetools-linux-7583922_latest.zip
yes | /usr/lib/android-sdk/cmdline-tools/bin/sdkmanager --licenses --sdk_root=/usr/lib/android-sdk/
rm -rf /tmp/commandlinetools-linux-7583922_latest.zip

/usr/lib/android-sdk/cmdline-tools/bin/sdkmanager --sdk_root=/usr/lib/android-sdk/ --install "patcher;v4"
 
if [ ! -z "$NDK_VERSION" ]
then
 /usr/lib/android-sdk/cmdline-tools/bin/sdkmanager --sdk_root=/usr/lib/android-sdk/ --install "ndk;${NDK_VERSION}"
fi

chmod -R a+rw /usr/lib/android-sdk/
