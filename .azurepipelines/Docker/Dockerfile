FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

COPY scripts/install-cmake.sh /tmp/
RUN chmod +x /tmp/install-cmake.sh \
  && /tmp/install-cmake.sh 3.19.7 \
  && rm -rf /tmp/install-cmake.sh /var/lib/apt/lists/*
ENV PATH="/opt/cmake/bin:${PATH}"

COPY scripts/install-android-dev.sh /tmp/
RUN chmod +x /tmp/install-android-dev.sh \
  && /tmp/install-android-dev.sh 6.5.1 \
  && rm -rf /tmp/install-android-dev.sh /var/lib/apt/lists/*
ENV GRADLE_HOME="/opt/gradle/latest"
ENV ANDROID_SDK_ROOT="/usr/lib/android-sdk/"
ENV PATH="/opt/gradle/latest/bin:${PATH}"

COPY setup.sh /tmp/
RUN chmod +x /tmp/setup.sh \
  && /tmp/setup.sh \
  && rm -rf /tmp/setup.sh /var/lib/apt/lists/*
