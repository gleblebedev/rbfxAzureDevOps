apt-get update -y
apt-get install wget -y

wget https://packages.microsoft.com/config/ubuntu/20.04/packages-microsoft-prod.deb -O packages-microsoft-prod.deb
dpkg -i packages-microsoft-prod.deb
rm packages-microsoft-prod.deb

apt-get update -y
apt-get install apt-transport-https -y
apt-get update -y
apt-get install \
    gcc \
    g++ \
    ninja-build \
    make \
    uuid-dev \
    libgl-dev \
    libssl-dev \
    libxext-dev \
    openjdk-11-jdk \
    android-sdk \
    p7zip-full \
    dotnet-sdk-6.0 \
    ca-certificates \
    curl \
    file \
    ftp \
    git \
    gnupg \
    gss-ntlmssp \
    iproute2 \
    iputils-ping \
    locales \
    lsb-release \
    sudo \
    time \
    unzip \
    wget \
    zip \
    powershell \
    -y

CMAKE_VERSION=3.19.7
wget https://github.com/Kitware/CMake/releases/download/v${CMAKE_VERSION}/cmake-${CMAKE_VERSION}.tar.gz
tar -zxvf cmake-${CMAKE_VERSION}.tar.gz
cd cmake-${CMAKE_VERSION}/
./bootstrap
make
make install
cd ..
rm -rf cmake-${CMAKE_VERSION}

GRADLE_VERSION=6.5.1
wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp
unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip
ln -s /opt/gradle/gradle-${GRADLE_VERSION} /opt/gradle/latest
GRADLE_HOME=/opt/gradle/latest
PATH=${GRADLE_HOME}/bin:${PATH}
rm -rf /tmp/gradle-${GRADLE_VERSION}-bin.zip

wget https://dl.google.com/android/repository/commandlinetools-linux-7583922_latest.zip -P /tmp
unzip -d /usr/lib/android-sdk/ /tmp/commandlinetools-linux-7583922_latest.zip
yes | /usr/lib/android-sdk/cmdline-tools/bin/sdkmanager --licenses
rm -rf /tmp/commandlinetools-linux-7583922_latest.zip

curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash

AGENTRELEASE="$(curl -s https://api.github.com/repos/Microsoft/azure-pipelines-agent/releases/latest | grep -oP '"tag_name": "v\K(.*)(?=")')"
AGENTURL="https://vstsagentpackage.azureedge.net/agent/${AGENTRELEASE}/vsts-agent-linux-x64-${AGENTRELEASE}.tar.gz"
echo "Release "${AGENTRELEASE}" appears to be latest" 
echo "Downloading..."
wget ${AGENTURL}  -P /agent
cd /agent
tar zxvf vsts-agent-linux-x64-${AGENTRELEASE}.tar.gz
chmod -R 777 .
echo "extracted"
./bin/installdependencies.sh

apt-get autoremove -y
apt-get clean -y
