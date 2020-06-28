FROM java:8

COPY *.jar /app.jar

CMD ["--server.port=10001"]

EXPOSE 10001

ENTRYPOINT ["java","-jar","/app.jar"]