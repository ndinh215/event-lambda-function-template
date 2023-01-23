JAVA_HOME=$(/usr/libexec/java_home) mvn clean install -DskipTests

aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin 743949180496.dkr.ecr.us-west-2.amazonaws.com
docker build -t test-function .
docker tag test-function:latest 743949180496.dkr.ecr.us-west-2.amazonaws.com/test-function:0.1
docker push 743949180496.dkr.ecr.us-west-2.amazonaws.com/test-function:0.1
