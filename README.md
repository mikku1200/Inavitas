# Inavitas
Inavitas Sample Data Producer

Step 1 : Run Zookeeper on server

Step 2 : Run Kafka on server

Step 3 : Run application on server.
	1: Having JAR 
		Run jar by using "java -jar <Jar name>"
	2. Having Project
		Run Project using command "mvn spring-boot:run"
Step 4 : Check API is working correctly by doing get call (Optional) 
	 GET :: http://localhost:8080/publish/sampledata (Respose "SUCCESS")

Step 5 : Do a post call to produce csv data into kafka.

	POST :: http://localhost:8080/publish/sampledata?path=E:\\SampleData_.csv
	

Step 6 : Verify all data is published or not by running kafka api.

	bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic sample-data --from-beginning


Thanks
Mukesh Kumar
	
	
