## Zero-Config

* Create Spring-Boot starter at https://start.spring.io/
  - meetup
  - random-generator
  - actuator, web, devtools
* Create RandomNumberEndpoint
  - `@RestController`
  - `@RequestMapping` (produces = "application/json")
* Run locally
  - `mvn spring-boot:run`
* Open in browser
  - `open http://localhost:8080/random`
* Show devtools by changing code
* Add f-m-p depenency (snapshot)
* Remove cached tools & start minikube
  - `rm -rf ~/.fabric8`
  - `minikube start`
* Build docker image
  - `mvn package fabric8:build`
* Show create Docker image
  - `eval $(minkube docker-env)`
  - `docker images`
* Run with docker
  - `docker run -it -p 8181:8080 meetup/demo`
  - `open http://$(minikube ip):8181/random`
* Create deployment descriptors
  - `mvn fabric8:resource`
  - Show resources in target/class/META-INF/fabric8
  - Maybe try also with `mvn fabric8:resource -Dfabric8.profile=minimal`
* Apply them to Kubernetes
  - `mvn fabric8:apply`
* Show object with kubectl
* Edit service type to NodePort
  - `kubectl patch svc random-generator -p '{"spec":{"type":"NodePort"}}'`
  - `kubectl describe svc random-generator`
  - `open http://$(minikube ip):32156/random`

## Watch

* Setup `exposecontroller`
  - `kubectl create -f http://central.maven.org/maven2/io/fabric8/devops/apps/exposecontroller/2.2.329/exposecontroller-2.2.329-kubernetes.yml`
* Add a token to `src/main/resources/application.properties`
  - `spring.devtools.remote.secret=1234`
* Start watch
  - `mvn package fabric8:resource fabric8:build fabric8:watch`
* Add unique identifier to code + compile
* Rescale:
  - `kubectl scale deployment random-generator --replicas=5`

```
while true
do
  curl http://192.168.99.100:30065/random
  echo
  sleep 1
done
```

## Debug

* Run
  - `mvn clean package fabric8:resource fabric8:build fabric8:debug`
* Connect via IDE
* Set breakpoint in handler
* Change object on the fly

## Client

* New Java project
* Dependency: json-simple
* Configuration: Exclude enricher "fmp-service"

## Fragments:

* pv001-pv.yml

```
spec:
  accessModes:
    - ReadWriteOnce
  capacity:
    storage: 20M
  hostPath:
    path: /data/pv001/
```

* random-client-deployment.yml

```
spec:
  replicas: 3
  template:
    spec:
      containers:
        - name: java-exec
          volumeMounts:
          - mountPath: "/random-data"
            name: random-data
      volumes:
        - name: random-data
          persistentVolumeClaim:
            claimName: random-data
```

* random-data-pvc.yml

```
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 10M
```


* Add a volume


## Snippets

```
open http://$(minikube ip):$(kubectl get svc random-generator -o jsonpath='{.spec.ports[*].nodePort}')/random
```
