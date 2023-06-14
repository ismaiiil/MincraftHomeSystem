#Build (download paper)
FROM amazoncorretto:17 AS build

ARG version=1.19.4
ARG build=550

RUN apt-get update -y && apt-get install -y curl jq
WORKDIR /opt/minecraft
COPY ./getpaperserver.sh /
RUN chmod +x /getpaperserver.sh
RUN /getpaperserver.sh ${version} ${build}

#Running
FROM amazoncorretto:17 AS running

ARG TARGETARCH
ARG RCON_CLI_VER=1.6.0
ARG MEMORY_SIZE=1G

#install gosu
RUN set -eux; \
 apt-get update; \
 apt-get install -y gosu; \
 rm -rf /var/lib/apt/lists/*; \
 gosu nobody true

WORKDIR /data
COPY --from=build /opt/minecraft/paper.jar /opt/minecraft/paperspigot.jar

ADD https://github.com/itzg/rcon-cli/releases/download/${RCON_CLI_VER}/rcon-cli_${RCON_CLI_VER}_linux_${TARGETARCH}.tar.gz /tmp/rcon-cli.tgz
RUN tar -x -C /usr/local/bin -f /tmp/rcon-cli.tgz rcon-cli && \
rm /tmp/rcon-cli.tgz

VOLUME "/data"

EXPOSE 25565/tcp
EXPOSE 25565/udp

ENV MEMORYSIZE=$MEMORY_SIZE

#aikar's java flags, see https://docs.papermc.io/paper/aikars-flags for more details




