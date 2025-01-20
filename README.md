## Lancement du docker HADOOP

### 1. Création de l'image docker
```bash
cd ./deploy
docker buildx build -t hadoop-project-img . 

```
### 2. Lancement du container
```bash

cd .. (retourner à la racine du projet)*
docker run --name=tploix-tp-final --rm -p 8088:8088 -p 9870:9870 -p 9864:9864 -v ./data:/data -v ./jars:/jars -d hadoop-project-img
    
```

### 3. Accès au docker 
```bash
docker exec -it tploix-tp-final bash
```

### 4. Création des dossiers HDFS
```bash
hdfs dfs -mkdir -p /relationships
```

### 5. Copie des fichiers dans HDFS
```bash
hdfs dfs -put /data/relationships/data.txt /relationships/
```

### 6. Lancement du job1

D'abord lancer mvn clean install
puis copier le jar dans le dossier jars
```bash
hadoop jar /jars/hadoop-tp3-collaborativeFiltering-job1-1.0.jar /relationships/data.txt /output/job1

```

Résultat du job1
```bash
hdfs dfs -cat /output/job1/part-r-00001
```