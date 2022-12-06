
This repo contains 2 projects:
* `api` app adds tasks to queue 
* `worker`app processes the tasks 

# Installation

## Run Redis
```
docker run -d --name redis -p 6379:6379 redis:7.0.5-alpine
```


## Running the api
```
cd api
npm i
npm run start:dev
```

## Running the worker
```
cd worker
npm i
npm run start:dev
```
