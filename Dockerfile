FROM java:8
LABEL maintainer="nieqiurong@163.com"
WORKDIR /opt/lanjerry
RUN echo "Asia/Shanghai" > /etc/timezone
RUN apt install fontconfig -y --force-yes  && apt install --fix-broken -y --force-yes
ADD lanjerry-admin/target/lanjerry-admin-1.0-SNAPSHOT.jar admin.jar
EXPOSE 1000/tcp
EXPOSE 1000/udp
ENTRYPOINT ["java","-jar","admin.jar"]
