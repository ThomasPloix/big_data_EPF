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
puis copier le jar créer (/target) dans le dossier jars
```bash
hadoop jar /jars/hadoop-tp3-collaborativeFiltering-job1-1.0.jar /relationships/data.txt /output/job1

```

Résultat du job1
```bash
hdfs dfs -cat /output/job1/part-r-00001
```


### 7. Lancement du job2

D'abord lancer mvn clean install dans le répository hadoop-tp3-collaborativeFiltering-job2
puis copier le jar crée (/target) dans le dossier jars
```bash
hadoop jar /jars/hadoop-tp3-collaborativeFiltering-job2-1.0.jar /output/job1/part-r-00001 /output/job2

```

Résultat du job2
```bash
hdfs dfs -cat /output/job2/part-r-00000
hdfs dfs -cat /output/job2/part-r-00001
```


### 8. Lancement du job3

D'abord lancer mvn clean install dans le répository hadoop-tp3-collaborativeFiltering-job3
puis copier le jar crée (/target) dans le dossier jars

```bash
hadoop jar /jars/hadoop-tp3-collaborativeFiltering-job3-1.0.jar /output/job2 /output/job3

```

Résultat du job3
```bash
hdfs dfs -cat /output/job3/part-r-00000
hdfs dfs -cat /output/job3/part-r-00001
```
Exemple de résultat :
rachelrobertson 20240924220740(529), chandlerbing(17), adammitchell(10), irenemartin(10), maxtucker(10)
rebeccamcdonald 20240924220740(182), rachelgreen(15), chandlerbing(12), rossgeller(12), joeytribbiani(9)
rebeccataylor   20240924220740(277), joeytribbiani(9), mariapaige(8), mattbell(8), sallyquinn(8)
richardcarr     20240924220741(243), rossgeller(22), chandlerbing(16), joeytribbiani(16), rachelgreen(15)
richardellison  20240924220740(531), monicageller(16), katherinemanning(9), stephaniemcdonald(9), julianvance(8)
