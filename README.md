# android-letUS-server
안드로이드 렛츠 서버(spring)

> 스터디를 편리하게 구성해주고 공모전 정보를 공유할 수 있는 앱 letUS SERVER
<hr/>

## 개발도구
* Spring Boot - 어플리케이션 프레임워크
* Maven - 라이브러리 관리, 빌드
* Tomcat - WAS
* STS - IDE
* ORM - MyBatis
* MySQL - 저장소
<hr/>


## Maven dependency
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>1.3.0</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-mail</artifactId>
			<version>1.4.3.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>commons-fileupload</groupId>
			<artifactId>commons-fileupload</artifactId>
			<version>1.3.1</version>
		</dependency>
	</dependencies>
<hr/>

## 저장소
![image](https://user-images.githubusercontent.com/33171233/40887819-3e862392-6789-11e8-8ef3-a143aba27744.png)

