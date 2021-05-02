printf 'Start building application'

printf '\n\nRunning tests\n'
mvn clean test

printf '\n\nBuilding application jar\n'
mvn clean install -DskipTests

printf '\n\nBuilding docker-image\n'
docker build -t muhammedshaheer/booking-service .

printf '\n\nRunning application\n'
docker-compose up -d

printf 'Application is successfully running on port 9071\n'