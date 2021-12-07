FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

COPY setup.sh /tmp/
RUN chmod +x /tmp/setup.sh \
  && /tmp/setup.sh \
  && rm -rf /tmp/setup.sh /var/lib/apt/lists/*
ENV GRADLE_HOME="/opt/gradle/latest"
ENV ANDROID_SDK_ROOT="/usr/lib/android-sdk/"
ENV PATH="/opt/gradle/latest/bin:${PATH}"

# CMD ["/azdo/runAgent.sh"]