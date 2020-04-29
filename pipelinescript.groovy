node{
    stage("Clone Git Repo"){
        git credentialsId: 'GIT_CREDENTIALS', url: 'https://github.com/yogmangela/spring-boot-mongo-docker.git'
    }
    
    stage("Compile Maven Clean Build"){
        def mavenHome = tool name: "Maven_3.6.3", type:"maven"   
        def mavenCMD = "${mavenHome}/bin/mvn clean package"
        
        sh "${mavenCMD}"
    }
    
    stage("Build Docker Image"){
        sh "docker build -t yogmicroservices/spring-boot-mongo ."
    }
    
    stage("Push Docker Image to dockerHub"){
        withCredentials([string(credentialsId: 'DOCKER_CREDENTIALS', variable: 'DOCKER_CREDENTIALS')]) {
        sh "docker login -u yogmicroservices -p ${DOCKER_CREDENTIALS}"
        }
        sh "docker push yogmicroservices/spring-boot-mongo"
    }
    
    stage("Deploy K8 Kubectl on Jenkins"){
        sh 'kubectl apply -f springBootMongo.yml'
    }
    
    stage("SonarQubeAnalysis"){
        withSonarQubeEnv(credentialsId: 'SONARQUBE_TOKEN') {
           sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.6.0.1398:sonar'
        }    
    }
}
