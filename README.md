# Start the database and redis using docker

````shell
docker-compose up
````

### Links
- [Redis in 20 minutes](https://www.youtube.com/watch?v=QpBaA6B1U90)
- [Чай, Java, покодим? Многомодульное приложение на Spring+Java+Gradle+Redis](https://www.youtube.com/watch?v=0zb54cB8SP8)
- [Spring Boot | Spring Data Redis | Database | CRUD Example | JavaTechie](https://www.youtube.com/watch?v=oRGqCz8OLcM)

### Install redis on mac os

```shell
brew install redis
```

### Show all brew services
```shell
brew services list
```

### Stop all brew services
```shell
brew services stop --all
```

### Stop a brew service
```shell
brew services stop service-name
```

### Start redis
```shell
brew services start redis
```

### Start redis-cli on default host and port
```shell
redis-cli
```

### Start redis-cli on a specific host and port
```shell
redis-cli -h 127.0.0.1 -p 6379
```

### Add first key value
```shell
SET firstKey "Hello"
```

### Add second key value
```shell
SET firstKey "Hello"
```

### Get first key value
```shell
GET firstKey
```

### Get second key value
```shell
GET secondKey
```

### Remove second key value
```shell
DEL secondKey
```
