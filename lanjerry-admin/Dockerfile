FROM java:8
LABEL maintainer="38046851@qq.com"
WORKDIR /opt/lanjerry
RUN echo "Asia/Shanghai" > /etc/timezone
RUN apt install fontconfig -y --force-yes  && apt install --fix-broken -y --force-yes
ADD target/lanjerry-admin-1.0-SNAPSHOT.jar lanjerry-admin-api.jar
EXPOSE 1000/tcp
EXPOSE 1000/udp
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar lanjerry-admin-api.jar --spring.profiles.active=prod"]