# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.1/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.1/maven-plugin/build-image.html)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.


##Login to docker desktop by sarveshojha69@gmail.com email id.


# Adminer and MSSQL Setup
## ###**Adminer and MSSQL Setup**<br>
This guide will help you set up Adminer and connect it to a Microsoft SQL Server (MSSQL) database using Docker.
## ###**Prerequisites**<br>
- Docker installed on your machine
- Basic knowledge of Docker commands
- A running instance of Microsoft SQL Server (MSSQL) in a Docker container
- Adminer Docker image
- Docker Compose installed (optional but recommended for easier management)
- A text editor to modify configuration files
- Network connectivity to the MSSQL container from the Adminer container
- Basic understanding of SQL Server authentication
- Basic knowledge of Docker networking concepts
- A terminal or command prompt to run Docker commands
- A web browser to access Adminer
## ###**Step 1: Pull the Docker Images**<br>    
Open your terminal and pull the necessary Docker images for Adminer and MSSQL:

run the docker-compose file to set up both Adminer and MSSQL containers. 
file named are already created `docker-compose.yml`



## ###**Adminer settings**<br>

To connect Adminer to the MSSQL database, use these details:

- **System**: MS SQL
- **Server**: mssql-db
- **Username**: sa
- **Password**: Manager@123
- **Port**: 1433

Adminer will connect to the MSSQL container using these credentials.

verify the connection by logging in to Adminer using the provided credentials. If everything is set up correctly, you should see the MSSQL database and its tables.
SELECT TOP (50) *
FROM [dbo].[user_hello]

## Hello web app

The responsive Hello app is served by Spring Boot at `/`. Run the application and open
`http://localhost:8080` on your computer. The greeting form calls the existing `/hello`
endpoint, remembers the last name on that device, and provides a local greeting if the
backend is unavailable.

To open it on another device on the same Wi-Fi network, use the computer's local IP
address, for example `http://192.168.1.20:8080`. The app adapts to phone, tablet, and
desktop screens. For browsers that support web app installation, serve it over HTTPS
(or use localhost) and choose **Install app** or the browser's **Add to Home Screen**
option.

## Deploying with Helm and Neon PostgreSQL

The Helm chart connects the app to Neon using `DB_URL`, `DB_USERNAME`, and a
Kubernetes Secret for `DB_PASSWORD`. Provide the JDBC URL and credentials in your
shell environment before running `scripts/start-helm-service.sh`:

```bash
export DB_URL='jdbc:postgresql://<Neon-host>/neondb?sslmode=require&channel_binding=require'
export DB_USERNAME='neondb_owner'
export DB_PASSWORD='<your-Neon-password>'
./scripts/start-helm-service.sh
```

The script creates or updates the `neon-db-credentials` Secret and passes only its
name to Helm. Do not put database passwords in chart values or commit them to source
control. Rotate any Neon password that has been shared or exposed.

## Neon postgresql
postgresql://neondb_owner:npg_Tm18AFPMqDwh@ep-lucky-lab-begnhfu0-pooler.c-9.us-east-2.aws.neon.tech/neondb?sslmode=require&channel_binding=require

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

GOOGLE_OAUTH_CLIENT_ID
GOOGLE_OAUTH_CLIENT_SECRET