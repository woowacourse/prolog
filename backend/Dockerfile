FROM openjdk:11 as build

WORKDIR /work

# 빌드 스테이지에 소스 코드 파일들 추가
COPY src ./src
COPY build.gradle .
COPY settings.gradle .
COPY PrologJavaStyle.xml .
COPY gradle ./gradle
COPY ./gradlew .

# 테스트를 제외하고 빌드 진행
RUN ./gradlew clean build -x test -x asciidoctor -x documentationTest -x acceptanceTest
RUN mv /work/build/libs/*[!-plain].jar ./app.jar


FROM eclipse-temurin:17-jre

COPY --from=build /work/app.jar .

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh ./wait-for-it.sh
RUN chmod +x ./wait-for-it.sh

CMD ./wait-for-it.sh -t 0 db:3306 -- java -Dspring.profiles.active=local -jar ./app.jar

