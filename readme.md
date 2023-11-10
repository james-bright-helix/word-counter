# Word Counter Implementation

Implemented by James Baker

## Assumptions

* Null or blank words shouldn't be counted
* Case-insensitive
* Each call is atomic so don't count some words if others are invalid
* Can request count using non-English word and get the same result

## Microservice

This is a spring boot based microservice. It can be run with just a JRE and the jar.
This could be deployed on any cloud providers IAAS infrastructure or packaged as docker container and run on a PAAS,
e.g., on AWS fargate.
Due to the nature of the applications in memory counter it would not be possible to scale it horizontally without
introducing some common storage for the count. DR would similarly not be possible without some HA storage for the
count (e.g. redis or some db).
