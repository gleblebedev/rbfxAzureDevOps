apt-get update -y
apt-get install wget -y

wget https://packages.microsoft.com/config/ubuntu/20.04/packages-microsoft-prod.deb -O packages-microsoft-prod.deb
dpkg -i packages-microsoft-prod.deb
rm packages-microsoft-prod.deb

apt-get update -y
apt-get install apt-transport-https -y
apt-get update -y
apt-get install \
    ccache \
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

curl -sL https://aka.ms/InstallAzureCLIDeb | bash

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
cd ..

apt-get autoremove -y
apt-get clean -y
