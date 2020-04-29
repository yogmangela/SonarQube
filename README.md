# **SonarQube 7.9**

## **SonarQube Installation**

SonarQube Installation and Configuration

- Pre-Requisite: [check PreReq here](https://docs.sonarqube.org/display/SONARqube71/Requirements)
- - java ; OpenJDK-8
- - at least 2GB RAM
- - AWS instance type: ``t2.medium`` should be ok
- - RDS ``postgress`` **mysql deprecated**

### Step1: Install Java:

```
apt-get update
apt-get install unzip software-properties-common wget default-jdk
```

### Step2: Install Postgres

```
apt-get install postgresql postgresql-contrib

su - postgres
psql

```
### Step3: Create postgress DB

```
CREATE USER sonarqube WITH PASSWORD '<<YOUR_PASSWORD>>';
CREATE DATABASE sonarqube OWNER sonarqube;
GRANT ALL PRIVILEGES ON DATABASE sonarqube TO sonarqube;
\q
```

- type exit to instaall SOnarQube as sudo / root user

### Step4: Insatalling ``SonarQube 8.2``

```
mkdir /downloads/sonarqube -p
cd /downloads/sonarqube
wget https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-7.9.1.zip
unzip sonarqube-7.9.1.zip
mv sonarqube-7.9.1 /opt/sonarqube
```

### Step5: Create user 'sonarqube' and Set permission 

```
adduser --system --no-create-home --group --disabled-login sonarqube
chown -R sonarqube:sonarqube /opt/sonarqube
```

### Step6: Edit sonar.sh configuration file

```
vi /opt/sonarqube/bin/linux-x86-64/sonar.sh
```

Change ``RUN_AS_USER=sonarqube`` we created above in step5.

### Step7: edit [sonar.properties](/sonar.properties) file:

```
sonar.jdbc.username=sonarqube
sonar.jdbc.password=kamisama123
sonar.jdbc.url=jdbc:postgresql://localhost/sonarqube
sonar.web.javaAdditionalOpts=-server
sonar.web.host=0.0.0.0
```

- create ``vi /etc/security/limits.d/99-sonarqube.conf`` and add:
```
sonarqube   -   nofile   65536
sonarqube   -   nproc    4096
```

- and finally edit ``vi /etc/sysctl.conf`` and add below at the end of the file:

```
vm.max_map_count=262144
fs.file-max=65536
```

### Step8: Restart / reboot your machine for configuration to take effect.

- Oncec restarted logback in as root user: sudo -i

- Start SonarQube:

```
/opt/sonarqube/bin/linux-x86-64/sonar.sh start
```

- To monitor SonarQube

```
tail -f /opt/sonarqube/logs/sonar.log
```
- you should get something like below output:

```
root@ip-172-31-22-74:~#  tail -f /opt/sonarqube/logs/sonar.log
2020.04.28 13:04:39 INFO  app[][o.s.a.SchedulerImpl] Waiting for Elasticsearch to be up and running
.
.
.
sonarqube/lib/jdbc/postgresql/postgresql-42.2.5.jar org.sonar.ce.app.CeServer /opt/sonarqube/temp/sq-process18186769582185103581properties
2020.04.28 13:05:32 INFO  app[][o.s.a.SchedulerImpl] Process[ce] is up
2020.04.28 13:05:32 INFO  app[][o.s.a.SchedulerImpl] SonarQube is up
```



### Step9: Login on SonarQube

- Copy and paste <<your-machine-public-ip>>:9000
- i.e 3.547.354.4:9000
- default User:admin & pwd: admin

## **Next >> SonarQube with Jenkin integration:**

