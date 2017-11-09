## Zero-Config

* Create Spring-Boot starter at `https://start.spring.io/`
  * meetup
  * random-generator
  * actuator, web, devtools
* Create RandomNumberEndpoint
  * Set version to non-snapshot version
  * `@RestController`
  * `@RequestMapping(value = "/random", produces = "application/json")`
  * Add a UUID id
  * Return a map with "random" and "id"
* Run locally
  * `mvn spring-boot:run`
* Check with IDEA console
* Open in browser
  * `open http://localhost:8080/random`
* Show devtools by changing code
* Add f-m-p dependency (3.3-SNAPSHOT)
* Start minikube
  * `minikube start`
  * `minikube addon enable heapster`
  * `eval $(minikube docker-env)`
* Build docker image
  * `mvn package fabric8:build`
* Show create Docker image
  * `docker images`
* Run with docker
  * `docker run -it -p 8181:8080 jax2017/random-generator`
  * `open http://$(minikube ip):8181/random`
* Create deployment descriptors
  * `mvn fabric8:resource`
  * Show resources in target/class/META-INF/fabric8
  * Maybe try also with `mvn fabric8:resource -Dfabric8.profile=minimal`
* Apply them to Kubernetes
  * `mvn fabric8:apply`
* Show objects
  * with `kubectl`
  * `minikube dashboard`
* Edit pom.xml, add

```xml 
<properties>
  <fabric8.enricher.fmp-service.type>NodePort</fabric8.enricher.fmp-service.type>
</properties>

<enricher>
  <config>
    <fmp-service>
      <type>NodePort<type>
    </fmp-service>
  </config>
</enricher>
```

* Edit service type to NodePort
  * `kubectl patch svc random-generator -p '{"spec":{"type":"NodePort"}}'`
  * `kubectl describe svc random-generator`
  * `open http://$(minikube service --url random-generator):32156/random`
  * `curl -s $(minikube service --url random-generator)/random | jq .`

## Debug

* Run
  - `mvn fabric8:debug`
* Connect via IDE
* Set breakpoint in handler
* Change object on the fly

## Watch

* Check that execution mappings are set in the pom.xml:

```
<executions>
  <execution>
    <goals>
      <goal>resource</goal>
      <goal>build</goal>
    </goals>
  </execution>
</executions>
```

* Check that secret is set in application.properties:

```
spring.devtools.remote.secret=3b9c37f9-6777-4c9c-bc76-8f790752f7a6
```

* Start watch
  - `mvn fabric8:undeploy fabric8:watch`
  - Be sure the goals are bound
  - Show also live reload in browser
* Rescale:
  - `kubectl scale deployment random-generator --replicas=3`

```
 watch "curl -s $(minikube service --url random-generator)/random | jq ."
```

## Client

* New Java project
* Dependency: json-simple
* Configuration: Exclude enricher "fmp-service"
* Class jax2017.Client
  - UUID
  - main with
    * Endless thread and Thread.sleep 1000 
    * URL to service (by name)
    * JSONObject response via JSONParser and InputStreamReader on url
    * output response to "/random-data/responses.txt"
    * new FileWriter with append == true
    * response.writeJSONString(out)
* pom.xml: add maven exec plugin
* 
## Fragments:

* pv001-pv.yml

```yaml
spec:
  accessModes:
    - ReadWriteOnce
  capacity:
    storage: 20M
  hostPath:
    path: /data/pv001/
```

* random-client-deployment.yml

```yaml
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

```yaml
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
