# **Next >> SonarQube with Jenkin integration:**

 
### Step1: Generate SQ Token:
Go to myAccount >> **User > My Account > Security**
>> Type "Token Pharase" in Generate Tocken then click ``Generate``. Make sure to copy and paste somewhere safe.
We will need this to access it from jenkins.
- my looks like this:
```
2fe3e155af11619cfce3ae06a6d985f274d0bae8
```

### Step2: Add SonarQube Token user Credentials 
- Under ``Snippet Generator >> Select withSonarQubeEnv:prepare SonarQube Scanner environment`` 
- Click Add>> Select Jenkins: and Add your secret credential.
- finally click 'Generate Pipeline Script' copy and paste the script into ''pipeline'' 

![Sonarqube Secret](/images/SQ_Secret.png)

### Step3: Install SonarScanner plugins:

- Go to >> jenkins>> Manage plugins >> search for ``Sonar Scanner`` under available tab. select and install it.


### Step4: Configure SonarScanner in Jenkins 
- Go to ``Manage Jenkins >> Configure System >>``
- Scroll down to ``SonarQube servers`` >> Add SonarQube >>

``name``: ``SonarQubeServer7.9``
``Server URL``: <<your public-IP of SonarQube>>:900 . ***use private IP*** if you are on same network.
``Server Auth``: 

![Sonarqube Server](/images/SQ_servers.png)

### Step5: Configure SonarQube Scanner installations
``Jenkins >> Global Tool Configuration`` 
![Sonarqube Global Tool](/images/SQ_GlobalTool.png)

### Step6: Finally build the job:

- Now you should have complete [pipeline script](/pipelinescript.groovy) : 
- Stages are self-defined and explanatory:
    stage("Clone Git Repo")
    stage("Compile Maven Clean Build")
    stage("Build Docker Image")
    stage("Push Docker Image to dockerHub")
    stage("Deploy K8 Kubectl on Jenkins")
    stage("SonarQubeAnalysis") 

![Sonarqube Analysis](/images/SQ_Access.png)


### Step7: Access SonarQube Analysis:

- Click OK as above screenshot. OR
- Login into SQ-server >> Project>>

![Sonarqube Analysis](/images/SQ_Project.png)

- Click on project to see Code review:

