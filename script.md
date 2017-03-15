* Create Spring-Boot starter at https://start.spring.io/
  - meetup
  - demo
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
